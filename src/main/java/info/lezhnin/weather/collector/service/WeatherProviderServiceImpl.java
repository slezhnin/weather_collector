package info.lezhnin.weather.collector.service;

import info.lezhnin.weather.collector.dao.WeatherProviderDAO;
import info.lezhnin.weather.collector.domain.WeatherProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nullable;

/**
 * Weather Provider service implementation.
 * <p/>
 * Date: 24.04.12
 *
 * @author Sergey Lezhnin <s.lezhnin@gmail.com>
 */
@Service
public class WeatherProviderServiceImpl implements WeatherProviderService {

    @Autowired
    private WeatherProviderDAO weatherProviderDAO;

    @Nullable
    @Transactional
    public WeatherProvider findWeatherProvider(String name, boolean createIfNotFound) {
        WeatherProvider weatherProvider = weatherProviderDAO.findWeatherProvider(name);
        if (weatherProvider == null && createIfNotFound) {
            weatherProvider = new WeatherProvider();
            weatherProvider.setName(name);
            weatherProviderDAO.saveWeatherProvider(weatherProvider);
        }
        return weatherProvider;
    }
}