/**
 * 
 */
package com.vinodborole.portal.web.error;

/**
 * @author vinodborole
 *
 */
public final class AccountNotFoundException extends RuntimeException {

	/** 
	 * 
	 */
	private static final long serialVersionUID = -2662711612681501185L;

	public AccountNotFoundException() {
		super();
	}

	public AccountNotFoundException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public AccountNotFoundException(final String message) {
		super(message);
	}

	public AccountNotFoundException(final Throwable cause) {
		super(cause);
	}
}
