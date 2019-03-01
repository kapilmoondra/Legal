package com.legalfriend.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.legalfriend.entities.BillingMaster;
import com.legalfriend.entities.Branch;
import com.legalfriend.entities.City;
import com.legalfriend.entities.Compliance;
import com.legalfriend.entities.Court;
import com.legalfriend.entities.CourtRequest;
import com.legalfriend.entities.DashboardReport;
import com.legalfriend.entities.District;
import com.legalfriend.entities.IndividualBillingMaster;
import com.legalfriend.entities.Institution;
import com.legalfriend.entities.Recourse;
import com.legalfriend.entities.RecourseRequest;
import com.legalfriend.entities.Role;
import com.legalfriend.entities.Stage;
import com.legalfriend.entities.StageRecourse;
import com.legalfriend.entities.StageRequest;
import com.legalfriend.entities.State;
import com.legalfriend.entities.User;
import com.legalfriend.enums.UserRole;
import com.legalfriend.repository.BillingMasterRepository;
import com.legalfriend.repository.BranchRepository;
import com.legalfriend.repository.CityRepository;
import com.legalfriend.repository.ComplianceRepository;
import com.legalfriend.repository.CourtRepository;
import com.legalfriend.repository.CourtRequestRepository;
import com.legalfriend.repository.DistrictRepository;
import com.legalfriend.repository.IndividualBillingMasterRepository;
import com.legalfriend.repository.InstitutionRepository;
import com.legalfriend.repository.RecourseRepository;
import com.legalfriend.repository.RecourseRequestRepository;
import com.legalfriend.repository.RoleRepository;
import com.legalfriend.repository.StageRepository;
import com.legalfriend.repository.StageRequestRepository;
import com.legalfriend.repository.StateRepository;
import com.legalfriend.repository.UserRepository;
import com.legalfriend.response.BillingResponse;
import com.legalfriend.response.BranchResponse;
import com.legalfriend.response.CityResponse;
import com.legalfriend.response.ComplianceResponse;
import com.legalfriend.response.CourtResponse;
import com.legalfriend.response.DistrictResponse;
import com.legalfriend.response.IndividualBillingResponse;
import com.legalfriend.response.InstitutionResponse;
import com.legalfriend.response.LegalFriendResponse;
import com.legalfriend.response.RecourseResponse;
import com.legalfriend.response.StageRecourseResponse;
import com.legalfriend.response.StageResponse;
import com.legalfriend.response.StateResponse;
import com.legalfriend.service.BranchService;
import com.legalfriend.service.CaseService;

@RestController
@RequestMapping("/master")
public class MasterManagementController {
	
	@Autowired
	CaseService caseService;
	
	@Autowired
	BranchService branchService;
	
	@Autowired
	CityRepository cityRepository;

	@Autowired
	StateRepository stateRepository;

	@Autowired
	DistrictRepository districtRepository;

	@Autowired
	CourtRepository courtRepository;

	@Autowired
	RecourseRepository recourseRepository;

	@Autowired
	RecourseRequestRepository recourseRequestRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	InstitutionRepository institutionRepository;

	@Autowired
	BranchRepository branchRepository;

	@Autowired
	ComplianceRepository complianceRepository;

	@Autowired
	StageRepository stageRepository;

	@Autowired
	StageRequestRepository stageRequestRepository;

	@Autowired
	BillingMasterRepository billingRepository;

	@Autowired
	IndividualBillingMasterRepository individualBillingMasterRepository;

	@Autowired
	RoleRepository roleRepo;
	
	@Autowired
	CourtRequestRepository courtRequestRepo;

	@Autowired
	private SessionFactory sessionFactory;

	private String FETCH_ADMIN_USER = "select * from user_role where user_id=:userId and role_id=:roleId";

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

	private Boolean isAdmin(Long userId) {
		Role role = roleRepo.findByRoleName(UserRole.ADMIN.name());
		Session session = sessionFactory.openSession();
		Query query = session.createSQLQuery(FETCH_ADMIN_USER);
		query.setLong("userId", userId);
		query.setLong("roleId", role.getId());
		List<Object[]> rows = (List<Object[]>) query.list();
		session.close();
		if (rows.size() > 0)
			return Boolean.TRUE;
		else
			return Boolean.FALSE;
	}

	@GetMapping(value = "/cities")
	public CityResponse getCities(@RequestParam Long userId) {
		CityResponse cityResponse = new CityResponse();
		Iterable<City> iterator = cityRepository.findAll();
		Set<City> cities = new HashSet<>();
		iterator.forEach(cities::add);
		List<User> users = userRepository.findById(userId);
		if (users.size() != 0 && users.get(0).getEmail() != null) {
			cities.addAll(cityRepository.findByUserId(users.get(0).getId()));
		}
		cityResponse.setHttpCode(HttpStatus.OK.value());
		cityResponse.setCities(cities);
		return cityResponse;
	}

	@GetMapping(value = "/states")
	public StateResponse getStates(@RequestParam Long userId) {
		StateResponse stateResponse = new StateResponse();
		Iterable<State> iterator = stateRepository.findAll();
		Set<State> states = new HashSet<>();
		iterator.forEach(states::add);
		List<User> users = userRepository.findById(userId);
		if (users.size() != 0 && users.get(0).getEmail() != null) {
			states.addAll(stateRepository.findByUserId(users.get(0).getId()));
		}
		stateResponse.setHttpCode(HttpStatus.OK.value());
		stateResponse.setStates(states);
		return stateResponse;
	}

	@PostMapping(value = "/add/city")
	public CityResponse addCity(@RequestBody City city) {
		CityResponse cityResponse = new CityResponse();
		if (city.getUserId() == null) {
			cityResponse.setFailureReason("Invalid request");
			cityResponse.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			return cityResponse;
		}
		List<City> cities = cityRepository.findByCityName(city.getCityName().toLowerCase());
		if (cities.size() == 0) {
			city.setCityName(city.getCityName().toLowerCase());
			City savedCity = cityRepository.save(city);
			cityResponse.setId(savedCity.getId());
			cityResponse.setSuccessMessage(" City has been successfully added");
			cityResponse.setHttpCode(HttpStatus.OK.value());
		} else {
			cityResponse.setFailureReason("City already present");
			cityResponse.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		return cityResponse;
	}

	@PostMapping("/update/city")
	public LegalFriendResponse updateCity(@RequestBody City city) {
		LegalFriendResponse friendResponse = new LegalFriendResponse();
		if (city.getUserId() == null) {
			friendResponse.setFailureReason("Invalid request");
			friendResponse.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			return friendResponse;
		}
		City c = cityRepository.findOne(city.getId());
		if (c.getUserId() == city.getUserId() || isAdmin(city.getUserId())) {
			BeanUtils.copyProperties(city, c);
			c.setCityName(city.getCityName().toLowerCase());
			cityRepository.save(c);
			friendResponse.setSuccessMessage("City has been successfully updated");
			friendResponse.setHttpCode(HttpStatus.OK.value());
		} else {
			friendResponse.setFailureReason("Invalid userId");
			friendResponse.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		return friendResponse;
	}

	@PostMapping(value = "/add/state")
	public StateResponse addState(@RequestBody State state) {
		StateResponse stateResponse = new StateResponse();
		if (state.getUserId() == null) {
			stateResponse.setFailureReason("Invalid request");
			stateResponse.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			return stateResponse;
		}
		List<State> states = stateRepository.findByStateName(state.getStateName().toLowerCase());
		if (states.size() == 0) {
			State saveState = stateRepository.save(state);
			stateResponse.setId(saveState.getId());
			stateResponse.setSuccessMessage(" State has been successfully added");
			stateResponse.setHttpCode(HttpStatus.OK.value());
		} else {
			stateResponse.setFailureReason("State already present");
			stateResponse.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		return stateResponse;
	}

	@PostMapping("/update/state")
	public LegalFriendResponse updateState(@RequestBody State state) {
		LegalFriendResponse stateResponse = new LegalFriendResponse();
		if (state.getUserId() == null) {
			stateResponse.setFailureReason("Invalid request");
			stateResponse.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			return stateResponse;
		}
		LegalFriendResponse friendResponse = new LegalFriendResponse();
		try {
			State s = stateRepository.findById(state.getId());
			if (s.getUserId() == state.getUserId() || isAdmin(s.getUserId())) {
				BeanUtils.copyProperties(state, s);
				stateRepository.save(state);
				friendResponse.setSuccessMessage("state has been successfully updated");
				friendResponse.setHttpCode(HttpStatus.OK.value());
			} else {
				friendResponse.setFailureReason("Invalid userId");
				friendResponse.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			}
		} catch (DataIntegrityViolationException e) {
			friendResponse.setFailureReason("State with same name already exists");
			friendResponse.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		return friendResponse;
	}

	@PostMapping(value = "/add/district")
	public DistrictResponse addDistrict(@RequestBody District district) {
		DistrictResponse districtResponse = new DistrictResponse();
		if (district.getUserId() == null) {
			districtResponse.setFailureReason("Invalid request");
			districtResponse.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			return districtResponse;
		}
		List<District> districts = districtRepository.findByDistrictName(district.getDistrictName().toLowerCase());
		if (districts.size() == 0) {
			District savedDistrict = districtRepository.save(district);
			districtResponse.setId(savedDistrict.getId());
			districtResponse.setSuccessMessage("District has been successfully added");
			districtResponse.setHttpCode(HttpStatus.OK.value());
		} else {
			districtResponse.setFailureReason("District already present");
			districtResponse.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		return districtResponse;
	}

	@PostMapping("/update/district")
	public LegalFriendResponse updateDistrict(@RequestBody District district) {
		LegalFriendResponse districtResponse = new LegalFriendResponse();
		if (district.getUserId() == null) {
			districtResponse.setFailureReason("Invalid request");
			districtResponse.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			return districtResponse;
		}
		LegalFriendResponse friendResponse = new LegalFriendResponse();
		District d = districtRepository.findById(district.getId());
		if (d.getUserId() == district.getUserId() || isAdmin(d.getUserId())) {
			BeanUtils.copyProperties(district, d);
			districtRepository.save(district);
			friendResponse.setSuccessMessage("District has been successfully updated");
			friendResponse.setHttpCode(HttpStatus.OK.value());
		} else {
			friendResponse.setFailureReason("Invalid userId");
			friendResponse.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		return friendResponse;
	}

	@PostMapping(value = "/add/court")
	public CourtResponse addCourt(@RequestBody Court court) {
		CourtResponse courtResponse = new CourtResponse();
		if (court.getUserId() == null) {
			courtResponse.setFailureReason("Invalid request");
			courtResponse.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			return courtResponse;
		}
		List<Court> courts = courtRepository.findByCourtName(court.getCourtName().toLowerCase());
		if (courts.size() == 0) {
			Court savedCourt = courtRepository.save(court);
			courtResponse.setId(savedCourt.getId());
			courtResponse.setSuccessMessage("Court has been successfully added");
			courtResponse.setHttpCode(HttpStatus.OK.value());
		} else {
			courtResponse.setFailureReason("Court already present");
			courtResponse.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		return courtResponse;
	}

	@PostMapping("/update/court")
	public LegalFriendResponse updateCourt(@RequestBody Court court) {
		LegalFriendResponse courtResponse = new LegalFriendResponse();
		if (court.getUserId() == null) {
			courtResponse.setFailureReason("Invalid request");
			courtResponse.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			return courtResponse;
		}
		LegalFriendResponse friendResponse = new LegalFriendResponse();
		try {
			Court c = courtRepository.findById(court.getId());
			if (c.getUserId() == court.getUserId() || isAdmin(c.getUserId())) {
				BeanUtils.copyProperties(court, c);
				courtRepository.save(court);
				friendResponse.setSuccessMessage("Court has been successfully updated");
				friendResponse.setHttpCode(HttpStatus.OK.value());
			} else {
				friendResponse.setFailureReason("Invalid userId");
				friendResponse.setHttpCode(HttpStatus.OK.value());
			}
		} catch (DataIntegrityViolationException e) {
			friendResponse.setFailureReason("Court with same name already exists");
			friendResponse.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		return friendResponse;
	}

	@PostMapping(value = "/add/recourse")
	public RecourseResponse addRecourse(@RequestBody Recourse recourse) {
		RecourseResponse recourseResponse = new RecourseResponse();
		if (recourse.getUserId() == null) {
			recourseResponse.setFailureReason("Invalid request");
			recourseResponse.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			return recourseResponse;
		}
		List<Recourse> recourses = recourseRepository.findByRecourseCode(recourse.getRecourseCode().toLowerCase());
		if (recourses.size() == 0 && isAdmin(recourse.getUserId())) {
			Recourse savedRecourse = recourseRepository.save(recourse);
			recourseResponse.setSuccessMessage("Recourse has been successfully added");
			recourseResponse.setId(savedRecourse.getId());
		} else {
			RecourseRequest recourseRequest = new RecourseRequest();
			BeanUtils.copyProperties(recourse, recourseRequest);
			recourseRequest.setCreatedDate(new Date());
			recourseRequestRepository.save(recourseRequest);
			recourseResponse.setSuccessMessage("Your request has been submitted to administrator");
		}
		recourseResponse.setHttpCode(HttpStatus.OK.value());
		return recourseResponse;
	}

	@GetMapping(value = "/recourses")
	public RecourseResponse getRecourses(@RequestParam Long userId) {
		RecourseResponse recourseResponse = new RecourseResponse();
		Iterable<Recourse> iterator = recourseRepository.findAll();
		Set<Recourse> recourses = new HashSet<>();
		iterator.forEach(recourses::add);
		List<User> users = userRepository.findById(userId);
		if (users.size() != 0 && users.get(0).getEmail() != null) {
			recourses.addAll(recourseRepository.findByUserId(users.get(0).getId()));
		}
		recourseResponse.setHttpCode(HttpStatus.OK.value());
		recourseResponse.setRecourses(recourses);
		return recourseResponse;
	}

	@PostMapping("/update/recourse")
	public LegalFriendResponse updateRecourse(@RequestBody Recourse recourse) {
		LegalFriendResponse recourseResponse = new LegalFriendResponse();
		if (recourse.getUserId() == null) {
			recourseResponse.setFailureReason("Invalid request");
			recourseResponse.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			return recourseResponse;
		}
		LegalFriendResponse friendResponse = new LegalFriendResponse();
		Recourse r = recourseRepository.findById(recourse.getId());
		if (r.getUserId() == recourse.getUserId() || isAdmin(r.getUserId())) {
			BeanUtils.copyProperties(recourse, r);
			recourseRepository.save(recourse);
			friendResponse.setSuccessMessage("Recourse has been successfully updated");
		} else {
			RecourseRequest recourseRequest = new RecourseRequest();
			BeanUtils.copyProperties(recourse, recourseRequest);
			recourseRequest.setCreatedDate(new Date());
			recourseRequestRepository.save(recourseRequest);
			friendResponse.setSuccessMessage("Your request has been submitted to administrator");
		}
		friendResponse.setHttpCode(HttpStatus.OK.value());
		return friendResponse;
	}

	@GetMapping(value = "/districts")
	public DistrictResponse getDistricts(@RequestParam Long userId) {
		DistrictResponse districtResponse = new DistrictResponse();
		Iterable<District> iterator = districtRepository.findAll();
		Set<District> districts = new HashSet<>();
		iterator.forEach(districts::add);
		List<User> users = userRepository.findById(userId);
		if (users.size() != 0 && users.get(0).getEmail() != null) {
			districts.addAll(districtRepository.findByUserId(users.get(0).getId()));
		}
		districtResponse.setHttpCode(HttpStatus.OK.value());
		districtResponse.setDistricts(districts);
		return districtResponse;
	}

	@GetMapping(value = "/courts")
	public CourtResponse getCourts(@RequestParam Long userId) {
		CourtResponse courtResponse = new CourtResponse();
		Iterable<Court> iterator = courtRepository.findAll();
		Set<Court> courts = new HashSet<>();
		iterator.forEach(courts::add);
		List<User> users = userRepository.findById(userId);
		if (users.size() != 0 && users.get(0).getEmail() != null) {
			courts.addAll(courtRepository.findByUserId(users.get(0).getId()));
		}
		courtResponse.setHttpCode(HttpStatus.OK.value());
		courtResponse.setCourts(courts);
		return courtResponse;
	}

	@GetMapping(value = "/stages")
	public StageResponse getStages() {
		StageResponse stageRecourseResponse = new StageResponse();
		List<Stage> satges = stageRepository.findAll();
		stageRecourseResponse.setStages(satges);
		stageRecourseResponse.setHttpCode(HttpStatus.OK.value());
		return stageRecourseResponse;

	}

	@SuppressWarnings("unchecked")
	@GetMapping(value = "/recourse/stage")
	public StageRecourseResponse getRecourseStages(@RequestParam Long userId, @RequestParam Long recourseId) {
		List<StageRecourse> stageRecourses = new ArrayList<>();
		StageRecourseResponse stageRecourseResponse = new StageRecourseResponse();
		List<User> users = userRepository.findById(userId);
		if (users.size() != 0 && users.get(0).getEmail() != null) {
			Session session = sessionFactory.openSession();
			Query query = session.createSQLQuery(
					"SELECT s.id, r.recourse_code, s.stage_name, s.fk_status_id, r.id as ids, s.stage_code FROM stage s left outer join recourse r on r.id = s.fk_recourse_id WHERE (s.fk_user_id = :userId or s.fk_user_id is null) and r.id= :rId");
			query.setLong("userId", users.get(0).getId());
			query.setLong("rId", recourseId);
			List<Object[]> rows = (List<Object[]>) query.list();
			for (Object[] row : rows) {
				StageRecourse recourse = new StageRecourse();
				recourse.setId(Long.parseLong(row[0].toString()));
				recourse.setRecourseCode(row[1].toString());
				recourse.setStageName(row[2].toString());
				recourse.setStageStatusId(Long.parseLong(row[3].toString()));
				recourse.setRecourseId(Long.parseLong(row[4].toString()));
				recourse.setStageCode(row[5].toString());
				stageRecourses.add(recourse);
			}
			session.close();
		}
		stageRecourseResponse.setStageRecourses(stageRecourses);
		stageRecourseResponse.setHttpCode(HttpStatus.OK.value());
		return stageRecourseResponse;

	}

	@PostMapping(value = "/add/stage")
	public StageResponse addStage(@RequestBody Stage stage) {
		StageResponse stageResponse = new StageResponse();
		if (stage.getUserId() == null) {
			stageResponse.setFailureReason("Invalid request");
			stageResponse.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			return stageResponse;
		}
		List<Stage> stages = stageRepository.findByStageCode(stage.getStageCode().toLowerCase());
		if (stages.size() == 0 && isAdmin(stage.getUserId())) {
			Stage savedStage = stageRepository.save(stage);
			stageResponse.setId(savedStage.getId());
			stageResponse.setSuccessMessage(" Stage has been successfully added");
			stageResponse.setHttpCode(HttpStatus.OK.value());
		} else {
			StageRequest stageRequest = new StageRequest();
			stageRequest.setStageCode(stage.getStageCode());
			stageRequest.setStageName(stage.getStageName());
			stageRequest.setStatusId(stage.getStatusId());
			stageRequest.setRecourse(recourseRepository.findById(stage.getRecourse().getId()));
			stageRequest.setCreatedDate(new Date());
			stageRequestRepository.save(stageRequest);
			stageResponse.setHttpCode(HttpStatus.OK.value());
			stageResponse.setSuccessMessage("Your request has been submitted to administrator");
		}
		return stageResponse;

	}

	@PostMapping("/update/stage")
	public LegalFriendResponse updateStage(@RequestBody Stage stage) {
		LegalFriendResponse stageResponse = new LegalFriendResponse();
		if (stage.getUserId() == null) {
			stageResponse.setFailureReason("Invalid request");
			stageResponse.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			return stageResponse;
		}
		LegalFriendResponse friendResponse = new LegalFriendResponse();
		try {
			Stage s = stageRepository.findById(stage.getId());
			if (s.getUserId() == stage.getUserId() || isAdmin(stage.getUserId())) {
				BeanUtils.copyProperties(stage, s);
				stage.setRecourse(recourseRepository.findById(stage.getRecourse().getId()));
				stageRepository.save(stage);
				friendResponse.setSuccessMessage("Stage has been successfully updated");
				friendResponse.setHttpCode(HttpStatus.OK.value());
			} else {
				StageRequest stageRequest = new StageRequest();
				stageRequest.setStageCode(stage.getStageCode());
				stageRequest.setStageName(stage.getStageName());
				stageRequest.setStatusId(stage.getStatusId());
				stageRequest.setRecourse(recourseRepository.findById(stage.getRecourse().getId()));
				stageRequest.setCreatedDate(new Date());
				stageRequestRepository.save(stageRequest);
				friendResponse.setHttpCode(HttpStatus.OK.value());
				friendResponse.setSuccessMessage("Your request has been submitted to administrator");
			}
		} catch (DataIntegrityViolationException e) {
			friendResponse.setFailureReason("Stage already present");
			friendResponse.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		return friendResponse;
	}

	@GetMapping(value = "/compliances")
	public ComplianceResponse getCompliance(@RequestParam Long userId) {
		List<User> users = userRepository.findById(userId);
		List<Compliance> compliances = new ArrayList<>();
		ComplianceResponse complianceResponse = new ComplianceResponse();
		if (users.size() != 0 && users.get(0).getEmail() != null) {
			compliances = complianceRepository.findByUserId(userId);
		}
		complianceResponse.setHttpCode(HttpStatus.OK.value());
		complianceResponse.setCompliances(compliances);
		return complianceResponse;
	}

	@PostMapping(value = "/add/compliance")
	public ComplianceResponse addCompliance(@RequestBody Compliance compliance) {
		ComplianceResponse complianceResponse = new ComplianceResponse();
		if (compliance.getUserId() == null) {
			complianceResponse.setFailureReason("Invalid request");
			complianceResponse.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			return complianceResponse;
		}
		List<Compliance> compliances = complianceRepository
				.findByComplianceNameAndUserId(compliance.getComplianceName().toLowerCase(), compliance.getUserId());
		if (compliances.size() == 0) {
			compliance.setRecourse(recourseRepository.findById(compliance.getRecourse().getId()));
			compliance.setStage(stageRepository.findById(compliance.getStage().getId()));
			Compliance savedCompl = complianceRepository.save(compliance);
			complianceResponse.setId(savedCompl.getId());
			complianceResponse.setSuccessMessage(" Compliance has been successfully added");
			complianceResponse.setHttpCode(HttpStatus.OK.value());
		} else {
			complianceResponse.setFailureReason("Compliance already present");
			complianceResponse.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		return complianceResponse;
	}

	@PostMapping("/update/compliance")
	public LegalFriendResponse updateCompliance(@RequestBody Compliance compliance) {
		LegalFriendResponse complianceResponse = new LegalFriendResponse();
		if (compliance.getUserId() == null) {
			complianceResponse.setFailureReason("Invalid request");
			complianceResponse.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			return complianceResponse;
		}
		LegalFriendResponse friendResponse = new LegalFriendResponse();
		Compliance c = complianceRepository.findById(compliance.getId());
		if (c.getUserId() == compliance.getUserId()) {
			BeanUtils.copyProperties(compliance, c);
			compliance.setRecourse(recourseRepository.findById(compliance.getRecourse().getId()));
			compliance.setStage(stageRepository.findById(compliance.getStage().getId()));
			complianceRepository.save(compliance);
			friendResponse.setSuccessMessage("Compliance has been successfully updated");
			friendResponse.setHttpCode(HttpStatus.OK.value());
		} else {
			friendResponse.setFailureReason("Invalid userId");
			friendResponse.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		return friendResponse;
	}

	@GetMapping(value = "/branches")
	public BranchResponse getBranch(@RequestParam Long userId) {
		BranchResponse branchResponse = new BranchResponse();
		Iterable<Branch> iterator = branchRepository.findAll();
		Set<Branch> branches = new HashSet<>();
		iterator.forEach(branches::add);
		List<String> roles = findUserRole(userId);
		if (roles.contains(UserRole.EMPLOYEE.name()) || roles.contains(UserRole.MANAGER.name())
				|| roles.contains(UserRole.CLIENT.name())) {
			userId = userRepository.findById(userId).get(0).getClientId();
		}
		List<User> users = userRepository.findById(userId);
		if (users.size() != 0 && users.get(0).getEmail() != null) {
			branches.addAll(branchRepository.findByUserId(users.get(0).getId()));
		}
		branchResponse.setHttpCode(HttpStatus.OK.value());
		branchResponse.setBranches(branches);
		return branchResponse;
	}

	@PostMapping(value = "/add/branch")
	public BranchResponse addBranch(@RequestBody Branch branch) {
		BranchResponse branchResponse = new BranchResponse();
		if (branch.getUserId() == null) {
			branchResponse.setFailureReason("Invalid request");
			branchResponse.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			return branchResponse;
		}
		List<Branch> branches = branchRepository.findByBranchCodeAndUserIdAndBranchName(
				branch.getBranchCode().toUpperCase(), branch.getUserId(), branch.getBranchName().toUpperCase());
		if (branches.size() == 0 && isAdmin(branch.getUserId())) {
			branch.setBranchName(branch.getBranchName().toUpperCase());
			Branch savedBranch = branchRepository.save(branch);
			branchResponse.setId(savedBranch.getId());
			branchResponse.setSuccessMessage(" Branch has been successfully added");
			branchResponse.setHttpCode(HttpStatus.OK.value());
		} else {
			branchResponse.setFailureReason("Branch already present");
			branchResponse.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		return branchResponse;
	}

	@PostMapping("/update/branch")
	public LegalFriendResponse updateBranch(@RequestBody Branch branch) {
		LegalFriendResponse branchResponse = new LegalFriendResponse();
		if (branch.getUserId() == null) {
			branchResponse.setFailureReason("Invalid request");
			branchResponse.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			return branchResponse;
		}
		LegalFriendResponse friendResponse = new LegalFriendResponse();
		try {
			Branch b = branchRepository.findById(branch.getId());
			if (b.getUserId() == branch.getUserId()
					|| isAdmin(b.getUserId()) && !b.getBranchName().equals(branch.getBranchName())
							&& !b.getBranchCode().equals(branch.getBranchCode())) {
				BeanUtils.copyProperties(branch, b);
				branch.setBranchName(branch.getBranchName().toUpperCase());
				branchRepository.save(branch);
				friendResponse.setSuccessMessage("Branch has been successfully updated");
				friendResponse.setHttpCode(HttpStatus.OK.value());
			} else {
				friendResponse.setFailureReason("Invalid userId");
				friendResponse.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			}
		} catch (DataIntegrityViolationException e) {
			friendResponse.setFailureReason("Branch already present");
			friendResponse.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		return friendResponse;
	}

	@GetMapping(value = "/institutions")
	public InstitutionResponse getInstitution(@RequestParam Long userId) {
		InstitutionResponse institutionResponse = new InstitutionResponse();
		Iterable<Institution> iterator = institutionRepository.findAll();
		Set<Institution> institutions = new HashSet<>();
		iterator.forEach(institutions::add);
		List<String> roles = findUserRole(userId);
		if (roles.contains(UserRole.EMPLOYEE.name()) || roles.contains(UserRole.CUSTOMER.name())) {
			userId = userRepository.findById(userId).get(0).getClientId();
		}
		List<User> users = userRepository.findById(userId);
		institutions.addAll(institutionRepository.findByUserId(users.get(0).getId()));
		// if (users.size() != 0 && users.get(0).getEmail() != null) {
		// institutions.addAll(institutionRepository.findByUserId(users.get(0).getId()));
		// }
		institutionResponse.setHttpCode(HttpStatus.OK.value());
		institutionResponse.setInstitutions(institutions);
		return institutionResponse;
	}

	@PostMapping(value = "/add/institution")
	public InstitutionResponse addInstitution(@RequestBody Institution institution) {
		InstitutionResponse institutionResponse = new InstitutionResponse();
		if (institution.getUserId() == null) {
			institutionResponse.setFailureReason("Invalid request");
			institutionResponse.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			return institutionResponse;
		}
		List<Institution> institutions = institutionRepository.findByInstitutionNameAndUserId(
				institution.getInstitutionName().toUpperCase(), institution.getUserId());
		if (institutions.size() == 0 && isAdmin(institution.getUserId())) {
			institution.setInstitutionName(institution.getInstitutionName().toUpperCase());
			Institution savedInstitution = institutionRepository.save(institution);
			institutionResponse.setId(savedInstitution.getId());
			institutionResponse.setSuccessMessage(" Institution has been successfully added");
			institutionResponse.setHttpCode(HttpStatus.OK.value());
		} else {
			institutionResponse.setFailureReason("Institution already present");
			institutionResponse.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		return institutionResponse;
	}

	@PostMapping("/update/institution")
	public LegalFriendResponse updateInstitution(@RequestBody Institution institution) {
		LegalFriendResponse institutionResponse = new LegalFriendResponse();
		if (institution.getUserId() == null) {
			institutionResponse.setFailureReason("Invalid request");
			institutionResponse.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			return institutionResponse;
		}
		LegalFriendResponse friendResponse = new LegalFriendResponse();
		Institution b = institutionRepository.findById(institution.getId());
		if (b.getUserId() == institution.getUserId() || isAdmin(b.getUserId())) {
			BeanUtils.copyProperties(institution, b);
			institution.setInstitutionName(institution.getInstitutionName().toUpperCase());
			institutionRepository.save(institution);
			friendResponse.setSuccessMessage("Institution has been successfully updated");
			friendResponse.setHttpCode(HttpStatus.OK.value());
		} else {
			friendResponse.setFailureReason("Invalid userId");
			friendResponse.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		return friendResponse;
	}

	@PostMapping("/update/default/institution")
	public LegalFriendResponse updateDefaultInstitution(@RequestBody Institution institution) {
		LegalFriendResponse institutionResponse = new LegalFriendResponse();
		if (institution.getUserId() == null) {
			institutionResponse.setFailureReason("Invalid request");
			institutionResponse.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			return institutionResponse;
		}
		LegalFriendResponse friendResponse = new LegalFriendResponse();
		try {
			List<Institution> b = institutionRepository.findByIdAndUserId(institution.getId(), institution.getUserId());
			if (b.size() > 0) {
				BeanUtils.copyProperties(institution, b);
				Institution i = institutionRepository.findByUserIdAndDefaultInstitution(institution.getUserId(), true);
				if (i != null) {
					i.setDefaultInstitution(Boolean.FALSE);
					institutionRepository.save(i);
				}
				institutionRepository.save(institution);
				friendResponse.setSuccessMessage("Institution has been successfully updated");
				friendResponse.setHttpCode(HttpStatus.OK.value());
			} else {
				friendResponse.setSuccessMessage("Invalid userId");
				friendResponse.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			}
		} catch (DataIntegrityViolationException e) {
			friendResponse.setFailureReason("Institution with same name already present");
			friendResponse.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		return friendResponse;
	}

	@GetMapping(value = "/billings")
	public BillingResponse getBillingList(@RequestParam Long userId) {
		BillingResponse billingResponse = new BillingResponse();
		Iterable<BillingMaster> iterator = billingRepository.findAll();
		Set<BillingMaster> billings = new HashSet<>();
		iterator.forEach(billings::add);
		List<User> users = userRepository.findById(userId);
		if (users.size() != 0 && users.get(0).getEmail() != null) {
			billings.addAll(billingRepository.findByUserId(users.get(0).getId()));
		}
		billingResponse.setHttpCode(HttpStatus.OK.value());
		billingResponse.setBillings(billings);
		return billingResponse;
	}
	
	@GetMapping(value = "/billings/individual")
	public IndividualBillingResponse getIndividualBillingList(@RequestParam Long userId) {
		IndividualBillingResponse billingResponse = new IndividualBillingResponse();
		Iterable<IndividualBillingMaster> iterator = individualBillingMasterRepository.findAll();
		Set<IndividualBillingMaster> billings = new HashSet<>();
		iterator.forEach(billings::add);
		List<User> users = userRepository.findById(userId);
		if (users.size() != 0 && users.get(0).getEmail() != null) {
			billings.addAll(individualBillingMasterRepository.findByUserId(users.get(0).getId()));
		}
		billingResponse.setHttpCode(HttpStatus.OK.value());
		billingResponse.setBillings(billings);
		return billingResponse;
	}

	@PostMapping(value = "/add/billing")
	public BillingResponse addBilling(@RequestBody BillingMaster billing) {
		BillingResponse billingResponse = new BillingResponse();
		if (billing.getUserId() == null) {
			billingResponse.setFailureReason("Invalid request");
			billingResponse.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			return billingResponse;
		}
		Institution institution = institutionRepository.findById(billing.getInstitution().getId());
		Recourse recourse = recourseRepository.findById(billing.getRecourse().getId());
		Stage stage = stageRepository.findById(billing.getStage().getId());
		List<BillingMaster> billings = billingRepository.findByInstitutionAndRecourseAndStage(institution, recourse,
				stage);
		if (billings.size() == 0) {
			BillingMaster savedBilling = billingRepository.save(billing);
			billingResponse.setId(savedBilling.getId());
			billingResponse.setSuccessMessage(" Billing has been successfully added");
			billingResponse.setHttpCode(HttpStatus.OK.value());
		} else {
			billingResponse.setFailureReason("Billing already present");
			billingResponse.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		return billingResponse;
	}

	@PostMapping(value = "/add/billing/individual")
	public IndividualBillingResponse addIndividualBilling(@RequestBody IndividualBillingMaster billing) {
		IndividualBillingResponse billingResponse = new IndividualBillingResponse();
		if (billing.getUserId() == null) {
			billingResponse.setFailureReason("Invalid request");
			billingResponse.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			return billingResponse;
		}
		Recourse recourse = recourseRepository.findById(billing.getRecourse().getId());
		Stage stage = stageRepository.findById(billing.getStage().getId());
		List<IndividualBillingMaster> billings = individualBillingMasterRepository.findByStageAndRecourse(stage,
				recourse);
		if (billings.size() == 0) {
			IndividualBillingMaster savedBilling = individualBillingMasterRepository.save(billing);
			billingResponse.setId(savedBilling.getId());
			billingResponse.setSuccessMessage(" Billing has been successfully added");
			billingResponse.setHttpCode(HttpStatus.OK.value());
		} else {
			billingResponse.setFailureReason("Billing already present");
			billingResponse.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		return billingResponse;

	}

	@PostMapping("/update/billing")
	public LegalFriendResponse updateBilling(@RequestBody BillingMaster billing) {
		BillingResponse billingResponse = new BillingResponse();
		if (billing.getUserId() == null) {
			billingResponse.setFailureReason("Invalid request");
			billingResponse.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			return billingResponse;
		}
		LegalFriendResponse friendResponse = new LegalFriendResponse();
		BillingMaster b = billingRepository.findById(billing.getId());
		if (b.getUserId() == billing.getUserId() || isAdmin(b.getUserId())) {
			BeanUtils.copyProperties(billing, b);
			billingRepository.save(billing);
			friendResponse.setSuccessMessage("Billing has been successfully updated");
			friendResponse.setHttpCode(HttpStatus.OK.value());
		} else {
			friendResponse.setFailureReason("Invalid userId");
			friendResponse.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		return friendResponse;
	}
	
	@PostMapping("/update/billing/individual")
	public LegalFriendResponse updateBillingIndividual(@RequestBody IndividualBillingMaster billing) {
		BillingResponse billingResponse = new BillingResponse();
		if (billing.getUserId() == null) {
			billingResponse.setFailureReason("Invalid request");
			billingResponse.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			return billingResponse;
		}
		LegalFriendResponse friendResponse = new LegalFriendResponse();
		IndividualBillingMaster b = individualBillingMasterRepository.findById(billing.getId());
		if (b.getUserId() == billing.getUserId() || isAdmin(b.getUserId())) {
			BeanUtils.copyProperties(billing, b);
			individualBillingMasterRepository.save(billing);
			friendResponse.setSuccessMessage("Billing has been successfully updated");
			friendResponse.setHttpCode(HttpStatus.OK.value());
		} else {
			friendResponse.setFailureReason("Invalid userId");
			friendResponse.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		return friendResponse;
	}

	@GetMapping("/dashbranch")
	public List<DashboardReport> getBranchUserCount(@RequestParam Long userId){
		return branchService.findTotalUsersByBranch(userId);
	}
	
	@GetMapping("/institutional/month")
	public List<DashboardReport> getInstitutionalCasesMonth(@RequestParam Long userId){
		return caseService.findInstitutionalCasesMonth(userId);
	}
	
	@GetMapping("/institutional/week")
	public List<DashboardReport> getInstitutionalCasesWeek(@RequestParam Long userId){
		return caseService.findInstitutionalCasesWeek(userId);
	}
	
	@GetMapping("/institutional/date")
	public List<DashboardReport> getInstitutionalCasesDate(@RequestParam Long userId
			,@RequestParam String startDate,@RequestParam String endDate){
		return caseService.findInstitutionalCasesDate(userId, startDate, endDate);
	}
	
	@GetMapping("/allcases/month")
	public List<DashboardReport> getAllCasesMonth(@RequestParam Long userId){
		return caseService.findAllCasesMonth(userId);
	}
	
	@GetMapping("/allcases/week")
	public List<DashboardReport> getAllCasesWeek(@RequestParam Long userId){
		return caseService.findAllCasesWeek(userId);
	}
	
	@GetMapping("/allcases/date")
	public List<DashboardReport> getAllCasesDate(@RequestParam Long userId
			,@RequestParam String startDate,@RequestParam String endDate){
		return caseService.findAllCasesDate(userId, startDate, endDate);
	}
	
	@GetMapping("/branch/billing")
	public List<DashboardReport> getBranchBillings(@RequestParam Long userId,
			@RequestParam String start,@RequestParam String end){
		return branchService.findBranchBilling(userId,start,end);
	}
	
	@GetMapping("/selectedbranch/billing")
	public List<DashboardReport> getSelectedBranchBillings(@RequestParam Long userId,
			@RequestParam List<String> branches,
			@RequestParam String start,@RequestParam String end){
		return branchService.findBranchBilling(userId, branches,start,end);
	}
	
	@GetMapping("/branch/institution/billing")
	public List<DashboardReport> getBranchInstitutionBillings(@RequestParam Long userId,
			@RequestParam List<String> branches,@RequestParam String start,@RequestParam String end){
		return branchService.findBranchInstitutionBilling(userId, branches,start,end);
	}
	
	@GetMapping("/branch/institutions/billing")
	public List<DashboardReport> getBranchInstitutionsBillings(@RequestParam Long userId,
			@RequestParam String month,@RequestParam String start,@RequestParam String end){
		return branchService.findBranchInstitutionBilling(userId, month,start,end);
	}
	
	@GetMapping("/branch/institution/monthbilling")
	public List<DashboardReport> getBranchInstMonthBilling(@RequestParam Long userId,
			@RequestParam List<String> branches,@RequestParam String month,@RequestParam String start,
			@RequestParam String end){
		return branchService.findBranchInstitutionBilling(userId, branches, month,start,end);
	}
	
	@GetMapping("/recourse/requests")
	public List<RecourseRequest> getRecourseRequests(){
		return recourseRequestRepository.getRecourseRequests();
	}
	
	@GetMapping("/stage/requests")
	public List<StageRequest> getStageRequests(){
		return stageRequestRepository.getStageRequests();
	}
	
	@GetMapping("/court/requests")
	public List<CourtRequest> getCourtRequests(){
		return courtRequestRepo.getCourtRequests();
	}
	
	@PutMapping("/recourse/requests")
	public void updateRecourseRequestsStatus(@RequestParam Long id){
	
		RecourseRequest ob = recourseRequestRepository.findOne(id);
		ob.setStatus("RESOLVED");
		recourseRequestRepository.save(ob);
	}
	
	@PutMapping("/stage/requests")
	public void updateStageRequestsStatus(@RequestParam Long id){
		StageRequest ob = stageRequestRepository.findOne(id);
		ob.setStatus("RESOLVED");
		stageRequestRepository.save(ob);
	}
	
	@PutMapping("/court/requests")
	public void updateCourtRequestsStatus(@RequestParam Long id){
		CourtRequest ob = courtRequestRepo.findOne(id);
		ob.setStatus("RESOLVED");
		courtRequestRepo.save(ob);
	}
	
	@PutMapping("/recourse/requests/decline")
	public void updateRecourseRequestsDecline(@RequestParam Long id){
		RecourseRequest ob = recourseRequestRepository.findOne(id);
		ob.setStatus("DECLINE");
		recourseRequestRepository.save(ob);
	}
	
	@PutMapping("/stage/requests/decline")
	public void updateStageRequestsDecline(@RequestParam Long id){
		StageRequest ob = stageRequestRepository.findOne(id);
		ob.setStatus("DECLINE");
		stageRequestRepository.save(ob);
	}
	
	@PutMapping("/court/requests/decline")
	public void updateCourtRequestsDecline(@RequestParam Long id){
		CourtRequest ob = courtRequestRepo.findOne(id);
		ob.setStatus("DECLINE");
		courtRequestRepo.save(ob);
	}

}
