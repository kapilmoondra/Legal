package com.legalfriend.entities;

public class LegalCaseVO {

	private Long id;

	private String courtCaseId;

	private String caseId;

	private String parentCaseId;

	private Long userId;

	private String nextHearingDate;

	private String groundForClosingFile;

	private String filingDate;

	private Long fkCourtDistrict;

	private String courtPlace;

	private String lastHearingDate;

	private long rev;

	private long revTimeStamp;

	private String stage;

	private String remark;

	private String remarkFile;

	private String firstName;

	private String lastName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCourtCaseId() {
		return courtCaseId;
	}

	public void setCourtCaseId(String courtCaseId) {
		this.courtCaseId = courtCaseId;
	}

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public String getParentCaseId() {
		return parentCaseId;
	}

	public void setParentCaseId(String parentCaseId) {
		this.parentCaseId = parentCaseId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getNextHearingDate() {
		return nextHearingDate;
	}

	public void setNextHearingDate(String nextHearingDate) {
		this.nextHearingDate = nextHearingDate;
	}

	public String getGroundForClosingFile() {
		return groundForClosingFile;
	}

	public void setGroundForClosingFile(String groundForClosingFile) {
		this.groundForClosingFile = groundForClosingFile;
	}

	public String getFilingDate() {
		return filingDate;
	}

	public void setFilingDate(String filingDate) {
		this.filingDate = filingDate;
	}

	public Long getFkCourtDistrict() {
		return fkCourtDistrict;
	}

	public void setFkCourtDistrict(Long fkCourtDistrict) {
		this.fkCourtDistrict = fkCourtDistrict;
	}

	public String getLastHearingDate() {
		return lastHearingDate;
	}

	public void setLastHearingDate(String lastHearingDate) {
		this.lastHearingDate = lastHearingDate;
	}

	public long getRev() {
		return rev;
	}

	public void setRev(long rev) {
		this.rev = rev;
	}

	public long getRevTimeStamp() {
		return revTimeStamp;
	}

	public void setRevTimeStamp(long revTimeStamp) {
		this.revTimeStamp = revTimeStamp;
	}

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCourtPlace() {
		return courtPlace;
	}

	public void setCourtPlace(String courtPlace) {
		this.courtPlace = courtPlace;
	}

	public String getRemarkFile() {
		return remarkFile;
	}

	public void setRemarkFile(String remarkFile) {
		this.remarkFile = remarkFile;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

}
