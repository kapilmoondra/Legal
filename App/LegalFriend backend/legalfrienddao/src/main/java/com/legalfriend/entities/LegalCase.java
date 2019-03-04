package com.legalfriend.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.envers.Audited;

@Entity
@Table(name = "legal_case")
@Audited
public class LegalCase extends Auditable<String> {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;

	@Column(name = "court_case_id")
	private String courtCaseId;

	@Column(name = "case_id")
	private String caseId;

	@Column(name = "title")
	private String title;

	@Column(name = "parent_case_id")
	private Long parentCaseId;

	@Column(name = "fk_user_id")
	private Long userId;

	@Column(name = "fk_branch_id")
	private Long branchId;

	@Column(name = "fk_stage_id")
	private Long stageId;

	@Column(name = "fk_recourse_id", nullable = false)
	private Long recourseId;

	@Column(name = "fk_court_id")
	private Long courtId;

	@Column(name = "fk_state_id")
	private Long stateId;

	@Column(name = "next_hearing_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date nextHearingDate;

	@Column(name = "completion_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date completionDate;

	@Column(name = "customer_id")
	private Long customerId;

	@Column(name = "ground_for_closing_file")
	private String groundForClosingFile;

	@Column(name = "filing_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date filingDate;

	@Column(name = "employee_id", nullable = false)
	private Long employeeId;

	@Column(name = "court_place")
	private Long fkCourtDistrict;

	@Column(name = "opp_lawyer")
	private String oppLawyer;

	@Column(name = "manager_id")
	private Long managerId;

	@Column(name = "child_case")
	private String childCase;

	@Column(name = "last_hearing_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastHearingDate;

	@Column(name = "remark", length = 100000)
	private String remark;

	@Column(name = "remark_history", length = 100000)
	private String remarkHistory;

	@Column(name = "remark_file")
	private String remarkFile;

	@Column(name = "compliance")
	private Boolean compliance;

	@Column(name = "compliance_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date complianceDate;

	@Transient
	private List<CaseFiles> caseFiles;

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

	public Long getBranchId() {
		return branchId;
	}

	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}

	public Long getStageId() {
		return stageId;
	}

	public void setStageId(Long stageId) {
		this.stageId = stageId;
	}

	public Long getRecourseId() {
		return recourseId;
	}

	public void setRecourseId(Long recourseId) {
		this.recourseId = recourseId;
	}

	public Long getCourtId() {
		return courtId;
	}

	public void setCourtId(Long courtId) {
		this.courtId = courtId;
	}

	public Long getStateId() {
		return stateId;
	}

	public void setStateId(Long stateId) {
		this.stateId = stateId;
	}

	public Date getNextHearingDate() {
		return nextHearingDate;
	}

	public void setNextHearingDate(Date nextHearingDate) {
		this.nextHearingDate = nextHearingDate;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Date getFilingDate() {
		return filingDate;
	}

	public void setFilingDate(Date filingDate) {
		this.filingDate = filingDate;
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public Long getFkCourtDistrict() {
		return fkCourtDistrict;
	}

	public void setFkCourtDistrict(Long fkCourtDistrict) {
		this.fkCourtDistrict = fkCourtDistrict;
	}

	public String getOppLawyer() {
		return oppLawyer;
	}

	public void setOppLawyer(String oppLawyer) {
		this.oppLawyer = oppLawyer;
	}

	public String getChildCase() {
		return childCase;
	}

	public void setChildCase(String childCase) {
		this.childCase = childCase;
	}

	public Date getLastHearingDate() {
		return lastHearingDate;
	}

	public void setLastHearingDate(Date lastHearingDate) {
		this.lastHearingDate = lastHearingDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public Long getParentCaseId() {
		return parentCaseId;
	}

	public void setParentCaseId(Long parentCaseId) {
		this.parentCaseId = parentCaseId;
	}

	public Date getCompletionDate() {
		return completionDate;
	}

	public void setCompletionDate(Date completionDate) {
		this.completionDate = completionDate;
	}

	public String getGroundForClosingFile() {
		return groundForClosingFile;
	}

	public void setGroundForClosingFile(String groundForClosingFile) {
		this.groundForClosingFile = groundForClosingFile;
	}

	public Long getManagerId() {
		return managerId;
	}

	public void setManagerId(Long managerId) {
		this.managerId = managerId;
	}

	public Boolean getCompliance() {
		return compliance;
	}

	public void setCompliance(Boolean compliance) {
		this.compliance = compliance;
	}

	public Date getComplianceDate() {
		return complianceDate;
	}

	public void setComplianceDate(Date complianceDate) {
		this.complianceDate = complianceDate;
	}

	public List<CaseFiles> getCaseFiles() {
		return caseFiles;
	}

	public void setCaseFiles(List<CaseFiles> caseFiles) {
		this.caseFiles = caseFiles;
	}

	public String getRemarkHistory() {
		return remarkHistory;
	}

	public void setRemarkHistory(String remarkHistory) {
		this.remarkHistory = remarkHistory;
	}

	public String getRemarkFile() {
		return remarkFile;
	}

	public void setRemarkFile(String remarkFile) {
		this.remarkFile = remarkFile;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
