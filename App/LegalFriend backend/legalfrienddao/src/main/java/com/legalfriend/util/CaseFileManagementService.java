package com.legalfriend.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.legalfriend.entities.CaseFiles;
import com.legalfriend.entities.LegalCase;
import com.legalfriend.repository.CaseFilesRepository;
import com.legalfriend.repository.CaseRepository;
import com.legalfriend.repository.InstitutionRepository;
import com.legalfriend.service.S3Services;
import com.legalfriend.service.S3ServicesImpl;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

/**
 * @author Swapnil.Mesharam
 */
@Service
public class CaseFileManagementService {

	@Autowired
	S3Services s3Services;

	@Autowired
	CaseFilesRepository caseFilesRepository;

	@Autowired
	CaseRepository caseRepository;

	@Autowired
	InstitutionRepository institutionRepository;

	private static Logger logger = LoggerFactory.getLogger(S3ServicesImpl.class);
	File zip = null;

	/**
	 * 
	 * @param userId
	 * @param zipFile
	 *            - uploaded zip to extract CaseFiles from.
	 * @param institutionName
	 * @param fileMap
	 * @return
	 * @throws IOException
	 */
	public void processAndUploadZipToMapCaseFileAgainstCaseId(Long userId, MultipartFile zipFile,
			String institutionName, String branchName, Map<String, String> fileMap) throws Exception {
		unZipIt(userId, zipFile, institutionName, branchName, fileMap);
	}

	/**
	 * 
	 * @param userId
	 * @param uploadedFile
	 *            - zip for unzipping
	 * @param fileMap
	 * @return
	 * @return
	 */
	public void unZipIt(Long userId, MultipartFile uploadedFile, String institutionName, String branchName,
			Map<String, String> fileMap) throws Exception {
		/**
		 * save file to temp
		 */
		String osname = System.getProperty("os.name", "").toLowerCase();
		zip = new File(UUID.randomUUID().toString());
		FileOutputStream o = new FileOutputStream(zip);
		IOUtils.copy(uploadedFile.getInputStream(), o);
		o.close();

		/**
		 * unizp file from temp by zip4j
		 */
		String destination = null;
		File directory = null;
		String path = "casefiles/" + userId;

		if (!osname.contains("windows")) {
			path = "/var/infotek/casefiles/" + userId;
			directory = new File(path);
		} else {
			directory = new File(path);
		}
		if (!directory.exists()) {
			directory.mkdirs();
		}
		destination = directory.getPath();
		try {
			ZipFile zipFile = new ZipFile(zip);
			zipFile.extractAll(destination);
		} catch (ZipException e) {
			e.printStackTrace();
		}

		// get all the files from a directory
		//path = path + "/" + uploadedFile.getOriginalFilename().split("\\.")[0];
		directory = new File(path);
		File[] fList = directory.listFiles();
		if (fList.length > 0) {
			s3Services.createFolder(branchName);
			path = s3Services.createSubFolder(branchName, institutionName);
			path = s3Services.create3rdLevelSubFolder(path, userId.toString());
			for (File file : fList) {
				String fileName = file.getName().split("\\.")[0] + "_"
						+ new SimpleDateFormat("yyyyMMddHHmm").format(new Date()) + "."
						+ file.getName().split("\\.")[1];
				s3Services.uploadCaseFiles(path, fileName, file);
				CaseFiles caseFiles = new CaseFiles();
				caseFiles.setCaseId(fileMap.get(file.getName()));
				caseFiles.setFileName(fileName);
				caseFiles.setFilePath(path);
				caseFiles.setFromDate(new Date());
				caseFiles.setRemarkOnly(Boolean.FALSE);
				caseFiles.setIsInstitutional(Boolean.TRUE);
				caseFilesRepository.save(caseFiles);
				file.delete();
			}
		}
		zip.delete();
		directory.delete();
	}

	/**
	 * 
	 * @param userId
	 * @param branchName
	 * @param uploadedFile
	 *            - zip for unzipping
	 * @param fileMap
	 * @return
	 * @return
	 */
	public void uploadFile(Long userId, String caseId, Long institutionId, String branchName,
			MultipartFile uploadedFile) throws Exception {
		String institutionName = institutionRepository.findById(institutionId).getInstitutionName().toLowerCase();
		File file = new File(uploadedFile.getOriginalFilename());
		FileOutputStream o = new FileOutputStream(file);
		o.write(uploadedFile.getBytes());
		o.close();
		s3Services.createFolder(userId.toString());
		String path = s3Services.createSubFolder(branchName, institutionName);
		path = s3Services.create3rdLevelSubFolder(path, userId.toString());
		String fileName = uploadedFile.getOriginalFilename().split("\\.")[0] + "_"
				+ new SimpleDateFormat("yyyyMMddHHmm").format(new Date()) + "."
				+ uploadedFile.getOriginalFilename().split("\\.")[1];
		s3Services.uploadCaseFiles(path, fileName, file);
		CaseFiles caseFiles = new CaseFiles();
		caseFiles.setCaseId(caseId);
		caseFiles.setFileName(fileName);
		caseFiles.setFilePath(path);
		caseFiles.setFromDate(new Date());
		caseFiles.setRemarkOnly(Boolean.FALSE);
		caseFiles.setIsInstitutional(Boolean.TRUE);
		caseFilesRepository.save(caseFiles);
		file.delete();
	}

	/**
	 * 
	 * @param userId
	 * @param uploadedFile
	 *            - zip for unzipping
	 * @param remark
	 * @param fileMap
	 * @return
	 * @return
	 */
	public void uploadFile(Long userId, String caseId, MultipartFile uploadedFile, String remark) throws Exception {

		File file = new File(uploadedFile.getOriginalFilename());
		FileOutputStream o = new FileOutputStream(file);
		o.write(uploadedFile.getBytes());
		o.close();
		s3Services.createFolder(userId.toString());
		String path = s3Services.getBucketName() + "/" + userId.toString();
		String fileName = uploadedFile.getOriginalFilename().split("\\.")[0] + "_"
				+ new SimpleDateFormat("yyyyMMddHHmm").format(new Date()) + "."
				+ uploadedFile.getOriginalFilename().split("\\.")[1];
		s3Services.uploadCaseFiles(path, fileName, file);
		CaseFiles caseFiles = new CaseFiles();
		caseFiles.setCaseId(caseId);
		caseFiles.setFileName(fileName);
		caseFiles.setFilePath(path);
		caseFiles.setFromDate(new Date());
		caseFiles.setRemark(remark);
		caseFiles.setRemarkOnly(Boolean.FALSE);
		caseFilesRepository.save(caseFiles);
		file.delete();
	}

	/**
	 * 
	 * @param userId
	 * @param uploadedFile
	 *            - zip for unzipping
	 * @param remark
	 * @param fileMap
	 * @return
	 * @return
	 */
	public void updateCaseRemarkAndFile(Long userId, String caseId, MultipartFile uploadedFile, String remark)
			throws Exception {
		String fileName = null;
		CaseFiles caseFiles = new CaseFiles();
		String path = null;
		File file = null;
		if (uploadedFile != null) {
			file = new File(uploadedFile.getOriginalFilename());
			FileOutputStream o = new FileOutputStream(file);
			o.write(uploadedFile.getBytes());
			o.close();
			s3Services.createFolder(userId.toString());
			path = s3Services.getBucketName() + "/" + userId.toString();
			fileName = uploadedFile.getOriginalFilename().split("\\.")[0] + "_"
					+ new SimpleDateFormat("yyyyMMddHHmm").format(new Date()) + "."
					+ uploadedFile.getOriginalFilename().split("\\.")[1];
			s3Services.uploadCaseFiles(path, fileName, file);
			caseFiles.setFileName(fileName);
			caseFiles.setFilePath(path);
		} else {
			caseFiles.setRemarkOnly(Boolean.TRUE);
		}
		caseFiles.setCaseId(caseId);
		caseFiles.setFromDate(new Date());
		caseFiles.setRemark(remark);
		CaseFiles persistedCaseFiles = caseFilesRepository.save(caseFiles);
		LegalCase legalCase = caseRepository.findByCaseId(caseId);
		legalCase.setRemarkHistory(remark);
		if (uploadedFile != null) {
			legalCase.setRemarkFile(uploadedFile.getOriginalFilename()+"~"+persistedCaseFiles.getId());
			file.delete();
		}
		caseRepository.save(legalCase);

	}
}
