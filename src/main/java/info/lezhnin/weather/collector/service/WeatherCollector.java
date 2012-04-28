package info.lezhnin.weather.collector.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Weather collector runnable class.
 * <p/>
 * Date: 28.04.12
 *
 * @author Sergey Lezhnin <s.lezhnin@metamodel.ru>
 */
@Service
public class WeatherCollector implements Runnable {

    @Autowired
    private WeatherConfigService weatherConfigService;

    @Autowired
    private WeatherService weatherService;

    private boolean continueRun = true;

    public synchronized boolean isRunning() {
        return continueRun;
    }

    public synchronized void stop() {
        continueRun = false;
    }

    public void run() {
        Logger.getLogger(getClass()).debug("Starting.");
        weatherConfigService.initData();
        int intervalInSeconds = (weatherConfigService.getWeatherConfig().getUpdateIntervalInMinutes() == null ? 60 :
                weatherConfigService.getWeatherConfig().getUpdateIntervalInMinutes()) * 60;
        while (isRunning()) {
            Logger.getLogger(getClass()).debug("Collecting weather data.");
            weatherService.collect();
            try {
                for (int i = 0; i < intervalInSeconds; i++) {
                    Thread.sleep(1000);
                    if (!isRunning()) break;
                }
            } catch (InterruptedException e) {
                Logger.getLogger(getClass()).warn(String
                        .format("Thread %s was interrupted. %s", Thread.currentThread().getName(), e.getMessage()));
                break;
            }
            if (!isRunning()) break;
        }
        Logger.getLogger(getClass()).debug("Stopped.");
    }
}
