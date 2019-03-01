package com.legalfriend.response;

import java.util.List;

import com.legalfriend.entities.ComplianceStageRecourse;

public class ComplianceStageRecourseResponse extends LegalFriendResponse {

	private List<ComplianceStageRecourse> complianceStageRecourses;

	public ComplianceStageRecourseResponse() {

	}

	public List<ComplianceStageRecourse> getComplianceStageRecourses() {
		return complianceStageRecourses;
	}

	public void setComplianceStageRecourses(List<ComplianceStageRecourse> complianceStageRecourses) {
		this.complianceStageRecourses = complianceStageRecourses;
	}

}
