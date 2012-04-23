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

    @OneToMany(mappedBy = "city")
    private Set<CityData> cityData;

    @OneToMany(mappedBy = "city")
    private Set<WeatherData> weatherData;

}
