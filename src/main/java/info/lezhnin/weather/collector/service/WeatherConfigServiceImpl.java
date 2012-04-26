package info.lezhnin.weather.collector.service;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import info.lezhnin.weather.collector.config.CityConfig;
import info.lezhnin.weather.collector.config.CityDataConfig;
import info.lezhnin.weather.collector.config.WeatherConfig;
import info.lezhnin.weather.collector.config.WeatherProviderConfig;
import info.lezhnin.weather.collector.domain.City;
import info.lezhnin.weather.collector.domain.CityData;
import info.lezhnin.weather.collector.domain.WeatherProvider;
import org.joox.Match;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;

import javax.annotation.Nullable;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import static org.joox.JOOX.$;

/**
 * Weather configuration service implementation.
 * <p/>
 * Date: 26.04.12
 *
 * @author Sergey Lezhnin <s.lezhnin@gmail.com>
 */
@Service
public class WeatherConfigServiceImpl implements WeatherConfigService {

    @Autowired
    private WeatherConfig weatherConfig;
    @Autowired
    private WeatherProviderService weatherProviderService;
    @Autowired
    private WeatherDataService weatherDataService;
    @Autowired
    private CityService cityService;
    @Autowired
    private CityDataService cityDataService;

    @Transactional
    public void initData() {
        List<WeatherProvider> weatherProviders = Lists.newArrayList();
        for (WeatherProviderConfig weatherProviderConfig : weatherConfig.getProviders()) {
            WeatherProvider weatherProvider =
                    weatherProviderService.findWeatherProvider(weatherProviderConfig.getName(), true);
            weatherProviders.add(weatherProvider);
        }
        for (CityConfig cityConfig : weatherConfig.getCities()) {
            City city = null;
            for (CityDataConfig cityDataConfig : cityConfig.getCityData()) {
                if (Strings.isNullOrEmpty(cityDataConfig.getId())) {
                    cityDataConfig.setId(findPlaceId(cityDataConfig));
                }
                for (WeatherProvider weatherProvider : weatherProviders) {
                    CityData cityData = cityDataService
                            .findCityData(weatherProvider, cityDataConfig.getId(), cityDataConfig.getName(), true);
                    if (city == null) {
                        city = cityService.findCity(cityData, true);
                    } else {
                        cityService.addDataTo(city, cityData);
                    }
                }
            }
        }
    }

    @Nullable
    private String findPlaceId(CityDataConfig cityDataConfig) {
        WeatherProviderConfig weatherProviderConfig = cityDataConfig.getWeatherProvider();
        try {
            if (YAHOO.equalsIgnoreCase(weatherProviderConfig.getName())) {
                String uri = String.format(weatherProviderConfig.getPlacesURI(), cityDataConfig.getName(),
                        weatherProviderConfig.getId());
                URL url = new URL(uri);
                InputStream is = (InputStream) url.getContent();
                try {
                    Document document = $(is).document();
                    Match place = $(document).child("place");
                    return place.child("woeid").text();
                } finally {
                    is.close();
                }
            }
            if (YANDEX.equalsIgnoreCase(weatherProviderConfig.getName())) {
                URL url = new URL(weatherProviderConfig.getPlacesURI());
                InputStream is = (InputStream) url.getContent();
                try {
                    Document document = $(is).document();
                    Match country = $(document).children("country");
                    Match city = country.children("city").matchText(cityDataConfig.getName());
                    return city.attr("id");
                } finally {
                    is.close();
                }
            }
        } catch (Exception e) {
        }
        return null;
    }

}
