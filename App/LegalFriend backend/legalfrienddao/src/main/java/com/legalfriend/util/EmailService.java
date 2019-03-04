package com.legalfriend.util;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import javax.mail.MessagingException;

import com.legalfriend.entities.LegalCase;

import freemarker.core.ParseException;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;

public interface EmailService {

	public void sendEmail(String toEmail, String subject, String content);

	public void sendEmail(String token, String email) throws MessagingException, IOException, TemplateException;

	public void sendReferralEmail(String toEmail, String token);

	void sendForgotPasswordEmail(String email, String token, String subject);

	void sendUserVerificationEmail(String token, String email)throws TemplateNotFoundException
	, MalformedTemplateNameException, ParseException, IOException, MessagingException, TemplateException;

	void sendRemarkEmail(String email, String caseId) throws MalformedTemplateNameException,
	ParseException, IOException, MessagingException, TemplateException;

	void sendReferralSignupEmail(String email, String name);

	void sendCaseCreatedEmail(String email, String caseId) throws TemplateNotFoundException,
	MalformedTemplateNameException, ParseException, IOException, MessagingException, TemplateException;

	void sendReferralWelcomeEmail(String toEmail, String name, String token);
	
	void sendHearingDateReminderEmail(String email, String username, List<LegalCase> cases, Set dates);

}