/**
 * 
 */
package com.vinodborole.portal.service;

import java.util.List;

import com.vinodborole.portal.persistence.model.PortalAccount;
import com.vinodborole.portal.web.dto.PortalAccountDto;
import com.vinodborole.portal.web.error.AccountAlreadyExistException;
import com.vinodborole.portal.web.error.AccountNotFoundException;

/**
 * @author vinodborole
 *
 */
public interface IPortalAccountService {

	public PortalAccount addNewAccount(PortalAccountDto accountDto) throws AccountAlreadyExistException;

	List<PortalAccount> getAllAccounts();

	PortalAccount getAccount(Long accountId) throws AccountNotFoundException;

	PortalAccount getAccountByName(String accountName) throws AccountNotFoundException;

	PortalAccount updateAccount(PortalAccount account) throws AccountNotFoundException;

	void deleteAccount(PortalAccount account) throws AccountNotFoundException;
}
