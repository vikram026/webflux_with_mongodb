package com.nisum.webflux.model;

public class City {
	private String cityId;
	private String cityName;
	public String getCityId() {
		return cityId;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	public City(String cityId, String cityName) {
		super();
		this.cityId = cityId;
		this.cityName = cityName;
	}
	public String getCityName() {
		return cityName;
	}
	public City() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "City [cityId=" + cityId + ", cityName=" + cityName + "]";
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

}
