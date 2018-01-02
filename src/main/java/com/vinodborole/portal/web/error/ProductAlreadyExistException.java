/**
 * 
 */
package com.vinodborole.portal.web.error;

/**
 * @author vinodborole
 *
 */
public final class ProductAlreadyExistException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5669165790889114806L;

	public ProductAlreadyExistException() {
		super();
	}

	public ProductAlreadyExistException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public ProductAlreadyExistException(final String message) {
		super(message);
	}

	public ProductAlreadyExistException(final Throwable cause) {
		super(cause);
	}
}
