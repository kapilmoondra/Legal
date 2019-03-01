package com.legalfriend.response;

import java.util.Set;

import com.legalfriend.entities.Branch;

public class BranchResponse extends LegalFriendResponse {

	private Set<Branch> branches;

	public Set<Branch> getBranches() {
		return branches;
	}

	public void setBranches(Set<Branch> branches) {
		this.branches = branches;
	}

}
