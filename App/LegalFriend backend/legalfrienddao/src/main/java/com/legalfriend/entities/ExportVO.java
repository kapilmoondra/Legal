package com.legalfriend.entities;

import java.util.List;

public class ExportVO {

	private Long institutionId;
	private Long branchId;
	List<Long> institutionalCaseIds;
	List<String> caseIds;

	public Long getInstitutionId() {
		return institutionId;
	}

	public void setInstitutionId(Long institutionId) {
		this.institutionId = institutionId;
	}

	public Long getBranchId() {
		return branchId;
	}

	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}

	public List<Long> getInstitutionalCaseIds() {
		return institutionalCaseIds;
	}

	public void setInstitutionalCaseIds(List<Long> institutionalCaseIds) {
		this.institutionalCaseIds = institutionalCaseIds;
	}

	public List<String> getCaseIds() {
		return caseIds;
	}

	public void setCaseIds(List<String> caseIds) {
		this.caseIds = caseIds;
	}

}
