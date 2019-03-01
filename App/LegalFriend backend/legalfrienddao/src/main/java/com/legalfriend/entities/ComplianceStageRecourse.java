package com.legalfriend.entities;

public class ComplianceStageRecourse {

	private Long id;
	private String complianceName;
	private String stageCode;
	private String recourseCode;
	private Long recourseId;
	private Long stageId;
	private Long userId;
	private Long statusId;

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

	public String getStageCode() {
		return stageCode;
	}

	public void setStageCode(String stageCode) {
		this.stageCode = stageCode;
	}

	public String getRecourseCode() {
		return recourseCode;
	}

	public void setRecourseCode(String recourseCode) {
		this.recourseCode = recourseCode;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getStatusId() {
		return statusId;
	}

	public void setStatusId(Long statusId) {
		this.statusId = statusId;
	}

	public Long getRecourseId() {
		return recourseId;
	}

	public void setRecourseId(Long recourseId) {
		this.recourseId = recourseId;
	}

	public Long getStageId() {
		return stageId;
	}

	public void setStageId(Long stageId) {
		this.stageId = stageId;
	}

}
