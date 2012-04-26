package info.lezhnin.weather.collector;

import org.joox.Match;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.w3c.dom.Document;

import java.io.InputStream;
import java.net.URL;

import static org.joox.JOOX.$;

/**
 * Weather Test.
 * <p/>
 * Date: 22.04.12
 *
 * @author Sergey Lezhnin <s.lezhnin@gmail.com>
 */
public class WeatherTest {

    public static WeatherTestConfig weatherConfig;

    @BeforeClass
    public static void before() throws Exception {
        weatherConfig = (WeatherTestConfig) new ClassPathXmlApplicationContext("weather_test_config.xml")
                .getBean("weatherConfig");
    }

    @Test
    public void testWOEID() throws Exception {
        String uri = String.format(weatherConfig.getYahooPlacesURI(), weatherConfig.getYahooPlaceName(),
                weatherConfig.getYahooAppId());
        URL url = new URL(uri);
        InputStream is = (InputStream) url.getContent();
        try {
            Document document = $(is).document();
            Match place = $(document).child("place");
            System.out.println(place.child("woeid").text());
            System.out.println(place.child("name").text());
            System.out.println(place.child("country").text());
        } finally {
            is.close();
        }
    }

    @Test
    public void testYahooWeather() throws Exception {
        String uri = String.format(weatherConfig.getYahooForecastURI(), weatherConfig.getYahooPlaceId());
        URL url = new URL(uri);
        InputStream is = (InputStream) url.getContent();
        try {
            Document document = $(is).document();
            Match channel = $(document).child("channel");
            Match location = channel.child("yweather:location");
            System.out.println(location.attr("city"));
            System.out.println(location.attr("country"));
            Match item = channel.child("item");
            Match condition = item.child("yweather:condition");
            System.out.println(condition.attr("date"));
            System.out.println(condition.attr("text"));
            System.out.println(condition.attr("temp"));
        } finally {
            is.close();
        }
    }

    @Test
    public void testYaCityId() throws Exception {
        URL url = new URL(weatherConfig.getYandexCitiesURI());
        InputStream is = (InputStream) url.getContent();
        try {
            Document document = $(is).document();
            Match country = $(document).children("country");
            Match city = country.children("city").matchText(weatherConfig.getYandexCityName());
            System.out.println(city);
            System.out.println(city.attr("id"));
            System.out.println(city.text());
            System.out.println(city.attr("country"));
        } finally {
            is.close();
        }
    }

    @Test
    public void testYaWeather() throws Exception {
        URL url = new URL(String.format(weatherConfig.getYandexForecastURI(), weatherConfig.getYandexCityId()));
        InputStream is = (InputStream) url.getContent();
        try {
            Document document = $(is).document();
            System.out.println($(document).attr("city"));
            System.out.println($(document).attr("country"));
            Match fact = $(document).child("fact");
            System.out.println(fact.child("station").text());
            System.out.println(fact.child("observation_time").text());
            System.out.println(fact.child("weather_type").text());
            System.out.println(fact.child("temperature").text());
        } finally {
            is.close();
        }
    }
}
