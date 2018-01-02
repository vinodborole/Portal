/**
 * 
 */
package com.vinodborole.portal.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vinodborole.portal.persistence.dao.PortalProductRepository;
import com.vinodborole.portal.persistence.model.PortalProduct;
import com.vinodborole.portal.products.PortalProductType;
import com.vinodborole.portal.web.dto.PortalProductDto;
import com.vinodborole.portal.web.error.ProductNotFoundException;
import com.vinodborole.portal.web.error.ProductTypeNotFoundException;

/**
 * @author vinodborole
 *
 */
@Service
@Transactional
public class PortalProductService implements IPortalProductService {

	@Autowired
	private PortalProductRepository portalProductRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.vinodborole.portal.service.IPortalProductService#addNewProduct(com.vinodborole.portal.web.dto.
	 * PortalProductDto)
	 */
	@Override
	public PortalProduct addNewProduct(PortalProductDto portalProductDto) throws ProductTypeNotFoundException {
		PortalProduct portalProduct = new PortalProduct();
		portalProduct.setProductName(portalProductDto.getProductName());
		portalProduct.setProductDesc(portalProductDto.getProductDesc());
		portalProduct.setProductType(PortalProductType.fromString(portalProductDto.getProductType()));
		return portalProductRepository.save(portalProduct);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vinodborole.portal.service.IPortalProductService#getAllProducts()
	 */
	@Override
	public List<PortalProduct> getAllProducts() {
		return portalProductRepository.findAll();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vinodborole.portal.service.IPortalProductService#getProduct(java.lang.Long)
	 */
	@Override
	public PortalProduct getProduct(Long productId) throws ProductNotFoundException {
		PortalProduct portalProduct = portalProductRepository.findOne(productId);
		if (portalProduct == null) {
			throw new ProductNotFoundException("Product with the given Id does not exist");
		}
		return portalProduct;
	}

	@Override
	public PortalProduct updateProduct(PortalProduct portalProduct) throws ProductNotFoundException {
		PortalProduct checkProduct = portalProductRepository.findOne(portalProduct.getId());
		if (checkProduct == null)
			throw new ProductNotFoundException("Product does not exist");
		return portalProductRepository.save(portalProduct);
	}

	@Override
	public void deleteProduct(PortalProduct portalProduct) throws ProductNotFoundException {
		PortalProduct checkProduct = portalProductRepository.findOne(portalProduct.getId());
		if (checkProduct == null)
			throw new ProductNotFoundException("Product does not exist");
		portalProductRepository.delete(portalProduct);
	}

	@Override
	public PortalProduct getProductByName(String productName) throws ProductNotFoundException {
		PortalProduct product = portalProductRepository.findByProductName(productName);
		if (product == null)
			throw new ProductNotFoundException("Product does not exist");
		return product;
	}

}
