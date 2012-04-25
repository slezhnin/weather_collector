package info.lezhnin.weather.collector.dao;

import info.lezhnin.weather.collector.domain.CityData;
import info.lezhnin.weather.collector.domain.WeatherProvider;

import javax.annotation.Nullable;

/**
 * City Data DAO interface.
 * <p/>
 * Date: 25.04.12
 *
 * @author Sergey Lezhnin <s.lezhnin@gmail.com>
 */
public interface CityDataDAO {

    public void saveCityData(CityData cityData);

    @Nullable
    public CityData findCityData(WeatherProvider weatherProvider, String placeId);
}
