package info.lezhnin.weather.collector.config;

import java.util.List;

/**
 * Weather configuration.
 * <p/>
 * Date: 26.04.12
 *
 * @author Sergey Lezhnin <s.lezhnin@gmail.com>
 */
public class WeatherConfig {

    private List<WeatherProviderConfig> providers;
    private List<CityConfig> cities;

    public List<CityConfig> getCities() {
        return cities;
    }

    public void setCities(List<CityConfig> cities) {
        this.cities = cities;
    }

    public List<WeatherProviderConfig> getProviders() {
        return providers;
    }

    public void setProviders(List<WeatherProviderConfig> providers) {
        this.providers = providers;
    }

}
