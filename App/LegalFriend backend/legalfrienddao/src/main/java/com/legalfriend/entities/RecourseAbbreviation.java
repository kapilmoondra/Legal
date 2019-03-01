package com.legalfriend.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "recourse_abbr")
public class RecourseAbbreviation {
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;
	@Column(name = "recourse_code", unique = true)
	private String recourseCode;
	@Column(name = "abbreviation")
	private String abbreviation;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRecourseCode() {
		return recourseCode;
	}

	public void setRecourseCode(String recourseCode) {
		this.recourseCode = recourseCode;
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

}
