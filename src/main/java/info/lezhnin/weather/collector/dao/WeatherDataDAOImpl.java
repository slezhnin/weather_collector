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
import java.util.Date;
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

    public void saveWeatherData(WeatherData weatherData) {
        sessionFactory.getCurrentSession().save(weatherData);
    }

    @Nullable
    public WeatherData findWeatherData(City city, WeatherProvider weatherProvider, Date observationTime) {
        List<WeatherData> weatherData =
                sessionFactory.getCurrentSession().createQuery("from WeatherData where city = :city and weatherProvider = :weatherProvider and observationTime = :observationTime")
                        .setEntity("city", city).setEntity("weatherProvider", weatherProvider).setDate("observationTime", observationTime).list();
        if (weatherData.size() > 0) {
            return weatherData.get(0);
        }
        return null;
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
