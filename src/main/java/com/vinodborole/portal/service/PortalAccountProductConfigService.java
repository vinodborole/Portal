/**
 * 
 */
package com.vinodborole.portal.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vinodborole.portal.persistence.dao.PortalAccountProductConfigRepository;
import com.vinodborole.portal.persistence.dao.PortalAccountRepository;
import com.vinodborole.portal.persistence.dao.PortalProductRepository;
import com.vinodborole.portal.persistence.model.PortalAccount;
import com.vinodborole.portal.persistence.model.PortalAccountProductConfig;
import com.vinodborole.portal.persistence.model.PortalProduct;
import com.vinodborole.portal.products.PortalProductType;
import com.vinodborole.portal.web.dto.PortalAccountProductConfigDto;
import com.vinodborole.portal.web.error.AccountNotFoundException;
import com.vinodborole.portal.web.error.ProductNotFoundException;

/**
 * @author vinodborole
 *
 */
@Service
@Transactional
public class PortalAccountProductConfigService implements IPortalAccountProductConfigService {

	@Autowired
	private PortalAccountRepository accountRepository;
	@Autowired
	private PortalProductRepository productRepository;

	@Autowired
	private PortalAccountProductConfigRepository accountProductConfigRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.vinodborole.portal.service.IPortalAccountProductConfigService#saveProductConfig()
	 */
	@Override
	public PortalAccountProductConfig saveProductConfig(PortalAccountProductConfigDto accountProductConfig, long productId)
			throws AccountNotFoundException, ProductNotFoundException {
		PortalAccount account = accountRepository.findByAccountName(accountProductConfig.getPortalAccount().getAccoutName());
		if (account == null)
			throw new AccountNotFoundException("Account does not exist");
		PortalProduct product = productRepository.getOne(productId);
		if (product == null)
			throw new ProductNotFoundException("Product does not exist");

		try {
			PortalAccountProductConfig productConfig = new PortalAccountProductConfig();
			productConfig.setAccounts(account);
			productConfig.setProducts(product);
			productConfig.setConfig(accountProductConfig.getConfig());
			return accountProductConfigRepository.save(productConfig);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("Product Config counld not be saved " + e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.vinodborole.portal.service.IPortalAccountProductConfigService#updateProductConfig()
	 */
	@Override
	public PortalAccountProductConfig updateProductConfig(PortalAccountProductConfigDto accountProductConfig, long productId)
			throws AccountNotFoundException, ProductNotFoundException {
		PortalAccount account = accountRepository.findByAccountName(accountProductConfig.getPortalAccount().getAccoutName());
		if (account == null)
			throw new AccountNotFoundException("Account does not exist");
		PortalProduct product = productRepository.getOne(productId);
		if (product == null)
			throw new ProductNotFoundException("Product does not exist");

		List<PortalAccountProductConfig> productConfigs = accountProductConfigRepository.findByAccounts(account);

		for (PortalAccountProductConfig productConfig : productConfigs) {
			if (PortalProductType.fromString(accountProductConfig.getPortalProduct().getProductType())
					.equals(productConfig.getProducts().getProductType())) {
				try {
					productConfig.setConfig(accountProductConfig.getConfig());
					return accountProductConfigRepository.save(productConfig);
				} catch (JsonProcessingException e) {
					throw new RuntimeException("Product Config counld not be updated " + e.getMessage());
				}
			}
		}
		return null;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vinodborole.portal.service.IPortalAccountProductConfigService#
	 * getProductConfigByAccountId(java.lang.Long)
	 */
	@Override
	public List<PortalAccountProductConfig> getProductConfigByAccountId(Long accountId) throws AccountNotFoundException {
		PortalAccount account = accountRepository.getOne(accountId);
		if (account == null)
			throw new AccountNotFoundException("Account does not exist");
		return accountProductConfigRepository.findByAccounts(account);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vinodborole.portal.service.IPortalAccountProductConfigService#
	 * getProductConfigByProductId(java.lang.Long)
	 */
	@Override
	public PortalAccountProductConfig getProductConfigByProductId(Long productId) throws ProductNotFoundException {
		PortalProduct product = productRepository.getOne(productId);
		if (product == null)
			throw new ProductNotFoundException("Product does not exist");
		return accountProductConfigRepository.findByProducts(product);
	}

}
