package com.legalfriend.response;

import java.util.Set;

import com.legalfriend.entities.District;

public class DistrictResponse extends LegalFriendResponse {

	private Set<District> districts;
	
	private Long districtId;

	public Set<District> getDistricts() {
		return districts;
	}

	public void setDistricts(Set<District> districts) {
		this.districts = districts;
	}

	public void setDistrictId(Long districtId) {
		this.districtId = districtId;
	}

	public Long getDistrictId() {
		return districtId;
	}

}
