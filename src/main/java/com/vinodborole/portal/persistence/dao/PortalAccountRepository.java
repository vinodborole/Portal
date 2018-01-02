/**
 * 
 */
package com.vinodborole.portal.persistence.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vinodborole.portal.persistence.model.PortalAccount;

/**
 * @author vinodborole
 *
 */
public interface PortalAccountRepository extends JpaRepository<PortalAccount, Long> {

	PortalAccount findByAccountName(String accountName);
}
