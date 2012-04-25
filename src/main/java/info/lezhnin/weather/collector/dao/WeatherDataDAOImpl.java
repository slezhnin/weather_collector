package info.lezhnin.weather.collector.dao;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import info.lezhnin.weather.collector.domain.City;
import info.lezhnin.weather.collector.domain.WeatherData;
import info.lezhnin.weather.collector.domain.WeatherProvider;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Weather Data DAO implementation.
 * <p/>
 * Date: 25.04.12
 *
 * @author Sergey Lezhnin <s.lezhnin@gmail.com>
 */
@Repository
public class WeatherDataDAOImpl implements WeatherDataDAO {

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

    public void saveWeatherData(WeatherData weatherData) {
        sessionFactory.getCurrentSession().save(weatherData);
    }

    public List<WeatherData> list(@Nullable City city, @Nullable WeatherProvider weatherProvider,
            boolean chronologicalOrder) {
        StringBuilder queryBuilder = new StringBuilder("from WeatherData");
        if (city != null || weatherProvider != null) {
            List<String> conditions = Lists.newArrayList();
            if (city != null) {
                conditions.add("city = :city");
            }
            if (weatherProvider != null) {
                conditions.add("weatherProvider = :weatherProvider");
            }
            queryBuilder.append(" where ").append(Joiner.on(" and ").join(conditions));
        }
        queryBuilder.append(" order by observationTime");
        if (!chronologicalOrder) {
            queryBuilder.append(" desc");
        }
        Query query = sessionFactory.getCurrentSession().createQuery(queryBuilder.toString());
        if (city != null) {
            query.setEntity("city", city);
        }
        if (weatherProvider != null) {
            query.setEntity("weatherProvider", weatherProvider);
        }
        return query.list();
    }
}
