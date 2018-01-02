/**
 * 
 */
package com.vinodborole.portal.persistence.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vinodborole.portal.persistence.model.PortalProduct;

/**
 * @author vinodborole
 *
 */
public interface PortalProductRepository extends JpaRepository<PortalProduct, Long> {

	PortalProduct findByProductName(String productName);
}
