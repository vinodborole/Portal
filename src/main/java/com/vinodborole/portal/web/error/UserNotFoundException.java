/**
 * 
 */
package com.vinodborole.portal.web.error;

/**
 * @author vinodborole
 *
 */
public class UserNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8635907190099232939L;

	public UserNotFoundException() {
		super();
	}

	public UserNotFoundException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public UserNotFoundException(final String message) {
		super(message);
	}

	public UserNotFoundException(final Throwable cause) {
		super(cause);
	}
}
