package com.legalfriend.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity(name = "recourse_request")
public class RecourseRequest {
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;
	@Column(name = "recourse_code", unique = true)
	private String recourseCode;
	@Column(name = "recourse_name")
	private String recourseName;
	@Column(name = "recourse_desc")
	private String recourseDesc;

	@JoinColumn(name = "fk_user_id")
	@ManyToOne
	private User user;

	@Column(name = "status")
	private String status;
	@Column(name = "created_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@Column(name = "updated_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedDate;

	@Column(name = "fk_user_updated_by")
	private Long updatedBy;

	public String getRecourseCode() {
		return recourseCode;
	}

	public void setRecourseCode(String recourseCode) {
		this.recourseCode = recourseCode;
	}

	public String getRecourseName() {
		return recourseName;
	}

	public void setRecourseName(String recourseName) {
		this.recourseName = recourseName;
	}

	public String getRecourseDesc() {
		return recourseDesc;
	}

	public void setRecourseDesc(String recourseDesc) {
		this.recourseDesc = recourseDesc;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
