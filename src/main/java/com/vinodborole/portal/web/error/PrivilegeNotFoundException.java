/**
 * 
 */
package com.vinodborole.portal.web.error;

/**
 * @author vinodborole
 *
 */
public class PrivilegeNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2601517474257346781L;

	public PrivilegeNotFoundException() {
		super();
	}

	public PrivilegeNotFoundException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public PrivilegeNotFoundException(final String message) {
		super(message);
	}

	public PrivilegeNotFoundException(final Throwable cause) {
		super(cause);
	}
}
