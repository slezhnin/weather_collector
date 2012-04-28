package info.lezhnin.weather.collector.dao;

import info.lezhnin.weather.collector.domain.CityData;
import info.lezhnin.weather.collector.domain.WeatherData;

import javax.annotation.Nullable;
import java.util.Date;
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

    WeatherData findWeatherData(CityData cityData, Date observationTime);

    List<WeatherData> list(@Nullable CityData cityData, boolean chronologicalOrder);

}
