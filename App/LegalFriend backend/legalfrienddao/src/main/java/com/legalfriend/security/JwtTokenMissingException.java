package com.legalfriend.security;

import org.springframework.security.core.AuthenticationException;

/**
 * Thrown when token cannot be found in the request header
 * 
 * @author deepak.j
 */

public class JwtTokenMissingException extends AuthenticationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JwtTokenMissingException(String msg) {
		super(msg);
	}
}
