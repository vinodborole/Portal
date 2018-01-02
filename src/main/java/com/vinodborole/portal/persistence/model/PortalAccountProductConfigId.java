/**
 * 
 */
package com.vinodborole.portal.persistence.model;

import java.io.Serializable;

/**
 * @author vinodborole
 *
 */
public class PortalAccountProductConfigId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 43577177999231931L;
	private long accounts;
	private long products;

	/**
	 * @return the accounts
	 */
	public long getAccounts() {
		return accounts;
	}

	/**
	 * @param accounts
	 *            the accounts to set
	 */
	public void setAccounts(long accounts) {
		this.accounts = accounts;
	}

	/**
	 * @return the products
	 */
	public long getProducts() {
		return products;
	}

	/**
	 * @param products
	 *            the products to set
	 */
	public void setProducts(long products) {
		this.products = products;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (accounts ^ (accounts >>> 32));
		result = prime * result + (int) (products ^ (products >>> 32));
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PortalAccountProductConfigId other = (PortalAccountProductConfigId) obj;
		if (accounts != other.accounts)
			return false;
		if (products != other.products)
			return false;
		return true;
	}

}
