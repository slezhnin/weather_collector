package info.lezhnin.weather.collector.service;

import info.lezhnin.weather.collector.config.WeatherProviderConfig;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Weather config service interface.
 * <p/>
 * Date: 26.04.12
 *
 * @author Sergey Lezhnin <s.lezhnin@metamodel.ru>
 */
public interface WeatherConfigService {

    public static final String YAHOO = "yahoo";
    public static final String YANDEX = "yandex";

    void initData();

    List<String> listWeatherProviders();

    WeatherProviderConfig getWeatherProviderConfig(String weatherProviderName);

    String findPlaceId(String weatherProviderName, String placeName);

}
