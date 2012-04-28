package info.lezhnin.weather.collector.domain;

import org.hibernate.annotations.ForeignKey;
import org.joda.time.DateTime;

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

    @ManyToOne(targetEntity = CityData.class)
    @JoinColumn(name = "CITY_DATA_ID")
    @ForeignKey(name = "FK_WEATHER_DATA_TO_CITY_DATA")
    private CityData cityData;

    @Column(name = "OBSERVATION_TIME")
    private Date observationTime;

    @Column(name = "CONDITIONS")
    private String conditions;

    @Column(name = "TEMPERATURE")
    private Integer temperature;

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

    public Date getObservationTime() {
        return observationTime;
    }

    public String getObservationTimeAsString() {
        return new DateTime(observationTime).toString("dd MMM yyyy HH:mm");
    }

    public CityData getCityData() {
        return cityData;
    }

    public void setCityData(CityData cityData) {
        this.cityData = cityData;
    }

    public void setObservationTime(Date observationTime) {
        this.observationTime = observationTime;
    }

    public Integer getTemperature() {
        return temperature;
    }

    public void setTemperature(Integer temperature) {
        this.temperature = temperature;
    }

}
