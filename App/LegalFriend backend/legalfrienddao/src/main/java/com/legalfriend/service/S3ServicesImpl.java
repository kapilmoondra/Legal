package com.legalfriend.service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;

@Service
public class S3ServicesImpl implements S3Services {

	private Logger logger = LoggerFactory.getLogger(S3ServicesImpl.class);

	@Autowired
	private AmazonS3 s3client;

	@Value("${s3.bucket}")
	private String bucketName;

	private static final String SUFFIX = "/";

	public List<S3ObjectSummary> getBucketObjectSummaries(String bucketName) {

		List<S3ObjectSummary> s3ObjectSummaries = new ArrayList<S3ObjectSummary>();

		try {
			ListObjectsRequest listObjectsRequest = new ListObjectsRequest().withBucketName(bucketName);
			ObjectListing objectListing;

			do {
				objectListing = s3client.listObjects(listObjectsRequest);
				for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
					s3ObjectSummaries.add(objectSummary);
				}
				listObjectsRequest.setMarker(objectListing.getNextMarker());
			} while (objectListing.isTruncated());
		} catch (AmazonServiceException ase) {
			System.out.println("Caught an AmazonServiceException, " + "which means your request made it "
					+ "to Amazon BdS3Client, but was rejected with an error response " + "for some reason.");
			System.out.println("Error Message:    " + ase.getMessage());
			System.out.println("HTTP Status Code: " + ase.getStatusCode());
			System.out.println("AWS Error Code:   " + ase.getErrorCode());
			System.out.println("Error Type:       " + ase.getErrorType());
			System.out.println("Request ID:       " + ase.getRequestId());
		} catch (AmazonClientException ace) {
			System.out.println("Caught an AmazonClientException, " + "which means the client encountered "
					+ "an internal error while trying to communicate" + " with BdS3Client, "
					+ "such as not being able to access the network.");
			System.out.println("Error Message: " + ace.getMessage());
		}
		return s3ObjectSummaries;
	}

	@Override
	public List<String> getBucketObjectNames(String bucketName) {
		List<String> s3ObjectNames = new ArrayList<String>();
		List<S3ObjectSummary> s3ObjectSummaries = getBucketObjectSummaries(bucketName);

		for (S3ObjectSummary s3ObjectSummary : s3ObjectSummaries) {
			s3ObjectNames.add(s3ObjectSummary.getKey());
		}
		return s3ObjectNames;
	}

	@Override
	public void downloadFile(String keyName) {

		try {
			System.out.println("Downloading an object");
			S3Object s3object = s3client.getObject(new GetObjectRequest(bucketName, keyName));
			System.out.println("Content-Type: " + s3object.getObjectMetadata().getContentType());
			logger.info("===================== Import File - Done! =====================");

		} catch (AmazonServiceException ase) {
			logger.info("Caught an AmazonServiceException from GET requests, rejected reasons:");
			logger.info("Error Message:    " + ase.getMessage());
			logger.info("HTTP Status Code: " + ase.getStatusCode());
			logger.info("AWS Error Code:   " + ase.getErrorCode());
			logger.info("Error Type:       " + ase.getErrorType());
			logger.info("Request ID:       " + ase.getRequestId());
		} catch (AmazonClientException ace) {
			logger.info("Caught an AmazonClientException: ");
			logger.info("Error Message: " + ace.getMessage());
		}
	}

	@Override
	public S3ObjectInputStream downloadFile(String bucketName, String keyName) {

		try {
			System.out.println("Downloading an object");
			S3Object s3object = s3client.getObject(new GetObjectRequest(bucketName, keyName));
			System.out.println("Content-Type: " + s3object.getObjectMetadata().getContentType());
			logger.info("===================== Import File - Done! =====================");
			S3ObjectInputStream objectInputStream = s3object.getObjectContent();
			return objectInputStream;
		} catch (AmazonServiceException ase) {
			logger.info("Caught an AmazonServiceException from GET requests, rejected reasons:");
			logger.info("Error Message:    " + ase.getMessage());
			logger.info("HTTP Status Code: " + ase.getStatusCode());
			logger.info("AWS Error Code:   " + ase.getErrorCode());
			logger.info("Error Type:       " + ase.getErrorType());
			logger.info("Request ID:       " + ase.getRequestId());
		} catch (AmazonClientException ace) {
			logger.info("Caught an AmazonClientException: ");
			logger.info("Error Message: " + ace.getMessage());
		}
		return null;
	}

	@Override
	public void uploadFile(String keyName, File file) {
		try {
			// File file = new File(uploadFilePath);
			s3client.putObject(new PutObjectRequest(bucketName, keyName, file));
			logger.info("===================== Upload File - Done! =====================");
		} catch (AmazonServiceException ase) {
			logger.info("Caught an AmazonServiceException from PUT requests, rejected reasons:");
			logger.info("Error Message:    " + ase.getMessage());
			logger.info("HTTP Status Code: " + ase.getStatusCode());
			logger.info("AWS Error Code:   " + ase.getErrorCode());
			logger.info("Error Type:       " + ase.getErrorType());
			logger.info("Request ID:       " + ase.getRequestId());
		} catch (AmazonClientException ace) {
			logger.info("Caught an AmazonClientException: ");
			logger.info("Error Message: " + ace.getMessage());
		}
	}

	@Override
	public void createFolder(String folderName) {
		boolean exists = s3client.doesObjectExist(bucketName, folderName);
		if (!exists) {
			// create meta-data for your folder and set content-length to 0
			ObjectMetadata metadata = new ObjectMetadata();
			metadata.setContentLength(0);
			// create empty content
			InputStream emptyContent = new ByteArrayInputStream(new byte[0]);
			// create a PutObjectRequest passing the folder name suffixed by /
			PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, folderName + SUFFIX, emptyContent,
					metadata);
			// send request to S3 to create folder
			s3client.putObject(putObjectRequest);
		}
	}

	@Override
	public String createSubFolder(String folderName, String subFolderName) {
		boolean exists = s3client.doesObjectExist(bucketName + SUFFIX + folderName, subFolderName);
		if (!exists) {
			// create meta-data for your folder and set content-length to 0
			ObjectMetadata metadata = new ObjectMetadata();
			metadata.setContentLength(0);
			// create empty content
			InputStream emptyContent = new ByteArrayInputStream(new byte[0]);
			// create a PutObjectRequest passing the folder name suffixed by /
			PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName + SUFFIX + folderName,
					subFolderName + SUFFIX, emptyContent, metadata);
			// send request to S3 to create folder
			s3client.putObject(putObjectRequest);
		}
		return bucketName + SUFFIX + folderName + SUFFIX + subFolderName;
	}

	@Override
	public String create3rdLevelSubFolder(String path, String subFolderName) {
		boolean exists = s3client.doesObjectExist(path, subFolderName);
		if (!exists) {
			// create meta-data for your folder and set content-length to 0
			ObjectMetadata metadata = new ObjectMetadata();
			metadata.setContentLength(0);
			// create empty content
			InputStream emptyContent = new ByteArrayInputStream(new byte[0]);
			// create a PutObjectRequest passing the folder name suffixed by /
			PutObjectRequest putObjectRequest = new PutObjectRequest(path, subFolderName + SUFFIX, emptyContent,
					metadata);
			// send request to S3 to create folder
			s3client.putObject(putObjectRequest);
		}
		return path + SUFFIX + subFolderName;
	}

	@Override
	public void createBucket(String bucketName) {
		try {
			// File file = new File(uploadFilePath);
			bucketName = bucketName.toLowerCase();
			if (!s3client.doesBucketExist(bucketName)) {
				s3client.createBucket(bucketName);
				logger.info("===================== Creating Bucket - Done! =====================");
			}
		} catch (AmazonServiceException ase) {
			logger.info("Caught an AmazonServiceException from PUT requests, rejected reasons:");
			logger.info("Error Message:    " + ase.getMessage());
			logger.info("HTTP Status Code: " + ase.getStatusCode());
			logger.info("AWS Error Code:   " + ase.getErrorCode());
			logger.info("Error Type:       " + ase.getErrorType());
			logger.info("Request ID:       " + ase.getRequestId());
		} catch (AmazonClientException ace) {
			logger.info("Caught an AmazonClientException: ");
			logger.info("Error Message: " + ace.getMessage());
		}
	}

	@Override
	public void uploadCaseFiles(String bucketName, String keyName, File file) {
		try {
			s3client.putObject(new PutObjectRequest(bucketName, keyName, file));
			logger.info("===================== Upload File - Done! =====================");
		} catch (AmazonServiceException ase) {
			logger.info("Caught an AmazonServiceException from PUT requests, rejected reasons:");
			logger.info("Error Message:    " + ase.getMessage());
			logger.info("HTTP Status Code: " + ase.getStatusCode());
			logger.info("AWS Error Code:   " + ase.getErrorCode());
			logger.info("Error Type:       " + ase.getErrorType());
			logger.info("Request ID:       " + ase.getRequestId());
		} catch (AmazonClientException ace) {
			logger.info("Caught an AmazonClientException: ");
			logger.info("Error Message: " + ace.getMessage());
		}
	}

	@Override
	public byte[] download(String fileName, String filePath) throws IOException {
		GetObjectRequest getObjectRequest = new GetObjectRequest(filePath, fileName);
		S3Object s3Object = s3client.getObject(getObjectRequest);
		S3ObjectInputStream objectInputStream = s3Object.getObjectContent();
		return IOUtils.toByteArray(objectInputStream);
	}

	@Override
	public String getBucketName() {
		return bucketName;
	}
}