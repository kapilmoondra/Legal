package com.legalfriend.response;

import java.util.Set;

import com.legalfriend.entities.BillingMaster;

public class BillingResponse extends LegalFriendResponse {

	private Set<BillingMaster> billings;

	public Set<BillingMaster> getBillings() {
		return billings;
	}

	public void setBillings(Set<BillingMaster> billings) {
		this.billings = billings;
	}

}
