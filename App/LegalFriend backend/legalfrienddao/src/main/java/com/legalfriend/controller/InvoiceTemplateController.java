package com.legalfriend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.legalfriend.entities.InvoiceTemplate;
import com.legalfriend.repository.InvoiceTemplateRepository;

@RestController
@RequestMapping("/invoice/template")
public class InvoiceTemplateController {

	@Autowired
	private InvoiceTemplateRepository invoiceRepository;

	@GetMapping
	public InvoiceTemplate getInvoiceTemplates(@RequestParam Long userId) {
		return invoiceRepository.findByUserId(userId);
	}

	@PostMapping
	public Long createInvoiceTemplate(@RequestBody InvoiceTemplate invoice) {
		InvoiceTemplate invoiceTemplate = invoiceRepository.findByUserId(invoice.getUserId());
		if (invoiceTemplate != null) {
			invoiceRepository.delete(invoiceTemplate);
		}
		InvoiceTemplate persistedInvoice = invoiceRepository.save(invoice);
		return persistedInvoice.getId();
	}

}
