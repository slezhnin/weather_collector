package info.lezhnin.weather.collector.service;

import info.lezhnin.weather.collector.domain.City;
import info.lezhnin.weather.collector.domain.CityData;

import javax.annotation.Nullable;
import java.util.List;

/**
 * City service interface.
 * <p/>
 * Date: 24.04.12
 *
 * @author Sergey Lezhnin <s.lezhnin@gmail.com>
 */
public interface CityService {
    List<City> listCities();
    @Nullable
    City findCity(CityData cityData, boolean createIfNotFound);
    void addDataTo(City city, CityData cityData);
}
