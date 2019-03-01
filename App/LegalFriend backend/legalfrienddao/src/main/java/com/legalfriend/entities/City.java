package com.legalfriend.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "city")
public class City {
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;
	@Column(name = "city_name", unique=true)
	private String cityName;

	@Column(name = "fk_user_id")
	private Long userId;

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
