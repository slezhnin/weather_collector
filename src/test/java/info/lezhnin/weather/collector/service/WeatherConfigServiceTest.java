package info.lezhnin.weather.collector.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
        weatherConfigService.initData();
    }

}
