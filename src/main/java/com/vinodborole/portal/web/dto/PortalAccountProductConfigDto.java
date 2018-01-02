/**
 * 
 */
package com.vinodborole.portal.web.dto;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author vinodborole
 *
 */
public class PortalAccountProductConfigDto {

	private PortalAccountDto portalAccount;
	private PortalProductDto portalProduct;
	private Map<String, String> config = new HashMap<String, String>();

	/**
	 * @return the portalAccount
	 */
	public PortalAccountDto getPortalAccount() {
		return portalAccount;
	}

	/**
	 * @param portalAccount
	 *            the portalAccount to set
	 */
	public void setPortalAccount(PortalAccountDto portalAccount) {
		this.portalAccount = portalAccount;
	}

	/**
	 * @return the portalProduct
	 */
	public PortalProductDto getPortalProduct() {
		return portalProduct;
	}

	/**
	 * @param portalProduct
	 *            the portalProduct to set
	 */
	public void setPortalProduct(PortalProductDto portalProduct) {
		this.portalProduct = portalProduct;
	}

	/**
	 * @return the config
	 * @throws JsonProcessingException
	 */
	public String getConfig() throws JsonProcessingException {
		return new ObjectMapper().writeValueAsString(config);
	}

	/**
	 * @param config
	 *            the config to set
	 */
	public void setConfig(Map<String, String> config) {
		this.config = config;
	}

}
