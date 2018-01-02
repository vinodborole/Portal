/**
 * 
 */
package com.vinodborole.portal.web.error;

/**
 * @author vinodborole
 *
 */
public class ProductTypeNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1369627004800726156L;

	public ProductTypeNotFoundException() {
		super();
	}

	public ProductTypeNotFoundException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public ProductTypeNotFoundException(final String message) {
		super(message);
	}

	public ProductTypeNotFoundException(final Throwable cause) {
		super(cause);
	}
}
