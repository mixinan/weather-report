package cc.hao2.spring.cloud.weather.vo;

import java.io.Serializable;
import java.util.List;

public class Weather implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Yesterday yesterday;
	private String city;
	private List<Forecast> forecast;
	private String ganmao;
	private String wendu;
	
	public Yesterday getYesterday() {
		return yesterday;
	}
	public void setYesterday(Yesterday yesterday) {
		this.yesterday = yesterday;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public List<Forecast> getForecast() {
		return forecast;
	}
	public void setForecast(List<Forecast> forcast) {
		this.forecast = forcast;
	}
	public String getGanmao() {
		return ganmao;
	}
	public void setGanmao(String ganmao) {
		this.ganmao = ganmao;
	}
	public String getWendu() {
		return wendu;
	}
	public void setWendu(String wendu) {
		this.wendu = wendu;
	}
	
	
}
