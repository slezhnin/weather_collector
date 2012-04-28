package info.lezhnin.weather.collector.service;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import info.lezhnin.weather.collector.config.CityConfig;
import info.lezhnin.weather.collector.config.CityDataConfig;
import info.lezhnin.weather.collector.config.WeatherConfig;
import info.lezhnin.weather.collector.config.WeatherProviderConfig;
import info.lezhnin.weather.collector.domain.City;
import info.lezhnin.weather.collector.domain.CityData;
import info.lezhnin.weather.collector.domain.WeatherProvider;
import org.apache.log4j.Logger;
import org.joox.Match;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.annotation.Nullable;
import java.io.IOException;
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
    private CityService cityService;
    @Autowired
    private CityDataService cityDataService;

    @Transactional
    public void initData() {
        Logger.getLogger(getClass()).debug("Init. weather data.");
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
                    String provider = Strings.nullToEmpty(weatherProvider.getName());
                    if (provider.equalsIgnoreCase(cityDataConfig.getWeatherProvider().getName())) {
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
    }

    public List<String> listWeatherProviders() {
        return Lists.transform(weatherConfig.getProviders(), new Function<WeatherProviderConfig, String>() {
            public String apply(@Nullable WeatherProviderConfig input) {
                return input.getName();
            }
        });
    }

    public WeatherConfig getWeatherConfig() {
        return weatherConfig;
    }

    public WeatherProviderConfig getWeatherProviderConfig(String weatherProviderName) {
        WeatherProviderConfig weatherProviderConfig = null;
        for (WeatherProviderConfig wpc : weatherConfig.getProviders()) {
            if (weatherProviderName.equalsIgnoreCase(wpc.getName())) {
                weatherProviderConfig = wpc;
                break;
            }
        }
        return weatherProviderConfig;
    }

    @Nullable
    private String findPlaceId(CityDataConfig cityDataConfig) {
        return findPlaceId(cityDataConfig.getWeatherProvider().getName(), cityDataConfig.getName());
    }

    @Nullable
    public String findPlaceId(String weatherProviderName, String placeName) {
        WeatherProviderConfig weatherProviderConfig = getWeatherProviderConfig(weatherProviderName);
        Preconditions.checkArgument(weatherProviderConfig != null,
                String.format("Invalid weather provider name: %s", weatherProviderName));
        try {
            if (YAHOO.equalsIgnoreCase(weatherProviderConfig.getName())) {
                return getPlaceIdFromYahoo(weatherProviderConfig, placeName);
            } else if (YANDEX.equalsIgnoreCase(weatherProviderConfig.getName())) {
                return getPlaceIdFromYandex(weatherProviderConfig, placeName);
            }
        } catch (Exception e) {
            Logger.getLogger(getClass()).warn(String.format("Can't get id for place: %s", placeName), e);
        }
        return null;
    }

    private String getPlaceIdFromYahoo(WeatherProviderConfig weatherProviderConfig, String placeName)
            throws IOException, SAXException {
        String uri = String.format(weatherProviderConfig.getPlacesURI(), placeName, weatherProviderConfig.getId());
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

    private String getPlaceIdFromYandex(WeatherProviderConfig weatherProviderConfig, String placeName)
            throws IOException, SAXException {
        URL url = new URL(weatherProviderConfig.getPlacesURI());
        InputStream is = (InputStream) url.getContent();
        try {
            Document document = $(is).document();
            Match country = $(document).children("country");
            Match city = country.children("city").matchText(placeName);
            return city.attr("id");
        } finally {
            is.close();
        }
    }

}
