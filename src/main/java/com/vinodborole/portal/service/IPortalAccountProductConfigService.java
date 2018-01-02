/**
 * 
 */
package com.vinodborole.portal.service;

import java.util.List;

import com.vinodborole.portal.persistence.model.PortalAccountProductConfig;
import com.vinodborole.portal.web.dto.PortalAccountProductConfigDto;
import com.vinodborole.portal.web.error.AccountNotFoundException;
import com.vinodborole.portal.web.error.ProductNotFoundException;

/**
 * @author vinodborole
 *
 */
public interface IPortalAccountProductConfigService {

	PortalAccountProductConfig saveProductConfig(PortalAccountProductConfigDto accountProductConfig, long productId)
			throws AccountNotFoundException, ProductNotFoundException;

	PortalAccountProductConfig updateProductConfig(PortalAccountProductConfigDto accountProductConfig, long productId)
			throws AccountNotFoundException, ProductNotFoundException;

	List<PortalAccountProductConfig> getProductConfigByAccountId(Long accountId) throws AccountNotFoundException;

	PortalAccountProductConfig getProductConfigByProductId(Long productId) throws ProductNotFoundException;

}
