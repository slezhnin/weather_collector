package info.lezhnin.weather.collector.config;

import java.util.List;

/**
 * City configuration.
 * <p/>
 * Date: 26.04.12
 *
 * @author Sergey Lezhnin <s.lezhnin@metamodel.ru>
 */
public class CityConfig {

    private List<CityDataConfig> cityData;

    public List<CityDataConfig> getCityData() {
        return cityData;
    }

    public void setCityData(List<CityDataConfig> cityData) {
        this.cityData = cityData;
    }
}
