package com.legalfriend.response;

import java.util.List;

import com.legalfriend.entities.StageRecourse;

public class StageRecourseResponse extends LegalFriendResponse {

	private List<StageRecourse> stageRecourses;

	public StageRecourseResponse() {

	}

	public List<StageRecourse> getStageRecourses() {
		return stageRecourses;
	}

	public void setStageRecourses(List<StageRecourse> stageRecourses) {
		this.stageRecourses = stageRecourses;
	}

}
