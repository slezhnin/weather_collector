package info.lezhnin.weather.collector.dao;

import info.lezhnin.weather.collector.domain.CityData;
import info.lezhnin.weather.collector.domain.WeatherProvider;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * City Data DAO implementation.
 * <p/>
 * Date: 25.04.12
 *
 * @author Sergey Lezhnin <s.lezhnin@gmail.com>
 */
@Repository
public class CityDataDAOImpl implements CityDataDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public void saveCityData(CityData cityData) {
        sessionFactory.getCurrentSession().save(cityData);
    }

    public CityData findCityData(WeatherProvider weatherProvider, String placeId) {
        List<CityData> cityData = sessionFactory.getCurrentSession()
                .createQuery("from CityData where weatherProvider = :weatherProvider and placeId = :placeId")
                .setEntity("weatherProvider", weatherProvider).setString("placeId", placeId).list();
        if (cityData.size() > 0) {
            return cityData.get(0);
        }
        return null;
    }

    public List<CityData> listCityData() {
        return sessionFactory.getCurrentSession().createQuery("from CityData").list();
    }

    public CityData getCityData(Integer id) {
        List<CityData> cityData = sessionFactory.getCurrentSession()
                .createQuery("from CityData where id = :id")
                .setInteger("id", id).list();
        if (cityData.size() > 0) {
            return cityData.get(0);
        }
        return null;
    }
}
