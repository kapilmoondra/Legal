package com.legalfriend.response;

import java.util.Set;

import com.legalfriend.entities.IndividualBillingMaster;

public class IndividualBillingResponse extends LegalFriendResponse {

	private Set<IndividualBillingMaster> billings;

	public Set<IndividualBillingMaster> getBillings() {
		return billings;
	}

	public void setBillings(Set<IndividualBillingMaster> billings) {
		this.billings = billings;
	}

}
