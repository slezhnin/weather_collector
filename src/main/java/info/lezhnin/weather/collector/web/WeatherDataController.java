package info.lezhnin.weather.collector.web;

import info.lezhnin.weather.collector.service.WeatherDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * Weather Data Controller.
 * <p/>
 * Date: 27.04.12
 *
 * @author Sergey Lezhnin <s.lezhnin@gmail.com>
 */
@Controller
public class WeatherDataController {

    @Autowired
    private WeatherDataService weatherDataService;

    @RequestMapping("data")
    public String listWeatherData(Map<String, Object> map) {
        map.put("weatherList", weatherDataService.list(null, false));
        return "weather";
    }

    @RequestMapping("/")
    public String home() {
        return "redirect:data";
    }

}
