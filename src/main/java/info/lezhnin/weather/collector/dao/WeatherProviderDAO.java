package info.lezhnin.weather.collector.dao;

import info.lezhnin.weather.collector.domain.WeatherProvider;

/**
 * Weather Provider DAO interface.
 * <p/>
 * Date: 25.04.12
 *
 * @author Sergey Lezhnin <s.lezhnin@gmail.com>
 */
public interface WeatherProviderDAO {
    void saveWeatherProvider(WeatherProvider weatherProvider);

    WeatherProvider findWeatherProvider(String name);
}
