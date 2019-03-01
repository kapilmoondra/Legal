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

@Entity(name = "against_inst_case_compliance")
public class AgainstInstitutionalCaseCompliance {
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;

	@JoinColumn(name = "compliance_id")
	@ManyToOne
	private Compliance compliance;

	@JoinColumn(name = "case_id")
	@ManyToOne
	private AgainstInstitutionalCase legalCase;

	@Column(name = "status")
	private String status;

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

	public Compliance getCompliance() {
		return compliance;
	}

	public void setCompliance(Compliance compliance) {
		this.compliance = compliance;
	}

	public AgainstInstitutionalCase getLegalCase() {
		return legalCase;
	}

	public void setLegalCase(AgainstInstitutionalCase legalCase) {
		this.legalCase = legalCase;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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
