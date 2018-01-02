/**
 * 
 */
package com.vinodborole.portal.products.product1;

import java.util.HashMap;
import java.util.Map;

import com.vinodborole.portal.products.IPortalProductSession;
import com.vinodborole.portal.products.PortalProductType;

/**
 * @author vinodborole
 *
 */
public class Product1ProductSession implements IPortalProductSession {

	private String token;
	private Map<String, String> productConfig = new HashMap<String, String>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vinodborole.portal.products.IProductSession#getProductType()
	 */
	@Override
	public PortalProductType getProductType() {
		return PortalProductType.PRODUCT1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vinodborole.portal.products.IProductSession#getProductToken()
	 */
	@Override
	public String getProductToken() {
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
	 * @return the productConfig
	 */
	public Map<String, String> getProductConfig() {
		return productConfig;
	}

	/**
	 * @param productConfig
	 *            the productConfig to set
	 */
	public void setProductConfig(Map<String, String> productConfig) {
		this.productConfig = productConfig;
	}

}
