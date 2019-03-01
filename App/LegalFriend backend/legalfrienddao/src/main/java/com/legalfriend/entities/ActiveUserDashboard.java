package com.legalfriend.entities;

import java.math.BigInteger;

public class ActiveUserDashboard {

	private String name;
	private String designation;
	private String lastLogin;
	private BigInteger totalLogin;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public String getLastLogin() {
		return lastLogin;
	}
	public void setLastLogin(String lastLogin) {
		this.lastLogin = lastLogin;
	}
	public BigInteger getTotalLogin() {
		return totalLogin;
	}
	public void setTotalLogin(BigInteger totalLogin) {
		this.totalLogin = totalLogin;
	}
	
	
}
