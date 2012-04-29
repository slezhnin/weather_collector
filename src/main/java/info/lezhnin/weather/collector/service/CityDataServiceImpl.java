package info.lezhnin.weather.collector.service;

import com.google.common.collect.Sets;
import info.lezhnin.weather.collector.dao.CityDataDAO;
import info.lezhnin.weather.collector.dao.WeatherProviderDAO;
import info.lezhnin.weather.collector.domain.CityData;
import info.lezhnin.weather.collector.domain.WeatherProvider;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nullable;
import java.util.List;

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

    @Autowired
    private WeatherProviderDAO weatherProviderDAO;

    @Nullable
    @Transactional
    public CityData findCityData(WeatherProvider weatherProvider, String placeId, @Nullable String cityName,
            boolean createIfNotFound) {
        Logger.getLogger(getClass())
                .debug(String.format("Find CityData for weatherProvider=%s and placeId=%s", weatherProvider, placeId));
        CityData cityData = cityDataDAO.findCityData(weatherProvider, placeId);
        Logger.getLogger(getClass()).debug(String.format("Found CityData: %s", cityData));
        if (cityData == null && createIfNotFound) {
            cityData = new CityData();
            cityData.setWeatherProvider(weatherProvider);
            cityData.setPlaceId(placeId);
            cityData.setName(cityName);
            cityDataDAO.saveCityData(cityData);
            Logger.getLogger(getClass()).debug(String.format("Created CityData: %s", cityData));
            if (weatherProvider.getCityData() == null) {
                weatherProvider.setCityData(Sets.newHashSet(cityData));
            } else {
                weatherProvider.getCityData().add(cityData);
            }
            weatherProviderDAO.saveWeatherProvider(weatherProvider);
        }
        return cityData;
    }

    @Transactional
    public List<CityData> listCityData() {
        return cityDataDAO.listCityData();
    }

    @Transactional
    public CityData getCityData(Integer id) {
        return cityDataDAO.getCityData(id);
    }
}
