package cc.hao2.spring.cloud.weather.service;

import java.util.List;

import cc.hao2.spring.cloud.weather.vo.City;

public interface CityDataService {
	List<City> listCity() throws Exception;
}
