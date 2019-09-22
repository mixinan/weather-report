package cc.hao2.spring.cloud.weather.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cc.hao2.spring.cloud.weather.service.WeatherDataService;
import cc.hao2.spring.cloud.weather.vo.WeatherResponse;

@RestController
@RequestMapping("/weather")
public class WeatherController {
	
	@Autowired
	private WeatherDataService weatherDataService;
	
	@RequestMapping("/cityId/{cityId}")
	public WeatherResponse getWeatherByCityId(@PathVariable("cityId") String cityId){
		return weatherDataService.getDataByCityId(cityId);
	}
	
	@RequestMapping("/cityName/{cityName}")
	public WeatherResponse getWeatherByCityName(@PathVariable("cityName") String cityName){
		return weatherDataService.getDataByCityName(cityName);
	}
}
