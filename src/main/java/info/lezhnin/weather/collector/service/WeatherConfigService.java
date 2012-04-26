package info.lezhnin.weather.collector.service;

import org.springframework.transaction.annotation.Transactional;

/**
 * Weather config service interface.
 * <p/>
 * Date: 26.04.12
 *
 * @author Sergey Lezhnin <s.lezhnin@metamodel.ru>
 */
public interface WeatherConfigService {

    public static final String YAHOO = "yahoo";
    public static final String YANDEX = "yandex";

    void initData();

}
