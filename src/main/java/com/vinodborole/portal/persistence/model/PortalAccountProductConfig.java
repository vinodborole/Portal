/**
 * 
 */
package com.vinodborole.portal.persistence.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * @author vinodborole
 *
 */
@Entity
@Table(name = "PORTAL_ACCOUNT_PRODUCT")
@IdClass(PortalAccountProductConfigId.class)
public class PortalAccountProductConfig {

	@Id
	@ManyToOne
	@JoinColumn(name = "account_id", referencedColumnName = "id")
	@JsonBackReference
	private PortalAccount accounts;

	@Id
	@ManyToOne
	@JoinColumn(name = "product_id", referencedColumnName = "id")
	@JsonBackReference
	private PortalProduct products;

	private String config;

	/**
	 * @return the accounts
	 */
	public PortalAccount getAccounts() {
		return accounts;
	}

	/**
	 * @param accounts
	 *            the accounts to set
	 */
	public void setAccounts(PortalAccount accounts) {
		this.accounts = accounts;
	}

	/**
	 * @return the products
	 */
	public PortalProduct getProducts() {
		return products;
	}

	/**
	 * @param products
	 *            the products to set
	 */
	public void setProducts(PortalProduct products) {
		this.products = products;
	}

	/**
	 * @return the config
	 */
	public String getConfig() {
		return config;
	}

	/**
	 * @param config
	 *            the config to set
	 */
	public void setConfig(String config) {
		this.config = config;
	}

}
