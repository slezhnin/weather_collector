package info.lezhnin.weather.collector.config;

/**
 * City Data configuration.
 * <p/>
 * Date: 26.04.12
 *
 * @author Sergey Lezhnin <s.lezhnin@gmail.com>
 */
public class CityDataConfig {
    private WeatherProviderConfig weatherProvider;
    private String name;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public WeatherProviderConfig getWeatherProvider() {
        return weatherProvider;
    }

    public void setWeatherProvider(WeatherProviderConfig weatherProvider) {
        this.weatherProvider = weatherProvider;
    }
}
