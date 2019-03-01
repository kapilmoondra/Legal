package com.legalfriend.response;

import java.util.Set;

import com.legalfriend.entities.State;

public class StateResponse extends LegalFriendResponse {

	private Set<State> states;

	public Set<State> getStates() {
		return states;
	}

	public void setStates(Set<State> states) {
		this.states = states;
	}

}
