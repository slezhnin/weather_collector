package info.lezhnin.weather.collector.service;

import com.google.common.collect.Sets;
import info.lezhnin.weather.collector.dao.CityDataDAO;
import info.lezhnin.weather.collector.dao.WeatherDataDAO;
import info.lezhnin.weather.collector.domain.CityData;
import info.lezhnin.weather.collector.domain.WeatherData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nullable;
import java.util.Date;
import java.util.List;

/**
 * Weather Data service implementation.
 * <p/>
 * Date: 25.04.12
 *
 * @author Sergey Lezhnin <s.lezhnin@gmail.com>
 */
@Service
public class WeatherDataServiceImpl implements WeatherDataService {

    @Autowired
    private WeatherDataDAO weatherDataDAO;

    @Autowired
    private CityDataDAO cityDataDAO;

    @Transactional
    public boolean addWeatherData(CityData cityData, Date observationTime, String conditions, Integer temperature) {
        if (weatherDataDAO.findWeatherData(cityData, observationTime) != null) {
            return false;
        }
        WeatherData weatherData = new WeatherData();
        weatherData.setCityData(cityData);
        weatherData.setObservationTime(observationTime);
        weatherData.setConditions(conditions);
        weatherData.setTemperature(temperature);
        weatherDataDAO.saveWeatherData(weatherData);
        if (cityData.getWeatherData() == null) {
            cityData.setWeatherData(Sets.newHashSet(weatherData));
        } else {
            cityData.getWeatherData().add(weatherData);
        }
        cityDataDAO.saveCityData(cityData);
        return true;
    }

    @Transactional
    public List<WeatherData> list(@Nullable CityData cityData, boolean chronologicalOrder) {
        return weatherDataDAO.list(cityData, chronologicalOrder);
    }
}