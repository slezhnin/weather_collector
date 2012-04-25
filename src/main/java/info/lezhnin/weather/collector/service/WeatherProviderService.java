package info.lezhnin.weather.collector.service;

import info.lezhnin.weather.collector.domain.WeatherProvider;

import javax.annotation.Nullable;

/**
 * Weather Provider service interface.
 * <p/>
 * Date: 24.04.12
 *
 * @author Sergey Lezhnin <s.lezhnin@gmail.com>
 */
public interface WeatherProviderService {
    @Nullable
    WeatherProvider findWeatherProvider(String name, boolean createIfNotFound);
}
