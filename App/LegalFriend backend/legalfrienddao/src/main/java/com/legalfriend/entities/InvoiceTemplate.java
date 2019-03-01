package com.legalfriend.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity(name = "invoice_template")
public class InvoiceTemplate {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;
	@JoinColumn(name = "fk_invoice_header")
	@OneToOne(cascade = CascadeType.ALL)
	private InvoiceHeader invoiceHeader;
	@JoinColumn(name = "fk_invoice_footer")
	@OneToOne(cascade = CascadeType.ALL)
	private InvoiceFooter invoiceFooter;
	@Column(name = "fk_user_id")
	private Long userId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public InvoiceHeader getInvoiceHeader() {
		return invoiceHeader;
	}

	public void setInvoiceHeader(InvoiceHeader invoiceHeader) {
		this.invoiceHeader = invoiceHeader;
	}

	public InvoiceFooter getInvoiceFooter() {
		return invoiceFooter;
	}

	public void setInvoiceFooter(InvoiceFooter invoiceFooter) {
		this.invoiceFooter = invoiceFooter;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
