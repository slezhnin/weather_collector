package info.lezhnin.weather.collector.domain;

import javax.persistence.*;
import java.util.Set;

/**
 * City Entity.
 * <p/>
 * Date: 23.04.12
 *
 * @author Sergey Lezhnin <s.lezhnin@gmail.com>
 */
@Entity
@Table(name = "CITIES")
public class City {

    @Id
    @Column(name = "ID")
    @GeneratedValue
    private Integer id;

    @OneToMany(mappedBy = "city", targetEntity = CityData.class)
    private Set<CityData> cityData;

    @OneToMany(mappedBy = "city", targetEntity = WeatherData.class)
    private Set<WeatherData> weatherData;

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

    public Set<WeatherData> getWeatherData() {
        return weatherData;
    }

    public void setWeatherData(Set<WeatherData> weatherData) {
        this.weatherData = weatherData;
    }
}
