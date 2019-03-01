package com.legalfriend.entities;

public class DashboardCountDetail {

	private Long totalCustomers;
	private Long totalCases;
	private Long totalLogins;
	private Long totalBillings;
	private Double totalAmount;
	
	public Double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public Long getTotalCustomers() {
		return totalCustomers;
	}
	public void setTotalCustomers(Long totalCustomers) {
		this.totalCustomers = totalCustomers;
	}
	public Long getTotalCases() {
		return totalCases;
	}
	public void setTotalCases(Long totalCases) {
		this.totalCases = totalCases;
	}
	public Long getTotalLogins() {
		return totalLogins;
	}
	public void setTotalLogins(Long totalLogins) {
		this.totalLogins = totalLogins;
	}
	public Long getTotalBillings() {
		return totalBillings;
	}
	public void setTotalBillings(Long totalBillings) {
		this.totalBillings = totalBillings;
	}
	
	
	
}
