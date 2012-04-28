package info.lezhnin.weather.collector.service;

import org.springframework.transaction.annotation.Transactional;

/**
 * Weather service interface.
 * <p/>
 * Date: 26.04.12
 *
 * @author Sergey Lezhnin <s.lezhnin@gmail.com>
 */
public interface WeatherService {

    @Transactional
    void collect();

}
