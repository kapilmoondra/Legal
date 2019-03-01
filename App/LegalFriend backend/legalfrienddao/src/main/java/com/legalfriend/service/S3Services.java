package com.legalfriend.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.amazonaws.services.s3.model.S3ObjectInputStream;

public interface S3Services {
	public void downloadFile(String keyName);

	void uploadFile(String keyName, File file);

	void createBucket(String bucketName);

	void createFolder(String bucketName);

	void uploadCaseFiles(String bucketName, String keyName, File file);

	String createSubFolder(String folderName, String subFolderName);

	byte[] download(String filePath, String key) throws IOException;

	public String getBucketName();

	String create3rdLevelSubFolder(String path, String subFolderName);

	List<String> getBucketObjectNames(String bucketName);

	S3ObjectInputStream downloadFile(String bucketName, String keyName);
}