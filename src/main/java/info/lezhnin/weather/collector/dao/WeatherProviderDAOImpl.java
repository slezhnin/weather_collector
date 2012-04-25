package info.lezhnin.weather.collector.dao;

import info.lezhnin.weather.collector.domain.WeatherProvider;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Weather Provider DAO implementation.
 * <p/>
 * Date: 25.04.12
 *
 * @author Sergey Lezhnin <s.lezhnin@gmail.com>
 */
@Repository
public class WeatherProviderDAOImpl implements WeatherProviderDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public void saveWeatherProvider(WeatherProvider weatherProvider) {
        sessionFactory.getCurrentSession().save(weatherProvider);
    }

    public WeatherProvider findWeatherProvider(String name) {
        List<WeatherProvider> weatherProviders =
                sessionFactory.getCurrentSession().createQuery("from WeatherProvider where name = :name")
                        .setString("name", name).list();
        if (weatherProviders.size() > 0) {
            return weatherProviders.get(0);
        }
        return null;
    }
}
