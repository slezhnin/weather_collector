package info.lezhnin.weather.collector.domain;

import javax.persistence.*;
import java.util.Set;

/**
 * Weather Service Entity.
 * <p/>
 * Date: 23.04.12
 *
 * @author Sergey Lezhnin <s.lezhnin@metamodel.ru>
 */
@Entity
@Table(name = "WEATHER_SERVICE")
public class WeatherService {

    @Id
    @Column(name = "ID")
    @GeneratedValue
    private Integer id;

    @OneToMany(mappedBy = "weatherService")
    private Set<CityData> cityData;

    @OneToMany(mappedBy = "weatherService")
    private Set<WeatherData> weatherData;

    private String name;

    public Set<CityData> getCityData() {
        return cityData;
    }

    public void setCityData(Set<CityData> cityData) {
        this.cityData = cityData;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<WeatherData> getWeatherData() {
        return weatherData;
    }

    public void setWeatherData(Set<WeatherData> weatherData) {
        this.weatherData = weatherData;
    }
}
