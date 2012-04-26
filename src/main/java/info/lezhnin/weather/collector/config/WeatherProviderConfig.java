package info.lezhnin.weather.collector.config;

/**
 * Weather Provider configuration.
 * <p/>
 * Date: 26.04.12
 *
 * @author Sergey Lezhnin <s.lezhnin@gmail.com>
 */
public class WeatherProviderConfig {

    String placesURI;
    String forecastURI;
    String name;
    String id;

    public String getForecastURI() {
        return forecastURI;
    }

    public void setForecastURI(String forecastURI) {
        this.forecastURI = forecastURI;
    }

    public String getPlacesURI() {
        return placesURI;
    }

    public void setPlacesURI(String placesURI) {
        this.placesURI = placesURI;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
