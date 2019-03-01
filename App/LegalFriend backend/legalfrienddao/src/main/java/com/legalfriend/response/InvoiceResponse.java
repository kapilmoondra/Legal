package com.legalfriend.response;

import java.util.List;

import com.legalfriend.entities.Billing;
import com.legalfriend.entities.IndividualBilling;
import com.legalfriend.entities.Invoice;

public class InvoiceResponse extends LegalFriendResponse {

	private Long id;

	private Invoice invoice;

	private List<Billing> institutionalBillings;

	private List<IndividualBilling> individualBillings;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Billing> getInstitutionalBillings() {
		return institutionalBillings;
	}

	public void setInstitutionalBillings(List<Billing> institutionalBillings) {
		this.institutionalBillings = institutionalBillings;
	}

	public List<IndividualBilling> getIndividualBillings() {
		return individualBillings;
	}

	public void setIndividualBillings(List<IndividualBilling> individualBillings2) {
		this.individualBillings = individualBillings2;
	}

	public Invoice getInvoice() {
		return invoice;
	}

	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

}
