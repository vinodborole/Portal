/**
 * 
 */
package com.vinodborole.portal.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vinodborole.portal.persistence.dao.PortalPrivilegeRepository;
import com.vinodborole.portal.persistence.dao.PortalRoleRepository;
import com.vinodborole.portal.persistence.model.PortalPrivilege;
import com.vinodborole.portal.persistence.model.PortalRole;
import com.vinodborole.portal.web.dto.PortalRoleDto;
import com.vinodborole.portal.web.error.PrivilegeNotFoundException;
import com.vinodborole.portal.web.error.RoleAlreadyExistException;
import com.vinodborole.portal.web.error.RoleNotFoundException;

/**
 * @author vinodborole
 *
 */
@Service
@Transactional
public class PortalRolePrivilegeManagementService implements IPortalRolePrivilegeManagementService {

	@Autowired
	private PortalRoleRepository roleRepository;

	@Autowired
	private PortalPrivilegeRepository privilegeRepository;

	@Override
	public PortalRole addRole(PortalRoleDto roleDto) throws RoleAlreadyExistException {
		PortalRole checkrole = roleRepository.findByName(roleDto.getName());
		if (checkrole != null)
			throw new RoleAlreadyExistException("Role already exist");

		PortalRole role = new PortalRole();
		role.setName(roleDto.getName());
		return roleRepository.save(role);
	}

	@Override
	public PortalRole getRoleByName(String roleName) throws RoleNotFoundException {
		PortalRole role = roleRepository.findByName(roleName);
		if (role == null)
			throw new RoleNotFoundException("Role not found");
		return role;
	}

	@Override
	public PortalRole getRole(long id) throws RoleNotFoundException {
		PortalRole role = roleRepository.getOne(id);
		if (role == null)
			throw new RoleNotFoundException("Role not found");
		return role;
	}

	@Override
	public void deleteRole(long id) throws RoleNotFoundException {
		PortalRole role = roleRepository.getOne(id);
		if (role == null)
			throw new RoleNotFoundException("Role not found");
		roleRepository.delete(role);
	}

	@Override
	public PortalRole assignPrivilegeToRole(PortalRoleDto roleDto, long roleId)
			throws RoleNotFoundException, PrivilegeNotFoundException {
		PortalRole role = roleRepository.getOne(roleId);
		if (role == null)
			throw new RoleNotFoundException("Role not found");
		List<PortalPrivilege> privileges = privilegeExist(roleDto.getPrivileges());
		role.setPrivileges(privileges);
		return roleRepository.save(role);
	}

	private List<PortalPrivilege> privilegeExist(List<String> privileges) {
		List<PortalPrivilege> portalPrivileges = new ArrayList<PortalPrivilege>();
		for (String privilege : privileges) {
			PortalPrivilege portalPrivilege = privilegeRepository.findByName(privilege);
			if (portalPrivilege == null)
				throw new PrivilegeNotFoundException("Privilege not found");
			portalPrivileges.add(portalPrivilege);
		}
		return portalPrivileges;
	}

	@Override
	public List<PortalPrivilege> getAllPriviliges() {
		return privilegeRepository.findAll();
	}

	@Override
	public PortalPrivilege getPrivilige(long id) throws PrivilegeNotFoundException {
		return privilegeRepository.getOne(id);
	}

	@Override
	public PortalPrivilege getPriviligeByName(String name) throws PrivilegeNotFoundException {
		return privilegeRepository.findByName(name);
	}

	@Override
	public List<PortalRole> getAllRoles() {
		return roleRepository.findAll();
	}

}
