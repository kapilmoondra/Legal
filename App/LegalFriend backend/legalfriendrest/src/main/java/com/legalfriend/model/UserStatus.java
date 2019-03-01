package com.legalfriend.model;

public class UserStatus {
	private Long id;
	private Status status;
	private String userLoginId;
		
	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getUserLoginId() {
		return userLoginId;
	}

	public void setUserLoginId(String userLoginId) {
		this.userLoginId = userLoginId;
	}

	
}
