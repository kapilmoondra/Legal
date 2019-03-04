package com.legalfriend.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.legalfriend.config.Messages;
import com.legalfriend.entities.CaseCompliance;
import com.legalfriend.entities.CaseFiles;
import com.legalfriend.entities.Compliance;
import com.legalfriend.entities.DashboardReport;
import com.legalfriend.entities.Event;
import com.legalfriend.entities.IndividualBilling;
import com.legalfriend.entities.IndividualBillingMaster;
import com.legalfriend.entities.LegalCase;
import com.legalfriend.entities.LegalCaseDashboard;
import com.legalfriend.entities.LegalCaseVO;
import com.legalfriend.entities.Organization;
import com.legalfriend.entities.Recourse;
import com.legalfriend.entities.Stage;
import com.legalfriend.entities.User;
import com.legalfriend.entities.UserCases;
import com.legalfriend.entities.UserRelationship;
import com.legalfriend.enums.ComplianceStatus;
import com.legalfriend.enums.UserRole;
import com.legalfriend.repository.BranchRepository;
import com.legalfriend.repository.CaseComplianceRepository;
import com.legalfriend.repository.CaseFilesRepository;
import com.legalfriend.repository.CaseRepository;
import com.legalfriend.repository.ComplianceRepository;
import com.legalfriend.repository.EventRepository;
import com.legalfriend.repository.IndividualBillingMasterRepository;
import com.legalfriend.repository.IndividualBillingRepository;
import com.legalfriend.repository.RecourseRepository;
import com.legalfriend.repository.StageRepository;
import com.legalfriend.repository.UserRelationshipRepository;
import com.legalfriend.repository.UserRepository;
import com.legalfriend.response.ComplianceResponse;
import com.legalfriend.response.LegalFriendResponse;
import com.legalfriend.service.AgainstInstitutionalCaseService;
import com.legalfriend.service.CaseService;
import com.legalfriend.service.ForInstitutionalCaseService;
import com.legalfriend.util.CaseFileManagementService;
import com.legalfriend.util.CsvFileReader;
import com.legalfriend.util.EmailService;

@RestController
@RequestMapping("/case")
public class CaseController {

	@Autowired
	CaseService caseService;

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	ComplianceRepository complianceRepository;

	@Autowired
	RecourseRepository recourseRepository;

	@Autowired
	StageRepository stageRepository;

	@Autowired
	CaseComplianceRepository caseComplianceRepository;

	@Autowired
	CaseRepository caseRepository;

	@Autowired
	EventRepository eventRepo;

	@Autowired
	CaseFilesRepository caseFilesRepo;

	@Autowired
	CaseFileManagementService caseFileManagementService;

	@Autowired
	UserRelationshipRepository userRelationshipRepo;

	@Autowired
	Messages messages;

	@Autowired
	ForInstitutionalCaseService forInstitutionalService;

	@Autowired
	AgainstInstitutionalCaseService againstInstitutionalService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	EmailService emailService;

	@Autowired
	CsvFileReader csvFileReader;

	@Autowired
	BranchRepository branchRepository;

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private IndividualBillingRepository individualBillingRepository;

	@Autowired
	private IndividualBillingMasterRepository billingMasterRepository;

	private String FETCH_USER_ROLE = "select r.role_name from user_role ur left join role r on r.id=ur.role_id WHERE ur.user_id = :userId";

	private String FETCH_ALL_USER_CASES = "select lc.id, lc.case_id, lc.court_case_id,  c.first_name as customerFirstName, "
			+ "c.last_name as customerLastName, r.recourse_code, s.stage_name, lc.next_hearing_date, "
			+ "b.branch_name, e.first_name as empFirstName, e.last_name as empLastName, lc.completion_date, lc.parent_case_id, lc.ground_for_closing_file, lc.compliance from legal_case lc "
			+ "left outer join user c on lc.customer_id=c.id  left outer join user e on lc.employee_id=e.id "
			+ "left outer join stage s on s.id=lc.fk_stage_id "
			+ "left outer join recourse r on r.id = lc.fk_recourse_id "
			+ "left outer join branch b on b.id = lc.fk_branch_id where lc.fk_user_id = :userId  and lc.fk_branch_id = :branchId";

	private String FETCH_ALL_MANGER_EMP_CASES = "select lc.id, lc.case_id, lc.court_case_id,  c.first_name as customerFirstName, "
			+ "c.last_name as customerLastName, r.recourse_code, s.stage_name, lc.next_hearing_date, "
			+ "b.branch_name, e.first_name as empFirstName, e.last_name as empLastName, lc.completion_date, lc.parent_case_id, lc.ground_for_closing_file, lc.compliance from legal_case lc "
			+ "left outer join user c on lc.customer_id=c.id  left outer join user e on lc.employee_id=e.id "
			+ "left outer join stage s on s.id=lc.fk_stage_id "
			+ "left outer join recourse r on r.id = lc.fk_recourse_id "
			+ "left outer join branch b on b.id = lc.fk_branch_id where (lc.employee_id = :userId OR lc.manager_id = :userId) and lc.fk_branch_id = :branchId";

	private String FETCH_ALL_CUSTOMER_CASES = "select lc.id, lc.case_id, lc.court_case_id,  c.first_name as customerFirstName, "
			+ "c.last_name as customerLastName, r.recourse_code, s.stage_name, lc.next_hearing_date, "
			+ "b.branch_name, e.first_name as empFirstName, e.last_name as empLastName, lc.completion_date, lc.parent_case_id, lc.ground_for_closing_file, lc.compliance from legal_case lc "
			+ "left outer join user c on lc.customer_id=c.id  left outer join user e on lc.employee_id=e.id "
			+ "left outer join stage s on s.id=lc.fk_stage_id "
			+ "left outer join recourse r on r.id = lc.fk_recourse_id "
			+ "left outer join branch b on b.id = lc.fk_branch_id where lc.customer_id = :userId";

	private String FETCH_CASE_HISTORY = "select r.rev, r.revtstmp, lc.court_case_id, lc.court_place, l.case_id, lc.next_hearing_date, lc.ground_for_closing_file, lc.filing_date, lc.last_hearing_date, s.stage_name, lc.remark_history, lc.remark_file, u.first_name, u.last_name from legal_case_aud lc left join revinfo r on r.rev=lc.rev left outer join legal_case l on lc.parent_case_id=l.parent_case_id left outer join stage s on s.id = lc.fk_stage_id left outer join user u on u.id = lc.fk_user_id where lc.id=:caseId order by r.rev";

	private static final Logger LOGGER = LoggerFactory.getLogger(CaseController.class);

	@PostMapping("/add")
	public LegalFriendResponse addCase(@RequestParam("legalCase") String legalCase,
			@RequestParam(value = "file", required = false) MultipartFile uploadedFile) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		LegalCase caseObj = mapper.readValue(legalCase, LegalCase.class);
		LegalCase createdCase = null;
		LegalFriendResponse caseResponse = new LegalFriendResponse();
		if (caseObj.getUserId() != null) {
			Recourse recourse = recourseRepository.findById(caseObj.getRecourseId());
			List<Stage> stages = stageRepository.findByIdAndRecourse(caseObj.getStageId(), recourse);
			if (stages.size() > 0) {
				createdCase = caseRepository.save(caseObj);
				String caseId = "I/" + recourse.getRecourseCode() + "/" + createdCase.getId();
				createdCase.setCaseId(caseId);
				caseRepository.save(createdCase);
				if (createdCase.getNextHearingDate() != null) {
					Event event = new Event();
					event.setEventName("Hearing Date for case " + caseId);
					event.setEventDescription("Hearing Date for case " + caseId);
					event.setUserId(createdCase.getUserId());
					event.setReferenceNumber(createdCase.getCaseId());
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
					Calendar c = Calendar.getInstance();
					c.set(Calendar.MINUTE, 0);
					c.setTime(simpleDateFormat.parse(simpleDateFormat.format(caseObj.getNextHearingDate())));
					c.set(Calendar.SECOND, 0);
					Date d1 = c.getTime();
					event.setStartDate(d1);
					c = Calendar.getInstance();
					c.set(Calendar.MINUTE, 23);
					c.setTime(simpleDateFormat.parse(simpleDateFormat.format(caseObj.getNextHearingDate())));
					c.set(Calendar.HOUR_OF_DAY, 23);
					c.set(Calendar.SECOND, 0);
					Date d2 = c.getTime();
					event.setEndDate(d2);
					eventRepo.save(event);
				}
				List<IndividualBillingMaster> billingMasters = billingMasterRepository
						.findByStageAndRecourseAndUserId(stages.get(0), recourse, caseObj.getUserId());
				if (billingMasters.size() > 0) {
					IndividualBilling billing = new IndividualBilling();
					billing.setAmount(billingMasters.get(0).getAmount());
					billing.setCaseId(createdCase.getCaseId());
					billing.setRecourse(recourse);
					billing.setStage(stages.get(0));
					billing.setBillingDate(new Date());
					billing.setBranch(branchRepository.findById(caseObj.getBranchId()));
					billing.setUserId(createdCase.getUserId());
					individualBillingRepository.save(billing);
				}
				if (uploadedFile != null) {
					caseFileManagementService.uploadFile(caseObj.getUserId(), caseId, uploadedFile, null);
				}
				caseResponse.setHttpCode(HttpStatus.OK.value());
				caseResponse.setId(createdCase.getId());
				caseResponse.setSuccessMessage("Case added successfully");
				if (createdCase.getCustomerId() != null) {
					List<User> user = userRepository.findById(createdCase.getCustomerId());
					emailService.sendCaseCreatedEmail(user.get(0).getEmail(), createdCase.getCaseId());
				}
				if (createdCase.getManagerId() != null) {
					List<User> user = userRepository.findById(createdCase.getManagerId());
					emailService.sendCaseCreatedEmail(user.get(0).getEmail(), createdCase.getCaseId());
				}
				if (createdCase.getEmployeeId() != null) {
					List<User> user = userRepository.findById(createdCase.getEmployeeId());
					emailService.sendCaseCreatedEmail(user.get(0).getEmail(), createdCase.getCaseId());
				}
			} else {
				caseResponse.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
				caseResponse.setSuccessMessage("Invalid stage/recourse mapping");
			}
		} else {
			caseResponse.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			caseResponse.setSuccessMessage("Invalid userId");
		}
		return caseResponse;
	}

	@PostMapping("/update")
	public LegalFriendResponse updateCase(@RequestParam("legalCase") String legalCase,
			@RequestParam(value = "file", required = false) MultipartFile uploadedFile) throws Exception {
		LegalFriendResponse caseResponse = new LegalFriendResponse();
		ObjectMapper mapper = new ObjectMapper();
		LegalCase caseObj = mapper.readValue(legalCase, LegalCase.class);
		LegalCase createdCase = null;
		LegalCase persistedCase = caseRepository.findOne(caseObj.getId());
		if (persistedCase.getUserId().equals(caseObj.getUserId())) {
			Recourse recourse = recourseRepository.findById(caseObj.getRecourseId());
			List<Stage> stages = stageRepository.findByIdAndRecourse(caseObj.getStageId(), recourse);
			if (stages.size() > 0) {
				List<IndividualBillingMaster> billingMasters = billingMasterRepository
						.findByStageAndRecourseAndUserId(stages.get(0), recourse, caseObj.getUserId());
				if (billingMasters.size() > 0 && !persistedCase.getStageId().equals(caseObj.getStageId())) {
					IndividualBilling billing = new IndividualBilling();
					billing.setAmount(billingMasters.get(0).getAmount());
					billing.setCaseId(persistedCase.getCaseId());
					billing.setRecourse(recourse);
					billing.setStage(stages.get(0));
					billing.setBillingDate(new Date());
					billing.setBranch(branchRepository.findById(caseObj.getBranchId()));
					billing.setUserId(persistedCase.getUserId());
					individualBillingRepository.save(billing);
				}
				BeanUtils.copyProperties(caseObj, persistedCase);
				createdCase = caseRepository.save(persistedCase);
				Session session = sessionFactory.openSession();
				session.beginTransaction();
				if (caseObj.getNextHearingDate() != null) {
					Event event = eventRepo.findByReferenceNumber(createdCase.getCaseId());
					eventRepo.delete(event);
					event = new Event();
					event.setEventName("Hearing Date for case " + createdCase.getCaseId());
					event.setEventDescription("Hearing Date for case " + createdCase.getCaseId());
					event.setUserId(createdCase.getUserId());
					event.setReferenceNumber(createdCase.getCaseId());
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
					Calendar c = Calendar.getInstance();
					c.set(Calendar.MINUTE, 0);
					c.setTime(simpleDateFormat.parse(simpleDateFormat.format(caseObj.getNextHearingDate())));
					c.set(Calendar.SECOND, 0);
					Date d1 = c.getTime();
					event.setStartDate(d1);
					c = Calendar.getInstance();
					c.set(Calendar.MINUTE, 23);
					c.setTime(simpleDateFormat.parse(simpleDateFormat.format(caseObj.getNextHearingDate())));
					c.set(Calendar.HOUR_OF_DAY, 23);
					c.set(Calendar.SECOND, 0);
					Date d2 = c.getTime();
					event.setEndDate(d2);
					eventRepo.save(event);
				}
				if (uploadedFile != null) {
					caseFileManagementService.uploadFile(caseObj.getUserId(), persistedCase.getCaseId(), uploadedFile,
							null);
				}
				caseResponse.setHttpCode(HttpStatus.OK.value());
				caseResponse.setSuccessMessage("Case updated successfully");
				session.close();
			} else {
				caseResponse.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
				caseResponse.setSuccessMessage("Invalid stage/recourse mapping");
			}
			if (createdCase.getCustomerId() != null) {
				List<User> user = userRepository.findById(createdCase.getCustomerId());
				emailService.sendCaseCreatedEmail(user.get(0).getEmail(), createdCase.getCaseId());
			}
			if (createdCase.getManagerId() != null) {
				List<User> user = userRepository.findById(createdCase.getManagerId());
				emailService.sendCaseCreatedEmail(user.get(0).getEmail(), createdCase.getCaseId());
			}
			if (createdCase.getEmployeeId() != null) {
				List<User> user = userRepository.findById(createdCase.getEmployeeId());
				emailService.sendCaseCreatedEmail(user.get(0).getEmail(), createdCase.getCaseId());
			}
		} else {
			caseResponse.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			caseResponse.setSuccessMessage("Invalid userId");
		}
		return caseResponse;
	}

	@PostMapping("/file/upload")
	public LegalFriendResponse uploadCaseRemarkAndFile(@RequestParam("caseId") Long caseId,
			@RequestParam(value = "remark") String remark,
			@RequestParam(value = "file", required = false) MultipartFile uploadedFile) throws Exception {
		LegalFriendResponse caseResponse = new LegalFriendResponse();
		LegalCase persistedCase = caseRepository.findOne(caseId);
		caseFileManagementService.updateCaseRemarkAndFile(persistedCase.getUserId(), persistedCase.getCaseId(),
				uploadedFile, remark);
		if (persistedCase.getCustomerId() != null) {
			List<User> user = userRepository.findById(persistedCase.getCustomerId());
			emailService.sendRemarkEmail(user.get(0).getEmail(), persistedCase.getCaseId());
		}
		if (persistedCase.getManagerId() != null) {
			List<User> user = userRepository.findById(persistedCase.getManagerId());
			emailService.sendRemarkEmail(user.get(0).getEmail(), persistedCase.getCaseId());
		}
		if (persistedCase.getEmployeeId() != null) {
			List<User> user = userRepository.findById(persistedCase.getEmployeeId());
			emailService.sendRemarkEmail(user.get(0).getEmail(), persistedCase.getCaseId());
		}
		caseResponse.setHttpCode(HttpStatus.OK.value());
		caseResponse.setSuccessMessage("Case remark updated successfully");
		return caseResponse;
	}

	@DeleteMapping("/delete")
	public LegalFriendResponse deleteCase(@RequestParam Long caseId) {
		LegalFriendResponse caseResponse = new LegalFriendResponse();
		LegalCase persistedCase = caseRepository.findOne(caseId);
		if (persistedCase != null) {
			caseRepository.delete(persistedCase);
			caseResponse.setHttpCode(HttpStatus.OK.value());
			caseResponse.setSuccessMessage("Case deleted successfully");
		} else {
			caseResponse.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			caseResponse.setSuccessMessage("No Case with Id exists");
		}
		return caseResponse;
	}

	@GetMapping("/getCase")
	public LegalCase getCase(@RequestParam Long caseId) {
		LegalCase persistedCase = caseRepository.findOne(caseId);
		List<CaseFiles> caseFiles = caseFilesRepo.findByCaseIdAndThruDateAndRemarkOnly(persistedCase.getCaseId(), null,
				false);
		persistedCase.setCaseFiles(caseFiles);
		return persistedCase;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/history")
	public List<LegalCaseVO> getCaseHistory(@RequestParam Long caseId) throws ParseException {
		Session session = sessionFactory.openSession();
		Query query = session.createSQLQuery(FETCH_CASE_HISTORY);
		query.setLong("caseId", caseId);
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd mm:ss");
		List<Object[]> rows = (List<Object[]>) query.list();
		List<LegalCaseVO> legalCaseAuds = new ArrayList<>();
		for (Object[] row : rows) {
			LegalCaseVO legalCaseAud = new LegalCaseVO();
			legalCaseAud.setRev(Long.parseLong(row[0].toString()));
			legalCaseAud.setRevTimeStamp(row[1] != null ? Long.parseLong(row[1].toString()) : null);
			legalCaseAud.setCourtCaseId(row[2] != null ? row[2].toString() : null);
			legalCaseAud.setFkCourtDistrict(row[3] != null ? Long.parseLong(row[3].toString()) : null);
			legalCaseAud.setParentCaseId(row[4] != null ? row[4].toString() : null);
			legalCaseAud
					.setNextHearingDate(row[5] != null ? formatter.format(formatter.parse(row[5].toString())) : null);
			legalCaseAud.setGroundForClosingFile(row[6] != null ? row[6].toString() : null);
			legalCaseAud.setFilingDate(row[7] != null ? formatter.format(formatter.parse(row[7].toString())) : null);
			legalCaseAud
					.setLastHearingDate(row[8] != null ? formatter.format(formatter.parse(row[8].toString())) : null);
			legalCaseAud.setStage(row[9].toString());
			legalCaseAud.setRemark(row[10] != null ? row[10].toString() : null);
			legalCaseAud.setRemarkFile(row[11] != null ? row[11].toString() : null);
			legalCaseAud.setFirstName(row[12] != null ? row[12].toString() : null);
			legalCaseAud.setLastName(row[13] != null ? row[13].toString() : null);
			legalCaseAuds.add(legalCaseAud);
		}
		session.close();
		return legalCaseAuds;
	}

	@DeleteMapping("/file")
	public ResponseEntity<?> deleteCaseFile(@RequestParam Long caseFileId) {
		CaseFiles persistedCase = caseFilesRepo.findOne(caseFileId);
		persistedCase.setThruDate(new Date());
		caseFilesRepo.save(persistedCase);
		return new ResponseEntity<>(HttpStatus.OK);
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

	@GetMapping("/caseList")
	public List<UserCases> listCases(@RequestParam Long userId, @RequestParam Long branchId) throws ParseException {
		List<UserCases> userCases = new ArrayList<>();
		Query query = null;
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Session session = sessionFactory.openSession();
		List<String> list = findUserRole(userId);
		if (list.contains(UserRole.CLIENT.name())) {
			query = session.createSQLQuery(FETCH_ALL_CUSTOMER_CASES);
		} else if (list.contains(UserRole.MANAGER.name()) || list.contains(UserRole.EMPLOYEE.name())) {
			query = session.createSQLQuery(FETCH_ALL_MANGER_EMP_CASES);
		} else {
			query = session.createSQLQuery(FETCH_ALL_USER_CASES);
		}
		if (list.contains(UserRole.EMPLOYEE.name())) {
			userId = findEmployeeClient(userId);
		}
		query.setLong("userId", userId);
		if (!branchId.equals(-1) && !list.contains(UserRole.CLIENT.name())) {
			query.setLong("branchId", branchId);
		}
		List<Object[]> rows = (List<Object[]>) query.list();
		for (Object[] row : rows) {
			UserCases userCase = new UserCases();
			userCase.setId(Long.parseLong(row[0].toString()));
			userCase.setCaseId(row[1] != null ? row[1].toString() : null);
			userCase.setCourtCaseId(row[2] != null ? row[2].toString() : null);
			userCase.setCustomerFirstName(row[3] != null ? row[3].toString() : null);
			userCase.setCustomerLastName(row[4] != null ? row[4].toString() : null);
			userCase.setRecourseCode(row[5] != null ? row[5].toString() : null);
			userCase.setStageName(row[6] != null ? row[6].toString() : null);
			userCase.setNextHearingDate(row[7] != null ? formatter.format(formatter.parse(row[7].toString())) : null);
			userCase.setBranchName(row[8] != null ? row[8].toString() : null);
			userCase.setEmpFirstName(row[9].toString());
			userCase.setEmpLastName(row[10].toString());
			userCase.setCompletionDate(row[11] != null ? formatter.format(formatter.parse(row[11].toString())) : null);
			userCase.setParentCaseId(row[12] != null ? row[12].toString() : null);
			userCase.setGroundForClosingFile(row[13] != null ? row[13].toString() : null);
			userCase.setCompliance(row[14] != null ? Boolean.valueOf(row[14].toString()) : Boolean.FALSE);
			userCases.add(userCase);
		}
		session.close();
		return userCases;
	}

	private Long findEmployeeClient(Long userId) {
		UserRelationship u = userRelationshipRepo.findByToUserIdAndToRoleIdAndFromRoleIdAndThruDate(userId,
				UserRole.EMPLOYEE.name(), UserRole.ADMIN.name(), null);
		return u.getFromUserId();
	}

	@PostMapping("/update/compliance")
	public ComplianceResponse updateCaseCompliance(@RequestBody CaseCompliance caseCompliance) {
		ComplianceResponse complianceResponse = new ComplianceResponse();
		if (caseCompliance.getLegalCase() == null || caseCompliance.getLegalCase().getId() == null) {
			complianceResponse.setFailureReason("Invalid request");
			complianceResponse.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			return complianceResponse;
		}

		LegalCase legalCase = caseRepository.findById(caseCompliance.getLegalCase().getId());
		Stage stage = stageRepository.findById(legalCase.getStageId());
		Recourse recourse = recourseRepository.findById(legalCase.getRecourseId());
		List<Compliance> compliances = complianceRepository.findByStageAndRecourse(stage, recourse);
		if (!compliances.isEmpty()) {
			legalCase.setCompliance(Boolean.TRUE);
			caseRepository.save(legalCase);
			for (Compliance compliance2 : compliances) {
				caseCompliance = new CaseCompliance();
				caseCompliance.setCompliance(compliance2);
				caseCompliance.setLegalCase(legalCase);
				caseCompliance.setStatus(ComplianceStatus.OPEN.name());
				caseCompliance.setUpdatedDate(new Date());
				caseCompliance.setCreatedDate(new Date());
				caseComplianceRepository.save(caseCompliance);
			}
			complianceResponse.setSuccessMessage("Complaince has been updated successfully");
			complianceResponse.setHttpCode(HttpStatus.OK.value());
		} else {
			complianceResponse.setFailureReason("No complaince found");
			complianceResponse.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		return complianceResponse;

	}

	@GetMapping("/compliance")
	public List<CaseCompliance> getCaseCompliance(Long caseId) {
		List<CaseCompliance> caseCompliances = new ArrayList<>();
		LegalCase legalCase = caseRepository.findById(caseId);
		if (legalCase.getCompliance()) {
			caseCompliances = caseComplianceRepository.findByLegalCaseAndStatus(legalCase,
					ComplianceStatus.OPEN.name());
		}
		return caseCompliances;
	}

	@PutMapping("/compliance")
	public CaseCompliance updateCaseCompliance(Long caseComplianceId) {
		CaseCompliance caseCompliance = caseComplianceRepository.findById(caseComplianceId);
		if (caseCompliance != null) {
			caseCompliance.setStatus(ComplianceStatus.CLOSED.name());
			caseCompliance.setUpdatedDate(new Date());
			caseComplianceRepository.save(caseCompliance);
		}
		List<CaseCompliance> caseCompliances = caseComplianceRepository
				.findByLegalCaseAndStatus(caseCompliance.getLegalCase(), ComplianceStatus.OPEN.name());
		if (caseCompliances.size() == 0) {
			LegalCase legalCase = caseRepository.findById(caseCompliance.getLegalCase().getId());
			legalCase.setCompliance(Boolean.FALSE);
			caseRepository.save(legalCase);
		}
		return caseCompliance;
	}

	@PutMapping("/hearing")
	public ResponseEntity<?> updateHearingDate(@RequestBody LegalCase caseObj) throws IOException, ParseException {
		LegalCase persistedCase = caseRepository.findById(caseObj.getId());
		if (caseObj.getNextHearingDate() != null) {
			persistedCase.setNextHearingDate(caseObj.getNextHearingDate());
			Event event = eventRepo.findByReferenceNumber(caseObj.getCaseId());
			if (event != null) {
				eventRepo.delete(event);
			}
			event = new Event();
			event.setEventName("Hearing Date for case " + persistedCase.getCaseId());
			event.setEventDescription("Hearing Date for case " + persistedCase.getCaseId());
			event.setUserId(persistedCase.getUserId());
			event.setReferenceNumber(persistedCase.getCaseId());
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Calendar c = Calendar.getInstance();
			c.set(Calendar.MINUTE, 0);
			c.setTime(simpleDateFormat.parse(simpleDateFormat.format(caseObj.getNextHearingDate())));
			c.set(Calendar.SECOND, 0);
			Date d1 = c.getTime();
			event.setStartDate(d1);
			c = Calendar.getInstance();
			c.set(Calendar.MINUTE, 23);
			c.setTime(simpleDateFormat.parse(simpleDateFormat.format(caseObj.getNextHearingDate())));
			c.set(Calendar.HOUR_OF_DAY, 23);
			c.set(Calendar.SECOND, 0);
			Date d2 = c.getTime();
			event.setEndDate(d2);
			eventRepo.save(event);
		}

		if (caseObj.getLastHearingDate() != null) {
			persistedCase.setLastHearingDate(caseObj.getLastHearingDate());
		}
		caseRepository.save(persistedCase);
		return new ResponseEntity<>(HttpStatus.OK);
	}

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

	@GetMapping("/month")
	public List<DashboardReport> getCasesByMonth(@RequestParam Long userId) {
		return caseService.findCaseCountByMonth(userId);
	}

	@GetMapping("/date")
	public List<DashboardReport> getCasesByDate(@RequestParam Long userId, @RequestParam String startDate,
			@RequestParam String endDate) {
		return caseService.findCaseCountByDate(userId, startDate, endDate);
	}

	@GetMapping("/week")
	public List<DashboardReport> getCasesByWeek(@RequestParam Long userId) {
		return caseService.findCaseCountByWeek(userId);
	}

	@GetMapping("/lastupdated")
	public List<LegalCaseDashboard> getRecentCases(@RequestParam Long userId) {
		return caseService.findUpdatedCases(userId);
	}

	@GetMapping("/all")
	public List<LegalCase> getAllCases() {
		return caseService.findAll();
	}

	@GetMapping("/org/")
	public List<Organization> getOrganizationCases() {
		return caseService.findOrganizationCases();
	}

	@GetMapping("/org/for")
	public List<Organization> getOrganizationForCases() {
		return forInstitutionalService.findOrganizationsForCaseCount();
	}

	@GetMapping("/org/against")
	public List<Organization> getOrganizationAgainstCases() {
		return againstInstitutionalService.findOrganizationsAgainstCaseCount();
	}
}
