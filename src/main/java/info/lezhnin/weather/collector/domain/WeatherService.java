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
}
