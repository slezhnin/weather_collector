package info.lezhnin.weather.collector.service;

import info.lezhnin.weather.collector.config.WeatherConfig;
import info.lezhnin.weather.collector.domain.City;
import info.lezhnin.weather.collector.domain.CityData;
import info.lezhnin.weather.collector.domain.WeatherProvider;
import junit.framework.Assert;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Weather configuration service test.
 * <p/>
 * Date: 26.04.12
 *
 * @author Sergey Lezhnin <s.lezhnin@gmail.com>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = {"/root-context.xml"})
public class WeatherConfigServiceTest {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private WeatherConfig weatherConfig;

    @Autowired
    private WeatherConfigService weatherConfigService;

    private Transaction transaction;
    private Session session;

    @Before
    public void beforeTest() {
        session = sessionFactory.openSession();
        transaction = sessionFactory.getCurrentSession().beginTransaction();
    }

    @After
    public void afterTest() {
        transaction.rollback();
        session.close();
    }

    @Test
    public void initDataTest() {
        System.out.println(".initData()");
        for (int i = 0; i < 2; i++) {
            weatherConfigService.initData();
            List<WeatherProvider> weatherProviders =
                    sessionFactory.getCurrentSession().createQuery("from WeatherProvider").list();
            for (WeatherProvider wp : weatherProviders) {
                System.out.println(ReflectionToStringBuilder.toString(wp));
            }
            Assert.assertEquals("weatherProviders", weatherConfig.getProviders().size(), weatherProviders.size());
            List<CityData> cityData = sessionFactory.getCurrentSession().createQuery("from CityData").list();
            for (CityData cd : cityData) {
                System.out.println(ReflectionToStringBuilder.toString(cd));
            }
            List<City> cities = sessionFactory.getCurrentSession().createQuery("from City").list();
            for (City city : cities) {
                System.out.println(ReflectionToStringBuilder.toString(city));
            }
            Assert.assertEquals("cities", weatherConfig.getCities().size(), cities.size());
        }
    }

}
