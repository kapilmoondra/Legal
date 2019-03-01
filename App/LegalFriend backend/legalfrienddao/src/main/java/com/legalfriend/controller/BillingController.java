package com.legalfriend.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.legalfriend.entities.Billing;
import com.legalfriend.entities.DashboardReport;
import com.legalfriend.entities.IndividualBilling;
import com.legalfriend.repository.BillingRepository;
import com.legalfriend.repository.BranchRepository;
import com.legalfriend.repository.IndividualBillingRepository;
import com.legalfriend.service.BillingService;

@RestController
@RequestMapping("/billing")
public class BillingController {

	@Autowired
	private BillingRepository billingRepository;
	
	@Autowired
	private BillingService billingService;

	@Autowired
	private IndividualBillingRepository individualBillingRepository;

	@Autowired
	private BranchRepository branchRepository;
	
	@GetMapping
	public List<IndividualBilling> getBilling(@RequestParam Long userId, @RequestParam Long branchId) {
		return individualBillingRepository.findByUserIdAndBranch(userId, branchRepository.findById(branchId));
	}

	@GetMapping("/inst")
	public List<Billing> getInstitutionalBilling(@RequestParam Long userId) {
		return billingRepository.findByUserId(userId);
	}

	@DeleteMapping
	public ResponseEntity<?> deleteBilling(@RequestParam Long billingId) {
		billingRepository.delete(billingId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@DeleteMapping("/individual")
	public ResponseEntity<?> deleteIndividualBilling(@RequestParam Long billingId) {
		individualBillingRepository.delete(billingId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PutMapping
	public ResponseEntity<?> updateBilling(@RequestBody Billing biling) {
		billingRepository.save(biling);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PutMapping("/individual")
	public ResponseEntity<?> updateIndividualBilling(@RequestBody IndividualBilling biling) {
		individualBillingRepository.save(biling);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping
	public List<Billing> createBilling(@RequestBody List<Billing> biling) {
		List<Billing> billings = new ArrayList<>();
		billingRepository.save(biling).forEach(billings::add);
		return billings;
	}

	@PostMapping("/individual")
	public List<IndividualBilling> createIndividualBilling(@RequestBody List<IndividualBilling> biling) {
		List<IndividualBilling> billings = new ArrayList<>();
		individualBillingRepository.save(biling).forEach(billings::add);
		return billings;
	}
	
	@GetMapping("/institutions")
	List<DashboardReport> getBillingByInstitutions(@RequestParam Long userId){
		return billingService.findBillingByInstitution(userId);
	}
	
	@GetMapping("/institution")
	List<DashboardReport> getBillingByInstitution(@RequestParam Long userId,@RequestParam String name){
		return billingService.findBillingByInstitution(userId, name);
	}

	@GetMapping("/individual")
	List<DashboardReport> getIndividualBilling(@RequestParam Long userId){
		return billingService.findIndividualBilling(userId);
	}

}
