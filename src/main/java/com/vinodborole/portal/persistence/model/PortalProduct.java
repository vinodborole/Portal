/**
 * 
 */
package com.vinodborole.portal.persistence.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.vinodborole.portal.products.PortalProductType;

/**
 * @author vinodborole
 *
 */
@Entity
@Table(name = "portal_product")
public class PortalProduct {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String productName;
	private String productDesc;

	@Enumerated(EnumType.STRING)
	private PortalProductType productType;

	@OneToMany(mappedBy = "accounts", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<PortalAccountProductConfig> accountProductsConfig = new ArrayList<PortalAccountProductConfig>();

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductDesc() {
		return productDesc;
	}

	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
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

	/**
	 * @return the productType
	 */
	public PortalProductType getProductType() {
		return productType;
	}

	/**
	 * @param productType
	 *            the productType to set
	 */
	public void setProductType(PortalProductType productType) {
		this.productType = productType;
	}

}
