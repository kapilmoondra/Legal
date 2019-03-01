package com.legalfriend.entities;

public class UserCases {

	private Long id;
	private String caseId;
	private String courtCaseId;
	private String customerFirstName;
	private String customerLastName;
	private String recourseCode;
	private String stageName;
	private String nextHearingDate;
	private String branchName;
	private String empFirstName;
	private String empLastName;
	private String completionDate;
	private String groundForClosingFile;
	private String parentCaseId;
	private Boolean compliance;

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public String getCourtCaseId() {
		return courtCaseId;
	}

	public void setCourtCaseId(String courtCaseId) {
		this.courtCaseId = courtCaseId;
	}

	public String getCustomerFirstName() {
		return customerFirstName;
	}

	public void setCustomerFirstName(String customerFirstName) {
		this.customerFirstName = customerFirstName;
	}

	public String getCustomerLastName() {
		return customerLastName;
	}

	public void setCustomerLastName(String customerLastName) {
		this.customerLastName = customerLastName;
	}

	public String getRecourseCode() {
		return recourseCode;
	}

	public void setRecourseCode(String recourseCode) {
		this.recourseCode = recourseCode;
	}

	public String getStageName() {
		return stageName;
	}

	public void setStageName(String stageName) {
		this.stageName = stageName;
	}

	public String getNextHearingDate() {
		return nextHearingDate;
	}

	public void setNextHearingDate(String nextHearingDate) {
		this.nextHearingDate = nextHearingDate;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getEmpFirstName() {
		return empFirstName;
	}

	public void setEmpFirstName(String empFirstName) {
		this.empFirstName = empFirstName;
	}

	public String getEmpLastName() {
		return empLastName;
	}

	public void setEmpLastName(String empLastName) {
		this.empLastName = empLastName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCompletionDate() {
		return completionDate;
	}

	public void setCompletionDate(String completionDate) {
		this.completionDate = completionDate;
	}

	public String getGroundForClosingFile() {
		return groundForClosingFile;
	}

	public void setGroundForClosingFile(String groundForClosingFile) {
		this.groundForClosingFile = groundForClosingFile;
	}

	public String getParentCaseId() {
		return parentCaseId;
	}

	public void setParentCaseId(String parentCaseId) {
		this.parentCaseId = parentCaseId;
	}

	public Boolean getCompliance() {
		return compliance;
	}

	public void setCompliance(Boolean compliance) {
		this.compliance = compliance;
	}

}
