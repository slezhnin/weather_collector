package info.lezhnin.weather.collector.domain;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.ForeignKey;

import javax.persistence.*;

/**
 * City Data Entity.
 * <p/>
 * Date: 23.04.12
 *
 * @author Sergey Lezhnin <s.lezhnin@gmail.com>
 */
@Entity
@Table(name = "CITY_DATA")
public class CityData {

    @Id
    @Column(name = "ID")
    @GeneratedValue
    private Integer id;

    @ManyToOne(targetEntity = WeatherProvider.class)
    @JoinColumn(name = "WEATHER_PROVIDER_ID")
    @ForeignKey(name = "FK_CITY_DATA_TO_WEATHER_PROVIDER")
    private WeatherProvider weatherProvider;

    @ManyToOne(targetEntity = City.class)
    @JoinColumn(name = "CITY_ID")
    @ForeignKey(name = "FK_CITY_DATA_TO_CITIES")
    private City city;

    @Column(name = "NAME")
    private String name;

    @Column(name = "PLACE_ID")
    private String placeId;

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
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

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public WeatherProvider getWeatherProvider() {
        return weatherProvider;
    }

    public void setWeatherProvider(WeatherProvider weatherProvider) {
        this.weatherProvider = weatherProvider;
    }
}
