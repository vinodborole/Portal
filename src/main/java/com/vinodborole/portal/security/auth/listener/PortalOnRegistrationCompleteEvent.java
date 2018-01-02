/**
 * 
 */
package com.vinodborole.portal.security.auth.listener;

import org.springframework.context.ApplicationEvent;

import com.vinodborole.portal.persistence.model.PortalUser;

/**
 * @author vinodborole
 *
 */
public class PortalOnRegistrationCompleteEvent extends ApplicationEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3275858008916524314L;
	private final String appUrl;
	private PortalUser user;

	public PortalOnRegistrationCompleteEvent(final PortalUser user, final String appUrl) {
		super(user);
		this.user = user;
		this.appUrl = appUrl;
	}

	/**
	 * @return the appUrl
	 */
	public String getAppUrl() {
		return appUrl;
	}

	/**
	 * @return the user
	 */
	public PortalUser getUser() {
		return user;
	}
	

}
