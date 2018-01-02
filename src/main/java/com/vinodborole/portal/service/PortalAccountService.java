/**
 * 
 */
package com.vinodborole.portal.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vinodborole.portal.persistence.dao.PortalAccountRepository;
import com.vinodborole.portal.persistence.model.PortalAccount;
import com.vinodborole.portal.web.dto.PortalAccountDto;
import com.vinodborole.portal.web.error.AccountAlreadyExistException;
import com.vinodborole.portal.web.error.AccountNotFoundException;

/**
 * @author vinodborole
 *
 */
@Service
@Transactional
public class PortalAccountService implements IPortalAccountService {

	@Autowired
	private PortalAccountRepository portalAccountRepository;

	@Override
	public PortalAccount addNewAccount(PortalAccountDto accountDto) throws AccountAlreadyExistException {
		if (accountExists(accountDto.getAccoutName())) {
			throw new AccountAlreadyExistException("Account of the same name already exist");
		}
		PortalAccount account = new PortalAccount();
		account.setAccountDesc(accountDto.getAccountDesc());
		account.setAccountName(accountDto.getAccoutName());
		return portalAccountRepository.save(account);
	}

	private boolean accountExists(final String accountName) {
		PortalAccount account = portalAccountRepository.findByAccountName(accountName);
		if (account != null)
			return true;
		else
			return false;
	}

	@Override
	public List<PortalAccount> getAllAccounts() {
		return portalAccountRepository.findAll();
	}

	@Override
	public PortalAccount getAccount(Long accountId) throws AccountNotFoundException {
		PortalAccount account = portalAccountRepository.getOne(accountId);
		if (account == null)
			throw new AccountNotFoundException("Account of the given ID does not exists");
		return account;
	}

	@Override
	public PortalAccount updateAccount(PortalAccount account) throws AccountNotFoundException {
		PortalAccount checkAccount = portalAccountRepository.getOne(account.getId());
		if (checkAccount == null)
			throw new AccountNotFoundException("Account does not exists");
		return portalAccountRepository.save(account);
	}

	@Override
	public void deleteAccount(PortalAccount account) throws AccountNotFoundException {
		PortalAccount checkAccount = portalAccountRepository.getOne(account.getId());
		if (checkAccount == null)
			throw new AccountNotFoundException("Account does not exists");
		portalAccountRepository.delete(account);
	}

	@Override
	public PortalAccount getAccountByName(String accountName) throws AccountNotFoundException {
		PortalAccount account = portalAccountRepository.findByAccountName(accountName);
		if (account == null)
			throw new AccountNotFoundException("Account does not exists");
		return account;
	}

}
