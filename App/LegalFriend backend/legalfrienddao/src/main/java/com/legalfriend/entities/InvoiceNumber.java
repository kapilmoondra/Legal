package com.legalfriend.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "invoice_number")
public class InvoiceNumber {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;

	@Column(name = "fk_user_id")
	private Long userId;

	@Column(name = "next_invoice_number")
	private Long nextInvoiceNumber;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getNextInvoiceNumber() {
		return nextInvoiceNumber;
	}

	public void setNextInvoiceNumber(Long nextInvoiceNumber) {
		this.nextInvoiceNumber = nextInvoiceNumber;
	}

}
