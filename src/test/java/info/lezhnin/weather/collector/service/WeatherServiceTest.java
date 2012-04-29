package info.lezhnin.weather.collector.service;

import info.lezhnin.weather.collector.domain.WeatherData;
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
 * Weather service test.
 * <p/>
 * Date: 26.04.12
 *
 * @author Sergey Lezhnin <s.lezhnin@gmail.com>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = {"/root-context.xml"})
public class WeatherServiceTest {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private WeatherConfigService weatherConfigService;

    @Autowired
    private WeatherDataService weatherDataService;

    @Autowired
    private WeatherService weatherService;

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
    public void collectTest() {
        System.out.println(".initData()");
        weatherConfigService.initData();
        for (int i = 0; i < 2; i++) {
            System.out.println(".collect()");
            weatherService.collect();
            System.out.println(".list()");
            List<WeatherData> weatherData = weatherDataService.list(null, null, false);
            for (WeatherData wd : weatherData) {
                System.out.println(ReflectionToStringBuilder.toString(wd));
                System.out.println(ReflectionToStringBuilder.toString(wd.getCityData()));
                System.out.println(ReflectionToStringBuilder.toString(wd.getCityData().getWeatherProvider()));
                System.out.println("--------------------");
            }
        }
    }

}
