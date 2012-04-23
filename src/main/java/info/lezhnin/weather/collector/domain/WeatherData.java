package info.lezhnin.weather.collector.domain;

import org.hibernate.annotations.ForeignKey;

import javax.persistence.*;

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
    @JoinColumn(name = "WEATHER_SERVICE_ID")
    @ForeignKey(name = "FK_WEATHER_DATA_TO_WEATHER_SERVICE")
    private WeatherService weatherService;

    @Column(name = "CONDITIONS")
    private String conditions;

    @Column(name = "TEMPERATURE")
    private String temperature;

}
