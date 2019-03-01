package com.legalfriend.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name = "ind_billing_master")
public class IndividualBillingMaster {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;

	@Column(name = "amount")
	private Double amount;

	@JoinColumn(name = "fk_stage_id")
	@ManyToOne
	private Stage stage;

	@JoinColumn(name = "fk_recourse_id")
	@ManyToOne
	private Recourse recourse;

	@Column(name = "fk_user_id")
	private Long userId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUser(Long userId) {
		this.userId = userId;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public Recourse getRecourse() {
		return recourse;
	}

	public void setRecourse(Recourse recourse) {
		this.recourse = recourse;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
