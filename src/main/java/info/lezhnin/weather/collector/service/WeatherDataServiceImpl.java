package info.lezhnin.weather.collector.service;

import info.lezhnin.weather.collector.dao.WeatherDataDAO;
import info.lezhnin.weather.collector.domain.City;
import info.lezhnin.weather.collector.domain.WeatherData;
import info.lezhnin.weather.collector.domain.WeatherProvider;
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

    @Transactional
    public boolean addWeatherData(City city, WeatherProvider weatherProvider, Date observationTime, String conditions, Integer temperature) {
        if (weatherDataDAO.findWeatherData(city, weatherProvider, observationTime) != null) {
            return false;
        }
        WeatherData weatherData = new WeatherData();
        weatherData.setCity(city);
        weatherData.setWeatherProvider(weatherProvider);
        weatherData.setObservationTime(observationTime);
        weatherData.setConditions(conditions);
        weatherData.setTemperature(temperature);
        weatherDataDAO.saveWeatherData(weatherData);
        return true;
    }

    @Transactional
    public List<WeatherData> list(@Nullable City city, @Nullable WeatherProvider weatherProvider, boolean chronologicalOrder) {
        return weatherDataDAO.list(city, weatherProvider, chronologicalOrder);
    }
}