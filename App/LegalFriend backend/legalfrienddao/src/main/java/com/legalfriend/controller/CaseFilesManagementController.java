package com.legalfriend.controller;

import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletResponse;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.legalfriend.entities.AgainstInstitutionalCase;
import com.legalfriend.entities.AgainstInstitutionalCaseCompliance;
import com.legalfriend.entities.Billing;
import com.legalfriend.entities.BillingMaster;
import com.legalfriend.entities.Branch;
import com.legalfriend.entities.CaseFiles;
import com.legalfriend.entities.Compliance;
import com.legalfriend.entities.CsvToCaseFileDetailsMappingBean;
import com.legalfriend.entities.Event;
import com.legalfriend.entities.ExportVO;
import com.legalfriend.entities.ForInstitutionalCase;
import com.legalfriend.entities.ForInstitutionalCaseCompliance;
import com.legalfriend.entities.Institution;
import com.legalfriend.entities.LegalCaseVO;
import com.legalfriend.entities.Recourse;
import com.legalfriend.entities.Stage;
import com.legalfriend.entities.User;
import com.legalfriend.enums.ComplianceStatus;
import com.legalfriend.enums.UserRole;
import com.legalfriend.repository.AgainstInstitutionalCaseComplianceRepository;
import com.legalfriend.repository.AgainstInstitutionalCaseRepository;
import com.legalfriend.repository.BillingMasterRepository;
import com.legalfriend.repository.BillingRepository;
import com.legalfriend.repository.BranchRepository;
import com.legalfriend.repository.CaseFilesRepository;
import com.legalfriend.repository.ComplianceRepository;
import com.legalfriend.repository.EventRepository;
import com.legalfriend.repository.ForInstitutionalCaseComplianceRepository;
import com.legalfriend.repository.ForInstitutionalCaseRepository;
import com.legalfriend.repository.InstitutionRepository;
import com.legalfriend.repository.RecourseRepository;
import com.legalfriend.repository.StageRepository;
import com.legalfriend.repository.UserRepository;
import com.legalfriend.response.ComplianceResponse;
import com.legalfriend.response.LegalFriendResponse;
import com.legalfriend.service.S3ServicesImpl;
import com.legalfriend.util.CaseFileManagementService;
import com.legalfriend.util.CsvFileReader;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

@RestController
@RequestMapping("/institution")
public class CaseFilesManagementController {

	@Autowired
	S3ServicesImpl s3ServicesImpl;

	@Autowired
	ForInstitutionalCaseRepository institutionalCaseRepo;

	@Autowired
	AgainstInstitutionalCaseRepository againstInstRepo;

	@Autowired
	CaseFilesRepository caseFilesRepo;

	@Autowired
	CsvFileReader csvFileReader;

	@Autowired
	CaseFileManagementService zipUtils;

	@Autowired
	BranchRepository branchRepo;

	@Autowired
	private BillingRepository billingRepository;

	@Autowired
	private RecourseRepository recourseRepository;

	@Autowired
	private StageRepository stageRepository;

	@Autowired
	private InstitutionRepository institutionRepository;

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private BillingMasterRepository billingMasterRepository;

	@Autowired
	private ForInstitutionalCaseComplianceRepository forInstitutionalCaseComplianceRepository;

	@Autowired
	private AgainstInstitutionalCaseComplianceRepository againstInstitutionalCaseComplianceRepository;

	@Autowired
	private ComplianceRepository complianceRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	CaseFileManagementService caseFileManagementService;

	@Autowired
	EventRepository eventRepo;

	private String FETCH_FOR_CASE_HISTORY = "select r.rev, r.revtstmp, lc.court_case_id, lc.court_place, l.case_id, lc.next_hearing_date, lc.ground_for_closing_file, lc.filing_date, lc.previous_hearing_date, s.stage_name, lc.remarks, u.first_name, u.last_name from for_institutional_case_aud lc left join revinfo r on r.rev=lc.rev left outer join for_institutional_case l on lc.parent_id=l.parent_id left outer join stage s on s.stage_code = lc.case_stage left outer join user u on u.id = lc.fk_user_id where lc.id=:caseId order by r.rev";

	private String FETCH_AGAINST_CASE_HISTORY = "select r.rev, r.revtstmp, lc.court_case_id, lc.court_place, l.case_id, lc.next_hearing_date, lc.ground_for_closing_file, lc.filing_date, lc.previous_hearing_date, s.stage_name, lc.remarks, u.first_name, u.last_name from against_institutional_case_aud lc left join revinfo r on r.rev=lc.rev left outer join against_institutional_case l on lc.parent_id=l.parent_id left outer join stage s on s.stage_code = lc.case_stage left outer join user u on u.id = lc.fk_user_id where lc.id=:caseId order by r.rev";

	private String[] dataMapping = { "caseId", "recourse", "legalCaseId", "region", "state", "location", "product",
			"productGroup", "loanAccountNumber", "customerName", "posOnNoticeDate", "dpdOnNoticeDate",
			"npaStageOnNoticeDate", "noticeDate", "noticeSentDate", "noticePostalRemarks", "serveDate",
			"amountInvolved", "caseType", "lawyerName", "posOnFilingDate", "dpdOnFilingDate", "npaStageOnFilingDate",
			"filingDate", "courtCaseId", "courtName", "courtPlace", "recieveOrderApplied", "receiverOrderAppliedDate",
			"receiverOrderReceivedDate", "receiveOrderStatus", "receiverName", "executionFiled", "posOnEpFilingDate",
			"dpdOnEpFilingDate", "npaStageOnEpFilingDate", "executionFilingDate", "executionCaseNo", "ePCourtName",
			"ePCourtPlace", "advocateofEp", "caseStage", "stageInCourt", "previousHearingDate", "nextHearingDate",
			"ndohNullReason", "legalManager", "remarks", "repoFlag", "caseStatus", "closureDate",
			"closureReportingDate", "totalAmtRecovered", "accountStatus", "caseFiledAgainst", "posOnCurrentDate",
			"dpdOnCurrentDate", "npaStageOnCurrentDate", "caseCriticalityLevel", "type", "parentId",
			"spdcNoticeAckRemarks", "spdcNoticeServiceDate", "chqNo1", "chqNo2", "chqNo3", "bankName",
			"policeComplaintFiledDate", "noticeReferenceNumber", "loanAmount", "disbursalDate", "assetDetails",
			"overdueAmtOnNoticeDate", "sec132NoticeDate", "sec132NoticePostalRemarks", "sec132PublicationDate",
			"noticeAmount", "peacefulPossessionNoticeDate", "sec134NoticePostalRemarks", "symbolicPossessionDate",
			"sec134PublicationDate", "sec14FilingDate", "orderReceivedDate", "physicalPossessionDate",
			"publicationDatePhysicalPossessionNotice", "valuationDate", "valuationAmount", "coolingPeriodNoticeDate",
			"reservePrice", "saleNoticeDate", "saleNoticePublicationDate", "auctionDate", "saleDate", "nextActionPlan",
			"nextActionDate", "noticeType", "guarantorsName", "noticeDateAppointmentArbitrator", "arbitratorAppointed",
			"sec17OrderApplied", "sec17OrderAppliedDate", "sec17OrderReceivedDate", "sec17OrderStatus", "sec9Applied",
			"sec9LegalCaseId", "awardDate", "awardAmount", "awardCopyProvided", "transmissionRequired",
			"appealUs34Filed", "arbitrationInitiated", "arbitrationCaseId", "dateOfConduct", "whetherCustomerAttended",
			"settlementAmt", "fileName" };

	private String FETCH_USER_ROLE = " select r.role_name from user_role ur left join role r on r.id=ur.role_id WHERE ur.user_id = :userId";

	private List<String> findUserRole(Long userId) {
		Session session = sessionFactory.openSession();
		List<String> list = new ArrayList<>();
		Query query = session.createSQLQuery(FETCH_USER_ROLE);
		query.setLong("userId", userId);
		List<String> rows = (List<String>) query.list();
		for (String role : rows) {
			list.add(role);
		}
		session.close();
		return list;
	}

	@PostMapping("/upload")
	public LegalFriendResponse caseFilesUpload(@RequestParam("userId") Long userId,
			@RequestParam("institutionId") Long institutionId, @RequestParam("branchId") Long branchId,
			@RequestParam("csvfile") MultipartFile file,
			@RequestParam(value = "zipFile", required = false) MultipartFile zipFile,
			@RequestParam("isForInstitution") String isForInstitution) throws CsvException, Exception {
		LegalFriendResponse caseResponse = new LegalFriendResponse();
		if (file == null) {
			caseResponse.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			caseResponse.setSuccessMessage("Please upload files in required format");
			return caseResponse;
		}
		caseResponse.setHttpCode(HttpStatus.OK.value());
		caseResponse.setSuccessMessage("Case Files Uploaded Succesfully...");
		// Explode the zip and get all the caseFiles in it with their caseId
		// as key
		// update caseFiles with their corresponding file paths from the
		// exploded zip
		try {
			if (isForInstitution.equals("Y")) {
				csvFileReader.processCsvToGetFileDetailsForInstitution(userId, file, institutionId, branchId, zipFile);
			} else {
				csvFileReader.processCsvToGetFileDetailsAgainstInstitution(userId, file, institutionId, branchId,
						zipFile);
			}
		} catch (Exception e) {
			caseResponse.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			caseResponse.setFailureReason("Error in uploaded file data " + e.getMessage());
		}

		return caseResponse;
	}

	@GetMapping("/cases")
	public List<ForInstitutionalCase> getForInsitutionalCases(@RequestParam Long institutionId,
			@RequestParam Long branchId, @RequestParam Long userId) {
		List<String> roles = findUserRole(userId);
		if (roles.contains(UserRole.CLIENT.name()) || roles.contains(UserRole.EMPLOYEE.name())
				|| roles.contains(UserRole.MANAGER.name())) {
			userId = userRepository.findById(userId).get(0).getClientId();
		}
		List<ForInstitutionalCase> forInstitutionalCases = institutionalCaseRepo
				.findByInstitutionIdAndBranchIdAndUserId(institutionId, branchId, userId);
		for (ForInstitutionalCase case1 : forInstitutionalCases) {
			List<CaseFiles> caseFiles = caseFilesRepo.findByCaseIdAndThruDateAndRemarkOnly(case1.getCaseId(), null,
					false);
			case1.setCaseFiles(caseFiles);
		}
		return forInstitutionalCases;
	}

	@GetMapping("/against/cases")
	public List<AgainstInstitutionalCase> getAgainstInsitutionalCases(@RequestParam Long institutionId,
			@RequestParam Long branchId, @RequestParam Long userId) {
		List<String> roles = findUserRole(userId);
		if (roles.contains(UserRole.CLIENT.name()) || roles.contains(UserRole.EMPLOYEE.name())
				|| roles.contains(UserRole.MANAGER.name())) {
			userId = userRepository.findById(userId).get(0).getClientId();
		}
		List<AgainstInstitutionalCase> againstInstitutionalCases = againstInstRepo
				.findByInstitutionIdAndBranchIdAndUserId(institutionId, branchId, userId);
		for (AgainstInstitutionalCase case1 : againstInstitutionalCases) {
			List<CaseFiles> caseFiles = caseFilesRepo.findByCaseIdAndThruDateAndRemarkOnly(case1.getCaseId(), null,
					false);
			case1.setCaseFiles(caseFiles);
		}
		return againstInstitutionalCases;
	}

	@GetMapping("/for/case")
	public ForInstitutionalCase getInsitutionalCase(@RequestParam Long institutionId, @RequestParam Long branchId,
			@RequestParam Long institutionalCaseId) {
		ForInstitutionalCase forInstitutionalCase = institutionalCaseRepo
				.findByInstitutionIdAndBranchIdAndId(institutionId, branchId, institutionalCaseId);
		forInstitutionalCase.setRecourseId(
				recourseRepository.findByRecourseCode(forInstitutionalCase.getRecourse()).get(0).getId());
		List<CaseFiles> caseFiles = caseFilesRepo.findByCaseIdAndThruDateAndRemarkOnly(forInstitutionalCase.getCaseId(),
				null, false);
		forInstitutionalCase.setCaseFiles(caseFiles);
		return forInstitutionalCase;
	}

	@GetMapping("/against/case")
	public AgainstInstitutionalCase getAgainstInsitutionalCase(@RequestParam Long institutionId,
			@RequestParam Long branchId, @RequestParam Long institutionalCaseId) {
		AgainstInstitutionalCase againstInstitutionalCase = againstInstRepo
				.findByInstitutionIdAndBranchIdAndId(institutionId, branchId, institutionalCaseId);
		againstInstitutionalCase.setRecourseId(
				recourseRepository.findByRecourseCode(againstInstitutionalCase.getRecourse()).get(0).getId());
		List<CaseFiles> caseFiles = caseFilesRepo
				.findByCaseIdAndThruDateAndRemarkOnly(againstInstitutionalCase.getCaseId(), null, false);
		againstInstitutionalCase.setCaseFiles(caseFiles);
		return againstInstitutionalCase;
	}

	@PostMapping("/for/remark")
	public LegalFriendResponse uploadForCaseRemarkAndFile(@RequestParam("caseId") Long caseId,
			@RequestParam(value = "remark") String remark,
			@RequestParam(value = "file", required = false) MultipartFile uploadedFile) throws Exception {
		LegalFriendResponse caseResponse = new LegalFriendResponse();
		ForInstitutionalCase persistedCase = institutionalCaseRepo.findOne(caseId);
		caseFileManagementService.updateCaseRemarkAndFile(persistedCase.getUserId(), persistedCase.getCaseId(),
				uploadedFile, remark);
		caseResponse.setHttpCode(HttpStatus.OK.value());
		caseResponse.setSuccessMessage("Case remark updated successfully");
		return caseResponse;
	}

	@PostMapping("/against/remark")
	public LegalFriendResponse uploadAgainstCaseRemarkAndFile(@RequestParam("caseId") Long caseId,
			@RequestParam(value = "remark") String remark,
			@RequestParam(value = "file", required = false) MultipartFile uploadedFile) throws Exception {
		LegalFriendResponse caseResponse = new LegalFriendResponse();
		AgainstInstitutionalCase persistedCase = againstInstRepo.findOne(caseId);
		caseFileManagementService.updateCaseRemarkAndFile(persistedCase.getUserId(), persistedCase.getCaseId(),
				uploadedFile, remark);
		caseResponse.setHttpCode(HttpStatus.OK.value());
		caseResponse.setSuccessMessage("Case remark updated successfully");
		return caseResponse;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/for/history")
	public List<LegalCaseVO> getForCaseHistory(@RequestParam Long caseId) throws ParseException {
		Session session = sessionFactory.openSession();
		Query query = session.createSQLQuery(FETCH_FOR_CASE_HISTORY);
		query.setLong("caseId", caseId);
		List<Object[]> rows = (List<Object[]>) query.list();
		List<LegalCaseVO> legalCaseAuds = new ArrayList<>();
		for (Object[] row : rows) {
			LegalCaseVO legalCaseAud = new LegalCaseVO();
			legalCaseAud.setRev(Long.parseLong(row[0].toString()));
			legalCaseAud.setRevTimeStamp(row[1] != null ? Long.parseLong(row[1].toString()) : null);
			legalCaseAud.setCourtCaseId(row[2] != null ? row[2].toString() : null);
			legalCaseAud.setCourtPlace(row[3] != null ? row[3].toString() : null);
			legalCaseAud.setParentCaseId(row[4] != null ? row[4].toString() : null);
			legalCaseAud.setNextHearingDate(row[5] != null ? row[5].toString() : null);
			legalCaseAud.setGroundForClosingFile(row[6] != null ? row[6].toString() : null);
			legalCaseAud.setFilingDate(row[7] != null ? row[7].toString() : null);
			legalCaseAud.setLastHearingDate(row[8] != null ? row[8].toString() : null);
			legalCaseAud.setStage(row[9].toString());
			legalCaseAud.setRemark(row[10].toString());
			legalCaseAud.setFirstName(row[11] != null ? row[11].toString() : null);
			legalCaseAud.setLastName(row[12] != null ? row[12].toString() : null);
			legalCaseAuds.add(legalCaseAud);
		}
		session.close();
		return legalCaseAuds;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/against/history")
	public List<LegalCaseVO> getAgainstCaseHistory(@RequestParam Long caseId) throws ParseException {
		Session session = sessionFactory.openSession();
		Query query = session.createSQLQuery(FETCH_AGAINST_CASE_HISTORY);
		query.setLong("caseId", caseId);
		List<Object[]> rows = (List<Object[]>) query.list();
		List<LegalCaseVO> legalCaseAuds = new ArrayList<>();
		for (Object[] row : rows) {
			LegalCaseVO legalCaseAud = new LegalCaseVO();
			legalCaseAud.setRev(Long.parseLong(row[0].toString()));
			legalCaseAud.setRevTimeStamp(row[1] != null ? Long.parseLong(row[1].toString()) : null);
			legalCaseAud.setCourtCaseId(row[2] != null ? row[2].toString() : null);
			legalCaseAud.setCourtPlace(row[3] != null ? row[3].toString() : null);
			legalCaseAud.setParentCaseId(row[4] != null ? row[4].toString() : null);
			legalCaseAud.setNextHearingDate(row[5] != null ? row[5].toString() : null);
			legalCaseAud.setGroundForClosingFile(row[6] != null ? row[6].toString() : null);
			legalCaseAud.setFilingDate(row[7] != null ? row[7].toString() : null);
			legalCaseAud.setLastHearingDate(row[8] != null ? row[8].toString() : null);
			legalCaseAud.setStage(row[9].toString());
			legalCaseAud.setRemark(row[10].toString());
			legalCaseAud.setFirstName(row[11] != null ? row[12].toString() : null);
			legalCaseAud.setLastName(row[12] != null ? row[13].toString() : null);
			legalCaseAuds.add(legalCaseAud);
		}
		session.close();
		return legalCaseAuds;
	}

	@PostMapping("/for/case")
	public LegalFriendResponse updateForInsitutionalCase(
			@RequestParam("forInstitutionalCase") String forInstitutionalCase,
			@RequestParam(value = "file", required = false) MultipartFile uploadedFile) throws Exception {
		LegalFriendResponse caseResponse = new LegalFriendResponse();
		ObjectMapper mapper = new ObjectMapper();
		ForInstitutionalCase caseObj = mapper.readValue(forInstitutionalCase, ForInstitutionalCase.class);
		caseObj.setLastUpdated(new Date());
		String stageId = caseObj.getCaseStage();
		String recourseId = caseObj.getRecourse();
		Institution institution = institutionRepository.findById(caseObj.getInstitutionId());
		List<Stage> stage = stageRepository.findByStageCode(stageId);
		List<Recourse> recourses = recourseRepository.findByRecourseCode(recourseId);
		if (stage.size() > 0 && recourses.size() > 0) {
			List<BillingMaster> billingMasters = billingMasterRepository
					.findByInstitutionAndStageAndRecourse(institution, stage.get(0), recourses.get(0));
			ForInstitutionalCase forInstitutionalCase2 = institutionalCaseRepo.findByCaseId(caseObj.getCaseId());
			if (billingMasters.size() > 0 && !forInstitutionalCase2.getCaseStage().equals(caseObj.getCaseStage())) {
				Billing billing = new Billing();
				billing.setAmount(billingMasters.get(0).getAmount());
				billing.setCaseId(caseObj.getCaseId());
				billing.setInstitution(institutionRepository.findById(caseObj.getInstitutionId()));
				billing.setRecourse(recourses.get(0));
				billing.setBillingDate(new Date());
				billing.setStage(stage.get(0));
				billing.setUserId(caseObj.getUserId());
				billingRepository.save(billing);
			}
		}
		if (uploadedFile != null && caseObj.getBranchId() != null) {
			Branch branch = branchRepo.findById(caseObj.getBranchId());
			zipUtils.uploadFile(caseObj.getUserId(), caseObj.getCaseId(), caseObj.getInstitutionId(),
					branch.getBranchName(), uploadedFile);
		}
		if (caseObj.getNextHearingDate() != null && !caseObj.getNextHearingDate().isEmpty()) {
			Event event = eventRepo.findByReferenceNumber(caseObj.getCaseId());
			if (event != null) {
				eventRepo.delete(event);
			} else {
				event = new Event();
			}
			event.setEventName("Hearing Date for case " + caseObj.getCaseId());
			event.setEventDescription("Hearing Date for case " + caseObj.getCaseId());
			event.setUserId(caseObj.getUserId());
			event.setReferenceNumber(caseObj.getCaseId());
			event.setInstitutionId(caseObj.getInstitutionId());
			event.setBranchId(caseObj.getBranchId());
			SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("MM/dd/yyyy");
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Calendar c = Calendar.getInstance();
			c.set(Calendar.MINUTE, 0);
			c.setTime(simpleDateFormat
					.parse(simpleDateFormat.format(simpleDateFormat1.parse(caseObj.getNextHearingDate()))));
			c.set(Calendar.SECOND, 0);
			Date d1 = c.getTime();
			event.setStartDate(d1);
			c = Calendar.getInstance();
			c.set(Calendar.MINUTE, 23);
			c.setTime(simpleDateFormat
					.parse(simpleDateFormat.format(simpleDateFormat1.parse(caseObj.getNextHearingDate()))));
			c.set(Calendar.HOUR_OF_DAY, 23);
			c.set(Calendar.SECOND, 0);
			Date d2 = c.getTime();
			event.setEndDate(d2);
			eventRepo.save(event);
			caseObj.setNextHearingDate(simpleDateFormat.format(simpleDateFormat1.parse(caseObj.getNextHearingDate())));
		}

		ForInstitutionalCase case1 = institutionalCaseRepo.findById(caseObj.getId());
		BeanUtils.copyProperties(caseObj, case1);
		institutionalCaseRepo.save(case1);
		caseResponse.setHttpCode(HttpStatus.OK.value());
		caseResponse.setSuccessMessage("Case updated succesfully...");
		return caseResponse;
	}

	@PostMapping("/against/case")
	public LegalFriendResponse updateAgainstInsitutionalCase(
			@RequestParam("againstInstitutionalCase") String againstInstitutionalCase,
			@RequestParam(value = "file", required = false) MultipartFile uploadedFile) throws Exception {
		LegalFriendResponse caseResponse = new LegalFriendResponse();
		ObjectMapper mapper = new ObjectMapper();
		AgainstInstitutionalCase caseObj = mapper.readValue(againstInstitutionalCase, AgainstInstitutionalCase.class);
		caseObj.setLastUpdated(new Date());
		againstInstRepo.save(caseObj);
		String stageId = caseObj.getCaseStage();
		String recourseId = caseObj.getRecourse();
		List<Stage> stage = stageRepository.findByStageCode(stageId);
		Institution institution = institutionRepository.findById(caseObj.getInstitutionId());
		List<Recourse> recourses = recourseRepository.findByRecourseCode(recourseId);
		if (stage.size() > 0 && recourses.size() > 0) {
			List<BillingMaster> billingMasters = billingMasterRepository
					.findByInstitutionAndStageAndRecourse(institution, stage.get(0), recourses.get(0));
			if (billingMasters.size() > 0) {
				AgainstInstitutionalCase againstInstitutionalCase2 = againstInstRepo.findByCaseId(caseObj.getCaseId());
				if (billingMasters.size() > 0
						&& !againstInstitutionalCase2.getCaseStage().equals(caseObj.getCaseStage())) {
					Billing billing = new Billing();
					billing.setAmount(billingMasters.get(0).getAmount());
					billing.setCaseId(caseObj.getCaseId());
					billing.setInstitution(institutionRepository.findById(caseObj.getInstitutionId()));
					billing.setRecourse(recourses.get(0));
					billing.setBillingDate(new Date());
					billing.setStage(stage.get(0));
					billing.setUserId(caseObj.getUserId());
					billingRepository.save(billing);
				}
			}
		}
		if (uploadedFile != null) {
			Branch branch = branchRepo.findById(caseObj.getBranchId());
			zipUtils.uploadFile(caseObj.getUserId(), caseObj.getCaseId(), caseObj.getInstitutionId(),
					branch.getBranchName(), uploadedFile);
		}
		if (caseObj.getNextHearingDate() != null && !caseObj.getNextHearingDate().isEmpty()) {
			Event event = eventRepo.findByReferenceNumber(caseObj.getCaseId());
			if (event != null) {
				eventRepo.delete(event);
			}
			event = new Event();
			event.setEventName("Hearing Date for case " + caseObj.getCaseId());
			event.setEventDescription("Hearing Date for case " + caseObj.getCaseId());
			event.setUserId(caseObj.getUserId());
			event.setReferenceNumber(caseObj.getCaseId());
			event.setInstitutionId(caseObj.getInstitutionId());
			event.setBranchId(caseObj.getBranchId());
			SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("MM/dd/yyyy");
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Calendar c = Calendar.getInstance();
			c.set(Calendar.MINUTE, 0);
			c.setTime(simpleDateFormat
					.parse(simpleDateFormat.format(simpleDateFormat1.parse(caseObj.getNextHearingDate()))));
			c.set(Calendar.SECOND, 0);
			Date d1 = c.getTime();
			event.setStartDate(d1);
			c = Calendar.getInstance();
			c.set(Calendar.MINUTE, 23);
			c.setTime(simpleDateFormat
					.parse(simpleDateFormat.format(simpleDateFormat1.parse(caseObj.getNextHearingDate()))));
			c.set(Calendar.HOUR_OF_DAY, 23);
			c.set(Calendar.SECOND, 0);
			Date d2 = c.getTime();
			event.setEndDate(d2);
			eventRepo.save(event);
			caseObj.setNextHearingDate(simpleDateFormat.format(simpleDateFormat1.parse(caseObj.getNextHearingDate())));
		}
		AgainstInstitutionalCase case1 = againstInstRepo.findById(caseObj.getId());
		BeanUtils.copyProperties(caseObj, case1);
		againstInstRepo.save(case1);
		caseResponse.setHttpCode(HttpStatus.OK.value());
		caseResponse.setSuccessMessage("Case updated succesfully...");
		return caseResponse;
	}

	@GetMapping("/file/download")
	public ResponseEntity<byte[]> downloadCaseFile(@RequestParam Long fileId) throws IOException {
		CaseFiles caseFile = caseFilesRepo.findByIdAndThruDate(fileId, null);
		byte[] bytes = csvFileReader.getFileContent(caseFile.getFileName(), caseFile.getFilePath());
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		httpHeaders.setContentLength(bytes.length);
		httpHeaders.setContentDispositionFormData("attachment", caseFile.getFileName());
		return new ResponseEntity<>(bytes, httpHeaders, HttpStatus.OK);
	}

	@DeleteMapping("/file/delete")
	public ResponseEntity<?> deleteCaseFile(@RequestParam Long fileId) throws IOException {
		CaseFiles caseFile = caseFilesRepo.findByIdAndThruDate(fileId, null);
		caseFile.setThruDate(new Date());
		caseFilesRepo.save(caseFile);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PutMapping("for/hearing")
	public ResponseEntity<?> updateForHearingDate(@RequestBody ForInstitutionalCase institutionalCase)
			throws IOException, ParseException {
		ForInstitutionalCase forCase = institutionalCaseRepo.findById(institutionalCase.getId());
		if (institutionalCase.getNextHearingDate() != null) {
			Event event = eventRepo.findByReferenceNumber(forCase.getCaseId());
			if (event != null) {
				eventRepo.delete(event);
			}
			event = new Event();
			event.setEventName("Hearing Date for case " + forCase.getCaseId());
			event.setEventDescription("Hearing Date for case " + forCase.getCaseId());
			event.setUserId(forCase.getUserId());
			event.setReferenceNumber(forCase.getCaseId());
			event.setInstitutionId(forCase.getInstitutionId());
			event.setBranchId(forCase.getBranchId());
			SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("MM/dd/yyyy");
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Calendar c = Calendar.getInstance();
			c.set(Calendar.MINUTE, 0);
			c.setTime(simpleDateFormat
					.parse(simpleDateFormat.format(simpleDateFormat1.parse(institutionalCase.getNextHearingDate()))));
			c.set(Calendar.SECOND, 0);
			Date d1 = c.getTime();
			event.setStartDate(d1);
			c = Calendar.getInstance();
			c.set(Calendar.MINUTE, 23);
			c.setTime(simpleDateFormat
					.parse(simpleDateFormat.format(simpleDateFormat1.parse(institutionalCase.getNextHearingDate()))));
			c.set(Calendar.HOUR_OF_DAY, 23);
			c.set(Calendar.SECOND, 0);
			Date d2 = c.getTime();
			event.setEndDate(d2);
			eventRepo.save(event);
			forCase.setNextHearingDate(
					simpleDateFormat.format(simpleDateFormat1.parse(institutionalCase.getNextHearingDate())));
		}
		if (institutionalCase.getPreviousHearingDate() != null) {
			forCase.setPreviousHearingDate(institutionalCase.getPreviousHearingDate());
		}
		institutionalCaseRepo.save(forCase);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PutMapping("against/hearing")
	public ResponseEntity<?> updateAgainstHearingDate(@RequestBody AgainstInstitutionalCase institutionalCase)
			throws IOException, ParseException {
		AgainstInstitutionalCase againstInstitutionalCase = againstInstRepo.findById(institutionalCase.getId());
		if (institutionalCase.getNextHearingDate() != null) {
			Event event = eventRepo.findByReferenceNumber(againstInstitutionalCase.getCaseId());
			if (event != null) {
				eventRepo.delete(event);
			}
			event = new Event();
			event.setEventName("Hearing Date for case " + institutionalCase.getCaseId());
			event.setEventDescription("Hearing Date for case " + institutionalCase.getCaseId());
			event.setUserId(againstInstitutionalCase.getUserId());
			event.setInstitutionId(againstInstitutionalCase.getInstitutionId());
			event.setBranchId(againstInstitutionalCase.getBranchId());
			event.setReferenceNumber(againstInstitutionalCase.getCaseId());
			SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("MM/dd/yyyy");
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Calendar c = Calendar.getInstance();
			c.set(Calendar.MINUTE, 0);
			c.setTime(simpleDateFormat
					.parse(simpleDateFormat.format(simpleDateFormat1.parse(institutionalCase.getNextHearingDate()))));
			c.set(Calendar.SECOND, 0);
			Date d1 = c.getTime();
			event.setStartDate(d1);
			c = Calendar.getInstance();
			c.set(Calendar.MINUTE, 23);
			c.setTime(simpleDateFormat
					.parse(simpleDateFormat.format(simpleDateFormat1.parse(institutionalCase.getNextHearingDate()))));
			c.set(Calendar.HOUR_OF_DAY, 23);
			c.set(Calendar.SECOND, 0);
			Date d2 = c.getTime();
			event.setEndDate(d2);
			eventRepo.save(event);
			againstInstitutionalCase.setNextHearingDate(
					simpleDateFormat.format(simpleDateFormat1.parse(institutionalCase.getNextHearingDate())));
		}
		if (institutionalCase.getPreviousHearingDate() != null) {
			againstInstitutionalCase.setPreviousHearingDate(institutionalCase.getPreviousHearingDate());
		}
		againstInstRepo.save(againstInstitutionalCase);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping(value = "/for/export", produces = "text/csv")
	public void exportForInstitutionalCases(@RequestBody ExportVO exportVO, HttpServletResponse response)
			throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException, ParseException {
		String csvFileName = "FOR_INSTITUTION.csv";
		response.setContentType("text/csv");
		String fileName = "tbl_HDFC_RECOURSE_AGC.csv";
		CSVReader reader = new CSVReader(new FileReader(fileName));
		// if the first line is the header
		String[] header = reader.readNext();
		// creates mock data
		String headerKey = "Content-Disposition";
		String headerValue = String.format("attachment; filename=\"%s\"", csvFileName);
		response.setHeader(headerKey, headerValue);
		response.setHeader("filename", csvFileName);
		// uses the Super CSV API to generate CSV data from the model data
		ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
		List<ForInstitutionalCase> cases1 = new ArrayList<>();
		List<Long> institutionalCaseIds = exportVO.getInstitutionalCaseIds();
		if (institutionalCaseIds.size() > 0) {
			for (Long id : institutionalCaseIds) {
				cases1.add(institutionalCaseRepo.findByInstitutionIdAndBranchIdAndId(exportVO.getInstitutionId(),
						exportVO.getBranchId(), id));
			}
		} else {
			cases1.addAll(institutionalCaseRepo.findByInstitutionIdAndBranchId(exportVO.getInstitutionId(),
					exportVO.getBranchId()));
		}
		csvWriter.writeHeader(header);
		for (ForInstitutionalCase cases : cases1) {
			CsvToCaseFileDetailsMappingBean csv = new CsvToCaseFileDetailsMappingBean();
			BeanUtils.copyProperties(cases, csv);
			if (csv.getNextHearingDate() != null && !csv.getNextHearingDate().isEmpty()) {
				SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("MM/dd/yyyy");
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
				csv.setNextHearingDate(simpleDateFormat1.format(simpleDateFormat.parse(csv.getNextHearingDate())));
			}
			csvWriter.write(csv, dataMapping);
		}
		csvWriter.close();
	}

	@PostMapping(value = "/export/files", produces = "application/zip")
	public void exportForInstitutionalCasesWithFiles(@RequestBody ExportVO exportVO, HttpServletResponse response)
			throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ZipOutputStream zos = new ZipOutputStream(baos);
		try {
			ZipEntry zipentry = null;
			byte bytes[] = new byte[4096];
			response.setContentType("application/zip");
			response.setHeader("Content-Disposition", "attachment; filename=casefiles.zip");
			response.setHeader("filename", "attachment; filename=casefiles.zip");
			List<String> caseIds = exportVO.getCaseIds();
			for (String caseId : caseIds) {
				List<CaseFiles> caseFiles = caseFilesRepo.findByCaseIdAndThruDateAndRemarkOnly(caseId, null, false);
				for (CaseFiles caseFile : caseFiles) {
					zipentry = new ZipEntry(caseFile.getFileName());
					zos.putNextEntry(zipentry);
					InputStream in = s3ServicesImpl.downloadFile(caseFile.getFilePath(), caseFile.getFileName());
					int bytesRead = -1;
					while ((bytesRead = in.read(bytes)) != -1) {
						zos.write(bytes, 0, bytesRead);
					}
					in.close();
					zos.closeEntry();
				}
			}
			zipentry = new ZipEntry("file.csv");
			zos.putNextEntry(zipentry);
			ICsvBeanWriter csvWriter = new CsvBeanWriter(new OutputStreamWriter(zos), CsvPreference.STANDARD_PREFERENCE);
			
			List<ForInstitutionalCase> cases1 = new ArrayList<>();
			List<Long> institutionalCaseIds = exportVO.getInstitutionalCaseIds();
			if (institutionalCaseIds.size() > 0) {
				for (Long id : institutionalCaseIds) {
					cases1.add(institutionalCaseRepo.findByInstitutionIdAndBranchIdAndId(exportVO.getInstitutionId(),
							exportVO.getBranchId(), id));
				}
			} else {
				cases1.addAll(institutionalCaseRepo.findByInstitutionIdAndBranchId(exportVO.getInstitutionId(),
						exportVO.getBranchId()));
			}
			for (ForInstitutionalCase cases : cases1) {
				CsvToCaseFileDetailsMappingBean csv = new CsvToCaseFileDetailsMappingBean();
				BeanUtils.copyProperties(cases, csv);
				if (csv.getNextHearingDate() != null && !csv.getNextHearingDate().isEmpty()) {
					SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("MM/dd/yyyy");
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
					csv.setNextHearingDate(simpleDateFormat1.format(simpleDateFormat.parse(csv.getNextHearingDate())));
				}
				csvWriter.write(csv, dataMapping);
			}
		
			csvWriter.flush();
		    
			

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			zos.flush();
			baos.flush();
			zos.close();
			baos.close();
		}
		response.getOutputStream().write(baos.toByteArray());
	}

	@PostMapping(value = "/against/export", produces = "text/csv")
	public void exportAgainstInstitutionalCases(@RequestBody ExportVO exportVO, HttpServletResponse response)
			throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException, ParseException {
		String csvFileName = "AGC.csv";
		response.setContentType("text/csv");
		String fileName = "/var/infotek/tbl_HDFC_RECOURSE_AGC.csv";
		CSVReader reader = new CSVReader(new FileReader(fileName));
		// if the first line is the header
		String[] header = reader.readNext();
		// creates mock data
		String headerKey = "Content-Disposition";
		String headerValue = String.format("attachment; filename=\"%s\"", csvFileName);
		response.setHeader(headerKey, headerValue);
		response.setHeader("filename", csvFileName);
		// uses the Super CSV API to generate CSV data from the model data
		ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
		List<AgainstInstitutionalCase> cases1 = new ArrayList<>();
		List<Long> institutionalCaseIds = exportVO.getInstitutionalCaseIds();
		if (institutionalCaseIds.size() > 0) {
			for (Long id : institutionalCaseIds) {
				cases1.add(againstInstRepo.findByInstitutionIdAndBranchIdAndId(exportVO.getInstitutionId(),
						exportVO.getBranchId(), id));
			}
		} else {
			cases1.addAll(againstInstRepo.findByInstitutionIdAndBranchId(exportVO.getInstitutionId(),
					exportVO.getBranchId()));
		}
		csvWriter.writeHeader(header);
		for (AgainstInstitutionalCase cases : cases1) {
			CsvToCaseFileDetailsMappingBean csv = new CsvToCaseFileDetailsMappingBean();
			BeanUtils.copyProperties(cases, csv);
			if (csv.getNextHearingDate() != null && !csv.getNextHearingDate().isEmpty()) {
				SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("MM/dd/yyyy");
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
				csv.setNextHearingDate(simpleDateFormat1.format(simpleDateFormat.parse(csv.getNextHearingDate())));
			}
			csvWriter.write(csv, dataMapping);
		}
		csvWriter.close();
	}

	@PostMapping("/update/for/compliance")
	public ComplianceResponse updateForInstitutionalCaseCompliance(
			@RequestBody ForInstitutionalCaseCompliance caseCompliance) {
		ComplianceResponse complianceResponse = new ComplianceResponse();
		if (caseCompliance.getLegalCase() == null || caseCompliance.getLegalCase().getId() == null) {
			complianceResponse.setFailureReason("Invalid request");
			complianceResponse.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			return complianceResponse;
		}

		ForInstitutionalCase legalCase = institutionalCaseRepo.findById(caseCompliance.getLegalCase().getId());
		Long userId = legalCase.getUserId();
		List<Stage> stage = stageRepository.findByStageCode(legalCase.getCaseStage());
		List<Recourse> recourse = recourseRepository.findByRecourseCode(legalCase.getRecourse());
		List<String> roles = findUserRole(userId);
		if (roles.contains(UserRole.EMPLOYEE.name()) || roles.contains(UserRole.CUSTOMER.name())) {
			userId = userRepository.findById(userId).get(0).getClientId();
		}
		List<Compliance> compliances = complianceRepository.findByStageAndRecourseAndUserId(stage.get(0),
				recourse.get(0), userId);
		if (!compliances.isEmpty()) {
			legalCase.setCompliance(Boolean.TRUE);
			institutionalCaseRepo.save(legalCase);
			for (Compliance compliance2 : compliances) {
				caseCompliance = new ForInstitutionalCaseCompliance();
				caseCompliance.setCompliance(compliance2);
				caseCompliance.setLegalCase(legalCase);
				caseCompliance.setStatus(ComplianceStatus.OPEN.name());
				caseCompliance.setUpdatedDate(new Date());
				caseCompliance.setCreatedDate(new Date());
				forInstitutionalCaseComplianceRepository.save(caseCompliance);
			}
			complianceResponse.setSuccessMessage("Complaince has been updated successfully");
			complianceResponse.setHttpCode(HttpStatus.OK.value());
		} else {
			complianceResponse.setFailureReason("No complaince found");
			complianceResponse.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		return complianceResponse;

	}

	@GetMapping("/for/compliance")
	public List<ForInstitutionalCaseCompliance> getForCaseCompliance(Long caseId) {
		List<ForInstitutionalCaseCompliance> caseCompliances = new ArrayList<>();
		ForInstitutionalCase legalCase = institutionalCaseRepo.findById(caseId);
		if (legalCase.getCompliance()) {
			caseCompliances = forInstitutionalCaseComplianceRepository.findByLegalCaseAndStatus(legalCase,
					ComplianceStatus.OPEN.name());
		}
		return caseCompliances;
	}

	@PutMapping("/for/compliance")
	public ComplianceResponse updateForCaseCompliance(Long caseComplianceId) {
		ForInstitutionalCaseCompliance caseCompliance = forInstitutionalCaseComplianceRepository
				.findById(caseComplianceId);
		ComplianceResponse complianceResponse = new ComplianceResponse();
		if (caseCompliance != null) {
			caseCompliance.setStatus(ComplianceStatus.CLOSED.name());
			caseCompliance.setUpdatedDate(new Date());
			forInstitutionalCaseComplianceRepository.save(caseCompliance);
		}
		List<ForInstitutionalCaseCompliance> caseCompliances = forInstitutionalCaseComplianceRepository
				.findByLegalCaseAndStatus(caseCompliance.getLegalCase(), ComplianceStatus.OPEN.name());
		if (caseCompliances.size() == 0) {
			ForInstitutionalCase legalCase = institutionalCaseRepo.findById(caseCompliance.getLegalCase().getId());
			legalCase.setCompliance(Boolean.FALSE);
			institutionalCaseRepo.save(legalCase);
		}
		complianceResponse.setSuccessMessage("Complaince has been updated successfully");
		complianceResponse.setHttpCode(HttpStatus.OK.value());
		return complianceResponse;
	}

	@PostMapping("/update/against/compliance")
	public ComplianceResponse updateAgainstInstitutionalCaseCompliance(
			@RequestBody AgainstInstitutionalCaseCompliance caseCompliance) {
		ComplianceResponse complianceResponse = new ComplianceResponse();
		if (caseCompliance.getLegalCase() == null || caseCompliance.getLegalCase().getId() == null) {
			complianceResponse.setFailureReason("Invalid request");
			complianceResponse.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			return complianceResponse;
		}

		AgainstInstitutionalCase legalCase = againstInstRepo.findById(caseCompliance.getLegalCase().getId());
		List<Stage> stage = stageRepository.findByStageCode(legalCase.getCaseStage());
		List<Recourse> recourse = recourseRepository.findByRecourseCode(legalCase.getRecourse());
		Long userId = legalCase.getUserId();
		List<String> roles = findUserRole(userId);
		if (roles.contains(UserRole.EMPLOYEE.name()) || roles.contains(UserRole.CUSTOMER.name())) {
			userId = userRepository.findById(userId).get(0).getClientId();
		}
		List<Compliance> compliances = complianceRepository.findByStageAndRecourseAndUserId(stage.get(0),
				recourse.get(0), userId);
		if (!compliances.isEmpty()) {
			legalCase.setCompliance(Boolean.TRUE);
			againstInstRepo.save(legalCase);
			for (Compliance compliance2 : compliances) {
				caseCompliance = new AgainstInstitutionalCaseCompliance();
				caseCompliance.setCompliance(compliance2);
				caseCompliance.setLegalCase(legalCase);
				caseCompliance.setStatus(ComplianceStatus.OPEN.name());
				caseCompliance.setUpdatedDate(new Date());
				caseCompliance.setCreatedDate(new Date());
				againstInstitutionalCaseComplianceRepository.save(caseCompliance);
			}
			complianceResponse.setSuccessMessage("Complaince has been updated successfully");
			complianceResponse.setHttpCode(HttpStatus.OK.value());
		} else {
			complianceResponse.setFailureReason("No complaince found");
			complianceResponse.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		return complianceResponse;

	}

	@GetMapping("/against/compliance")
	public List<AgainstInstitutionalCaseCompliance> getAgainstInstCaseCompliance(Long caseId) {
		List<AgainstInstitutionalCaseCompliance> caseCompliances = new ArrayList<>();
		AgainstInstitutionalCase legalCase = againstInstRepo.findById(caseId);
		if (legalCase.getCompliance()) {
			caseCompliances = againstInstitutionalCaseComplianceRepository.findByLegalCaseAndStatus(legalCase,
					ComplianceStatus.OPEN.name());
		}
		return caseCompliances;
	}

	@PutMapping("/against/compliance")
	public ComplianceResponse updateAgainstCaseCompliance(Long caseComplianceId) {
		ComplianceResponse complianceResponse = new ComplianceResponse();
		AgainstInstitutionalCaseCompliance caseCompliance = againstInstitutionalCaseComplianceRepository
				.findById(caseComplianceId);
		if (caseCompliance != null) {
			caseCompliance.setStatus(ComplianceStatus.CLOSED.name());
			caseCompliance.setUpdatedDate(new Date());
			againstInstitutionalCaseComplianceRepository.save(caseCompliance);
		}
		List<AgainstInstitutionalCaseCompliance> caseCompliances = againstInstitutionalCaseComplianceRepository
				.findByLegalCaseAndStatus(caseCompliance.getLegalCase(), ComplianceStatus.OPEN.name());
		if (caseCompliances.size() == 0) {
			AgainstInstitutionalCase legalCase = againstInstRepo.findById(caseCompliance.getLegalCase().getId());
			legalCase.setCompliance(Boolean.FALSE);
			againstInstRepo.save(legalCase);
		}
		complianceResponse.setSuccessMessage("Complaince has been updated successfully");
		complianceResponse.setHttpCode(HttpStatus.OK.value());
		return complianceResponse;
	}
}
