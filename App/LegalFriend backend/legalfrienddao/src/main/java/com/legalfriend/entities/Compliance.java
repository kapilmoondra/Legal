package com.legalfriend.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name = "compliance")
public class Compliance {
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;
	@Column(name = "compliance_name")
	private String complianceName;

	@JoinColumn(name = "fk_stage_id")
	@ManyToOne
	private Stage stage;

	@JoinColumn(name = "fk_recourse_id")
	@ManyToOne
	private Recourse recourse;

	@JoinColumn(name = "fk_status_id")
	@ManyToOne
	private ComplianceStatus statusId;

	@Column(name = "fk_user_id")
	private Long userId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getComplianceName() {
		return complianceName;
	}

	public void setComplianceName(String complianceName) {
		this.complianceName = complianceName;
	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public Recourse getRecourse() {
		return recourse;
	}

	public void setRecourse(Recourse recourse) {
		this.recourse = recourse;
	}

	public ComplianceStatus getStatusId() {
		return statusId;
	}

	public void setStatusId(ComplianceStatus statusId) {
		this.statusId = statusId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
