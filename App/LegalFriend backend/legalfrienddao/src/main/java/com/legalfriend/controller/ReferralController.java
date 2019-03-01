package com.legalfriend.controller;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.legalfriend.entities.Referral;
import com.legalfriend.repository.ReferralRepository;
import com.legalfriend.util.EmailServiceImpl;

@RestController
@RequestMapping("/refer")
public class ReferralController {

	@Autowired
	private ReferralRepository referralRepository;

	@Autowired
	private EmailServiceImpl emailService;

	@GetMapping
	public List<Referral> getReferral(@RequestParam Long referrerId) {
		return referralRepository.findByReferrerId(referrerId);
	}

	@SuppressWarnings("deprecation")
	@PostMapping
	public Referral createReferral(@RequestBody Referral referral) {
		referral.setCreatedDate(new Date());
		referral.setUpdatedDate(new Date());
		Date expirtyDate = new Date();
		expirtyDate.setMonth(expirtyDate.getMonth() + 6);
		referral.setReferralExpiryDate(expirtyDate);
		Referral persistedReferral = referralRepository.save(referral);
		String token = UUID.randomUUID().toString();
		emailService.sendReferralWelcomeEmail(referral.getEmail(), persistedReferral.getName(), token);
		return persistedReferral;
	}
}
