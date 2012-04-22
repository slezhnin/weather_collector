package info.lezhnin.weather.collector;

import org.joox.Match;
import org.junit.Test;
import org.w3c.dom.Document;

import java.io.InputStream;
import java.net.URL;

import static org.joox.JOOX.*;

/**
 * Created with IntelliJ IDEA.
 * User: sirius
 * Date: 21.04.12
 * Time: 15:49
 * To change this template use File | Settings | File Templates.
 */
public class YahooTest {
    public static String YAHOO_APPID = "6Bs9OQLV34E2rlFrKF.H2Hhzzxv0G_TFRwZEspI8U6XJ0NmHAG9mrShLrM5_RUcy8CvFJMqqV1t9N868U6CGcQvTytlSpao-";
    public static String YAHOO_PLACES_URI = "http://where.yahooapis.com/v1/places.q(%s)?appid=" + YAHOO_APPID;

    @Test
    public void testWOEID() throws Exception {
        String uri = String.format(YAHOO_PLACES_URI, "Stockholm");
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

    public static String YAHOO_WEATHER_URI = "http://weather.yahooapis.com/forecastrss?w=%s&u=c";
    public static String PLACE_WOEID = "906057";

    @Test
    public void testYahooWeather() throws Exception {
        String uri = String.format(YAHOO_WEATHER_URI, PLACE_WOEID);
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

    public static String YA_CITY_URI = "http://weather.yandex.ru/static/cities.xml";
    public static String YA_CITY_NAME = "Екатеринбург";

    @Test
    public void testYaCityId() throws Exception {
        URL url = new URL(YA_CITY_URI);
        InputStream is = (InputStream) url.getContent();
        try {
            Document document = $(is).document();
            Match country = $(document).children("country");
            Match city = country.children("city").matchText(YA_CITY_NAME);
            System.out.println(city);
            System.out.println(city.attr("id"));
            System.out.println(city.text());
            System.out.println(city.attr("country"));
        } finally {
            is.close();
        }
    }

    public static String YA_FORECAST_URI = "http://export.yandex.ru/weather-ng/forecasts/%s.xml";
    public static String YA_CITY_ID = "28440";

    @Test
    public void testYaWeather() throws Exception {
        URL url = new URL(String.format(YA_FORECAST_URI, YA_CITY_ID));
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
