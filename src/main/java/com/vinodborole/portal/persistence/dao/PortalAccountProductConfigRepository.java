/**
 * 
 */
package com.vinodborole.portal.persistence.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vinodborole.portal.persistence.model.PortalAccount;
import com.vinodborole.portal.persistence.model.PortalAccountProductConfig;
import com.vinodborole.portal.persistence.model.PortalProduct;

/**
 * @author vinodborole
 *
 */
public interface PortalAccountProductConfigRepository extends JpaRepository<PortalAccountProductConfig, Long> {

	List<PortalAccountProductConfig> findByAccounts(PortalAccount account);

	PortalAccountProductConfig findByProducts(PortalProduct product);

}
