package com.legalfriend.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "legal_case_files")
public class CaseFileDetails {
	
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;
	
	@Column(name = "caseId")
	private String caseId;
	
	@Column(name = "recourse")
	private String recourse;
	
	@Column(name = "legalCaseID")
	private String legalCaseID;
	
	@Column(name = "region")
	private String region;
	
	@Column(name = "state")
	private String state;
	
	@Column(name = "location")
	private String location;
	
	@Column(name = "product")
	private String product;
	
	@Column(name = "productGroup")
	private String productGroup;
	
	@Column(name = "loanAccountNumber")
	private long loanAccountNumber;
	
	@Column(name = "customerName")
	private String customerName;
	
	@Column(name = "posNoticeDate")
	private Date posNoticeDate;
	
	@Column(name = "dpdNoticeDate")
	private Date dpdNoticeDate;
	
	@Column(name = "npaStageNoticeDate")	
	private Date npaStageNoticeDate;
	
	@Column(name = "noticeDate")
	private Date noticeDate;
	
	@Column(name = "noticeSentDate")
	private Date noticeSentDate;
	
	@Column(name = "noticePostalRemarks")
	private String noticePostalRemarks;
	
	
	/*private Date serveDate;
	private long amountInvolved;
	private String caseType;
	private String lawyerName;
	private Date posFilingDate;
	private Date dpdFilingDate;
	private Date npaStageFilingDate;
	private Date dateOfFilingCase;
	private long courtCaseId;
	private String courtName;
	private String courtPlace;
	private boolean recieveOrderApplied;
	private Date receiverOrderAppliedDate;
	private Date receiverOrderReceivedDate;
	private String receiveOrderStatus;
	private String receiverName;
	private String executionFiled;
	private Date pos_ep_FilingDate;
	private Date dpd_ep_FilingDate;
	private Date npa_stage_ep_FilingDate;
	private Date executionFilingDate;
	private long executionCaseNo;
	private String ePCourtName;
	private String ePCourtPlace;
	private String advocateofEP;
	private String caseStage;
	private String stageInCourt;
	private Date previousHearingDate;
	private Date nextHearingDate;
	private String ndohNullReason;
	private String legalManager;
	private String remarks;
	private boolean repoFlag;
	private String caseStatus;
	private String cosureDate;
	private Date closureReportingDate;
	private long totalAmtRecovered;
	private String accountStatus;
	private String caseFiledAgainst;
	private Date posAsOnCurrentSystemDate;
	private Date dpdAsOnCurrentSystemDate;
	private Date npaStageAsOnCurrentSystemDate;
	private String caseCriticalityLevel;
	private String type;
	private long parentID;
	private String generatedBy;
	private String spdcNoticeAcknowledgementRemarks;
	private Date spdcNoticeDateServiceDate;
	private String chqnNo1;
	private String chqnNo2;
	private String chqnNo3;
	private String bankName;
	private Date policeComplaintFiledDate;
	private long noticeRefrenceNumber;
	private long loanAmount;
	private Date disbursalDate;
	private String assetDetails;
	private long overdueAmountAsOnNoticeDate;
	private Date sec13_2NoticeDate;
	private String sec13_2NoticePostalRemarks;
	private Date sec13_2PublicationDate;
	private long noticeAmount;
	private Date peacefulPossessionNoticeDate;
	private String sec13_4NoticePostalRemarks;
	private Date symbolicPossessionDate;
	private Date sec13_4PublicationDate;
	private Date sec14FilingDate;
	private Date orderReceivedDate;
	private Date physicalPossessionDate;
	private String dateOfPublicationOfPhysicalPossessionNotice;
	private Date valuationDate;
	private long valuationAmount;
	private Date coolingPeriodNoticeDate;
	private long reservePrice;
	private Date saleNoticeDate;
	private Date saleNoticePublicationDate;
	private Date auctionDate;
	private Date saleDate;
	private String nextActionPlan;
	private Date nextActionDate;
	private String noticeType;
	private String guarantorsName;
	private Date noticeDateForAppointmentOfArbitrator;
	private String arbitratorAppointed;
	private String sec17OrderApplied;
	private Date sec17OrderAppliedDate;
	private Date sec17OrderReceivedDate;
	private String sec17OrderStatus;
	private String sec9Applied;
	private String sec9LegalCaseID;
	private Date awardDate;
	private long awardAmount;
	private String awardCopyProvided;
	private String transmissionRequired;
	private String appealUs34Filed;
	private String arbitrationInitiated;
	private String arbitrationCaseId;
	private Date dateOfConduct;
	private boolean whetherCustomerAttended;
	private long settlementAmt;*/
	private String filePath;
	
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
	public String getLegalCaseID() {
		return legalCaseID;
	}
	public void setLegalCaseID(String legalCaseID) {
		this.legalCaseID = legalCaseID;
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
	public long getLoanAccountNumber() {
		return loanAccountNumber;
	}
	public void setLoanAccountNumber(long loanAccountNumber) {
		this.loanAccountNumber = loanAccountNumber;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public Date getPosNoticeDate() {
		return posNoticeDate;
	}
	public void setPosNoticeDate(Date posNoticeDate) {
		this.posNoticeDate = posNoticeDate;
	}
	public Date getDpdNoticeDate() {
		return dpdNoticeDate;
	}
	public void setDpdNoticeDate(Date dpdNoticeDate) {
		this.dpdNoticeDate = dpdNoticeDate;
	}
	public Date getNpaStageNoticeDate() {
		return npaStageNoticeDate;
	}
	public void setNpaStageNoticeDate(Date npaStageNoticeDate) {
		this.npaStageNoticeDate = npaStageNoticeDate;
	}
	public Date getNoticeDate() {
		return noticeDate;
	}
	public void setNoticeDate(Date noticeDate) {
		this.noticeDate = noticeDate;
	}
	public Date getNoticeSentDate() {
		return noticeSentDate;
	}
	public void setNoticeSentDate(Date noticeSentDate) {
		this.noticeSentDate = noticeSentDate;
	}
	public String getNoticePostalRemarks() {
		return noticePostalRemarks;
	}
	public void setNoticePostalRemarks(String noticePostalRemarks) {
		this.noticePostalRemarks = noticePostalRemarks;
	}
	
	
	
	
	/*public Date getServeDate() {
		return serveDate;
	}
	public void setServeDate(Date serveDate) {
		this.serveDate = serveDate;
	}
	public long getAmountInvolved() {
		return amountInvolved;
	}
	public void setAmountInvolved(long amountInvolved) {
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
	public Date getPosFilingDate() {
		return posFilingDate;
	}
	public void setPosFilingDate(Date posFilingDate) {
		this.posFilingDate = posFilingDate;
	}
	public Date getDpdFilingDate() {
		return dpdFilingDate;
	}
	public void setDpdFilingDate(Date dpdFilingDate) {
		this.dpdFilingDate = dpdFilingDate;
	}
	public Date getNpaStageFilingDate() {
		return npaStageFilingDate;
	}
	public void setNpaStageFilingDate(Date npaStageFilingDate) {
		this.npaStageFilingDate = npaStageFilingDate;
	}
	public Date getDateOfFilingCase() {
		return dateOfFilingCase;
	}
	public void setDateOfFilingCase(Date dateOfFilingCase) {
		this.dateOfFilingCase = dateOfFilingCase;
	}
	public long getCourtCaseId() {
		return courtCaseId;
	}
	public void setCourtCaseId(long courtCaseId) {
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
	public boolean isRecieveOrderApplied() {
		return recieveOrderApplied;
	}
	public void setRecieveOrderApplied(boolean recieveOrderApplied) {
		this.recieveOrderApplied = recieveOrderApplied;
	}
	public Date getReceiverOrderAppliedDate() {
		return receiverOrderAppliedDate;
	}
	public void setReceiverOrderAppliedDate(Date receiverOrderAppliedDate) {
		this.receiverOrderAppliedDate = receiverOrderAppliedDate;
	}
	public Date getReceiverOrderReceivedDate() {
		return receiverOrderReceivedDate;
	}
	public void setReceiverOrderReceivedDate(Date receiverOrderReceivedDate) {
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
	public Date getPos_ep_FilingDate() {
		return pos_ep_FilingDate;
	}
	public void setPos_ep_FilingDate(Date pos_ep_FilingDate) {
		this.pos_ep_FilingDate = pos_ep_FilingDate;
	}
	public Date getDpd_ep_FilingDate() {
		return dpd_ep_FilingDate;
	}
	public void setDpd_ep_FilingDate(Date dpd_ep_FilingDate) {
		this.dpd_ep_FilingDate = dpd_ep_FilingDate;
	}
	public Date getNpa_stage_ep_FilingDate() {
		return npa_stage_ep_FilingDate;
	}
	public void setNpa_stage_ep_FilingDate(Date npa_stage_ep_FilingDate) {
		this.npa_stage_ep_FilingDate = npa_stage_ep_FilingDate;
	}
	public Date getExecutionFilingDate() {
		return executionFilingDate;
	}
	public void setExecutionFilingDate(Date executionFilingDate) {
		this.executionFilingDate = executionFilingDate;
	}
	public long getExecutionCaseNo() {
		return executionCaseNo;
	}
	public void setExecutionCaseNo(long executionCaseNo) {
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
	public String getAdvocateofEP() {
		return advocateofEP;
	}
	public void setAdvocateofEP(String advocateofEP) {
		this.advocateofEP = advocateofEP;
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
	public Date getPreviousHearingDate() {
		return previousHearingDate;
	}
	public void setPreviousHearingDate(Date previousHearingDate) {
		this.previousHearingDate = previousHearingDate;
	}
	public Date getNextHearingDate() {
		return nextHearingDate;
	}
	public void setNextHearingDate(Date nextHearingDate) {
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
	public String getCosureDate() {
		return cosureDate;
	}
	public void setCosureDate(String cosureDate) {
		this.cosureDate = cosureDate;
	}
	public Date getClosureReportingDate() {
		return closureReportingDate;
	}
	public void setClosureReportingDate(Date closureReportingDate) {
		this.closureReportingDate = closureReportingDate;
	}
	public long getTotalAmtRecovered() {
		return totalAmtRecovered;
	}
	public void setTotalAmtRecovered(long totalAmtRecovered) {
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
	public Date getPosAsOnCurrentSystemDate() {
		return posAsOnCurrentSystemDate;
	}
	public void setPosAsOnCurrentSystemDate(Date posAsOnCurrentSystemDate) {
		this.posAsOnCurrentSystemDate = posAsOnCurrentSystemDate;
	}
	public Date getDpdAsOnCurrentSystemDate() {
		return dpdAsOnCurrentSystemDate;
	}
	public void setDpdAsOnCurrentSystemDate(Date dpdAsOnCurrentSystemDate) {
		this.dpdAsOnCurrentSystemDate = dpdAsOnCurrentSystemDate;
	}
	public Date getNpaStageAsOnCurrentSystemDate() {
		return npaStageAsOnCurrentSystemDate;
	}
	public void setNpaStageAsOnCurrentSystemDate(Date npaStageAsOnCurrentSystemDate) {
		this.npaStageAsOnCurrentSystemDate = npaStageAsOnCurrentSystemDate;
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
	public long getParentID() {
		return parentID;
	}
	public void setParentID(long parentID) {
		this.parentID = parentID;
	}
	public String getGeneratedBy() {
		return generatedBy;
	}
	public void setGeneratedBy(String generatedBy) {
		this.generatedBy = generatedBy;
	}
	public String getSpdcNoticeAcknowledgementRemarks() {
		return spdcNoticeAcknowledgementRemarks;
	}
	public void setSpdcNoticeAcknowledgementRemarks(
			String spdcNoticeAcknowledgementRemarks) {
		this.spdcNoticeAcknowledgementRemarks = spdcNoticeAcknowledgementRemarks;
	}
	public Date getSpdcNoticeDateServiceDate() {
		return spdcNoticeDateServiceDate;
	}
	public void setSpdcNoticeDateServiceDate(Date spdcNoticeDateServiceDate) {
		this.spdcNoticeDateServiceDate = spdcNoticeDateServiceDate;
	}
	public String getChqnNo1() {
		return chqnNo1;
	}
	public void setChqnNo1(String chqnNo1) {
		this.chqnNo1 = chqnNo1;
	}
	public String getChqnNo2() {
		return chqnNo2;
	}
	public void setChqnNo2(String chqnNo2) {
		this.chqnNo2 = chqnNo2;
	}
	public String getChqnNo3() {
		return chqnNo3;
	}
	public void setChqnNo3(String chqnNo3) {
		this.chqnNo3 = chqnNo3;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public Date getPoliceComplaintFiledDate() {
		return policeComplaintFiledDate;
	}
	public void setPoliceComplaintFiledDate(Date policeComplaintFiledDate) {
		this.policeComplaintFiledDate = policeComplaintFiledDate;
	}
	public long getNoticeRefrenceNumber() {
		return noticeRefrenceNumber;
	}
	public void setNoticeRefrenceNumber(long noticeRefrenceNumber) {
		this.noticeRefrenceNumber = noticeRefrenceNumber;
	}
	public long getLoanAmount() {
		return loanAmount;
	}
	public void setLoanAmount(long loanAmount) {
		this.loanAmount = loanAmount;
	}
	public Date getDisbursalDate() {
		return disbursalDate;
	}
	public void setDisbursalDate(Date disbursalDate) {
		this.disbursalDate = disbursalDate;
	}
	public String getAssetDetails() {
		return assetDetails;
	}
	public void setAssetDetails(String assetDetails) {
		this.assetDetails = assetDetails;
	}
	public long getOverdueAmountAsOnNoticeDate() {
		return overdueAmountAsOnNoticeDate;
	}
	public void setOverdueAmountAsOnNoticeDate(long overdueAmountAsOnNoticeDate) {
		this.overdueAmountAsOnNoticeDate = overdueAmountAsOnNoticeDate;
	}
	public Date getSec13_2NoticeDate() {
		return sec13_2NoticeDate;
	}
	public void setSec13_2NoticeDate(Date sec13_2NoticeDate) {
		this.sec13_2NoticeDate = sec13_2NoticeDate;
	}
	public String getSec13_2NoticePostalRemarks() {
		return sec13_2NoticePostalRemarks;
	}
	public void setSec13_2NoticePostalRemarks(String sec13_2NoticePostalRemarks) {
		this.sec13_2NoticePostalRemarks = sec13_2NoticePostalRemarks;
	}
	public Date getSec13_2PublicationDate() {
		return sec13_2PublicationDate;
	}
	public void setSec13_2PublicationDate(Date sec13_2PublicationDate) {
		this.sec13_2PublicationDate = sec13_2PublicationDate;
	}
	public long getNoticeAmount() {
		return noticeAmount;
	}
	public void setNoticeAmount(long noticeAmount) {
		this.noticeAmount = noticeAmount;
	}
	public Date getPeacefulPossessionNoticeDate() {
		return peacefulPossessionNoticeDate;
	}
	public void setPeacefulPossessionNoticeDate(Date peacefulPossessionNoticeDate) {
		this.peacefulPossessionNoticeDate = peacefulPossessionNoticeDate;
	}
	public String getSec13_4NoticePostalRemarks() {
		return sec13_4NoticePostalRemarks;
	}
	public void setSec13_4NoticePostalRemarks(String sec13_4NoticePostalRemarks) {
		this.sec13_4NoticePostalRemarks = sec13_4NoticePostalRemarks;
	}
	public Date getSymbolicPossessionDate() {
		return symbolicPossessionDate;
	}
	public void setSymbolicPossessionDate(Date symbolicPossessionDate) {
		this.symbolicPossessionDate = symbolicPossessionDate;
	}
	public Date getSec13_4PublicationDate() {
		return sec13_4PublicationDate;
	}
	public void setSec13_4PublicationDate(Date sec13_4PublicationDate) {
		this.sec13_4PublicationDate = sec13_4PublicationDate;
	}
	public Date getSec14FilingDate() {
		return sec14FilingDate;
	}
	public void setSec14FilingDate(Date sec14FilingDate) {
		this.sec14FilingDate = sec14FilingDate;
	}
	public Date getOrderReceivedDate() {
		return orderReceivedDate;
	}
	public void setOrderReceivedDate(Date orderReceivedDate) {
		this.orderReceivedDate = orderReceivedDate;
	}
	public Date getPhysicalPossessionDate() {
		return physicalPossessionDate;
	}
	public void setPhysicalPossessionDate(Date physicalPossessionDate) {
		this.physicalPossessionDate = physicalPossessionDate;
	}
	public String getDateOfPublicationOfPhysicalPossessionNotice() {
		return dateOfPublicationOfPhysicalPossessionNotice;
	}
	public void setDateOfPublicationOfPhysicalPossessionNotice(
			String dateOfPublicationOfPhysicalPossessionNotice) {
		this.dateOfPublicationOfPhysicalPossessionNotice = dateOfPublicationOfPhysicalPossessionNotice;
	}
	public Date getValuationDate() {
		return valuationDate;
	}
	public void setValuationDate(Date valuationDate) {
		this.valuationDate = valuationDate;
	}
	public long getValuationAmount() {
		return valuationAmount;
	}
	public void setValuationAmount(long valuationAmount) {
		this.valuationAmount = valuationAmount;
	}
	public Date getCoolingPeriodNoticeDate() {
		return coolingPeriodNoticeDate;
	}
	public void setCoolingPeriodNoticeDate(Date coolingPeriodNoticeDate) {
		this.coolingPeriodNoticeDate = coolingPeriodNoticeDate;
	}
	public long getReservePrice() {
		return reservePrice;
	}
	public void setReservePrice(long reservePrice) {
		this.reservePrice = reservePrice;
	}
	public Date getSaleNoticeDate() {
		return saleNoticeDate;
	}
	public void setSaleNoticeDate(Date saleNoticeDate) {
		this.saleNoticeDate = saleNoticeDate;
	}
	public Date getSaleNoticePublicationDate() {
		return saleNoticePublicationDate;
	}
	public void setSaleNoticePublicationDate(Date saleNoticePublicationDate) {
		this.saleNoticePublicationDate = saleNoticePublicationDate;
	}
	public Date getAuctionDate() {
		return auctionDate;
	}
	public void setAuctionDate(Date auctionDate) {
		this.auctionDate = auctionDate;
	}
	public Date getSaleDate() {
		return saleDate;
	}
	public void setSaleDate(Date saleDate) {
		this.saleDate = saleDate;
	}
	public String getNextActionPlan() {
		return nextActionPlan;
	}
	public void setNextActionPlan(String nextActionPlan) {
		this.nextActionPlan = nextActionPlan;
	}
	public Date getNextActionDate() {
		return nextActionDate;
	}
	public void setNextActionDate(Date nextActionDate) {
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
	public Date getNoticeDateForAppointmentOfArbitrator() {
		return noticeDateForAppointmentOfArbitrator;
	}
	public void setNoticeDateForAppointmentOfArbitrator(
			Date noticeDateForAppointmentOfArbitrator) {
		this.noticeDateForAppointmentOfArbitrator = noticeDateForAppointmentOfArbitrator;
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
	public Date getSec17OrderAppliedDate() {
		return sec17OrderAppliedDate;
	}
	public void setSec17OrderAppliedDate(Date sec17OrderAppliedDate) {
		this.sec17OrderAppliedDate = sec17OrderAppliedDate;
	}
	public Date getSec17OrderReceivedDate() {
		return sec17OrderReceivedDate;
	}
	public void setSec17OrderReceivedDate(Date sec17OrderReceivedDate) {
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
	public String getSec9LegalCaseID() {
		return sec9LegalCaseID;
	}
	public void setSec9LegalCaseID(String sec9LegalCaseID) {
		this.sec9LegalCaseID = sec9LegalCaseID;
	}
	public Date getAwardDate() {
		return awardDate;
	}
	public void setAwardDate(Date awardDate) {
		this.awardDate = awardDate;
	}
	public long getAwardAmount() {
		return awardAmount;
	}
	public void setAwardAmount(long awardAmount) {
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
	public Date getDateOfConduct() {
		return dateOfConduct;
	}
	public void setDateOfConduct(Date dateOfConduct) {
		this.dateOfConduct = dateOfConduct;
	}
	public boolean isWhetherCustomerAttended() {
		return whetherCustomerAttended;
	}
	public void setWhetherCustomerAttended(boolean whetherCustomerAttended) {
		this.whetherCustomerAttended = whetherCustomerAttended;
	}
	public long getSettlementAmt() {
		return settlementAmt;
	}
	public void setSettlementAmt(long settlementAmt) {
		this.settlementAmt = settlementAmt;
	}*/
	
	
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

}
