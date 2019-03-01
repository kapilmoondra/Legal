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

@Entity(name = "billing")
public class Billing {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;

	@JoinColumn(name = "fk_institution_id")
	@ManyToOne
	private Institution institution;

	@Column(name = "amount")
	private Double amount;

	@Column(name = "billing_desc")
	private String billingDesc;

	@JoinColumn(name = "fk_stage_id")
	@ManyToOne
	private Stage stage;

	@JoinColumn(name = "fk_recourse_id")
	@ManyToOne
	private Recourse recourse;

	@JoinColumn(name = "fk_invoice_id")
	@ManyToOne
	private Invoice invoice;

	@Column(name = "fk_user_id")
	private Long userId;

	@Column(name = "status")
	private String status;

	@Column(name = "case_id")
	private String caseId;

	@Column(name = "billing_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date billingDate;
	
	@JoinColumn(name = "fk_branch_id")
	@ManyToOne
	private Branch branch;
	
	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUser(Long userId) {
		this.userId = userId;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public Recourse getRecourse() {
		return recourse;
	}

	public void setRecourse(Recourse recourse) {
		this.recourse = recourse;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Institution getInstitution() {
		return institution;
	}

	public void setInstitution(Institution institution) {
		this.institution = institution;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public Date getBillingDate() {
		return billingDate;
	}

	public void setBillingDate(Date billingDate) {
		this.billingDate = billingDate;
	}

	public Invoice getInvoice() {
		return invoice;
	}

	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	public String getBillingDesc() {
		return billingDesc;
	}

	public void setBillingDesc(String billingDesc) {
		this.billingDesc = billingDesc;
	}

}
