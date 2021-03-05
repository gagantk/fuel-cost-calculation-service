package com.gagantk.fuelcostcalculator.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FuelEvent {

	@JsonProperty("fuelLid")
	private Boolean fuelLid;
	
	@JsonProperty("city")
	private String city;

	public Boolean getFuelLid() {
		return fuelLid;
	}

	public void setFuelLid(Boolean fuelLid) {
		this.fuelLid = fuelLid;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

}
