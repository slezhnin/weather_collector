package info.lezhnin.weather.collector.service;

import com.google.common.collect.Sets;
import info.lezhnin.weather.collector.dao.CityDAO;
import info.lezhnin.weather.collector.domain.City;
import info.lezhnin.weather.collector.domain.CityData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nullable;

/**
 * City service implementation.
 * <p/>
 * Date: 24.04.12
 *
 * @author Sergey Lezhnin <s.lezhnin@gmail.com>
 */
@Service
public class CityServiceImpl implements CityService {

    @Autowired
    private CityDAO cityDAO;

    @Nullable
    @Transactional
    public City findCity(CityData cityData, boolean createIfNotFound) {
        City city = cityDAO.findCity(cityData);
        if (city == null && createIfNotFound) {
            city = new City();
            city.setCityData(Sets.newHashSet(cityData));
            cityDAO.saveCity(city);
        }
        return city;
    }

    @Transactional
    public void addDataTo(City city, CityData cityData) {
        city.getCityData().add(cityData);
        cityDAO.saveCity(city);
    }
}
