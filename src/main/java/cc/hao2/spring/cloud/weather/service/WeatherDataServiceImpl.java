package cc.hao2.spring.cloud.weather.service;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import cc.hao2.spring.cloud.weather.vo.WeatherResponse;

@Service
public class WeatherDataServiceImpl implements WeatherDataService {

	private static final Logger logger = LoggerFactory.getLogger(WeatherDataServiceImpl.class);

	private static final String WEATHER_URI = "http://wthrcdn.etouch.cn/weather_mini?";

	private static final long TIME_OUT = 1200L;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Override
	public WeatherResponse getDataByCityId(String cityId) {
		String uri = WEATHER_URI + "citykey=" + cityId;
		return this.doGetWeather(uri);
	}

	@Override
	public WeatherResponse getDataByCityName(String cityName) {
		String uri = WEATHER_URI + "city=" + cityName;
		return this.doGetWeather(uri);
	}

	private WeatherResponse doGetWeather(String uri) {
		String key = uri;
		String strBody = null;
		ObjectMapper mapper = new ObjectMapper();
		WeatherResponse weather = null;

		ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();

		// 先查缓存
		if (stringRedisTemplate.hasKey(key)) {
			logger.info("redis has data");
			strBody = ops.get(key);
		} else {
			logger.info("redis have not data");
			ResponseEntity<String> respString = restTemplate.getForEntity(uri, String.class);

			if (respString.getStatusCodeValue() == 200) {
				strBody = respString.getBody();
			}

			// 写入缓存
			ops.set(key, strBody, TIME_OUT, TimeUnit.SECONDS);
			
			logger.info("data write in redis!");
		}

		try {
			weather = mapper.readValue(strBody, WeatherResponse.class);
		} catch (IOException e) {
			logger.error("Error!", e);
		}

		return weather;
	}

	@Override
	public void syncDataByCityId(String cityId) {
		String uri = WEATHER_URI + "citykey=" + cityId;
		this.saveWeatherData(uri);
	}

	/**
	 * 把天气数据放入缓存
	 * @param uri
	 */
	private void saveWeatherData(String uri) {
		String key = uri;
		String strBody = null;
		ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();

		ResponseEntity<String> respString = restTemplate.getForEntity(uri, String.class);

		if (respString.getStatusCodeValue() == 200) {
			strBody = respString.getBody();
		}

		// 写入缓存
		ops.set(key, strBody, TIME_OUT, TimeUnit.SECONDS);
	}

}
