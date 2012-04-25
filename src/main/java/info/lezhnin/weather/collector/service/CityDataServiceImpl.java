package info.lezhnin.weather.collector.service;

import com.google.common.collect.Sets;
import info.lezhnin.weather.collector.dao.CityDAO;
import info.lezhnin.weather.collector.dao.CityDataDAO;
import info.lezhnin.weather.collector.domain.City;
import info.lezhnin.weather.collector.domain.CityData;
import info.lezhnin.weather.collector.domain.WeatherProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nullable;

/**
 * City Data service implementation.
 * <p/>
 * Date: 24.04.12
 *
 * @author Sergey Lezhnin <s.lezhnin@gmail.com>
 */
@Service
public class CityDataServiceImpl implements CityDataService {

    @Autowired
    private CityDataDAO cityDataDAO;

    @Nullable
    @Transactional
    public CityData findCityData(WeatherProvider weatherProvider, String placeId, @Nullable String cityName,
            boolean createIfNotFound) {
        CityData cityData = cityDataDAO.findCityData(weatherProvider, placeId);
        if (cityData == null && createIfNotFound) {
            cityData = new CityData();
            cityData.setWeatherProvider(weatherProvider);
            cityData.setPlaceId(placeId);
            cityData.setName(cityName);
            cityDataDAO.saveCityData(cityData);
        }
        return cityData;
    }
}
