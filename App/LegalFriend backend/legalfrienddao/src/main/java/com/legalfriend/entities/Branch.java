package com.legalfriend.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "branch")
public class Branch {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;
	@Column(name = "branch_name")
	private String branchName;
	@Column(name = "branch_code")
	private String branchCode;
	@Column(name = "branch_address")
	private String branchAddress;
	@Column(name = "branch_contact")
	private String branchContact;

	@Column(name = "fk_city_id")
	private Long cityId;

	@Column(name = "fk_user_id")
	private Long userId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getBranchAddress() {
		return branchAddress;
	}

	public void setBranchAddress(String branchAddress) {
		this.branchAddress = branchAddress;
	}

	public String getBranchContact() {
		return branchContact;
	}

	public void setBranchContact(String branchContact) {
		this.branchContact = branchContact;
	}

	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
