package info.lezhnin.weather.collector.service;

import info.lezhnin.weather.collector.domain.CityData;
import info.lezhnin.weather.collector.domain.WeatherData;

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

    boolean addWeatherData(CityData cityData, Date observationTime, String conditions, Integer temperature);

    List<WeatherData> list(@Nullable CityData cityData, @Nullable Integer daysBack, boolean chronologicalOrder);

}
