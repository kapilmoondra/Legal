package com.legalfriend.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity(name = "recourse")
public class Recourse {
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;
	@Column(name = "recourse_code", unique = true)
	private String recourseCode;
	@Column(name = "recourse_name")
	private String recourseName;
	@Column(name = "recourse_desc")
	private String recourseDesc;
	@Column(name = "abbr")
	private String abbr;
	@Transient
	private Long userId;

	public String getRecourseCode() {
		return recourseCode;
	}

	public void setRecourseCode(String recourseCode) {
		this.recourseCode = recourseCode;
	}

	public String getRecourseName() {
		return recourseName;
	}

	public void setRecourseName(String recourseName) {
		this.recourseName = recourseName;
	}

	public String getRecourseDesc() {
		return recourseDesc;
	}

	public void setRecourseDesc(String recourseDesc) {
		this.recourseDesc = recourseDesc;
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

	public String getAbbr() {
		return abbr;
	}

	public void setAbbr(String abbr) {
		this.abbr = abbr;
	}

}
