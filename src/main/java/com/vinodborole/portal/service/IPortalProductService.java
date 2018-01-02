/**
 * 
 */
package com.vinodborole.portal.service;

import java.util.List;

import com.vinodborole.portal.persistence.model.PortalProduct;
import com.vinodborole.portal.web.dto.PortalProductDto;
import com.vinodborole.portal.web.error.ProductNotFoundException;
import com.vinodborole.portal.web.error.ProductTypeNotFoundException;

/**
 * @author vinodborole
 *
 */
public interface IPortalProductService {

	PortalProduct addNewProduct(PortalProductDto portalProductDto) throws ProductTypeNotFoundException;

	List<PortalProduct> getAllProducts();

	PortalProduct getProduct(Long productId) throws ProductNotFoundException;

	PortalProduct getProductByName(String productName) throws ProductNotFoundException;

	PortalProduct updateProduct(PortalProduct portalProduct) throws ProductNotFoundException;

	void deleteProduct(PortalProduct portalProduct) throws ProductNotFoundException;
}
