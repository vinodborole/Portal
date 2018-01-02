/**
 * 
 */
package com.vinodborole.portal.service;

import java.util.List;

import com.vinodborole.portal.persistence.model.PortalUser;
import com.vinodborole.portal.web.dto.PortalUserDto;
import com.vinodborole.portal.web.error.AccountNotFoundException;
import com.vinodborole.portal.web.error.RoleNotFoundException;
import com.vinodborole.portal.web.error.UserNotFoundException;

/**
 * @author vinodborole
 *
 */
public interface IPortalUserManagementService {

	public PortalUser manageUserRole(PortalUserDto cesUser, long userId) throws RoleNotFoundException, UserNotFoundException;

	public PortalUser manageUserAccount(PortalUserDto cesUser, long userId)
			throws AccountNotFoundException, UserNotFoundException;

	public List<PortalUser> getAllUsers();
}
