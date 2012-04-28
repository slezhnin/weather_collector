package info.lezhnin.weather.collector.service;

import info.lezhnin.weather.collector.web.WeatherServlet;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Application context provider.
 * <p/>
 * Date: 28.04.12
 *
 * @author Sergey Lezhnin <s.lezhnin@gmail.com>
 */
public class AppContextProvider implements ApplicationContextAware {
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        WeatherServlet.setApplicationContext(applicationContext);
    }
}
