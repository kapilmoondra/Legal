package com.legalfriend.model;

/**
 * Holder for JWT token from the request.
 * <p/>
 * Other fields aren't used but necessary to comply to the contracts of
 * AbstractUserDetailsAuthenticationProvider
 *
 * @author deepak.j
 */
public class JwtAuthenticationToken {

	private String token;
	private String clientId;

	public JwtAuthenticationToken() {

	}

	public JwtAuthenticationToken(String token) {

		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

}
