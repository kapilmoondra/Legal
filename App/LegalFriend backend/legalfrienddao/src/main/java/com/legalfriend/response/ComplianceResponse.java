package com.legalfriend.response;

import java.util.List;

import com.legalfriend.entities.Compliance;

public class ComplianceResponse extends LegalFriendResponse {

	private List<Compliance> compliances;

	public List<Compliance> getCompliances() {
		return compliances;
	}

	public void setCompliances(List<Compliance> compliances) {
		this.compliances = compliances;
	}

}
