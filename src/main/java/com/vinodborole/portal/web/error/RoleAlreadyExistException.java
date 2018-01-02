/**
 * 
 */
package com.vinodborole.portal.web.error;

/**
 * @author vinodborole
 *
 */
public class RoleAlreadyExistException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3223019491169042346L;

	public RoleAlreadyExistException() {
		super();
	}

	public RoleAlreadyExistException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public RoleAlreadyExistException(final String message) {
		super(message);
	}

	public RoleAlreadyExistException(final Throwable cause) {
		super(cause);
	}
}
