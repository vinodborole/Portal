package com.vinodborole.portal.persistence.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vinodborole.portal.persistence.model.PortalPrivilege;

public interface PortalPrivilegeRepository extends JpaRepository<PortalPrivilege, Long> {

	PortalPrivilege findByName(String name);
 
    @Override
    void delete(PortalPrivilege privilege);

}
