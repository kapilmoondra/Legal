package com.legalfriend.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.envers.Audited;
import org.springframework.format.annotation.DateTimeFormat;

@Entity(name = "for_institutional_case")
@Audited
public class ForInstitutionalCase extends Auditable<String> {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;

	@Column(name = "fk_institution_id")
	private Long institutionId;

	@Column(name = "title")
	private String title;

	@Column(name = "fk_branch_id")
	private Long branchId;

	@Column(name = "fk_user_id")
	private Long userId;

	@Column(name = "case_id")
	private String caseId;

	@Column(name = "child_case")
	private String childCase;

	@Column(name = "recourse")
	private String recourse;

	@Column(name = "disposed_off_file_no")
	private String disposedOffFileNo;

	@Column(name = "ground_for_closing_file")
	private String groundForClosingFile;

	@Transient
	private Long recourseId;

	@Column(name = "legal_case_id")
	private String legalCaseId;

	@Column(name = "region")
	private String region;

	@Column(name = "state")
	private String state;

	@Column(name = "location")
	private String location;

	@Column(name = "product")
	private String product;

	@Column(name = "product_group")
	private String productGroup;

	@Column(name = "loan_account_number")
	private Long loanAccountNumber;

	@Column(name = "customer_name")
	private String customerName;

	@Column(name = "pos_on_notice_date")
	private String posOnNoticeDate;

	@Column(name = "ro_execution_date")
	private String receiverOrderExecutionDate;

	@Column(name = "dpd_on_notice_date")
	private String dpdOnNoticeDate;

	@Column(name = "npa_stage_on_notice_date")
	private String npaStageOnNoticeDate;

	@Column(name = "notice_date")
	private String noticeDate;

	@Column(name = "notice_sent_date")
	private String noticeSentDate;

	@Column(name = "notice_postal_remarks")
	private String noticePostalRemarks;

	@Column(name = "serve_date")
	private String serveDate;

	@Column(name = "amount_involved")
	private Double amountInvolved;

	@Column(name = "case_type")
	private String caseType;

	@Column(name = "lawyer_name")
	private String lawyerName;

	@Column(name = "pos_on_filing_date")
	private String posOnFilingDate;

	@Column(name = "dpd_on_filing_date")
	private String dpdOnFilingDate;

	@Column(name = "npa_stage_on_filing_date")
	private String npaStageOnFilingDate;

	@Column(name = "filing_date")
	private String filingDate;

	@Column(name = "court_case_id")
	private String courtCaseId;

	@Column(name = "court_name")
	private String courtName;

	@Column(name = "court_place")
	private String courtPlace;

	@Column(name = "ro_applied")
	private String recieveOrderApplied;

	@Column(name = "ro_applied_date")
	private String receiverOrderAppliedDate;

	@Column(name = "ro_received_date")
	private String receiverOrderReceivedDate;

	@Column(name = "ro_status")
	private String receiveOrderStatus;

	@Column(name = "receiver_name")
	private String receiverName;

	@Column(name = "execution_filed")
	private String executionFiled;

	@Column(name = "pos_on_ep_filing_date")
	private String posOnEpFilingDate;

	@Column(name = "dpd_on_ep_filing_date")
	private String dpdOnEpFilingDate;

	@Column(name = "npa_stage_on_ep_filing_date")
	private String npaStageOnEpFilingDate;

	@Column(name = "execution_filing_date")
	private String executionFilingDate;

	@Column(name = "execution_case_no")
	private String executionCaseNo;

	@Column(name = "ep_court_name")
	private String ePCourtName;

	@Column(name = "ep_court_place")
	private String ePCourtPlace;

	@Column(name = "advocate_of_ep")
	private String advocateofEp;

	@Column(name = "case_stage")
	private String caseStage;

	@Column(name = "stage_in_court")
	private String stageInCourt;

	@Column(name = "previous_hearing_date")
	private String previousHearingDate;

	@Column(name = "next_hearing_date")
	private String nextHearingDate;

	@Column(name = "ndoh_null_reason")
	private String ndohNullReason;

	@Column(name = "legal_manager")
	private String legalManager;

	@Column(name = "remarks")
	private String remarks;

	@Column(name = "repo_flag")
	private boolean repoFlag;

	@Column(name = "case_status")
	private String caseStatus;

	@Column(name = "closure_date")
	private String closureDate;

	@Column(name = "closure_reporting_date")
	private String closureReportingDate;

	@Column(name = "total_Amt_Recovered")
	private Double totalAmtRecovered;

	@Column(name = "account_status")
	private String accountStatus;

	@Column(name = "case_filed_against")
	private String caseFiledAgainst;

	@Column(name = "pos_on_current_date")
	private String posOnCurrentDate;

	@Column(name = "dpd_on_current_date")
	private String dpdOnCurrentDate;

	@Column(name = "npa_stage_on_current_date")
	private String npaStageOnCurrentDate;

	@Column(name = "case_criticality_level")
	private String caseCriticalityLevel;

	@Column(name = "type")
	private String type;

	@Column(name = "parent_id")
	private Long parentId;

	@Column(name = "generated_by")
	private String generatedBy;

	@Column(name = "spdc_notice_ack_remarks")
	private String spdcNoticeAckRemarks;

	@Column(name = "spdc_notice_service_date")
	private String spdcNoticeServiceDate;

	@Column(name = "chq_no_1")
	private String chqNo1;

	@Column(name = "chq_no_2")
	private String chqNo2;

	@Column(name = "chq_no_3")
	private String chqNo3;

	@Column(name = "bank_name")
	private String bankName;

	@Column(name = "police_complaint_filed_date")
	private String policeComplaintFiledDate;

	@Column(name = "notice_Reference_number")
	private Long noticeReferenceNumber;

	@Column(name = "loan_amount")
	private Double loanAmount;

	@Column(name = "disbursal_date")
	private String disbursalDate;

	@Column(name = "asset_details")
	private String assetDetails;

	@Column(name = "overdue_amt_on_notice_date")
	private Double overdueAmtOnNoticeDate;

	@Column(name = "sec132_notice_Date")
	private String sec132NoticeDate;

	@Column(name = "sec132_notice_postal_remarks")
	private String sec132NoticePostalRemarks;

	@Column(name = "sec132_publication_date")
	private String sec132PublicationDate;

	@Column(name = "notice_amount")
	private Double noticeAmount;

	@Column(name = "peaceful_possession_notice_date")
	private String peacefulPossessionNoticeDate;

	@Column(name = "sec134_notice_postal_remarks")
	private String sec134NoticePostalRemarks;

	@Column(name = "symbolic_possession_date")
	private String symbolicPossessionDate;

	@Column(name = "sec134_publication_date")
	private String sec134PublicationDate;

	@Column(name = "sec14_filing_date")
	private String sec14FilingDate;

	@Column(name = "order_received_date")
	private String orderReceivedDate;

	@Column(name = "physical_possession_date")
	private String physicalPossessionDate;

	@Column(name = "publication_date_physical_possession_notice")
	private String publicationDatePhysicalPossessionNotice;

	@Column(name = "valuation_date")
	private String valuationDate;

	@Column(name = "valuation_amount")
	private Double valuationAmount;

	@Column(name = "cooling_period_notice_date")
	private String coolingPeriodNoticeDate;

	@Column(name = "reserve_price")
	private Double reservePrice;

	@Column(name = "sale_notice_date")
	private String saleNoticeDate;

	@Column(name = "sale_notice_publication_date")
	private String saleNoticePublicationDate;

	@Column(name = "auction_date")
	private String auctionDate;

	@Column(name = "sale_date")
	private String saleDate;

	@Column(name = "next_action_plan")
	private String nextActionPlan;

	@Column(name = "next_action_date")
	private String nextActionDate;

	@Column(name = "notice_type")
	private String noticeType;

	@Column(name = "guarantors_name")
	private String guarantorsName;

	@Column(name = "notice_date_appointment_arbitrator")
	private String noticeDateAppointmentArbitrator;

	@Column(name = "arbitrator_appointed")
	private String arbitratorAppointed;

	@Column(name = "sec17_order_applied")
	private String sec17OrderApplied;

	@Column(name = "sec17_order_applied_date")
	private String sec17OrderAppliedDate;

	@Column(name = "sec17_order_received_date")
	private String sec17OrderReceivedDate;

	@Column(name = "sec17_order_Status")
	private String sec17OrderStatus;

	@Column(name = "sec9_applied")
	private String sec9Applied;

	@Column(name = "sec9_legal_case_id")
	private String sec9LegalCaseId;

	@Column(name = "award_date")
	private String awardDate;

	@Column(name = "award_amount")
	private Double awardAmount;

	@Column(name = "award_copy_provided")
	private String awardCopyProvided;

	@Column(name = "transmission_required")
	private String transmissionRequired;

	@Column(name = "appeal_us_34_Filed")
	private String appealUs34Filed;

	@Column(name = "arbitration_initiated")
	private String arbitrationInitiated;

	@Column(name = "arbitration_case_id")
	private String arbitrationCaseId;

	@Column(name = "date_of_conduct")
	private String dateOfConduct;

	@Column(name = "whether_customer_attended")
	private boolean whetherCustomerAttended;

	@Column(name = "settlement_amt")
	private Double settlementAmt;

	@Column(name = "file_name")
	private String fileName;

	@Column(name = "created_date")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/YYYY")
	private Date createdDate;

	@Column(name = "last_updated_date")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/YYYY")
	private Date lastUpdated;

	private String completionDate;

	@Column(name = "remark_history", length = 100000)
	private String remarkHistory;

	@Column(name = "remark_file")
	private String remarkFile;

	@Column(name = "compliance")
	private Boolean compliance;

	@Transient
	private List<CaseFiles> caseFiles;

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

	public String getRecourse() {
		return recourse;
	}

	public void setRecourse(String recourse) {
		this.recourse = recourse;
	}

	public String getLegalCaseId() {
		return legalCaseId;
	}

	public void setLegalCaseId(String legalCaseId) {
		this.legalCaseId = legalCaseId;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getProductGroup() {
		return productGroup;
	}

	public void setProductGroup(String productGroup) {
		this.productGroup = productGroup;
	}

	public Long getLoanAccountNumber() {
		return loanAccountNumber;
	}

	public void setLoanAccountNumber(Long loanAccountNumber) {
		this.loanAccountNumber = loanAccountNumber;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getNoticeDate() {
		return noticeDate;
	}

	public void setNoticeDate(String noticeDate) {
		this.noticeDate = noticeDate;
	}

	public String getNoticeSentDate() {
		return noticeSentDate;
	}

	public void setNoticeSentDate(String noticeSentDate) {
		this.noticeSentDate = noticeSentDate;
	}

	public String getNoticePostalRemarks() {
		return noticePostalRemarks;
	}

	public void setNoticePostalRemarks(String noticePostalRemarks) {
		this.noticePostalRemarks = noticePostalRemarks;
	}

	public String getServeDate() {
		return serveDate;
	}

	public void setServeDate(String serveDate) {
		this.serveDate = serveDate;
	}

	public Double getAmountInvolved() {
		return amountInvolved;
	}

	public void setAmountInvolved(Double amountInvolved) {
		this.amountInvolved = amountInvolved;
	}

	public String getCaseType() {
		return caseType;
	}

	public void setCaseType(String caseType) {
		this.caseType = caseType;
	}

	public String getLawyerName() {
		return lawyerName;
	}

	public void setLawyerName(String lawyerName) {
		this.lawyerName = lawyerName;
	}

	public String getPosOnFilingDate() {
		return posOnFilingDate;
	}

	public void setPosOnFilingDate(String posOnFilingDate) {
		this.posOnFilingDate = posOnFilingDate;
	}

	public String getDpdOnFilingDate() {
		return dpdOnFilingDate;
	}

	public void setDpdOnFilingDate(String dpdOnFilingDate) {
		this.dpdOnFilingDate = dpdOnFilingDate;
	}

	public String getNpaStageOnFilingDate() {
		return npaStageOnFilingDate;
	}

	public void setNpaStageOnFilingDate(String npaStageOnFilingDate) {
		this.npaStageOnFilingDate = npaStageOnFilingDate;
	}

	public String getFilingDate() {
		return filingDate;
	}

	public void setFilingDate(String filingDate) {
		this.filingDate = filingDate;
	}

	public String getCourtCaseId() {
		return courtCaseId;
	}

	public void setCourtCaseId(String courtCaseId) {
		this.courtCaseId = courtCaseId;
	}

	public String getCourtName() {
		return courtName;
	}

	public void setCourtName(String courtName) {
		this.courtName = courtName;
	}

	public String getCourtPlace() {
		return courtPlace;
	}

	public void setCourtPlace(String courtPlace) {
		this.courtPlace = courtPlace;
	}

	public String getRecieveOrderApplied() {
		return recieveOrderApplied;
	}

	public void setRecieveOrderApplied(String recieveOrderApplied) {
		this.recieveOrderApplied = recieveOrderApplied;
	}

	public String getReceiverOrderAppliedDate() {
		return receiverOrderAppliedDate;
	}

	public void setReceiverOrderAppliedDate(String receiverOrderAppliedDate) {
		this.receiverOrderAppliedDate = receiverOrderAppliedDate;
	}

	public String getReceiverOrderReceivedDate() {
		return receiverOrderReceivedDate;
	}

	public void setReceiverOrderReceivedDate(String receiverOrderReceivedDate) {
		this.receiverOrderReceivedDate = receiverOrderReceivedDate;
	}

	public String getReceiveOrderStatus() {
		return receiveOrderStatus;
	}

	public void setReceiveOrderStatus(String receiveOrderStatus) {
		this.receiveOrderStatus = receiveOrderStatus;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getExecutionFiled() {
		return executionFiled;
	}

	public void setExecutionFiled(String executionFiled) {
		this.executionFiled = executionFiled;
	}

	public String getPosOnEpFilingDate() {
		return posOnEpFilingDate;
	}

	public void setPosOnEpFilingDate(String posOnEpFilingDate) {
		this.posOnEpFilingDate = posOnEpFilingDate;
	}

	public String getDpdOnEpFilingDate() {
		return dpdOnEpFilingDate;
	}

	public void setDpdOnEpFilingDate(String dpdOnEpFilingDate) {
		this.dpdOnEpFilingDate = dpdOnEpFilingDate;
	}

	public String getNpaStageOnEpFilingDate() {
		return npaStageOnEpFilingDate;
	}

	public void setNpaStageOnEpFilingDate(String npaStageOnEpFilingDate) {
		this.npaStageOnEpFilingDate = npaStageOnEpFilingDate;
	}

	public String getExecutionFilingDate() {
		return executionFilingDate;
	}

	public void setExecutionFilingDate(String executionFilingDate) {
		this.executionFilingDate = executionFilingDate;
	}

	public String getExecutionCaseNo() {
		return executionCaseNo;
	}

	public void setExecutionCaseNo(String executionCaseNo) {
		this.executionCaseNo = executionCaseNo;
	}

	public String getePCourtName() {
		return ePCourtName;
	}

	public void setePCourtName(String ePCourtName) {
		this.ePCourtName = ePCourtName;
	}

	public String getePCourtPlace() {
		return ePCourtPlace;
	}

	public void setePCourtPlace(String ePCourtPlace) {
		this.ePCourtPlace = ePCourtPlace;
	}

	public String getAdvocateofEp() {
		return advocateofEp;
	}

	public void setAdvocateofEp(String advocateofEp) {
		this.advocateofEp = advocateofEp;
	}

	public String getCaseStage() {
		return caseStage;
	}

	public void setCaseStage(String caseStage) {
		this.caseStage = caseStage;
	}

	public String getStageInCourt() {
		return stageInCourt;
	}

	public void setStageInCourt(String stageInCourt) {
		this.stageInCourt = stageInCourt;
	}

	public String getPreviousHearingDate() {
		return previousHearingDate;
	}

	public void setPreviousHearingDate(String previousHearingDate) {
		this.previousHearingDate = previousHearingDate;
	}

	public String getNextHearingDate() {
		return nextHearingDate;
	}

	public void setNextHearingDate(String nextHearingDate) {
		this.nextHearingDate = nextHearingDate;
	}

	public String getNdohNullReason() {
		return ndohNullReason;
	}

	public void setNdohNullReason(String ndohNullReason) {
		this.ndohNullReason = ndohNullReason;
	}

	public String getLegalManager() {
		return legalManager;
	}

	public void setLegalManager(String legalManager) {
		this.legalManager = legalManager;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public boolean isRepoFlag() {
		return repoFlag;
	}

	public void setRepoFlag(boolean repoFlag) {
		this.repoFlag = repoFlag;
	}

	public String getCaseStatus() {
		return caseStatus;
	}

	public void setCaseStatus(String caseStatus) {
		this.caseStatus = caseStatus;
	}

	public String getClosureReportingDate() {
		return closureReportingDate;
	}

	public void setClosureReportingDate(String closureReportingDate) {
		this.closureReportingDate = closureReportingDate;
	}

	public Double getTotalAmtRecovered() {
		return totalAmtRecovered;
	}

	public void setTotalAmtRecovered(Double totalAmtRecovered) {
		this.totalAmtRecovered = totalAmtRecovered;
	}

	public String getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}

	public String getCaseFiledAgainst() {
		return caseFiledAgainst;
	}

	public void setCaseFiledAgainst(String caseFiledAgainst) {
		this.caseFiledAgainst = caseFiledAgainst;
	}

	public String getPosOnCurrentDate() {
		return posOnCurrentDate;
	}

	public void setPosOnCurrentDate(String posOnCurrentDate) {
		this.posOnCurrentDate = posOnCurrentDate;
	}

	public String getDpdOnCurrentDate() {
		return dpdOnCurrentDate;
	}

	public void setDpdOnCurrentDate(String dpdOnCurrentDate) {
		this.dpdOnCurrentDate = dpdOnCurrentDate;
	}

	public String getNpaStageOnCurrentDate() {
		return npaStageOnCurrentDate;
	}

	public void setNpaStageOnCurrentDate(String npaStageOnCurrentDate) {
		this.npaStageOnCurrentDate = npaStageOnCurrentDate;
	}

	public String getCaseCriticalityLevel() {
		return caseCriticalityLevel;
	}

	public void setCaseCriticalityLevel(String caseCriticalityLevel) {
		this.caseCriticalityLevel = caseCriticalityLevel;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getGeneratedBy() {
		return generatedBy;
	}

	public void setGeneratedBy(String generatedBy) {
		this.generatedBy = generatedBy;
	}

	public String getSpdcNoticeAckRemarks() {
		return spdcNoticeAckRemarks;
	}

	public void setSpdcNoticeAckRemarks(String spdcNoticeAckRemarks) {
		this.spdcNoticeAckRemarks = spdcNoticeAckRemarks;
	}

	public String getSpdcNoticeServiceDate() {
		return spdcNoticeServiceDate;
	}

	public void setSpdcNoticeServiceDate(String spdcNoticeServiceDate) {
		this.spdcNoticeServiceDate = spdcNoticeServiceDate;
	}

	public String getChqNo1() {
		return chqNo1;
	}

	public void setChqNo1(String chqNo1) {
		this.chqNo1 = chqNo1;
	}

	public String getChqNo2() {
		return chqNo2;
	}

	public void setChqNo2(String chqNo2) {
		this.chqNo2 = chqNo2;
	}

	public String getChqNo3() {
		return chqNo3;
	}

	public void setChqNo3(String chqNo3) {
		this.chqNo3 = chqNo3;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getPoliceComplaintFiledDate() {
		return policeComplaintFiledDate;
	}

	public void setPoliceComplaintFiledDate(String policeComplaintFiledDate) {
		this.policeComplaintFiledDate = policeComplaintFiledDate;
	}

	public Long getNoticeReferenceNumber() {
		return noticeReferenceNumber;
	}

	public void setNoticeReferenceNumber(Long noticeReferenceNumber) {
		this.noticeReferenceNumber = noticeReferenceNumber;
	}

	public Double getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(Double loanAmount) {
		this.loanAmount = loanAmount;
	}

	public String getDisbursalDate() {
		return disbursalDate;
	}

	public void setDisbursalDate(String disbursalDate) {
		this.disbursalDate = disbursalDate;
	}

	public String getAssetDetails() {
		return assetDetails;
	}

	public void setAssetDetails(String assetDetails) {
		this.assetDetails = assetDetails;
	}

	public Double getOverdueAmtOnNoticeDate() {
		return overdueAmtOnNoticeDate;
	}

	public void setOverdueAmtOnNoticeDate(Double overdueAmtOnNoticeDate) {
		this.overdueAmtOnNoticeDate = overdueAmtOnNoticeDate;
	}

	public String getSec132NoticeDate() {
		return sec132NoticeDate;
	}

	public void setSec132NoticeDate(String sec132NoticeDate) {
		this.sec132NoticeDate = sec132NoticeDate;
	}

	public String getSec132NoticePostalRemarks() {
		return sec132NoticePostalRemarks;
	}

	public void setSec132NoticePostalRemarks(String sec132NoticePostalRemarks) {
		this.sec132NoticePostalRemarks = sec132NoticePostalRemarks;
	}

	public String getSec132PublicationDate() {
		return sec132PublicationDate;
	}

	public void setSec132PublicationDate(String sec132PublicationDate) {
		this.sec132PublicationDate = sec132PublicationDate;
	}

	public Double getNoticeAmount() {
		return noticeAmount;
	}

	public void setNoticeAmount(Double noticeAmount) {
		this.noticeAmount = noticeAmount;
	}

	public String getPeacefulPossessionNoticeDate() {
		return peacefulPossessionNoticeDate;
	}

	public void setPeacefulPossessionNoticeDate(String peacefulPossessionNoticeDate) {
		this.peacefulPossessionNoticeDate = peacefulPossessionNoticeDate;
	}

	public String getSec134NoticePostalRemarks() {
		return sec134NoticePostalRemarks;
	}

	public void setSec134NoticePostalRemarks(String sec134NoticePostalRemarks) {
		this.sec134NoticePostalRemarks = sec134NoticePostalRemarks;
	}

	public String getSymbolicPossessionDate() {
		return symbolicPossessionDate;
	}

	public void setSymbolicPossessionDate(String symbolicPossessionDate) {
		this.symbolicPossessionDate = symbolicPossessionDate;
	}

	public String getSec134PublicationDate() {
		return sec134PublicationDate;
	}

	public void setSec134PublicationDate(String sec134PublicationDate) {
		this.sec134PublicationDate = sec134PublicationDate;
	}

	public String getSec14FilingDate() {
		return sec14FilingDate;
	}

	public void setSec14FilingDate(String sec14FilingDate) {
		this.sec14FilingDate = sec14FilingDate;
	}

	public String getOrderReceivedDate() {
		return orderReceivedDate;
	}

	public void setOrderReceivedDate(String orderReceivedDate) {
		this.orderReceivedDate = orderReceivedDate;
	}

	public String getPhysicalPossessionDate() {
		return physicalPossessionDate;
	}

	public void setPhysicalPossessionDate(String physicalPossessionDate) {
		this.physicalPossessionDate = physicalPossessionDate;
	}

	public String getPublicationDatePhysicalPossessionNotice() {
		return publicationDatePhysicalPossessionNotice;
	}

	public void setPublicationDatePhysicalPossessionNotice(String publicationDatePhysicalPossessionNotice) {
		this.publicationDatePhysicalPossessionNotice = publicationDatePhysicalPossessionNotice;
	}

	public String getValuationDate() {
		return valuationDate;
	}

	public void setValuationDate(String valuationDate) {
		this.valuationDate = valuationDate;
	}

	public Double getValuationAmount() {
		return valuationAmount;
	}

	public void setValuationAmount(Double valuationAmount) {
		this.valuationAmount = valuationAmount;
	}

	public String getCoolingPeriodNoticeDate() {
		return coolingPeriodNoticeDate;
	}

	public void setCoolingPeriodNoticeDate(String coolingPeriodNoticeDate) {
		this.coolingPeriodNoticeDate = coolingPeriodNoticeDate;
	}

	public Double getReservePrice() {
		return reservePrice;
	}

	public void setReservePrice(Double reservePrice) {
		this.reservePrice = reservePrice;
	}

	public String getSaleNoticeDate() {
		return saleNoticeDate;
	}

	public void setSaleNoticeDate(String saleNoticeDate) {
		this.saleNoticeDate = saleNoticeDate;
	}

	public String getSaleNoticePublicationDate() {
		return saleNoticePublicationDate;
	}

	public void setSaleNoticePublicationDate(String saleNoticePublicationDate) {
		this.saleNoticePublicationDate = saleNoticePublicationDate;
	}

	public String getAuctionDate() {
		return auctionDate;
	}

	public void setAuctionDate(String auctionDate) {
		this.auctionDate = auctionDate;
	}

	public String getSaleDate() {
		return saleDate;
	}

	public void setSaleDate(String saleDate) {
		this.saleDate = saleDate;
	}

	public String getNextActionPlan() {
		return nextActionPlan;
	}

	public void setNextActionPlan(String nextActionPlan) {
		this.nextActionPlan = nextActionPlan;
	}

	public String getNextActionDate() {
		return nextActionDate;
	}

	public void setNextActionDate(String nextActionDate) {
		this.nextActionDate = nextActionDate;
	}

	public String getNoticeType() {
		return noticeType;
	}

	public void setNoticeType(String noticeType) {
		this.noticeType = noticeType;
	}

	public String getGuarantorsName() {
		return guarantorsName;
	}

	public void setGuarantorsName(String guarantorsName) {
		this.guarantorsName = guarantorsName;
	}

	public String getNoticeDateAppointmentArbitrator() {
		return noticeDateAppointmentArbitrator;
	}

	public void setNoticeDateAppointmentArbitrator(String noticeDateAppointmentArbitrator) {
		this.noticeDateAppointmentArbitrator = noticeDateAppointmentArbitrator;
	}

	public String getArbitratorAppointed() {
		return arbitratorAppointed;
	}

	public void setArbitratorAppointed(String arbitratorAppointed) {
		this.arbitratorAppointed = arbitratorAppointed;
	}

	public String getSec17OrderApplied() {
		return sec17OrderApplied;
	}

	public void setSec17OrderApplied(String sec17OrderApplied) {
		this.sec17OrderApplied = sec17OrderApplied;
	}

	public String getSec17OrderAppliedDate() {
		return sec17OrderAppliedDate;
	}

	public void setSec17OrderAppliedDate(String sec17OrderAppliedDate) {
		this.sec17OrderAppliedDate = sec17OrderAppliedDate;
	}

	public String getSec17OrderReceivedDate() {
		return sec17OrderReceivedDate;
	}

	public void setSec17OrderReceivedDate(String sec17OrderReceivedDate) {
		this.sec17OrderReceivedDate = sec17OrderReceivedDate;
	}

	public String getSec17OrderStatus() {
		return sec17OrderStatus;
	}

	public void setSec17OrderStatus(String sec17OrderStatus) {
		this.sec17OrderStatus = sec17OrderStatus;
	}

	public String getSec9Applied() {
		return sec9Applied;
	}

	public void setSec9Applied(String sec9Applied) {
		this.sec9Applied = sec9Applied;
	}

	public String getSec9LegalCaseId() {
		return sec9LegalCaseId;
	}

	public void setSec9LegalCaseId(String sec9LegalCaseId) {
		this.sec9LegalCaseId = sec9LegalCaseId;
	}

	public String getAwardDate() {
		return awardDate;
	}

	public void setAwardDate(String awardDate) {
		this.awardDate = awardDate;
	}

	public Double getAwardAmount() {
		return awardAmount;
	}

	public void setAwardAmount(Double awardAmount) {
		this.awardAmount = awardAmount;
	}

	public String getAwardCopyProvided() {
		return awardCopyProvided;
	}

	public void setAwardCopyProvided(String awardCopyProvided) {
		this.awardCopyProvided = awardCopyProvided;
	}

	public String getTransmissionRequired() {
		return transmissionRequired;
	}

	public void setTransmissionRequired(String transmissionRequired) {
		this.transmissionRequired = transmissionRequired;
	}

	public String getAppealUs34Filed() {
		return appealUs34Filed;
	}

	public void setAppealUs34Filed(String appealUs34Filed) {
		this.appealUs34Filed = appealUs34Filed;
	}

	public String getArbitrationInitiated() {
		return arbitrationInitiated;
	}

	public void setArbitrationInitiated(String arbitrationInitiated) {
		this.arbitrationInitiated = arbitrationInitiated;
	}

	public String getArbitrationCaseId() {
		return arbitrationCaseId;
	}

	public void setArbitrationCaseId(String arbitrationCaseId) {
		this.arbitrationCaseId = arbitrationCaseId;
	}

	public String getDateOfConduct() {
		return dateOfConduct;
	}

	public void setDateOfConduct(String dateOfConduct) {
		this.dateOfConduct = dateOfConduct;
	}

	public boolean isWhetherCustomerAttended() {
		return whetherCustomerAttended;
	}

	public void setWhetherCustomerAttended(boolean whetherCustomerAttended) {
		this.whetherCustomerAttended = whetherCustomerAttended;
	}

	public Double getSettlementAmt() {
		return settlementAmt;
	}

	public void setSettlementAmt(Double settlementAmt) {
		this.settlementAmt = settlementAmt;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getClosureDate() {
		return closureDate;
	}

	public void setClosureDate(String closureDate) {
		this.closureDate = closureDate;
	}

	public Long getInstitutionId() {
		return institutionId;
	}

	public void setInstitutionId(Long institutionId) {
		this.institutionId = institutionId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public List<CaseFiles> getCaseFiles() {
		return caseFiles;
	}

	public void setCaseFiles(List<CaseFiles> caseFiles) {
		this.caseFiles = caseFiles;
	}

	public Long getBranchId() {
		return branchId;
	}

	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public Long getRecourseId() {
		return recourseId;
	}

	public void setRecourseId(Long recourseId) {
		this.recourseId = recourseId;
	}

	public String getCompletionDate() {
		return completionDate;
	}

	public void setCompletionDate(String completionDate) {
		this.completionDate = completionDate;
	}

	public Boolean getCompliance() {
		return compliance;
	}

	public void setCompliance(Boolean compliance) {
		this.compliance = compliance;
	}

	public String getChildCase() {
		return childCase;
	}

	public void setChildCase(String childCase) {
		this.childCase = childCase;
	}

	public String getDisposedOffFileNo() {
		return disposedOffFileNo;
	}

	public void setDisposedOffFileNo(String disposedOffFileNo) {
		this.disposedOffFileNo = disposedOffFileNo;
	}

	public String getGroundForClosingFile() {
		return groundForClosingFile;
	}

	public void setGroundForClosingFile(String groundForClosingFile) {
		this.groundForClosingFile = groundForClosingFile;
	}

	public String getReceiverOrderExecutionDate() {
		return receiverOrderExecutionDate;
	}

	public void setReceiverOrderExecutionDate(String receiverOrderExecutionDate) {
		this.receiverOrderExecutionDate = receiverOrderExecutionDate;
	}

	public String getPosOnNoticeDate() {
		return posOnNoticeDate;
	}

	public void setPosOnNoticeDate(String posOnNoticeDate) {
		this.posOnNoticeDate = posOnNoticeDate;
	}

	public String getDpdOnNoticeDate() {
		return dpdOnNoticeDate;
	}

	public void setDpdOnNoticeDate(String dpdOnNoticeDate) {
		this.dpdOnNoticeDate = dpdOnNoticeDate;
	}

	public String getNpaStageOnNoticeDate() {
		return npaStageOnNoticeDate;
	}

	public void setNpaStageOnNoticeDate(String npaStageOnNoticeDate) {
		this.npaStageOnNoticeDate = npaStageOnNoticeDate;
	}

	public String getRemarkHistory() {
		return remarkHistory;
	}

	public void setRemarkHistory(String remarkHistory) {
		this.remarkHistory = remarkHistory;
	}

	public String getRemarkFile() {
		return remarkFile;
	}

	public void setRemarkFile(String remarkFile) {
		this.remarkFile = remarkFile;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
