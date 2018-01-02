/**
 * 
 */
package com.vinodborole.portal.security.auth;

import java.util.HashMap;
import java.util.Map;

import com.vinodborole.portal.persistence.model.token.PortalUserContext;
import com.vinodborole.portal.products.PortalProductType;
import com.vinodborole.portal.products.IPortalProductSession;

/**
 * @author vinodborole
 *
 */
public class PortalSession {

	private PortalUserContext userContext;
	private String token;
	private String refreshToken;
	private Map<PortalProductType, IPortalProductSession> productSession = new HashMap<PortalProductType, IPortalProductSession>();

	/**
	 * @return the userContext
	 */
	public PortalUserContext getUserContext() {
		return userContext;
	}

	/**
	 * @param userContext
	 *            the userContext to set
	 */
	public void setUserContext(PortalUserContext userContext) {
		this.userContext = userContext;
	}

	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token
	 *            the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * @return the refreshToken
	 */
	public String getRefreshToken() {
		return refreshToken;
	}

	/**
	 * @param refreshToken
	 *            the refreshToken to set
	 */
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	/**
	 * @return the productSession
	 */
	public Map<PortalProductType, IPortalProductSession> getProductSession() {
		return productSession;
	}

	/**
	 * @param productSession
	 *            the productSession to set
	 */
	public void setProductSession(Map<PortalProductType, IPortalProductSession> productSession) {
		this.productSession = productSession;
	}

}
