/**
 * 
 */
package com.vinodborole.portal.service;

import java.util.Arrays;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.vinodborole.portal.persistence.dao.PortalAccountRepository;
import com.vinodborole.portal.persistence.dao.PortalRoleRepository;
import com.vinodborole.portal.persistence.dao.PortalUserRepository;
import com.vinodborole.portal.persistence.model.PortalAccount;
import com.vinodborole.portal.persistence.model.PortalRole;
import com.vinodborole.portal.persistence.model.PortalUser;
import com.vinodborole.portal.web.dto.PortalUserDto;
import com.vinodborole.portal.web.error.AccountNotFoundException;
import com.vinodborole.portal.web.error.UserAlreadyExistException;

/**
 * @author vinodborole
 *
 */
@Service
@Transactional
public class PortalUserService implements IPortalUserService {

	@Autowired
	private PortalUserRepository userRepository;

	@Autowired
	private PortalRoleRepository roleRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private PortalAccountRepository accountRepositry;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.vinodborole.portal.service.IPortalUserService#registerNewAccount(com.vinodborole.portal.web.
	 * dto.PortalUserDto)
	 */
	@Override
	public PortalUser registerNewAccount(PortalUserDto portalUserDto)
			throws UserAlreadyExistException, AccountNotFoundException {
		if (userExists(portalUserDto.getUsername())) {
			throw new UserAlreadyExistException("There is an account with that username: " + portalUserDto.getUsername());
		}
		PortalAccount account = accountRepositry.findByAccountName(portalUserDto.getAccoutName());
		if (account == null)
			throw new AccountNotFoundException("Account does not exist");
		PortalRole portalRoleUser = roleRepository.findByName("ROLE_USER");
		final PortalUser user = new PortalUser();
		user.setFirstName(portalUserDto.getFirstName());
		user.setLastName(portalUserDto.getLastName());
		user.setUsername(portalUserDto.getUsername());
		user.setPassword(bCryptPasswordEncoder.encode(portalUserDto.getPassword()));
		user.setRoles(Arrays.asList(portalRoleUser));
		user.setAccount(account);
		return userRepository.save(user);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.vinodborole.portal.service.IPortalUserService#deleteUser(com.vinodborole.portal.persistence.
	 * model.PortalUser)
	 */
	@Override
	public void deleteUser(PortalUser user) {
		if (userExists(user.getUsername())) {
			userRepository.delete(user);
		}
	}

	private boolean userExists(final String username) {
		PortalUser user = userRepository.findByUsername(username);
		if (user != null)
			return true;
		else
			return false;
	}

	@Override
	public void changeUserPassword(PortalUser user, String password) {
		user.setPassword(bCryptPasswordEncoder.encode(password));
		userRepository.save(user);
	}

	@Override
	public boolean checkIfValidOldPassword(PortalUser user, String oldPassword) {
		return bCryptPasswordEncoder.matches(oldPassword, user.getPassword());
	}

}
