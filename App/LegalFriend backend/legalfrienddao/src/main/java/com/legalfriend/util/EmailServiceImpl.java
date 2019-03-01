package com.legalfriend.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.legalfriend.entities.LegalCase;
import com.legalfriend.entities.Mail;

import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;

@Service
public class EmailServiceImpl implements EmailService {

	@Value("${spring.host}")
	private String host;

	@Value("${spring.port}")
	private String port;

	@Value("${spring.mail.username}")
	private String mailFrom;
	
	@Autowired
	private JavaMailSender emailSender;

	@Autowired
	private Configuration freemarkerConfig;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EmailServiceImpl.class);

	@Async
	public void sendEmail(String toEmail, String subject, String content) {
		final String username = "noreplylegalfriend@gmail.com";
		final String password = "Global@123";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress("noreplylegalfriend@gmail.com"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
			message.setContent(content, "text/html; charset=utf-8");
			message.setSubject(subject);
			Transport.send(message);

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public void sendEmail(String token, String email){
		String verifyLink = "http://".concat(host).concat(":").concat(port).concat("/verifyUser/").concat(token);
		
		Mail mail = new Mail();
		mail.setFrom(mailFrom);
		mail.setTo(email);
		mail.setSubject("LegalFriend User Verification Mail");

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("user_name", email.split("@")[0]);
		model.put("verificationUrl", verifyLink);
		try {
		mail.setModel(model);
		Template verification = freemarkerConfig.getTemplate("email-verification.ftl");
		sendMail(mail, verification);
		
		}
		catch(Exception e) {
			
		}
		
	}

	@Override
	public void sendRemarkEmail(String email, String caseId)
			throws MalformedTemplateNameException, ParseException, IOException, MessagingException, TemplateException {

		Mail mail = new Mail();
		mail.setFrom(mailFrom);
		mail.setTo(email);
		mail.setSubject("Remark has been added");

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("userName", email.split("@")[0]);
		model.put("caseId", caseId);

		mail.setModel(model);
		Template userVerification = freemarkerConfig.getTemplate("email-caseremark.ftl");
		sendMail(mail, userVerification);
	}

	@Override
	public void sendUserVerificationEmail(String token, String email) throws TemplateNotFoundException,
			MalformedTemplateNameException, ParseException, IOException, MessagingException, TemplateException {

		String verifyLink = "http://".concat(host).concat(":").concat(port).concat("/verifyUser/").concat(token);

		Mail mail = new Mail();
		mail.setFrom(mailFrom);
		mail.setTo(email);
		mail.setSubject("LegalFriend User Verification Mail");

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("user_name", email.split("@")[0]);
		model.put("verificationUrl", verifyLink);

		mail.setModel(model);
		Template userVerification = freemarkerConfig.getTemplate("email-userverification.ftl");
		sendMail(mail, userVerification);

	}

	@Override
	public void sendReferralEmail(String toEmail, String token) {
		
		String referralUrl = "http://".concat(host).concat(":").concat(port).concat("/verifyReferralEmail/").concat(token);
		try {
			Mail mail = new Mail();
			mail.setFrom(mailFrom);
			mail.setTo(toEmail);
			mail.setSubject("Welcome to LegalFriend");

			Map<String, Object> model = new HashMap<String, Object>();
			model.put("userName", toEmail.split("@")[0]);
			model.put("referralUrl",referralUrl);

			mail.setModel(model);
			Template userVerification = freemarkerConfig.getTemplate("email-referralmail.ftl");
			sendMail(mail, userVerification);
			}
			catch(Exception e) {
				
			}
	}

	@Override
	public void sendReferralWelcomeEmail(String toEmail, String name, String token) {

		String signupUrl = "http://".concat(host).concat(":").concat(port).concat("/referralSignUp/").concat(token);
		try {
			Mail mail = new Mail();
			mail.setFrom(mailFrom);
			mail.setTo(toEmail);
			mail.setSubject("Welcome to LegalFriend");

			Map<String, Object> model = new HashMap<String, Object>();
			model.put("userName", name);
			model.put("signupUrl",signupUrl);

			mail.setModel(model);
			Template userVerification = freemarkerConfig.getTemplate("email-referralwelcome.ftl");
			sendMail(mail, userVerification);
			}
			catch(Exception e) {
				
			}
	}

	@Override
	public void sendForgotPasswordEmail(String email, String token, String subject) {
		String updatePasswordUrl = "http://".concat(host).concat(":").concat(port).concat("/updatePassword/").concat(token);
		try {
			Mail mail = new Mail();
			mail.setFrom(mailFrom);
			mail.setTo(email);
			mail.setSubject(subject);

			Map<String, Object> model = new HashMap<String, Object>();
			model.put("userName", email.split("@")[0]);
			model.put("updatePassword",updatePasswordUrl);

			mail.setModel(model);
			Template userVerification = freemarkerConfig.getTemplate("email-updatePassword.ftl");
			sendMail(mail, userVerification);
			}
			catch(Exception e) {
				
			}
	}

	@Override
	public void sendReferralSignupEmail(String email, String name)  {
		try {
		Mail mail = new Mail();
		mail.setFrom(mailFrom);
		mail.setTo(email);
		mail.setSubject("Referral Sign Up");

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("userName", email.split("@")[0]);
		model.put("name", name);

		mail.setModel(model);
		Template userVerification = freemarkerConfig.getTemplate("email-referralsignup.ftl");
		sendMail(mail, userVerification);
		}
		catch(Exception e) {
			
		}
	}

	@Override
	public void sendCaseCreatedEmail(String email, String caseId) throws TemplateNotFoundException, MalformedTemplateNameException,
	ParseException, IOException, MessagingException, TemplateException {
		
		Mail mail = new Mail();
		mail.setFrom(mailFrom);
		mail.setTo(email);
		mail.setSubject("New Case");

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("userName", email.split("@")[0]);
		model.put("caseId", caseId);

		mail.setModel(model);
		Template userVerification = freemarkerConfig.getTemplate("email-casecreation.ftl");
		sendMail(mail, userVerification);
	}

	void sendMail(Mail mail, Template template) throws MessagingException, IOException, TemplateException {

		MimeMessage message = emailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
				StandardCharsets.UTF_8.name());

		String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, mail.getModel());

		helper.setTo(mail.getTo());
		helper.setText(html, true);
		helper.setSubject(mail.getSubject());
		helper.setFrom(mail.getFrom());

		emailSender.send(message);

	}
	
	@Override
	public void sendHearingDateReminderEmail(String email,String username, List<LegalCase> cases, Set dates) {
		try {
			
		Mail mail = new Mail();
		mail.setFrom(mailFrom);
		mail.setTo(email);
		mail.setSubject("Next Hearing date reminder");

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("userName", username);
		model.put("dateList", dates);
		model.put("caseList", cases);

		mail.setModel(model);
		Template userVerification = freemarkerConfig.getTemplate("email-reminder.ftl");
		sendMail(mail, userVerification);
		
		}
		catch(Exception e) {
			LOGGER.error("Error while sending Hearing Date Reminder Mail........");
		}
	}

}
