package com.legalfriend.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import com.legalfriend.entities.Invoice;
import com.legalfriend.entities.InvoiceNumber;
import com.legalfriend.enums.InvoiceStatus;
import com.legalfriend.enums.UserRole;
import com.legalfriend.repository.BillingRepository;
import com.legalfriend.repository.BranchRepository;
import com.legalfriend.repository.IndividualBillingRepository;
import com.legalfriend.repository.InvoiceNumberRepository;
import com.legalfriend.repository.InvoiceRepository;
import com.legalfriend.repository.UserRepository;
import com.legalfriend.response.InvoiceResponse;
import com.legalfriend.service.InvoiceService;

@RestController
@RequestMapping("/invoice")
public class InvoiceController {

	private String FETCH_USER_ROLE = " select r.role_name from user_role ur left join role r on r.id=ur.role_id WHERE ur.user_id = :userId";

	@Autowired
	private InvoiceService invoiceService;
	
	@Autowired
	private InvoiceRepository invoiceRepository;

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private BillingRepository billingRepository;

	@Autowired
	private InvoiceNumberRepository invoiceNumberRepo;

	@Autowired
	private IndividualBillingRepository individualBillingRepository;
	
	@Autowired
	private BranchRepository branchRepo;

	@Autowired
	private UserRepository userRepository;

	@GetMapping("/institutional/single")
	public InvoiceResponse getInstitutionalInvoice(@RequestParam Long invoiceId) {
		InvoiceResponse invoiceResponse = new InvoiceResponse();
		Invoice invoice = invoiceRepository.findById(invoiceId);
		invoiceResponse.setInstitutionalBillings(billingRepository.findByInvoice(invoiceId));
		invoiceResponse.setInvoice(invoice);
		return invoiceResponse;
	}

	@GetMapping("/validate")
	public InvoiceResponse validateInvoice(@RequestParam Long invoiceNumber, @RequestParam Long userId) {
		InvoiceResponse invoiceResponse = new InvoiceResponse();
		Invoice invoice = invoiceRepository.findByInvoiceNumberAndUserId(invoiceNumber, userId);
		if (invoice != null) {
			invoiceResponse.setFailureReason("Invoice number already exists");
			invoiceResponse.setHttpCode(HttpStatus.CONFLICT.value());
		} else {
			invoiceResponse.setSuccessMessage("Invoice Number is unique");
			invoiceResponse.setHttpCode(HttpStatus.OK.value());
		}
		return invoiceResponse;
	}

	@GetMapping("/individual/single")
	public InvoiceResponse getIndividualInvoice(@RequestParam Long invoiceId) {
		InvoiceResponse invoiceResponse = new InvoiceResponse();
		Invoice invoice = invoiceRepository.findById(invoiceId);
		invoiceResponse.setIndividualBillings(individualBillingRepository.findByInvoice(invoiceId));
		invoiceResponse.setInvoice(invoice);
		return invoiceResponse;
	}

	public String getInvoiceNumber(Long userId) {
		InvoiceNumber invoiceNumber = invoiceNumberRepo.findByUserId(userId);
		Long nextInvoiceNumber = null;
		if (invoiceNumber == null) {
			nextInvoiceNumber = 100L;
			invoiceNumber = new InvoiceNumber();
			invoiceNumber.setUserId(userId);
		} else {
			nextInvoiceNumber = invoiceNumber.getNextInvoiceNumber();
		}
		String invoiceN = new SimpleDateFormat("yyyyMMddHHmm").format(new Date()) + nextInvoiceNumber;
		invoiceNumber.setNextInvoiceNumber(nextInvoiceNumber + 1);
		invoiceNumberRepo.save(invoiceNumber);
		return invoiceN;
	}

	private List<String> findUserRole(Long userId) {
		Session session = sessionFactory.openSession();
		List<String> list = new ArrayList<>();
		Query query = session.createSQLQuery(FETCH_USER_ROLE);
		query.setLong("userId", userId);
		List<String> rows = (List<String>) query.list();
		for (String role : rows) {
			list.add(role);
		}
		session.close();
		return list;
	}

	@GetMapping("/institutional")
	public List<Invoice> getInstitutionalInvoices(@RequestParam Long userId) {
		List<Invoice> invoices = invoiceRepository.findInstitutionalInvoices(userId, InvoiceStatus.CANCELLED.name());
		return invoices;
	}

	@GetMapping("/individual")
	public List<Invoice> getIndvidualInvoices(@RequestParam Long userId, @RequestParam Long branchId) {
		List<Invoice> invoices = invoiceRepository.findIndividualInvoices(userId, InvoiceStatus.CANCELLED.name(), branchId);
		return invoices;
	}

	@PostMapping
	public InvoiceResponse createInvoice(@RequestBody InvoiceResponse invoice) {
		Long userId = invoice.getInvoice().getUserId();
		Invoice invoice2 = new Invoice();
		List<String> roles = findUserRole(invoice.getInvoice().getUserId());
		if (!roles.contains(UserRole.ADMIN.name())) {
			userId = userRepository.findById(invoice.getInvoice().getUserId()).get(0).getClientId();
		}
		BeanUtils.copyProperties(invoice.getInvoice(), invoice2);
		invoice2.setCreatedBy(userId);
		invoice2.setUpdatedBy(userId);
		invoice2.setUserId(userId);
		invoice2.setAmountRecieved(false);
		invoice2.setCreatedDate(new Date());
		invoice2.setTermsCondition(invoice.getInvoice().getTermsCondition());
		invoice2.setStatus(InvoiceStatus.PENDING.name());
		invoice2.setInvoiceNumber(getInvoiceNumber(userId));
		Invoice persistedInvoice = invoiceRepository.save(invoice2);
		for (Billing billId : invoice.getInstitutionalBillings()) {
			Billing billing = billingRepository.findById(billId.getId());
			billing.setBillingDesc(billId.getBillingDesc());
			billing.setAmount(billId.getAmount());
			billing.setInvoice(persistedInvoice);
			billingRepository.save(billing);
		}
		InvoiceResponse invoiceResponse = new InvoiceResponse();
		invoiceResponse.setHttpCode(HttpStatus.OK.value());
		invoiceResponse.setSuccessMessage("Invoice created successfully");
		return invoiceResponse;
	}

	@PostMapping("/individual")
	public InvoiceResponse createIndividualInvoice(@RequestBody InvoiceResponse invoice) {
		Long userId = invoice.getInvoice().getUserId();
		Invoice invoice2 = new Invoice();
		List<String> roles = findUserRole(invoice.getInvoice().getUserId());
		if (!roles.contains(UserRole.ADMIN.name())) {
			userId = userRepository.findById(invoice.getInvoice().getUserId()).get(0).getClientId();
		}
		BeanUtils.copyProperties(invoice.getInvoice(), invoice2);
		invoice2.setInstitution(null);
		invoice2.setCreatedBy(userId);
		invoice2.setUpdatedBy(userId);
		invoice2.setUserId(userId);
		invoice2.setAmountRecieved(false);
		invoice2.setStatus(InvoiceStatus.PENDING.name());
		invoice2.setCreatedDate(new Date());
		invoice2.setInvoiceNumber(getInvoiceNumber(userId));
		invoice2.setTermsCondition(invoice.getInvoice().getTermsCondition());
		Invoice persistedInvoice = invoiceRepository.save(invoice2);
		for (IndividualBilling billId : invoice.getIndividualBillings()) {
			IndividualBilling billing = individualBillingRepository.findById(billId.getId());
			billing.setBillingDesc(billId.getBillingDesc());
			billing.setAmount(billId.getAmount());
			billing.setInvoice(persistedInvoice);
			individualBillingRepository.save(billing);
		}
		InvoiceResponse invoiceResponse = new InvoiceResponse();
		invoiceResponse.setHttpCode(HttpStatus.OK.value());
		invoiceResponse.setSuccessMessage("Invoice created successfully");
		return invoiceResponse;
	}

	@PutMapping
	public InvoiceResponse updateAmountRecieved(@RequestParam Long invoiceId) {
		Invoice persistedInvoice = invoiceRepository.findByIdAndStatus(invoiceId, InvoiceStatus.PENDING.name());
		persistedInvoice.setAmountRecieved(true);
		persistedInvoice.setStatus(InvoiceStatus.PAID.name());
		invoiceRepository.save(persistedInvoice);
		InvoiceResponse invoiceResponse = new InvoiceResponse();
		invoiceResponse.setHttpCode(HttpStatus.OK.value());
		invoiceResponse.setSuccessMessage("Invoice amount recieved successfully");
		return invoiceResponse;
	}

	@PutMapping("/update/amount")
	public InvoiceResponse updateAmount(@RequestParam Long invoiceId, @RequestParam Double amount) {
		Invoice persistedInvoice = invoiceRepository.findByIdAndStatus(invoiceId, InvoiceStatus.PENDING.name());
		persistedInvoice.setAmount(amount);
		invoiceRepository.save(persistedInvoice);
		InvoiceResponse invoiceResponse = new InvoiceResponse();
		invoiceResponse.setHttpCode(HttpStatus.OK.value());
		invoiceResponse.setSuccessMessage("Invoice amount updated successfully");
		return invoiceResponse;
	}

	@PutMapping("/status")
	public InvoiceResponse updateInvoiceStatus(@RequestParam Long invoiceId, @RequestParam Date date) {
		Invoice persistedInvoice = invoiceRepository.findByIdAndStatus(invoiceId, InvoiceStatus.PENDING.name());
		persistedInvoice.setStatus(InvoiceStatus.PAID.name());
		persistedInvoice.setPaymentDate(date);
		invoiceRepository.save(persistedInvoice);
		InvoiceResponse invoiceResponse = new InvoiceResponse();
		invoiceResponse.setHttpCode(HttpStatus.OK.value());
		invoiceResponse.setSuccessMessage("Invoice status updated successfully");
		return invoiceResponse;
	}

	@PutMapping("/institutional")
	public InvoiceResponse updateInstitutionalInvoice(@RequestBody InvoiceResponse invoiceResponse) {
		Long invoiceId = invoiceResponse.getInvoice().getId();
		Invoice persistedInvoice = invoiceRepository.findById(invoiceId);
		for (Billing billing : invoiceResponse.getInstitutionalBillings()) {
			Billing persisted = billingRepository.findById(billing.getId());
			persisted.setAmount(billing.getAmount());
			persisted.setBillingDesc(billing.getBillingDesc());
			persisted.setInvoice(persistedInvoice);
			billingRepository.save(persisted);
		}
		BeanUtils.copyProperties(invoiceResponse.getInvoice(), persistedInvoice);
		persistedInvoice.setAmount(invoiceResponse.getInvoice().getAmount());
		persistedInvoice.setTaxPercent(invoiceResponse.getInvoice().getTaxPercent());
		persistedInvoice.setTaxAmount(invoiceResponse.getInvoice().getTaxAmount());
		persistedInvoice.setStatus(InvoiceStatus.PENDING.name());
		persistedInvoice.setDescription(invoiceResponse.getInvoice().getDescription());
		persistedInvoice.setUpdatedBy(invoiceResponse.getInvoice().getUserId());
		persistedInvoice.setBillFrom(invoiceResponse.getInvoice().getBillFrom());
		persistedInvoice.setBillTo(invoiceResponse.getInvoice().getBillTo());
		persistedInvoice.setTermsCondition(invoiceResponse.getInvoice().getTermsCondition());
		if (invoiceResponse.getInvoice().getInvoiceNumber() != null) {
			persistedInvoice.setInvoiceNumber(invoiceResponse.getInvoice().getInvoiceNumber());
		}
		invoiceRepository.save(persistedInvoice);
		invoiceResponse.setHttpCode(HttpStatus.OK.value());
		invoiceResponse.setSuccessMessage("Invoice updated successfully");
		return invoiceResponse;
	}

	@PutMapping("/individual")
	public InvoiceResponse updateIndividualInvoice(@RequestBody InvoiceResponse invoiceResponse) {
		Long invoiceId = invoiceResponse.getInvoice().getId();
		Invoice persistedInvoice = invoiceRepository.findById(invoiceId);
		for (IndividualBilling billing : invoiceResponse.getIndividualBillings()) {
			IndividualBilling persisted = individualBillingRepository.findById(billing.getId());
			persisted.setAmount(billing.getAmount());
			persisted.setBillingDesc(billing.getBillingDesc());
			persisted.setInvoice(persistedInvoice);
			individualBillingRepository.save(persisted);
		}
		BeanUtils.copyProperties(invoiceResponse.getInvoice(), persistedInvoice);
		persistedInvoice.setAmount(invoiceResponse.getInvoice().getAmount());
		persistedInvoice.setTaxPercent(invoiceResponse.getInvoice().getTaxPercent());
		persistedInvoice.setTaxAmount(invoiceResponse.getInvoice().getTaxAmount());
		persistedInvoice.setDescription(invoiceResponse.getInvoice().getDescription());
		persistedInvoice.setUpdatedBy(invoiceResponse.getInvoice().getUserId());
		persistedInvoice.setBillFrom(invoiceResponse.getInvoice().getBillFrom());
		persistedInvoice.setBillTo(invoiceResponse.getInvoice().getBillTo());
		persistedInvoice.setStatus(InvoiceStatus.PENDING.name());
		persistedInvoice.setTermsCondition(invoiceResponse.getInvoice().getTermsCondition());
		persistedInvoice.setInstitution(null);
		if (invoiceResponse.getInvoice().getInvoiceNumber() != null) {
			persistedInvoice.setInvoiceNumber(invoiceResponse.getInvoice().getInvoiceNumber());
		}
		invoiceRepository.save(persistedInvoice);
		invoiceResponse.setHttpCode(HttpStatus.OK.value());
		invoiceResponse.setSuccessMessage("Invoice updated successfully");
		return invoiceResponse;
	}

	@PutMapping("/cancel")
	public InvoiceResponse cancelInstitutionalInvoice(@RequestParam Long invoiceId) {
		Invoice persistedInvoice = invoiceRepository.findByIdAndStatus(invoiceId, InvoiceStatus.PENDING.name());
		persistedInvoice.setAmountRecieved(false);
		persistedInvoice.setStatus(InvoiceStatus.CANCELLED.name());
		invoiceRepository.save(persistedInvoice);
		InvoiceResponse invoiceResponse = new InvoiceResponse();
		invoiceResponse.setHttpCode(HttpStatus.OK.value());
		invoiceResponse.setSuccessMessage("Invoice has been cancelled successfully");
		List<Billing> billings = billingRepository.findByInvoice(persistedInvoice.getId());
		for (Billing billing : billings) {
			billing.setInvoice(null);
			billingRepository.save(billing);
		}
		return invoiceResponse;
	}

	@PutMapping("/individual/cancel")
	public InvoiceResponse cancelIndidvidualInvoice(@RequestParam Long invoiceId) {
		Invoice persistedInvoice = invoiceRepository.findByIdAndStatus(invoiceId, InvoiceStatus.PENDING.name());
		persistedInvoice.setAmountRecieved(false);
		persistedInvoice.setStatus(InvoiceStatus.CANCELLED.name());
		invoiceRepository.save(persistedInvoice);
		InvoiceResponse invoiceResponse = new InvoiceResponse();
		invoiceResponse.setHttpCode(HttpStatus.OK.value());
		invoiceResponse.setSuccessMessage("Invoice has been cancelled successfully");
		List<IndividualBilling> billings = individualBillingRepository.findByInvoice(persistedInvoice.getId());
		for (IndividualBilling billing : billings) {
			billing.setInvoice(null);
			individualBillingRepository.save(billing);
		}
		return invoiceResponse;
	}
	

	@GetMapping("/amount")
	public List<DashboardReport> getInvoiceAmount(@RequestParam Long userId,
			@RequestParam String year) {
		return invoiceService.findInvoicesAmount(userId, year, null);
	}
	
	@GetMapping("/inst/amount")
	public List<DashboardReport> getInvoiceInstAmount(@RequestParam Long userId,
			@RequestParam String year) {
		return invoiceService.findInvoicesInstAmount(userId, year, null);
	}
	
	@GetMapping("/inst/amount/month")
	public List<DashboardReport> getInvoiceInstMonthAmount(@RequestParam Long userId,
			@RequestParam String year, @RequestParam String month) {
		return invoiceService.findInvoicesInstAmount(userId, year, null, month);
	}
	
	@GetMapping("/date/amount")
	public List<DashboardReport> getInvoiceAmountByDate(@RequestParam Long userId,
			@RequestParam String start, @RequestParam String end) {
		return invoiceService.findInvoicesAmount(userId, start, end);
	}
	
	@GetMapping("/date/inst/amount")
	public List<DashboardReport> getInvoiceInstAmountByDate(@RequestParam Long userId,
			@RequestParam String start, @RequestParam String end) {
		return invoiceService.findInvoicesInstAmount(userId, start, end);
	}
	
	@GetMapping("/date/inst/amount/month")
	public List<DashboardReport> getInvoiceInstMonthAmountByDate(@RequestParam Long userId,
			@RequestParam String start, @RequestParam String end,
			@RequestParam String month) {
		return invoiceService.findInvoicesInstAmount(userId, start, end, month);
	}
	
	@GetMapping("/selectedbranch/amount")
	public List<DashboardReport> getInvoicesAmountByBranch(@RequestParam Long userId,
			@RequestParam String branch,@RequestParam String start, @RequestParam String end) {
		return invoiceService.findInvoicesAmountByBranch(userId, start, end, branch);
	}
	
	@GetMapping("/selectedbranch/inst/amount")
	public List<DashboardReport> getInvoicesInstAmountByBranch(@RequestParam Long userId,
			@RequestParam String branch,@RequestParam String start, @RequestParam String end) {
		return invoiceService.findInvoicesBranchInstAmount(userId, start, end,branch);
	}
	
	@GetMapping("/selectedbranch/inst/month/amount")
	public List<DashboardReport> getInvoicesInstAmountByBranch(@RequestParam Long userId,
			@RequestParam String branch,@RequestParam String month,
			@RequestParam String start, @RequestParam String end) {
		return invoiceService.findInvoicesBranchInstAmount(userId, start, end, month,branch);
	}

}
