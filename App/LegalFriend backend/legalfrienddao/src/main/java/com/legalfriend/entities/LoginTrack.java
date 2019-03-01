package com.legalfriend.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "login_history")
public class LoginTrack {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name = "fk_user_id")
	private Long userId;
	
	@Column
	private Date logindate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Date getLogindate() {
		return logindate;
	}

	public void setLogindate(Date logindate) {
		this.logindate = logindate;
	}
	
	public LoginTrack() {
		
	}
	
	public LoginTrack(Long userId, Date loginDate) {
		this.userId = userId;
		this.logindate = loginDate;
	}
	
	
	

}
