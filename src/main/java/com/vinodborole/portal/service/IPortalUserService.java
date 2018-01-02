/**
 * 
 */
package com.vinodborole.portal.service;

import com.vinodborole.portal.persistence.model.PortalUser;
import com.vinodborole.portal.web.dto.PortalUserDto;
import com.vinodborole.portal.web.error.AccountNotFoundException;
import com.vinodborole.portal.web.error.UserAlreadyExistException;

/**
 * @author vinodborole
 *
 */
public interface IPortalUserService {

	PortalUser registerNewAccount(PortalUserDto cesUser) throws UserAlreadyExistException, AccountNotFoundException;

	void deleteUser(PortalUser user);

	void changeUserPassword(final PortalUser user, final String password);

	boolean checkIfValidOldPassword(final PortalUser user, final String oldPassword);

}
