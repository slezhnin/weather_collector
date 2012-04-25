package info.lezhnin.weather.collector.service;

import info.lezhnin.weather.collector.domain.City;
import info.lezhnin.weather.collector.domain.WeatherData;
import info.lezhnin.weather.collector.domain.WeatherProvider;

import javax.annotation.Nullable;
import java.util.Date;
import java.util.List;

/**
 * Weather Data service interface.
 * <p/>
 * Date: 25.04.12
 *
 * @author Sergey Lezhnin <s.lezhnin@gmail.com>
 */
public interface WeatherDataService {

    boolean addWeatherData(City city, WeatherProvider weatherProvider, Date observationTime, String conditions, Integer temperature);

    List<WeatherData> list(@Nullable City city, @Nullable WeatherProvider weatherProvider, boolean chronologicalOrder);

}
