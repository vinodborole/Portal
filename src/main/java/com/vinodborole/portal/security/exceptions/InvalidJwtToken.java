package com.vinodborole.portal.security.exceptions;

/**
 * JwtTokenNotValid
 * 
 * @author vinod borole
 *
 * 
 */
public class InvalidJwtToken extends RuntimeException {
	private static final long serialVersionUID = -294671188037098603L;

	public InvalidJwtToken() {
		super();
	}

	public InvalidJwtToken(final String message, final Throwable cause) {
		super(message, cause);
	}

	public InvalidJwtToken(final String message) {
		super(message);
	}

	public InvalidJwtToken(final Throwable cause) {
		super(cause);
	}
}
