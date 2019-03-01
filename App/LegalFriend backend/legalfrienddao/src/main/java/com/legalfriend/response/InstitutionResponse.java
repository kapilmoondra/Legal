package com.legalfriend.response;

import java.util.Set;

import com.legalfriend.entities.Institution;

public class InstitutionResponse extends LegalFriendResponse {
	private Set<Institution> institutions;

	public Set<Institution> getinstitutions() {
		return institutions;
	}

	public void setInstitutions(Set<Institution> institutions) {
		this.institutions = institutions;
	}


}
