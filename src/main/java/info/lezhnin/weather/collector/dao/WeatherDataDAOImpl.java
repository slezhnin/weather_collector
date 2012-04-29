package info.lezhnin.weather.collector.dao;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import info.lezhnin.weather.collector.domain.CityData;
import info.lezhnin.weather.collector.domain.WeatherData;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.joda.time.DateMidnight;
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
    public WeatherData findWeatherData(CityData cityData, Date observationTime) {
        List<WeatherData> weatherData = sessionFactory.getCurrentSession()
                .createQuery("from WeatherData where cityData = :cityData and observationTime = :observationTime")
                .setEntity("cityData", cityData).setDate("observationTime", observationTime).list();
        if (weatherData.size() > 0) {
            return weatherData.get(0);
        }
        return null;
    }

    public List<WeatherData> list(@Nullable CityData cityData, @Nullable Integer daysBack, boolean chronologicalOrder) {
        StringBuilder queryBuilder = new StringBuilder("from WeatherData");
        if (cityData != null || daysBack != null) {
            List<String> conditions = Lists.newArrayList();
            if (cityData != null) {
                conditions.add("cityData = :cityData");
            }
            if (daysBack != null) {
                conditions.add("observationTime > :dateBack");
            }
            queryBuilder.append(" where ").append(Joiner.on(" and ").join(conditions));
        }
        queryBuilder.append(" order by observationTime");
        if (!chronologicalOrder) {
            queryBuilder.append(" desc");
        }
        Query query = sessionFactory.getCurrentSession().createQuery(queryBuilder.toString());
        if (cityData != null) {
            query.setEntity("cityData", cityData);
        }
        if (daysBack != null) {
            query.setDate("dateBack", new DateMidnight().minusDays(daysBack).toDate());
        }
        return query.list();
    }
}
