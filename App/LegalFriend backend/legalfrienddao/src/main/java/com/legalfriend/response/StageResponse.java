package com.legalfriend.response;

import java.util.List;

import com.legalfriend.entities.Stage;

public class StageResponse extends LegalFriendResponse {

	private List<Stage> stages;

	public List<Stage> getStages() {
		return stages;
	}

	public void setStages(List<Stage> stages) {
		this.stages = stages;
	}

}
