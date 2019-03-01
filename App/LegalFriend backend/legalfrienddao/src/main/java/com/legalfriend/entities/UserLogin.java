package com.legalfriend.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "user_login")
public class UserLogin {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
		
	@Column(name = "user_login_id",unique=true)
	private String userLoginId;
	@Column(name = "password")
	private String password;
	@Column(name = "has_logged_out")
	private String hasLoggedOut;
	@Column(name = "no_failed_login")
	private String noFailedLogin;
	
	public String getUserLoginId() {
		return userLoginId;
	}

	public void setUserLoginId(String userLoginId) {
		this.userLoginId = userLoginId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getHasLoggedOut() {
		return hasLoggedOut;
	}

	public void setHasLoggedOut(String hasLoggedOut) {
		this.hasLoggedOut = hasLoggedOut;
	}

	public String getNoFailedLogin() {
		return noFailedLogin;
	}

	public void setNoFailedLogin(String noFailedLogin) {
		this.noFailedLogin = noFailedLogin;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	

}
