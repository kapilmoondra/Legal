package com.legalfriend.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity(name = "stage")
public class Stage {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;
	@Column(name = "stage_code", unique = true)
	private String stageCode;
	@Column(name = "stage_name")
	private String stageName;
	@Column(name = "stage_desc")
	private String stageDesc;

	@JoinColumn(name = "fk_recourse_id")
	@ManyToOne
	private Recourse recourse;

	@Transient
	private Long userId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStageCode() {
		return stageCode;
	}

	public void setStageCode(String stageCode) {
		this.stageCode = stageCode;
	}

	public String getStageName() {
		return stageName;
	}

	public void setStageName(String stageName) {
		this.stageName = stageName;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Recourse getRecourse() {
		return recourse;
	}

	public void setRecourse(Recourse recourse) {
		this.recourse = recourse;
	}

	public String getStageDesc() {
		return stageDesc;
	}

	public void setStageDesc(String stageDesc) {
		this.stageDesc = stageDesc;
	}

}
