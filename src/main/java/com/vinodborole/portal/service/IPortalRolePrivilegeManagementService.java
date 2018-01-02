/**
 * 
 */
package com.vinodborole.portal.service;

import java.util.List;

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
public interface IPortalRolePrivilegeManagementService {

	public PortalRole addRole(PortalRoleDto role) throws RoleAlreadyExistException;

	public PortalRole getRoleByName(String roleName) throws RoleNotFoundException;

	public PortalRole getRole(long id) throws RoleNotFoundException;

	public void deleteRole(long id) throws RoleNotFoundException;

	public List<PortalRole> getAllRoles();

	public PortalRole assignPrivilegeToRole(PortalRoleDto role, long roleId)
			throws RoleNotFoundException, PrivilegeNotFoundException;

	public List<PortalPrivilege> getAllPriviliges();

	public PortalPrivilege getPrivilige(long id) throws PrivilegeNotFoundException;

	public PortalPrivilege getPriviligeByName(String name) throws PrivilegeNotFoundException;
}
