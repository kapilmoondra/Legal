package com.legalfriend.entities;

import com.opencsv.bean.CsvBindByName;

public class CsvToCaseFileDetailsMappingBean {

	@CsvBindByName(column = "Case ID")
	private String caseId;

	@CsvBindByName(column = "Recourse", required = true)
	private String recourse;

	@CsvBindByName(column = "Legal Case ID")
	private String legalCaseId;

	@CsvBindByName(column = "Region")
	private String region;

	@CsvBindByName(column = "State")
	private String state;

	@CsvBindByName(column = "Child Case")
	private String childCase;

	@CsvBindByName(column = "Location")
	private String location;

	@CsvBindByName(column = "Product")
	private String product;

	@CsvBindByName(column = "Product Group")
	private String productGroup;

	@CsvBindByName(column = "Loan Account Number")
	private Long loanAccountNumber;

	@CsvBindByName(column = "Customer Name")
	private String customerName;

	@CsvBindByName(column = "POS as on notice date")
	private String posOnNoticeDate;

	@CsvBindByName(column = "DPD as on notice date")

	private String dpdOnNoticeDate;

	@CsvBindByName(column = "NPA Stage as on notice date")

	private String npaStageOnNoticeDate;

	@CsvBindByName(column = "Notice Date")

	private String noticeDate;

	@CsvBindByName(column = "Notice Sent Date")

	private String noticeSentDate;

	@CsvBindByName(column = "Notice  Postal Remarks")
	private String noticePostalRemarks;

	@CsvBindByName(column = "Serve Date")

	private String serveDate;

	@CsvBindByName(column = "Amount involved")
	private Double amountInvolved;

	@CsvBindByName(column = "Case Type")
	private String caseType;

	@CsvBindByName(column = "Lawyer Name")
	private String lawyerName;

	@CsvBindByName(column = "POS as on filing date")
	private String posOnFilingDate;

	@CsvBindByName(column = "DPD as on filing date")
	private String dpdOnFilingDate;

	@CsvBindByName(column = "NPA Stage as on filing date")
	private String npaStageOnFilingDate;

	@CsvBindByName(column = "String Of Filing Case")

	private String filingDate;

	@CsvBindByName(column = "Court Case Id")
	private String courtCaseId;

	@CsvBindByName(column = "Court Name")
	private String courtName;

	@CsvBindByName(column = "Court Place")
	private String courtPlace;

	@CsvBindByName(column = "RO Applied")
	private String recieveOrderApplied;

	@CsvBindByName(column = "Receiver Order applied date")

	private String receiverOrderAppliedDate;

	@CsvBindByName(column = "RO Execution date")

	private String receiverOrderExecutionDate;

	@CsvBindByName(column = "RO Received Date")

	private String receiverOrderReceivedDate;

	@CsvBindByName(column = "RO Status")
	private String receiveOrderStatus;

	@CsvBindByName(column = "Receiver Name")
	private String receiverName;

	@CsvBindByName(column = "Execution Filed")
	private String executionFiled;

	@CsvBindByName(column = "POS as on EP filing date")
	private String posOnEpFilingDate;

	@CsvBindByName(column = "DPD as on EP filing date")
	private String dpdOnEpFilingDate;

	@CsvBindByName(column = "NPA Stage as on EP filing date")
	private String npaStageOnEpFilingDate;

	@CsvBindByName(column = "Execution Filing date")

	private String executionFilingDate;

	@CsvBindByName(column = "Execution Case No.")
	private String executionCaseNo;

	@CsvBindByName(column = "EP Court Name")
	private String ePCourtName;

	@CsvBindByName(column = "EP Court Place")
	private String ePCourtPlace;

	@CsvBindByName(column = "Advocate of EP")
	private String advocateofEP;

	@CsvBindByName(column = "Case Stage")
	private String caseStage;

	@CsvBindByName(column = "Stage in Court")
	private String stageInCourt;

	@CsvBindByName(column = "Previous Hearing Date")
	private String previousHearingDate;

	@CsvBindByName(column = "Next Hearing date")
	private String nextHearingDate;

	@CsvBindByName(column = "NDOH Null Reason")
	private String ndohNullReason;

	@CsvBindByName(column = "Legal Manager")
	private String legalManager;

	@CsvBindByName(column = "Remarks")
	private String remarks;

	@CsvBindByName(column = "Repo Flag")
	private boolean repoFlag;

	@CsvBindByName(column = "Case Status")
	private String caseStatus;

	@CsvBindByName(column = "Closure Date")

	private String closureDate;

	@CsvBindByName(column = "Closure Reporting Date")

	private String closureReportingDate;

	@CsvBindByName(column = "Total Amt Recovered")
	private Double totalAmtRecovered;

	@CsvBindByName(column = "Account Status")
	private String accountStatus;

	@CsvBindByName(column = "Case Filed Against")
	private String caseFiledAgainst;

	@CsvBindByName(column = "POS as on current system Date")
	private String posOnCurrentDate;

	@CsvBindByName(column = "DPD as on current system date")
	private String dpdOnCurrentDate;

	@CsvBindByName(column = "NPA Stage as on current system date")
	private String npaStageOnCurrentDate;

	@CsvBindByName(column = "Case Criticality Level")
	private String caseCriticalityLevel;

	@CsvBindByName(column = "Type")
	private String type;

	@CsvBindByName(column = "ParentID")
	private Long parentId;

	@CsvBindByName(column = "SPDC Notice Acknowledgement Remarks")
	private String spdcNoticeAckRemarks;

	@CsvBindByName(column = "SPDC Notice String Service Date")

	private String spdcNoticeServiceDate;

	@CsvBindByName(column = "Chq no 1")
	private String chqNo1;

	@CsvBindByName(column = "Chq no 2")
	private String chqNo2;

	@CsvBindByName(column = "Chq no 3")
	private String chqNo3;

	@CsvBindByName(column = "Bank Name")
	private String bankName;

	@CsvBindByName(column = "Police Complaint Filed Date")

	private String policeComplaintFiledDate;

	@CsvBindByName(column = "Notice Refrence Number")
	private Long noticeReferenceNumber;

	@CsvBindByName(column = "Loan Amount")
	private Double loanAmount;

	@CsvBindByName(column = "Disbursal Date")

	private String disbursalDate;

	@CsvBindByName(column = "Asset Details")
	private String assetDetails;

	@CsvBindByName(column = "Overdue Amount as on notice date")
	private Double overdueAmtOnNoticeDate;

	@CsvBindByName(column = "Sec. 13(2) Notice Date")

	private String sec132NoticeDate;

	@CsvBindByName(column = "13(2) Notice  Postal Remarks")
	private String sec132NoticePostalRemarks;

	@CsvBindByName(column = "13 (2) Publication Date")

	private String sec132PublicationDate;

	@CsvBindByName(column = "Notice Amount")
	private Double noticeAmount;

	@CsvBindByName(column = "Peaceful Possession Notice Date")

	private String peacefulPossessionNoticeDate;

	@CsvBindByName(column = "13(4) Notice  Postal Remarks")
	private String sec134NoticePostalRemarks;

	@CsvBindByName(column = "Symbolic Possession date")

	private String symbolicPossessionDate;

	@CsvBindByName(column = "13 (4) Publication Date")

	private String sec134PublicationDate;

	@CsvBindByName(column = "Sec 14 filing Date")

	private String sec14FilingDate;

	@CsvBindByName(column = "Order Received date")

	private String orderReceivedDate;

	@CsvBindByName(column = "Physical possession date")

	private String physicalPossessionDate;

	@CsvBindByName(column = "String of publication of physical possession notice")
	private String publicationDatePhysicalPossessionNotice;

	@CsvBindByName(column = "Valuation Date")

	private String valuationDate;

	@CsvBindByName(column = "Valuation Amount")
	private Double valuationAmount;

	@CsvBindByName(column = "Cooling period notice date")

	private String coolingPeriodNoticeDate;

	@CsvBindByName(column = "Reserve Price")
	private Double reservePrice;

	@CsvBindByName(column = "Sale Notice Date")

	private String saleNoticeDate;

	@CsvBindByName(column = "Sale notice publication date")

	private String saleNoticePublicationDate;

	@CsvBindByName(column = "Auction Date")

	private String auctionDate;

	@CsvBindByName(column = "Sale Date")

	private String saleDate;

	@CsvBindByName(column = "Next Action Plan")
	private String nextActionPlan;

	@CsvBindByName(column = "Next Action Date")

	private String nextActionDate;

	@CsvBindByName(column = "Notice Type")
	private String noticeType;

	@CsvBindByName(column = "Guarantors name")
	private String guarantorsName;

	@CsvBindByName(column = "Notice date for Appointment of Arbitrator")

	private String noticeDateAppointmentArbitrator;

	@CsvBindByName(column = "Arbitrator Appointed")
	private String arbitratorAppointed;

	@CsvBindByName(column = "Sec 17 Order Applied")
	private String sec17OrderApplied;

	@CsvBindByName(column = "Sec 17 Order applied date")

	private String sec17OrderAppliedDate;

	@CsvBindByName(column = "SEC 17 Order Received Date")

	private String sec17OrderReceivedDate;

	@CsvBindByName(column = "Sec 17 Order Status")
	private String sec17OrderStatus;

	@CsvBindByName(column = "SEC 9 Applied")
	private String sec9Applied;

	@CsvBindByName(column = "SEC 9 Legal Case ID")
	private String sec9LegalCaseId;

	@CsvBindByName(column = "Award Date")

	private String awardDate;

	@CsvBindByName(column = "Award Amount")
	private Double awardAmount;

	@CsvBindByName(column = "Award Copy Provided")
	private String awardCopyProvided;

	@CsvBindByName(column = "Transmission Required")
	private String transmissionRequired;

	@CsvBindByName(column = "Appeal Us 34 Filed")
	private String appealUs34Filed;

	@CsvBindByName(column = "Arbitration Initiated")
	private String arbitrationInitiated;

	@CsvBindByName(column = "Arbitration Case ID")
	private String arbitrationCaseId;

	@CsvBindByName(column = "String of Conduct")
	private String dateOfConduct;

	@CsvBindByName(column = "Whether Customer attended")
	private boolean whetherCustomerAttended;

	@CsvBindByName(column = "Settlement Amt")
	private Double settlementAmt;

	@CsvBindByName(column = "File Name")
	private String fileName;

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

	public String getReceiverOrderExecutionDate() {
		return receiverOrderExecutionDate;
	}

	public void setReceiverOrderExecutionDate(String receiverOrderExecutionDate) {
		this.receiverOrderExecutionDate = receiverOrderExecutionDate;
	}

	public String getChildCase() {
		return childCase;
	}

	public void setChildCase(String childCase) {
		this.childCase = childCase;
	}

}
