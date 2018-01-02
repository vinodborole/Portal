/**
 * 
 */
package com.vinodborole.portal.web.error;

/**
 * @author vinodborole
 *
 */
public final class TokenExpiredExcepetion extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7720419407269159311L;

	public TokenExpiredExcepetion() {
		super();
	}

	public TokenExpiredExcepetion(final String message, final Throwable cause) {
		super(message, cause);
	}

	public TokenExpiredExcepetion(final String message) {
		super(message);
	}

	public TokenExpiredExcepetion(final Throwable cause) {
		super(cause);
	}
}
