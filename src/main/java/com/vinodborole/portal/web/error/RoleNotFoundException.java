/**
 * 
 */
package com.vinodborole.portal.web.error;

/**
 * @author vinodborole
 *
 */
public class RoleNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 686172651589882928L;

	public RoleNotFoundException() {
		super();
	}

	public RoleNotFoundException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public RoleNotFoundException(final String message) {
		super(message);
	}

	public RoleNotFoundException(final Throwable cause) {
		super(cause);
	}
}
