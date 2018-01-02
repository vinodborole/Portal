package com.vinodborole.portal.persistence.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vinodborole.portal.persistence.model.PortalRole;

public interface PortalRoleRepository extends JpaRepository<PortalRole, Long> {

	PortalRole findByName(String name);

    @Override
    void delete(PortalRole role);

}
