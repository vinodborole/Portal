/**
 * 
 */
package com.vinodborole.portal.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vinodborole.portal.persistence.dao.PortalAccountRepository;
import com.vinodborole.portal.persistence.dao.PortalRoleRepository;
import com.vinodborole.portal.persistence.dao.PortalUserRepository;
import com.vinodborole.portal.persistence.model.PortalAccount;
import com.vinodborole.portal.persistence.model.PortalRole;
import com.vinodborole.portal.persistence.model.PortalUser;
import com.vinodborole.portal.web.dto.PortalUserDto;
import com.vinodborole.portal.web.error.AccountNotFoundException;
import com.vinodborole.portal.web.error.RoleNotFoundException;
import com.vinodborole.portal.web.error.UserNotFoundException;

/**
 * @author vinodborole
 *
 */
@Service
@Transactional
public class PortalUserManagementService implements IPortalUserManagementService {

	@Autowired
	private PortalUserRepository userRepository;

	@Autowired
	private PortalAccountRepository accountRepository;

	@Autowired
	private PortalRoleRepository roleRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.vinodborole.portal.service.IPortalUserManagementService#manageUserRole(com.vinodborole.portal.
	 * persistence.model.PortalUser)
	 */
	@Override
	public PortalUser manageUserRole(PortalUserDto portalUser, long userId) throws RoleNotFoundException, UserNotFoundException {
		PortalUser user = userRepository.getOne(userId);
		if (user == null)
			throw new UserNotFoundException("User does not exist");
		List<String> roleNames = portalUser.getRoleNames();
		List<PortalRole> portalRoles = roleExists(roleNames);
		user.setRoles(portalRoles);
		return userRepository.save(user);
	}

	private List<PortalRole> roleExists(List<String> roleNames) {
		List<PortalRole> portalRoles = new ArrayList<PortalRole>();
		for (String name : roleNames) {
			PortalRole role = roleRepository.findByName(name);
			if (role == null)
				throw new RoleNotFoundException("Role does not exist");
			else
				portalRoles.add(role);
		}

		return portalRoles;
	}

	@Override
	public PortalUser manageUserAccount(PortalUserDto portalUser, long userId)
			throws AccountNotFoundException, UserNotFoundException {
		PortalUser user = userRepository.getOne(userId);
		if (user == null)
			throw new UserNotFoundException("User does not exist");
		PortalAccount account = accountRepository.findByAccountName(portalUser.getAccoutName());
		if (account == null)
			throw new AccountNotFoundException("Account does not exist");
		user.setAccount(account);
		return userRepository.save(user);
	}

	@Override
	public List<PortalUser> getAllUsers() {
		List<PortalUser> users = userRepository.findAll();
		List<PortalUser> securedUserList = new ArrayList<PortalUser>();
		for (PortalUser user : users) {
			user.setPassword(user.getPassword().replaceAll(user.getPassword(), "*******"));
			securedUserList.add(user);
		}
		return securedUserList;
	}

}
