package com.legalfriend.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity(name = "user_relationship")
public class UserRelationship {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;
	@Column(name = "fk_user_from_id")
	private Long fromUserId;
	@Column(name = "fk_user_to_id")
	private Long toUserId;
	@Column(name = "fk_user_role_from_id")
	private String fromRoleId;
	@Column(name = "fk_user_role_to_id")
	private String toRoleId;
	@Column(name = "from_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fromDate;
	@Column(name = "thru_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date thruDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getFromUserId() {
		return fromUserId;
	}

	public void setFromUserId(Long fromUserId) {
		this.fromUserId = fromUserId;
	}

	public Long getToUserId() {
		return toUserId;
	}

	public void setToUserId(Long toUserId) {
		this.toUserId = toUserId;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getThruDate() {
		return thruDate;
	}

	public void setThruDate(Date thruDate) {
		this.thruDate = thruDate;
	}

	public String getFromRoleId() {
		return fromRoleId;
	}

	public void setFromRoleId(String fromRoleId) {
		this.fromRoleId = fromRoleId;
	}

	public String getToRoleId() {
		return toRoleId;
	}

	public void setToRoleId(String toRoleId) {
		this.toRoleId = toRoleId;
	}

}
