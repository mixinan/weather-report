package cc.hao2.spring.cloud.weather.service;

import cc.hao2.spring.cloud.weather.vo.Weather;

public interface WeatherReportService {
	
	Weather getDataByCityId(String cityId);
}
