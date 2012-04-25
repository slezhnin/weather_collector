package info.lezhnin.weather.collector.domain;

import org.hibernate.annotations.ForeignKey;

import javax.persistence.*;
import java.util.Date;

/**
 * Weather Data Entity.
 * <p/>
 * Date: 23.04.12
 *
 * @author Sergey Lezhnin <s.lezhnin@gmail.com>
 */
@Entity
@Table(name = "WEATHER_DATA")
public class WeatherData {

    @Id
    @Column(name = "ID")
    @GeneratedValue
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "CITY_ID")
    @ForeignKey(name = "FK_WEATHER_DATA_TO_CITIES")
    private City city;

    @ManyToOne
    @JoinColumn(name = "WEATHER_PROVIDER_ID")
    @ForeignKey(name = "FK_WEATHER_DATA_TO_WEATHER_PROVIDER")
    private WeatherProvider weatherProvider;

    @Column(name = "OBSERVATION_TIME")
    private Date observationTime;

    @Column(name = "CONDITIONS")
    private String conditions;

    @Column(name = "TEMPERATURE")
    private String temperature;

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getConditions() {
        return conditions;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public WeatherProvider getWeatherProvider() {
        return weatherProvider;
    }

    public void setWeatherProvider(WeatherProvider weatherProvider) {
        this.weatherProvider = weatherProvider;
    }
}
