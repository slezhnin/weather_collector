package info.lezhnin.weather.collector.service;

import info.lezhnin.util.TimeZoneUtil;
import info.lezhnin.weather.collector.config.WeatherProviderConfig;
import info.lezhnin.weather.collector.domain.City;
import info.lezhnin.weather.collector.domain.CityData;
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
import java.util.Date;

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

    @Transactional
    public void collect() {
        for (City city : cityService.listCities()) {
            for (CityData cityData : city.getCityData()) {
                String weatherProviderName = cityData.getWeatherProvider().getName();
                try {
                    if (WeatherConfigService.YAHOO.equalsIgnoreCase(weatherProviderName)) {
                        collectFromYahoo(weatherProviderName, cityData);
                    } else if (WeatherConfigService.YANDEX.equalsIgnoreCase(weatherProviderName)) {
                        collectFromYandex(weatherProviderName, cityData);
                    }
                } catch (Exception e) {
                    Logger.getLogger(getClass()).warn(String
                            .format("Can't get weather data for place: %s from provider: %s", cityData.getName(),
                                    weatherProviderName), e);
                }
            }
        }
    }

    private void collectFromYandex(String weatherProviderName, CityData cityData) throws IOException, SAXException {
        WeatherProviderConfig weatherProviderConfig =
                weatherConfigService.getWeatherProviderConfig(weatherProviderName);
        URL url = new URL(String.format(weatherProviderConfig.getForecastURI(), cityData.getPlaceId()));
        InputStream is = (InputStream) url.getContent();
        try {
            Document document = $(is).document();
            Match fact = $(document).child("fact");
            Date observationTime = DateTime.parse(fact.child("observation_time").text()).toDate();
            String conditions = fact.child("weather_type").text();
            Integer temperature = Integer.valueOf(fact.child("temperature").text());
            if (weatherDataService.addWeatherData(cityData, observationTime, conditions, temperature)) {
                Logger.getLogger(getClass()).debug(String
                        .format("Added weather data: %s (%s) %s %s at %s", cityData.getName(), weatherProviderName,
                                conditions, temperature, observationTime));
            }
        } finally {
            is.close();
        }
    }

    private void collectFromYahoo(String weatherProviderName, CityData cityData) throws IOException, SAXException {
        WeatherProviderConfig weatherProviderConfig =
                weatherConfigService.getWeatherProviderConfig(weatherProviderName);
        String uri = String.format(weatherProviderConfig.getForecastURI(), cityData.getPlaceId());
        URL url = new URL(uri);
        InputStream is = (InputStream) url.getContent();
        try {
            Document document = $(is).document();
            Match channel = $(document).child("channel");
            Match location = channel.child("yweather:location");
            Match item = channel.child("item");
            Match condition = item.child("yweather:condition");
            Date observationTime = DateTime.parse(condition.attr("date"),
                    new DateTimeFormatterBuilder().appendDayOfWeekShortText().appendLiteral(", ").appendDayOfMonth(1)
                            .appendLiteral(' ').appendMonthOfYearShortText().appendLiteral(' ').appendYear(2, 4)
                            .appendLiteral(' ').appendHourOfDay(1).appendLiteral(':').appendMinuteOfHour(2)
                            .appendLiteral(' ').appendHalfdayOfDayText().appendLiteral(' ')
                            .appendTimeZoneShortName(TimeZoneUtil.getAbbreviatedNameToTimeZoneMap()).toFormatter())
                    .toDate();
            String conditions = condition.attr("text");
            Integer temperature = Integer.valueOf(condition.attr("temp"));
            if (weatherDataService.addWeatherData(cityData, observationTime, conditions, temperature)) {
                Logger.getLogger(getClass()).debug(String
                        .format("Added weather data: %s (%s) %s %s at %s", cityData.getName(), weatherProviderName,
                                conditions, temperature, observationTime));
            }
        } finally {
            is.close();
        }
    }

}
