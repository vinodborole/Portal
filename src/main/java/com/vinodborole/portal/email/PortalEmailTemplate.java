/**
 * 
 */
package com.vinodborole.portal.email;

/**
 * @author vinodborole
 *
 */
public enum PortalEmailTemplate {

	VERIFY_ACCOUNT("email/verifyAccount"), RESET_PASSWORD("email/resetPassword");

	private final String templatePath;

	private PortalEmailTemplate(String path) {
		templatePath = path;
	}

	public String toString() {
		return this.templatePath;
	}
}
