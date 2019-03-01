package com.legalfriend.entities;

public class StageRecourse {

	private Long id;
	private String stageName;
	private String recourseCode;
	private Long recourseId;
	private Long stageStatusId;
	private String stageCode;

	public StageRecourse() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStageName() {
		return stageName;
	}

	public void setStageName(String stageName) {
		this.stageName = stageName;
	}

	public String getRecourseCode() {
		return recourseCode;
	}

	public void setRecourseCode(String recourseCode) {
		this.recourseCode = recourseCode;
	}

	public Long getRecourseId() {
		return recourseId;
	}

	public void setRecourseId(Long recourseId) {
		this.recourseId = recourseId;
	}

	public Long getStageStatusId() {
		return stageStatusId;
	}

	public void setStageStatusId(Long stageStatusId) {
		this.stageStatusId = stageStatusId;
	}

	public String getStageCode() {
		return stageCode;
	}

	public void setStageCode(String stageCode) {
		this.stageCode = stageCode;
	}

}
