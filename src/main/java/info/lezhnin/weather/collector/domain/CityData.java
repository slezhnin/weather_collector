package info.lezhnin.weather.collector.domain;

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

    @ManyToOne
    @JoinColumn(name = "WEATHER_SERVICE_ID")
    @ForeignKey(name = "FK_CITY_DATA_TO_WEATHER_SERVICE")
    private WeatherService weatherService;

    @ManyToOne
    @JoinColumn(name = "CITY_ID")
    @ForeignKey(name = "FK_CITY_DATA_TO_CITIES")
    private City city;

    @Column(name = "NAME")
    private String name;

    @Column(name = "PLACE_ID")
    private String placeId;

}
