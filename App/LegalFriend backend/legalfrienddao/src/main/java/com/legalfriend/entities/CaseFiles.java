package com.legalfriend.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity(name = "case_files")
public class CaseFiles {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;

	@Column(name = "case_id")
	private String caseId;

	@Column(name = "is_institutional")
	private Boolean isInstitutional;

	@Column(name = "file_path")
	private String filePath;

	@Column(name = "file_name")
	private String fileName;

	@Column(name = "remark")
	private String remark;

	@Column(name = "remark_only")
	private Boolean remarkOnly = false;

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

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public Boolean getIsInstitutional() {
		return isInstitutional;
	}

	public void setIsInstitutional(Boolean isInstitutional) {
		this.isInstitutional = isInstitutional;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Boolean getRemarkOnly() {
		return remarkOnly;
	}

	public void setRemarkOnly(Boolean remarkOnly) {
		this.remarkOnly = remarkOnly;
	}

}
