package com.legalfriend.entities;

import java.util.Date;

public class LegalCaseDashboard {

	private String caseId;
	private String name;
	private String recourceType;
	private String nextHearingDate;
	private String branch;
	private String employee;
	private String lastUpdatedDate;
	
	public String getCaseId() {
		return caseId;
	}
	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRecourceType() {
		return recourceType;
	}
	public void setRecourceType(String recourceType) {
		this.recourceType = recourceType;
	}
	public String getNextHearingDate() {
		return nextHearingDate;
	}
	public void setNextHearingDate(String nextHearingDate) {
		this.nextHearingDate = nextHearingDate;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public String getEmployee() {
		return employee;
	}
	public void setEmployee(String employee) {
		this.employee = employee;
	}
	public String getLastUpdatedDate() {
		return lastUpdatedDate;
	}
	public void setLastUpdatedDate(String lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}
}
