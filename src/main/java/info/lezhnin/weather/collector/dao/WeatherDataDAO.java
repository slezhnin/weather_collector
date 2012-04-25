package info.lezhnin.weather.collector.dao;

import info.lezhnin.weather.collector.domain.City;
import info.lezhnin.weather.collector.domain.WeatherData;
import info.lezhnin.weather.collector.domain.WeatherProvider;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Weather Data DAO interface.
 * <p/>
 * Date: 25.04.12
 *
 * @author Sergey Lezhnin <s.lezhnin@gmail.com>
 */
public interface WeatherDataDAO {

    void saveWeatherData(WeatherData weatherData);

    List<WeatherData> list(@Nullable City city, @Nullable WeatherProvider weatherProvider, boolean chronologicalOrder);

}
