/**
 * 
 */
package com.vinodborole.portal.persistence.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 * @author vinodborole
 *
 */
@Entity
@Table(name = "portal_account")
public class PortalAccount {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String accountName;
	private String accountDesc;

	@OneToMany(mappedBy = "products", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<PortalAccountProductConfig> accountProductsConfig = new ArrayList<PortalAccountProductConfig>();

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the accountName
	 */
	public String getAccountName() {
		return accountName;
	}

	/**
	 * @param accountName
	 *            the accountName to set
	 */
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	/**
	 * @return the accountDesc
	 */
	public String getAccountDesc() {
		return accountDesc;
	}

	/**
	 * @param accountDesc
	 *            the accountDesc to set
	 */
	public void setAccountDesc(String accountDesc) {
		this.accountDesc = accountDesc;
	}

	/**
	 * @return the accountProductsConfig
	 */
	public List<PortalAccountProductConfig> getAccountProductsConfig() {
		return accountProductsConfig;
	}

	/**
	 * @param accountProductsConfig
	 *            the accountProductsConfig to set
	 */
	public void setAccountProductsConfig(List<PortalAccountProductConfig> accountProductsConfig) {
		this.accountProductsConfig = accountProductsConfig;
	}

}
