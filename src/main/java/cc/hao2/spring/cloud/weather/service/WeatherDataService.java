package cc.hao2.spring.cloud.weather.service;

import cc.hao2.spring.cloud.weather.vo.WeatherResponse;

public interface WeatherDataService {
	
	WeatherResponse getDataByCityId(String cityId);
	
	WeatherResponse getDataByCityName(String cityName);
	
	void syncDataByCityId(String cityId);
	
}
