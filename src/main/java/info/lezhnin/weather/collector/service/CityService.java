package info.lezhnin.weather.collector.service;

import info.lezhnin.weather.collector.domain.City;
import info.lezhnin.weather.collector.domain.CityData;

import javax.annotation.Nullable;

/**
 * City service interface.
 * <p/>
 * Date: 24.04.12
 *
 * @author Sergey Lezhnin <s.lezhnin@gmail.com>
 */
public interface CityService {
    @Nullable
    City findCity(CityData cityData, boolean createIfNotFound);
}
