package info.lezhnin.weather.collector.service;

import info.lezhnin.weather.collector.config.WeatherProviderConfig;
import info.lezhnin.weather.collector.domain.City;
import info.lezhnin.weather.collector.domain.CityData;
import info.lezhnin.weather.collector.domain.WeatherProvider;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatterBuilder;
import org.joox.Match;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import static org.joox.JOOX.$;

/**
 * Weather service. Collects data from weather data providers.
 * <p/>
 * Date: 26.04.12
 *
 * @author Sergey Lezhnin <s.lezhnin@gmail.com>
 */
@Service
public class WeatherServiceImpl implements WeatherService {

    @Autowired
    private WeatherConfigService weatherConfigService;

    @Autowired
    private WeatherDataService weatherDataService;

    @Autowired
    private CityService cityService;

    @Override
    @Transactional
    public void collect() {
        for (City city : cityService.listCities()) {
            for (CityData cityData : city.getCityData()) {
                WeatherProvider weatherProvider = cityData.getWeatherProvider();
                String providerName = weatherProvider.getName();
                try {
                    if (WeatherConfigService.YAHOO.equalsIgnoreCase(providerName)) {
                        collectFromYahoo(weatherProvider, city, cityData);
                    } else if (WeatherConfigService.YANDEX.equalsIgnoreCase(providerName)) {
                        collectFromYandex(weatherProvider, city, cityData);
                    }
                } catch (Exception e) {
                    Logger.getLogger(getClass()).warn(String
                            .format("Can't get weather data for place: %s from provider: %s", cityData.getName(),
                                    weatherProvider.getName()), e);
                }
            }
        }
    }

    private void collectFromYandex(WeatherProvider weatherProvider, City city, CityData cityData)
            throws IOException, SAXException {
        WeatherProviderConfig weatherProviderConfig =
                weatherConfigService.getWeatherProviderConfig(weatherProvider.getName());
        URL url = new URL(String.format(weatherProviderConfig.getForecastURI(), cityData.getPlaceId()));
        InputStream is = (InputStream) url.getContent();
        try {
            Document document = $(is).document();
            System.out.println($(document).attr("city"));
            System.out.println($(document).attr("country"));
            Match fact = $(document).child("fact");
            weatherDataService.addWeatherData(city, weatherProvider,
                    DateTime.parse(fact.child("observation_time").text()).toDate(), fact.child("weather_type").text(),
                    Integer.valueOf(fact.child("temperature").text()));
        } finally {
            is.close();
        }
    }

    private void collectFromYahoo(WeatherProvider weatherProvider, City city, CityData cityData)
            throws IOException, SAXException {
        WeatherProviderConfig weatherProviderConfig =
                weatherConfigService.getWeatherProviderConfig(weatherProvider.getName());
        String uri = String.format(weatherProviderConfig.getForecastURI(), cityData.getPlaceId());
        URL url = new URL(uri);
        InputStream is = (InputStream) url.getContent();
        try {
            Document document = $(is).document();
            Match channel = $(document).child("channel");
            Match location = channel.child("yweather:location");
            System.out.println(location.attr("city"));
            System.out.println(location.attr("country"));
            Match item = channel.child("item");
            Match condition = item.child("yweather:condition");
            weatherDataService.addWeatherData(city, weatherProvider, DateTime.parse(condition.attr("date"),
                    new DateTimeFormatterBuilder().appendDayOfWeekShortText().appendLiteral(", ").appendDayOfMonth(1)
                            .appendLiteral(' ').appendMonthOfYearShortText().appendLiteral(' ').appendYear(2, 4)
                            .appendLiteral(' ').appendHourOfHalfday(1).appendLiteral(':').appendMinuteOfHour(2)
                            .appendLiteral(' ').appendHalfdayOfDayText().appendLiteral(' ').appendTimeZoneShortName()
                            .toFormatter()).toDate(), condition.attr("text"), Integer.valueOf(condition.attr("temp")));
        } finally {
            is.close();
        }
    }

}
