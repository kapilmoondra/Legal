package com.legalfriend.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "institution")
public class Institution {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;
	@Column(name = "institution_name")
	private String institutionName;
	@Column(name = "address")
	private String address;
	@Column(name = "contact_name")
	private String contactName;
	@Column(name = "phone")
	private String phone;
	@Column(name = "fk_user_id")
	private Long userId;
	@Column(name = "fk_city")
	private Long fkCity;
	@Column(name = "billing_addr")
	private String billingAddr;
	@Column(name = "default_institution")
	private Boolean defaultInstitution;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getInstitutionName() {
		return institutionName;
	}

	public void setInstitutionName(String institutionName) {
		this.institutionName = institutionName;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getBillingAddr() {
		return billingAddr;
	}

	public void setBillingAddr(String billingAddr) {
		this.billingAddr = billingAddr;
	}

	public Long getFkCity() {
		return fkCity;
	}

	public void setFkCity(Long fkCity) {
		this.fkCity = fkCity;
	}

	public Boolean getDefaultInstitution() {
		return defaultInstitution;
	}

	public void setDefaultInstitution(Boolean defaultInstitution) {
		this.defaultInstitution = defaultInstitution;
	}

}
