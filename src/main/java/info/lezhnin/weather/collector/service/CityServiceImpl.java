package info.lezhnin.weather.collector.service;

import com.google.common.collect.Sets;
import info.lezhnin.weather.collector.dao.CityDAO;
import info.lezhnin.weather.collector.dao.CityDataDAO;
import info.lezhnin.weather.collector.domain.City;
import info.lezhnin.weather.collector.domain.CityData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nullable;
import java.util.List;

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

    @Autowired
    private CityDataDAO cityDataDAO;

    @Override
    public List<City> listCities() {
        return cityDAO.listCities();
    }

    @Nullable
    @Transactional
    public City findCity(CityData cityData, boolean createIfNotFound) {
        City city = cityDAO.findCity(cityData);
        if (city == null && createIfNotFound) {
            city = new City();
            if (city.getCityData() == null) {
                city.setCityData(Sets.newHashSet(cityData));
            } else {
                city.getCityData().add(cityData);
            }
            cityDAO.saveCity(city);
            cityData.setCity(city);
            cityDataDAO.saveCityData(cityData);
        }
        return city;
    }

    @Transactional
    public void addDataTo(City city, CityData cityData) {
        city.getCityData().add(cityData);
        cityDAO.saveCity(city);
        cityData.setCity(city);
        cityDataDAO.saveCityData(cityData);
    }
}
