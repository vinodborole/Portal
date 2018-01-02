/**
 * 
 */
package com.vinodborole.portal.web.error;

/**
 * @author vinodborole
 *
 */
public final class AccountAlreadyExistException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2494476765210528781L;

	public AccountAlreadyExistException() {
		super();
	}

	public AccountAlreadyExistException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public AccountAlreadyExistException(final String message) {
		super(message);
	}

	public AccountAlreadyExistException(final Throwable cause) {
		super(cause);
	}
}
