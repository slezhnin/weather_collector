package info.lezhnin.weather.collector.dao;

import info.lezhnin.weather.collector.domain.City;
import info.lezhnin.weather.collector.domain.CityData;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * City DAO implementation.
 * <p/>
 * Date: 24.04.12
 *
 * @author Sergey Lezhnin <s.lezhnin@gmail.com>
 */
@Repository
public class CityDAOImpl implements CityDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public void saveCity(City city) {
        sessionFactory.getCurrentSession().save(city);
    }

    public City findCity(CityData cityData) {
        List<City> cities = sessionFactory.getCurrentSession().createQuery("from City where cityData in (:cityData)")
                .setEntity("cityData", cityData).list();
        if (cities.size() > 0) {
            return cities.get(0);
        }
        return null;
    }
}
