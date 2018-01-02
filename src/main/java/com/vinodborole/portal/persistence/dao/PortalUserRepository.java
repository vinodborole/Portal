/**
 * 
 */
package com.vinodborole.portal.persistence.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vinodborole.portal.persistence.model.PortalUser;

/**
 * @author vinodborole
 *
 */ 
public interface PortalUserRepository extends JpaRepository<PortalUser, Long> {
    PortalUser findByUsername(String username);
    @Override
    void delete(PortalUser user);
}
