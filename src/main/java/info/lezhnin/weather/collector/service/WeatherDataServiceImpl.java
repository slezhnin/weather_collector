package info.lezhnin.weather.collector.service;

import com.google.common.collect.Sets;
import info.lezhnin.weather.collector.dao.CityDAO;
import info.lezhnin.weather.collector.dao.WeatherDataDAO;
import info.lezhnin.weather.collector.dao.WeatherProviderDAO;
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
    private WeatherProviderDAO weatherProviderDAO;

    @Autowired
    private WeatherDataDAO weatherDataDAO;

    @Autowired
    private CityDAO cityDAO;

    @Transactional
    public boolean addWeatherData(City city, WeatherProvider weatherProvider, Date observationTime, String conditions,
            Integer temperature) {
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
        if (city.getWeatherData() == null) {
            city.setWeatherData(Sets.newHashSet(weatherData));
        } else {
            city.getWeatherData().add(weatherData);
        }
        cityDAO.saveCity(city);
        if (weatherProvider.getWeatherData() == null) {
            weatherProvider.setWeatherData(Sets.newHashSet(weatherData));
        } else {
            weatherProvider.getWeatherData().add(weatherData);
        }
        weatherProviderDAO.saveWeatherProvider(weatherProvider);
        return true;
    }

    @Transactional
    public List<WeatherData> list(@Nullable City city, @Nullable WeatherProvider weatherProvider,
            boolean chronologicalOrder) {
        return weatherDataDAO.list(city, weatherProvider, chronologicalOrder);
    }
}