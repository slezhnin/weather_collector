package info.lezhnin.weather.collector.dao;

import info.lezhnin.weather.collector.domain.City;
import info.lezhnin.weather.collector.domain.CityData;

import javax.annotation.Nullable;
import java.util.List;

/**
 * City DAO interface.
 * <p/>
 * Date: 24.04.12
 *
 * @author Sergey Lezhnin <s.lezhnin@gmail.com>
 */
public interface CityDAO {

    public void saveCity(City city);

    @Nullable
    public City findCity(CityData cityData);
}