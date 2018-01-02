/**
 * 
 */
package com.vinodborole.portal.web.error;

/**
 * @author vinodborole
 *
 */
public final class UserAlreadyExistException extends RuntimeException {
	/**
	 *  
	 */
	private static final long serialVersionUID = -4636733291621398446L;

	public UserAlreadyExistException() {
        super();
    }

    public UserAlreadyExistException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public UserAlreadyExistException(final String message) {
        super(message);
    }

    public UserAlreadyExistException(final Throwable cause) {
        super(cause);
    }
}
