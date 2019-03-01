package com.legalfriend.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.legalfriend.entities.AgainstInstitutionalCase;
import com.legalfriend.entities.Billing;
import com.legalfriend.entities.BillingMaster;
import com.legalfriend.entities.Branch;
import com.legalfriend.entities.CsvToCaseFileDetailsMappingBean;
import com.legalfriend.entities.Event;
import com.legalfriend.entities.ForInstitutionalCase;
import com.legalfriend.entities.Institution;
import com.legalfriend.entities.Recourse;
import com.legalfriend.entities.Stage;
import com.legalfriend.repository.AgainstInstitutionalCaseRepository;
import com.legalfriend.repository.BillingMasterRepository;
import com.legalfriend.repository.BillingRepository;
import com.legalfriend.repository.BranchRepository;
import com.legalfriend.repository.EventRepository;
import com.legalfriend.repository.ForInstitutionalCaseRepository;
import com.legalfriend.repository.InstitutionRepository;
import com.legalfriend.repository.RecourseRepository;
import com.legalfriend.repository.StageRepository;
import com.legalfriend.service.S3Services;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvException;

/**
 * @author deepak.j
 */

@Service
public class CsvFileReader {

	@Autowired
	CaseFileManagementService caseFileManagementService;

	@Autowired
	ForInstitutionalCaseRepository institutionalCaseRepo;

	@Autowired
	AgainstInstitutionalCaseRepository againstInstutionRepo;

	@Autowired
	BranchRepository branchRepository;

	@Autowired
	S3Services s3Services;

	@Autowired
	private BillingRepository billingRepository;

	@Autowired
	private RecourseRepository recourseRepository;

	@Autowired
	private StageRepository stageRepository;

	@Autowired
	private InstitutionRepository institutionRepository;

	@Autowired
	private BillingMasterRepository billingMasterRepository;

	@Autowired
	EventRepository eventRepo;

	/**
	 * 
	 * @param userId
	 * @param filePathAgainstCaseIdMap
	 *            - CaseId and corresponding file Map
	 * @param csvCaseFile
	 * @param branchId
	 * @param zipFile
	 * @param isForInstitution
	 * @return
	 * @throws Exception
	 */
	public List<ForInstitutionalCase> processCsvToGetFileDetailsForInstitution(Long userId, MultipartFile csvCaseFile,
			Long institutionId, Long branchId, MultipartFile zipFile) throws Exception, CsvException {
		List<ForInstitutionalCase> caseList = new ArrayList<ForInstitutionalCase>();
		Institution institution = institutionRepository.findById(institutionId);
		Branch branch = branchRepository.findById(branchId);
		Map<String, String> fileMap = new HashMap<>();
		if (institution != null) {
			try (Reader reader = new InputStreamReader(csvCaseFile.getInputStream());) {
				CsvToBean<CsvToCaseFileDetailsMappingBean> csvToBean = new CsvToBeanBuilder(reader)
						.withType(CsvToCaseFileDetailsMappingBean.class).withIgnoreLeadingWhiteSpace(true)
						.withThrowExceptions(true).build();
				int rowNo = 1;
				Iterator<CsvToCaseFileDetailsMappingBean> csvDataIterator = csvToBean.iterator();
				// Reading Records One by One in a String array
				while (csvDataIterator.hasNext()) {
					rowNo++;
					pouplateCaseDetailsForInstitution(csvDataIterator.next(), caseList, institutionId, branchId, userId,
							rowNo);
				}
			}
			for (ForInstitutionalCase casel : caseList) {
				String caseId = "F/" + casel.getRecourse() + "/" + casel.getId();
				List<Recourse> recourses = recourseRepository.findByRecourseCode(casel.getRecourse());
				List<Stage> stage = stageRepository.findByStageCode(casel.getCaseStage());
				if (stage.size() > 0 && recourses.size() > 0) {
					List<BillingMaster> billingMasters = billingMasterRepository
							.findByInstitutionAndStageAndRecourse(institution, stage.get(0), recourses.get(0));
					if (casel.getId() == null && billingMasters.size() > 0) {
						Billing billing = new Billing();
						billing.setAmount(billingMasters.get(0).getAmount());
						billing.setCaseId(casel.getCaseId());
						billing.setInstitution(institutionRepository.findById(casel.getInstitutionId()));
						billing.setRecourse(recourses.get(0));
						billing.setStage(stage.get(0));
						billing.setBillingDate(new Date());
						billing.setUserId(casel.getUserId());
						billingRepository.save(billing);
					}
				}
				if (casel.getNextHearingDate() != null && !casel.getNextHearingDate().isEmpty()) {
					Event event = eventRepo.findByReferenceNumber(casel.getCaseId());
					if (event != null) {
						eventRepo.delete(event);
					}
					event = new Event();
					event.setEventName("Hearing Date for case " + caseId);
					event.setEventDescription("Hearing Date for case " + caseId);
					event.setUserId(casel.getUserId());
					event.setReferenceNumber(caseId);
					event.setInstitutionId(institutionId);
					event.setBranchId(branchId);
					SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("MM/dd/yyyy");
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
					Calendar c = Calendar.getInstance();
					c.set(Calendar.MINUTE, 0);
					c.setTime(simpleDateFormat
							.parse(simpleDateFormat.format(simpleDateFormat1.parse(casel.getNextHearingDate()))));
					c.set(Calendar.SECOND, 0);
					Date d1 = c.getTime();
					event.setStartDate(d1);
					c = Calendar.getInstance();
					c.set(Calendar.MINUTE, 23);
					c.setTime(simpleDateFormat
							.parse(simpleDateFormat.format(simpleDateFormat1.parse(casel.getNextHearingDate()))));
					c.set(Calendar.HOUR_OF_DAY, 23);
					c.set(Calendar.SECOND, 0);
					Date d2 = c.getTime();
					event.setEndDate(d2);
					eventRepo.save(event);
					casel.setNextHearingDate(
							simpleDateFormat.format(simpleDateFormat1.parse(casel.getNextHearingDate())));
				}
			}
			institutionalCaseRepo.save(caseList);
			for (ForInstitutionalCase casel : caseList) {
				String caseId = "F/" + casel.getRecourse() + "/" + casel.getId();
				casel.setCaseId(caseId);
				institutionalCaseRepo.save(caseList);
			}
			if (zipFile != null) {
				for (ForInstitutionalCase casel : caseList) {
					fileMap.put(casel.getFileName(), casel.getCaseId());
				}
				// Upload the caseFiles to S3
				caseFileManagementService.processAndUploadZipToMapCaseFileAgainstCaseId(userId, zipFile,
						institution.getInstitutionName(), branch.getBranchName(), fileMap);
			}
		}
		return caseList;

	}

	/**
	 * 
	 * @param userId
	 * @param filePathAgainstCaseIdMap
	 *            - CaseId and corresponding file Map
	 * @param csvCaseFile
	 * @param branchId
	 * @param zipFile
	 * @param isForInstitution
	 * @return
	 * @throws Exception
	 */
	public List<AgainstInstitutionalCase> processCsvToGetFileDetailsAgainstInstitution(Long userId,
			MultipartFile csvCaseFile, Long institutionId, Long branchId, MultipartFile zipFile)
			throws Exception, CsvException {
		List<AgainstInstitutionalCase> caseList = new ArrayList<AgainstInstitutionalCase>();
		Institution institution = institutionRepository.findById(institutionId);
		Branch branch = branchRepository.findById(branchId);
		Map<String, String> fileMap = new HashMap<>();
		if (institution != null) {
			try (Reader reader = new InputStreamReader(csvCaseFile.getInputStream());
			// CSVReader csvReader = new CSVReader(reader);
			) {
				CsvToBean<CsvToCaseFileDetailsMappingBean> csvToBean = new CsvToBeanBuilder(reader)
						.withType(CsvToCaseFileDetailsMappingBean.class).withIgnoreLeadingWhiteSpace(true)
						.withThrowExceptions(true).build();
				int rowNo = 1;
				Iterator<CsvToCaseFileDetailsMappingBean> csvDataIterator = csvToBean.iterator();

				// Reading Records One by One in a String array
				while (csvDataIterator.hasNext()) {
					rowNo++;
					pouplateCaseDetailsAgainstInstitution(csvDataIterator.next(), caseList, institutionId, branchId,
							userId, rowNo);
				}
			}
			for (AgainstInstitutionalCase casel : caseList) {
				String caseId = "A/" + casel.getRecourse() + "/" + casel.getId();
				List<Recourse> recourses = recourseRepository.findByRecourseCode(casel.getRecourse());
				List<Stage> stage = stageRepository.findByStageCode(casel.getCaseStage());
				if (stage.size() > 0 && recourses.size() > 0) {
					List<BillingMaster> billingMasters = billingMasterRepository
							.findByInstitutionAndStageAndRecourse(institution, stage.get(0), recourses.get(0));
					if (billingMasters.size() > 0 && casel.getId() == null) {
						Billing billing = new Billing();
						billing.setAmount(billingMasters.get(0).getAmount());
						billing.setCaseId(casel.getCaseId());
						billing.setInstitution(institutionRepository.findById(casel.getInstitutionId()));
						billing.setRecourse(recourses.get(0));
						billing.setStage(stage.get(0));
						billing.setBillingDate(new Date());
						billing.setUserId(casel.getUserId());
						billingRepository.save(billing);
					}
				}
				if (casel.getNextHearingDate() != null && !casel.getNextHearingDate().isEmpty()) {
					Event event = eventRepo.findByReferenceNumber(casel.getCaseId());
					if (event != null) {
						eventRepo.delete(event);
					}
					event = new Event();
					event.setEventName("Hearing Date for case " + caseId);
					event.setEventDescription("Hearing Date for case " + caseId);
					event.setUserId(casel.getUserId());
					event.setReferenceNumber(caseId);
					event.setInstitutionId(institutionId);
					event.setBranchId(branchId);
					SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("MM/dd/yyyy");
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
					Calendar c = Calendar.getInstance();
					c.set(Calendar.MINUTE, 0);
					c.setTime(simpleDateFormat
							.parse(simpleDateFormat.format(simpleDateFormat1.parse(casel.getNextHearingDate()))));
					c.set(Calendar.SECOND, 0);
					Date d1 = c.getTime();
					event.setStartDate(d1);
					c = Calendar.getInstance();
					c.set(Calendar.MINUTE, 23);
					c.setTime(simpleDateFormat
							.parse(simpleDateFormat.format(simpleDateFormat1.parse(casel.getNextHearingDate()))));
					c.set(Calendar.HOUR_OF_DAY, 23);
					c.set(Calendar.SECOND, 0);
					Date d2 = c.getTime();
					event.setEndDate(d2);
					eventRepo.save(event);
					casel.setNextHearingDate(
							simpleDateFormat.format(simpleDateFormat1.parse(casel.getNextHearingDate())));
				}
			}
			againstInstutionRepo.save(caseList);
			for (AgainstInstitutionalCase casel : caseList) {
				String caseId = "F/" + casel.getRecourse() + "/" + casel.getId();
				casel.setCaseId(caseId);
				againstInstutionRepo.save(caseList);
			}
			if (zipFile != null) {
				for (AgainstInstitutionalCase casel : caseList) {
					fileMap.put(casel.getFileName(), casel.getCaseId());
				}
				// Upload the caseFiles to S3
				caseFileManagementService.processAndUploadZipToMapCaseFileAgainstCaseId(userId, zipFile,
						institution.getInstitutionName(), branch.getBranchName(), fileMap);
			}
		}
		return caseList;
	}

	/**
	 * 
	 * @param eachCaseDetail
	 *            - Each File attribute data from csv
	 * @param userId
	 * @param rowNo
	 * @param caseFileList
	 *            - caseFileList to be updated.
	 * @param filePathAgainstCaseIdList
	 *            - unzipped file information map
	 */
	private void pouplateCaseDetailsForInstitution(CsvToCaseFileDetailsMappingBean eachCaseDetail,
			List<ForInstitutionalCase> caseList, Long institutionId, Long branchId, Long userId, int rowNo) {
		String stageId = eachCaseDetail.getCaseStage();
		String recourseId = eachCaseDetail.getRecourse();
		List<Recourse> r = recourseRepository.findByRecourseCode(recourseId);
		List<Stage> stage = stageRepository.findByStageCodeAndRecourse(stageId, r.get(0));
		Institution institution = institutionRepository.findById(institutionId);
		if (stage.size() == 0) {
			throw new RuntimeException("Invalid Recourse and Stage mapping at row " + rowNo);
		} else {
			ForInstitutionalCase institutionalCase = new ForInstitutionalCase();
			if (eachCaseDetail.getCaseId() == null || eachCaseDetail.getCaseId().isEmpty()) {
				BeanUtils.copyProperties(eachCaseDetail, institutionalCase);
				institutionalCase.setInstitutionId(institutionId);
				institutionalCase.setBranchId(branchId);
				institutionalCase.setUserId(userId);
				institutionalCase.setCreatedDate(new Date());
				institutionalCase.setLastUpdated(new Date());
				caseList.add(institutionalCase);
			} else {
				List<BillingMaster> billingMasters = billingMasterRepository
						.findByInstitutionAndStageAndRecourse(institution, stage.get(0), r.get(0));
				institutionalCase = institutionalCaseRepo.findByCaseId(eachCaseDetail.getCaseId());
				if (billingMasters.size() > 0
						&& !institutionalCase.getCaseStage().equals(stage.get(0).getStageCode())) {
					Billing billing = new Billing();
					billing.setAmount(billingMasters.get(0).getAmount());
					billing.setCaseId(institutionalCase.getCaseId());
					billing.setInstitution(institution);
					billing.setRecourse(r.get(0));
					billing.setStage(stage.get(0));
					billing.setBillingDate(new Date());
					billing.setUserId(userId);
					billingRepository.save(billing);
				}
				Long id = institutionalCase.getId();
				institutionalCase.setLastUpdated(new Date());
				copyProperties(eachCaseDetail, institutionalCase);
				institutionalCase.setId(id);
				caseList.add(institutionalCase);
			}
		}
	}

	private String[] getNullPropertyNames(Object source) {
		final BeanWrapper src = new BeanWrapperImpl(source);
		java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

		Set<String> emptyNames = new HashSet<String>();
		for (java.beans.PropertyDescriptor pd : pds) {
			Object srcValue = src.getPropertyValue(pd.getName());
			if (srcValue == null)
				emptyNames.add(pd.getName());
		}
		String[] result = new String[emptyNames.size()];
		return emptyNames.toArray(result);
	}

	// Spring BeanUtils to copy and ignore null
	private void copyProperties(Object src, Object target) {
		BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
	}

	/**
	 * 
	 * @param eachCaseDetail
	 * @param userId
	 * @param caseList
	 * @param institutionId
	 * @param userId
	 * @param userId2
	 */
	private void pouplateCaseDetailsAgainstInstitution(CsvToCaseFileDetailsMappingBean eachCaseDetail,
			List<AgainstInstitutionalCase> caseList, Long institutionId, Long branchId, Long userId, int rowNum) {
		String stageId = eachCaseDetail.getCaseStage();
		String recourseId = eachCaseDetail.getRecourse();
		List<Recourse> r = recourseRepository.findByRecourseCode(recourseId);
		List<Stage> stage = stageRepository.findByStageCodeAndRecourse(stageId, r.get(0));
		if (stage.size() == 0) {
			throw new RuntimeException("Invalid Recourse and Stage mapping at row " + rowNum);
		} else {
			AgainstInstitutionalCase institutionalCase = new AgainstInstitutionalCase();
			if (eachCaseDetail.getCaseId() == null || eachCaseDetail.getCaseId().isEmpty()) {
				BeanUtils.copyProperties(eachCaseDetail, institutionalCase);
				institutionalCase.setInstitutionId(institutionId);
				institutionalCase.setBranchId(branchId);
				institutionalCase.setUserId(userId);
				institutionalCase.setCreatedDate(new Date());
				institutionalCase.setLastUpdated(new Date());
				caseList.add(institutionalCase);
			} else {
				Institution institution = institutionRepository.findById(institutionId);
				List<BillingMaster> billingMasters = billingMasterRepository
						.findByInstitutionAndStageAndRecourse(institution, stage.get(0), r.get(0));
				institutionalCase = againstInstutionRepo.findByCaseId(eachCaseDetail.getCaseId());
				if (billingMasters.size() > 0
						&& !institutionalCase.getCaseStage().equals(stage.get(0).getStageCode())) {
					Billing billing = new Billing();
					billing.setAmount(billingMasters.get(0).getAmount());
					billing.setCaseId(institutionalCase.getCaseId());
					billing.setInstitution(institution);
					billing.setRecourse(r.get(0));
					billing.setStage(stage.get(0));
					billing.setBillingDate(new Date());
					billing.setUserId(userId);
					billingRepository.save(billing);
				}
				Long id = institutionalCase.getId();
				institutionalCase.setLastUpdated(new Date());
				copyProperties(eachCaseDetail, institutionalCase);
				institutionalCase.setId(id);
				caseList.add(institutionalCase);
			}

		}
	}

	public byte[] getFileContent(String fileName, String filePath) throws IOException {
		return s3Services.download(fileName, filePath);
	}

}
