package info.lezhnin.weather.collector.web;

import info.lezhnin.weather.collector.service.WeatherCollector;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * The weather servlet. Starts and stops the background collector thread.
 * <p/>
 * Date: 28.04.12
 *
 * @author Sergey Lezhnin <s.lezhnin@metamodel.ru>
 */
public class WeatherServlet extends HttpServlet {

    private static ApplicationContext applicationContext;

    private WeatherCollector weatherCollector;

    private static Thread weatherThread;

    @Override
    public void init() throws ServletException {
        super.init();
        Logger.getLogger(getClass()).info("NOTICE! Weather servlet init data.");
        weatherCollector = applicationContext.getBean(WeatherCollector.class);
        weatherThread = new Thread(weatherCollector, "weatherService");
        weatherThread.start();

    }

    @Override
    public void destroy() {
        super.destroy();
        Logger.getLogger(getClass()).info("NOTICE! Weather servlet destroy.");
        weatherCollector.stop();
    }

    public static void setApplicationContext(ApplicationContext applicationContext) {
        WeatherServlet.applicationContext = applicationContext;
    }
}
