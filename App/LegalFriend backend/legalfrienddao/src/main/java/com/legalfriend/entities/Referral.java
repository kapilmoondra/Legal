package com.legalfriend.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity(name = "referral")
public class Referral {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "email")
	private String email;
	
	@Column(name = "phone")
	private String phone;

	@Column(name = "fk_user_referrer")
	private Long referrerId;

	@Column(name = "referral_code")
	private String referralCode;

	@Column(name = "referral_expiry_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date referralExpiryDate;

	@Column(name = "singup_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date signUpDate;

	@Column(name = "paid_user_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date paidUserDate;

	@Column(name = "created_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@Column(name = "updated_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getReferrerId() {
		return referrerId;
	}

	public void setReferrerId(Long referrerId) {
		this.referrerId = referrerId;
	}

	public String getReferralCode() {
		return referralCode;
	}

	public void setReferralCode(String referralCode) {
		this.referralCode = referralCode;
	}

	public Date getReferralExpiryDate() {
		return referralExpiryDate;
	}

	public void setReferralExpiryDate(Date referralExpiryDate) {
		this.referralExpiryDate = referralExpiryDate;
	}

	public Date getSignUpDate() {
		return signUpDate;
	}

	public void setSignUpDate(Date signUpDate) {
		this.signUpDate = signUpDate;
	}

	public Date getPaidUserDate() {
		return paidUserDate;
	}

	public void setPaidUserDate(Date paidUserDate) {
		this.paidUserDate = paidUserDate;
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

}
