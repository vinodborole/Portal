/**
 * 
 */
package com.vinodborole.portal.web.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.vinodborole.portal.persistence.model.PortalAccount;
import com.vinodborole.portal.service.PortalAccountService;
import com.vinodborole.portal.util.PortalRESTResponse;
import com.vinodborole.portal.web.dto.PortalAccountDto;
import com.vinodborole.portal.web.error.AccountAlreadyExistException;
import com.vinodborole.portal.web.error.AccountNotFoundException;

import io.swagger.annotations.ApiOperation;

/**
 * @author vinodborole
 *
 */
@RestController
@RequestMapping(value = "/api")
public class PortalAccountController {
	public static final String ENDPOINT = "/accounts";

	@Autowired
	private PortalAccountService accountService;

	@ApiOperation(value = "Add a new account", nickname = "Add a new account", notes = "Endpoint to Add a new account.")
	@RequestMapping(value = ENDPOINT, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@PreAuthorize("hasAuthority('OP_CREATE_ACCOUNT')")
	public ResponseEntity<PortalRESTResponse<Object>> addAccount(@RequestBody PortalAccountDto portalAccountDto) {
		HashMap<String, PortalAccount> map = new HashMap<String, PortalAccount>();
		HttpStatus httpStatus = null;
		PortalRESTResponse<Object> response = null;
		try {
			PortalAccount account = accountService.addNewAccount(portalAccountDto);
			map.put("account", account);
			httpStatus = HttpStatus.OK;
			response = new PortalRESTResponse<Object>("Portal Account added Successfully", map);
		} catch (AccountAlreadyExistException e) {
			httpStatus = HttpStatus.CONFLICT;
			response = new PortalRESTResponse<Object>(e.getMessage(), map);
		}
		return new ResponseEntity<PortalRESTResponse<Object>>(response, httpStatus);
	}

	@ApiOperation(value = "Update a account", nickname = "Update a account", notes = "Endpoint to Update a account.")
	@RequestMapping(value = ENDPOINT
			+ "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@PreAuthorize("hasAuthority('OP_UPDATE_ACCOUNT')")
	public ResponseEntity<PortalRESTResponse<Object>> updateAccount(@PathVariable long id,
			@RequestBody PortalAccountDto portalAccountDto) {
		HashMap<String, PortalAccount> map = new HashMap<String, PortalAccount>();
		HttpStatus httpStatus = null;
		PortalRESTResponse<Object> response = null;
		try {
			PortalAccount account = accountService.getAccountByName(portalAccountDto.getAccoutName());
			account.setAccountDesc(portalAccountDto.getAccountDesc());
			PortalAccount updatedAccount = accountService.updateAccount(account);
			map.put("account", updatedAccount);
			httpStatus = HttpStatus.OK;
			response = new PortalRESTResponse<Object>("Portal Account updated Successfully", map);
		} catch (AccountNotFoundException ex) {
			httpStatus = HttpStatus.NOT_FOUND;
			response = new PortalRESTResponse<Object>(ex.getMessage(), map);
		}
		return new ResponseEntity<PortalRESTResponse<Object>>(response, httpStatus);
	}

	@ApiOperation(value = "Delete a account", nickname = "Delete a account", notes = "Endpoint to Delete a account.")
	@RequestMapping(value = ENDPOINT
			+ "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@PreAuthorize("hasAuthority('OP_DELETE_ACCOUNT')")
	public ResponseEntity<PortalRESTResponse<Object>> deleteAccount(@PathVariable long id) {
		HttpStatus httpStatus = null;
		PortalRESTResponse<Object> response = null;
		try {
			PortalAccount account = accountService.getAccount(id);
			accountService.deleteAccount(account);
			httpStatus = HttpStatus.OK;
			response = new PortalRESTResponse<Object>("Portal Account deleted Successfully", null);
		} catch (AccountNotFoundException ex) {
			httpStatus = HttpStatus.NOT_FOUND;
			response = new PortalRESTResponse<Object>(ex.getMessage(), null);
		}
		return new ResponseEntity<PortalRESTResponse<Object>>(response, httpStatus);
	}

	@ApiOperation(value = "Get all the accounts", nickname = "Get all the accounts", notes = "Endpoint to Get all the accounts.")
	@RequestMapping(value = ENDPOINT, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@PreAuthorize("hasAuthority('OP_READ_ACCOUNT')")
	public ResponseEntity<PortalRESTResponse<Object>> getAllAccounts() {
		HashMap<String, List<PortalAccount>> map = new HashMap<String, List<PortalAccount>>();
		List<PortalAccount> accounts = accountService.getAllAccounts();
		map.put("accounts", accounts);
		PortalRESTResponse<Object> response = new PortalRESTResponse<Object>("", map);
		return new ResponseEntity<PortalRESTResponse<Object>>(response, HttpStatus.OK);
	}

	@ApiOperation(value = "Get account Info", nickname = "Get account Info", notes = "Endpoint to Get account Info.")
	@RequestMapping(value = ENDPOINT + "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@PreAuthorize("hasAuthority('OP_READ_ACCOUNT')")
	public ResponseEntity<PortalRESTResponse<Object>> getAccountInfo(@PathVariable long id) {
		HashMap<String, PortalAccount> map = new HashMap<String, PortalAccount>();
		HttpStatus httpStatus = null;
		PortalRESTResponse<Object> response = null;
		try {
			PortalAccount account = accountService.getAccount(id);
			map.put("account", account);
			httpStatus = HttpStatus.OK;
			response = new PortalRESTResponse<Object>("", map);
			return new ResponseEntity<PortalRESTResponse<Object>>(response, httpStatus);
		} catch (AccountNotFoundException ex) {
			httpStatus = HttpStatus.NOT_FOUND;
			response = new PortalRESTResponse<Object>(ex.getMessage(), map);
			return new ResponseEntity<PortalRESTResponse<Object>>(response, httpStatus);
		}
	}

}
