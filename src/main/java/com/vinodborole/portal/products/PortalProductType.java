/**
 * 
 */
package com.vinodborole.portal.products;

import com.vinodborole.portal.web.error.ProductTypeNotFoundException;

/**
 * @author vinodborole
 *
 */
public enum PortalProductType {

	PRODUCT1, PRODUCT2;

	public static PortalProductType fromString(String value) throws ProductTypeNotFoundException {
		try {
			return PortalProductType.valueOf(value);
		} catch (Exception e) {
			throw new ProductTypeNotFoundException("Product Type does not exist");
		}
	}
}
