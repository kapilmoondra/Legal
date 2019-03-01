package com.legalfriend.entities;

public class SystemDashTiles {
	
	private Long totalCases;
	private Long trialUsers;
	private Long oneMonthInActiveUsers;
	private Long totalOrganization;
	private Long totalUsers;
	private Long institutionalCases;
	private Long againstInstitutionalCases;
		
	public Long getAgainstInstitutionalCases() {
		return againstInstitutionalCases;
	}
	public void setAgainstInstitutionalCases(Long againstInstitutionalCases) {
		this.againstInstitutionalCases = againstInstitutionalCases;
	}
	public Long getInstitutionalCases() {
		return institutionalCases;
	}
	public void setInstitutionalCases(Long institutionalCases) {
		this.institutionalCases = institutionalCases;
	}
	public Long getTrialUsers() {
		return trialUsers;
	}
	public void setTrialUsers(Long trialUsers) {
		this.trialUsers = trialUsers;
	}
	public Long getOneMonthInActiveUsers() {
		return oneMonthInActiveUsers;
	}
	public void setOneMonthInActiveUsers(Long oneMonthInActiveUsers) {
		this.oneMonthInActiveUsers = oneMonthInActiveUsers;
	}
	public Long getTotalOrganization() {
		return totalOrganization;
	}
	public void setTotalOrganization(Long totalOrganization) {
		this.totalOrganization = totalOrganization;
	}
	public Long getTotalCases() {
		return totalCases;
	}
	public void setTotalCases(Long totalCases) {
		this.totalCases = totalCases;
	}
	public Long getTotalUsers() {
		return totalUsers;
	}
	public void setTotalUsers(Long totalUsers) {
		this.totalUsers = totalUsers;
	}
	
}
