package com.legalfriend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.legalfriend.entities.UserEvaluation;
import com.legalfriend.repository.UserEvaluationRepository;

@RestController
@RequestMapping("/evaluation")
public class EvaluationController {

	@Autowired
	private UserEvaluationRepository evalRepo;

	@PostMapping
	public ResponseEntity<String> createUserEvaluation(@RequestBody UserEvaluation userEvaluation) {
		userEvaluation.setStatus("OPEN");
		evalRepo.save(userEvaluation);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@GetMapping
	public List<UserEvaluation> getUserEvaluation(@RequestParam Long userId) {
		return evalRepo.findByUserId(userId);
	}

	@PutMapping
	public ResponseEntity<String> updateUserEvaluation(@RequestBody UserEvaluation userEvaluation) {
		evalRepo.save(userEvaluation);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
