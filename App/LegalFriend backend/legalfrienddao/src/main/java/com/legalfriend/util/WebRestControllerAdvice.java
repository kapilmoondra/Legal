package com.legalfriend.util;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.legalfriend.response.LegalFriendResponse;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

@RestControllerAdvice
public class WebRestControllerAdvice {

	@ExceptionHandler(Exception.class)
	public LegalFriendResponse handleNotFoundException(Exception ex) {
		LegalFriendResponse responseMsg = new LegalFriendResponse();
		StringWriter sw = new StringWriter();
		ex.printStackTrace(new PrintWriter(sw));
		String exceptionAsString = sw.toString();
		responseMsg.setFailureReason(exceptionAsString);
		responseMsg.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		return responseMsg;
	}

	@ExceptionHandler(CsvRequiredFieldEmptyException.class)
	public LegalFriendResponse handleCsvRequiredFieldException(CsvRequiredFieldEmptyException ex) {
		LegalFriendResponse responseMsg = new LegalFriendResponse();
		responseMsg.setFailureReason("Missing required field in CSV, Please correct data -" + ex.getMessage());
		responseMsg.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		return responseMsg;
	}

	@ExceptionHandler(CsvDataTypeMismatchException.class)
	public LegalFriendResponse handleCsvDataTypeMismatchExceptionException(CsvDataTypeMismatchException ex) {
		LegalFriendResponse responseMsg = new LegalFriendResponse();
		responseMsg.setFailureReason("CSV data is incorrect -" + ex.getMessage());
		responseMsg.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		return responseMsg;
	}

}