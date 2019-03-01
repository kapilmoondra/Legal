package com.legalfriend.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity(name = "user")
public class User extends Auditable<String>{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "first_name")
	private String firstName;
	@Column(name = "last_name")
	private String lastName;
	@Column(name = "organization")
	private String organization;
	@JoinColumn(name = "fk_useraddress")
	@OneToOne(cascade = CascadeType.ALL)
	private UserAddress address;
	@JoinColumn(name = "fk_userlogin")
	@OneToOne(cascade = CascadeType.ALL)
	private UserLogin login;
	@JoinColumn(name = "fk_status")
	@OneToOne(cascade = CascadeType.ALL)
	private Status status;
	@Column(name = "email")
	private String email;
	@Column(name = "password")
	private String password;
	@Column(name = "mobile_number")
	private String mobileNumber;
	@JsonProperty
	@Column(name = "is_client")
	private Boolean isClient;
	@Column(name = "client_id")
	private Long clientId;
	@Column(name = "verified")
	private Boolean verified = Boolean.FALSE;
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "user_role", joinColumns = {
			@JoinColumn(name = "user_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "role_id", referencedColumnName = "id") })
	private List<Role> roles;

	@JoinColumn(name = "fk_branch_id")
	@ManyToOne
	private Branch branch;

	@JoinColumn(name = "fk_institution_id")
	@ManyToOne
	private Institution institution;

	@JoinColumn(name = "fk_customer_type")
	@ManyToOne(cascade = CascadeType.MERGE)
	private CustomerType customerType;

	@JoinColumn(name = "fk_user_type")
	@ManyToOne(cascade = CascadeType.MERGE)
	private UserType userType;

	@Transient
	private Long subscriptionId;

	@Transient
	private Date subscriptionEndDate;

	@Transient
	private Boolean showSubscriptionFlash = Boolean.FALSE;

	@Transient
	private Boolean subscriptionEnded = Boolean.FALSE;

	@Transient
	private Long daysLeftForRenew;

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public UserAddress getAddress() {
		return address;
	}

	public void setAddress(UserAddress address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public UserLogin getLogin() {
		return login;
	}

	public void setLogin(UserLogin login) {
		this.login = login;
	}

	public Boolean getIsClient() {
		return isClient;
	}

	public void setIsClient(Boolean isClient) {
		this.isClient = isClient;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public Boolean isVerified() {
		return verified;
	}

	public void setVerified(Boolean verified) {
		this.verified = verified;
	}

	public Long getSubscriptionId() {
		return subscriptionId;
	}

	public void setSubscriptionId(Long subscriptionId) {
		this.subscriptionId = subscriptionId;
	}

	public Boolean getVerified() {
		return verified;
	}

	public void setClient(boolean isClient) {
		this.isClient = isClient;
	}

	public Date getSubscriptionEndDate() {
		return subscriptionEndDate;
	}

	public void setSubscriptionEndDate(Date subscriptionEndDate) {
		this.subscriptionEndDate = subscriptionEndDate;
	}

	public Boolean getShowSubscriptionFlash() {
		return showSubscriptionFlash;
	}

	public void setShowSubscriptionFlash(Boolean showSubscriptionFlash) {
		this.showSubscriptionFlash = showSubscriptionFlash;
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	public Boolean getSubscriptionEnded() {
		return subscriptionEnded;
	}

	public void setSubscriptionEnded(Boolean subscriptionEnded) {
		this.subscriptionEnded = subscriptionEnded;
	}

	public Long getDaysLeftForRenew() {
		return daysLeftForRenew;
	}

	public void setDaysLeftForRenew(Long daysLeftForRenew) {
		this.daysLeftForRenew = daysLeftForRenew;
	}

	public CustomerType getCustomerType() {
		return customerType;
	}

	public void setCustomerType(CustomerType customerType) {
		this.customerType = customerType;
	}

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public Institution getInstitution() {
		return institution;
	}

	public void setInstitution(Institution institution) {
		this.institution = institution;
	}

}
