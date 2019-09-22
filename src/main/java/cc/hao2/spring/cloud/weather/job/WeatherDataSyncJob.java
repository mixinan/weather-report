package cc.hao2.spring.cloud.weather.job;

import java.util.List;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import cc.hao2.spring.cloud.weather.service.CityDataService;
import cc.hao2.spring.cloud.weather.service.WeatherDataService;
import cc.hao2.spring.cloud.weather.vo.City;

public class WeatherDataSyncJob extends QuartzJobBean{
	private static final Logger logger = LoggerFactory.getLogger(WeatherDataSyncJob.class);
	
	@Autowired
	private CityDataService cityDataService;
	
	@Autowired
	private WeatherDataService weatherDataService;
	
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		logger.info("Weather Data Sync Job. Start!");
		List<City> cityList = null;
		
		try {
			cityList = cityDataService.listCity();
		} catch (Exception e) {
			logger.error("Error!",e);
		}
		
		// 遍历城市id获得天气
		for (City city : cityList) {
			String cityId = city.getCityId();
			logger.info("\n== weather data sync job ==\ncityId: "+cityId+"\ncityName: "+city.getCityName()+"\nprovince: "+city.getProvince());
			
			weatherDataService.syncDataByCityId(cityId);
		}
		
		logger.info("Weather Data Sync Job. End!");
	}

}
