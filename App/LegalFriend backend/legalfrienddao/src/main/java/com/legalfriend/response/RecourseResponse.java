package com.legalfriend.response;

import java.util.Set;

import com.legalfriend.entities.Recourse;

public class RecourseResponse extends LegalFriendResponse {

	private Set<Recourse> recourses;

	public Set<Recourse> getRecourses() {
		return recourses;
	}

	public void setRecourses(Set<Recourse> recourses) {
		this.recourses = recourses;
	}

	

}
