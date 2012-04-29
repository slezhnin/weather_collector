package info.lezhnin.weather.collector.web;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import info.lezhnin.weather.collector.domain.CityData;
import info.lezhnin.weather.collector.service.CityDataService;
import info.lezhnin.weather.collector.service.WeatherDataService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

    private static WeatherFormData weatherFormData = new WeatherFormData();

    @Autowired
    private CityDataService cityDataService;

    @Autowired
    private WeatherDataService weatherDataService;

    @RequestMapping("/")
    public String home() {
        return "redirect:data";
    }

    @RequestMapping("data")
    public String listWeatherData(Map<String, Object> map) {
        map.put("cityData", mapCityData());
        CityData byCityData = Strings.isNullOrEmpty(weatherFormData.getCityDataId()) ? null : cityDataService.getCityData(Integer.valueOf(weatherFormData.getCityDataId()));
        map.put("weatherData", weatherDataService.list(byCityData, 0, false));
        map.put("weatherFormData", weatherFormData);
        return "weather";
    }

    @RequestMapping(value = "data/city", method = RequestMethod.POST)
    public String selectCity(@ModelAttribute("wfd") WeatherFormData wfd,
                             BindingResult result) {
        weatherFormData.setCityDataId(wfd.getCityDataId());
        return "redirect:..";
    }

    Map<String, String> mapCityData() {
        Map<String, String> cityData = Maps.newHashMap();
        cityData.put("", "--");
        for (CityData cd : cityDataService.listCityData()) {
            cityData.put(cd.getId().toString(), cd.getName());
        }
        return cityData;
    }

}
