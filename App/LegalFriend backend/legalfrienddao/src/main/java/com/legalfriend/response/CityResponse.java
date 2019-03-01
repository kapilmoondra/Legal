package com.legalfriend.response;

import java.util.Set;

import com.legalfriend.entities.City;

public class CityResponse extends LegalFriendResponse {

	private Set<City> cities;

	public Set<City> getCities() {
		return cities;
	}

	public void setCities(Set<City> cities) {
		this.cities = cities;
	}

}
