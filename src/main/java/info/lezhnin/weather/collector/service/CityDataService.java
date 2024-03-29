package info.lezhnin.weather.collector.service;

import info.lezhnin.weather.collector.domain.CityData;
import info.lezhnin.weather.collector.domain.WeatherProvider;

import javax.annotation.Nullable;
import java.util.List;

/**
 * City Data service interface.
 * <p/>
 * Date: 25.04.12
 *
 * @author Sergey Lezhnin <s.lezhnin@gmail.com>
 */
public interface CityDataService {
    @Nullable
    CityData findCityData(WeatherProvider weatherProvider, String placeId, @Nullable String cityName,
            boolean createIfNotFound);

    public List<CityData> listCityData();

    @Nullable
    public CityData getCityData(Integer id);

}
