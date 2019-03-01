package com.legalfriend.response;

import java.util.Set;

import com.legalfriend.entities.Court;

public class CourtResponse extends LegalFriendResponse {

	private Set<Court> courts;

	public Set<Court> getCourts() {
		return courts;
	}

	public void setCourts(Set<Court> courts) {
		this.courts = courts;
	}

}
