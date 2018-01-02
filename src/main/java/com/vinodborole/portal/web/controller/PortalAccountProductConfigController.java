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

import com.vinodborole.portal.persistence.model.PortalAccountProductConfig;
import com.vinodborole.portal.service.PortalAccountProductConfigService;
import com.vinodborole.portal.util.PortalRESTResponse;
import com.vinodborole.portal.web.dto.PortalAccountProductConfigDto;
import com.vinodborole.portal.web.error.AccountNotFoundException;
import com.vinodborole.portal.web.error.ProductNotFoundException;

import io.swagger.annotations.ApiOperation;

/**
 * @author vinodborole
 *
 */
@RestController
@RequestMapping(value = "/api")
public class PortalAccountProductConfigController {

	public static final String ENDPOINT = "/config/accounts";

	@Autowired
	private PortalAccountProductConfigService accountProductConfigService;

	@ApiOperation(value = "Get account product config Info", nickname = "Get account product config Info", notes = "Endpoint to Get account product config Info.")
	@RequestMapping(value = ENDPOINT
			+ "/{accountId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@PreAuthorize("hasAuthority('OP_READ_ACCOUNT_PRODUCT_CONFIG')")
	public ResponseEntity<PortalRESTResponse<Object>> getAccountProductConfigInfo(@PathVariable long accountId) {
		HashMap<String, List<PortalAccountProductConfig>> map = new HashMap<String, List<PortalAccountProductConfig>>();
		HttpStatus httpStatus = null;
		PortalRESTResponse<Object> response = null;
		try {
			List<PortalAccountProductConfig> productConfigs = accountProductConfigService
					.getProductConfigByAccountId(accountId);
			map.put("account-product-config", productConfigs);
			httpStatus = HttpStatus.OK;
			response = new PortalRESTResponse<Object>("", map);
			return new ResponseEntity<PortalRESTResponse<Object>>(response, httpStatus);
		} catch (AccountNotFoundException ex) {
			httpStatus = HttpStatus.NOT_FOUND;
			response = new PortalRESTResponse<Object>(ex.getMessage(), map);
			return new ResponseEntity<PortalRESTResponse<Object>>(response, httpStatus);
		}
	}

	@ApiOperation(value = "Add Account Product Config", nickname = "Add Account Product Config", notes = "Endpoint to Add Account Product Config.")
	@RequestMapping(value = ENDPOINT + "{accountId}" + "/products"
			+ "/{productId}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@PreAuthorize("hasAuthority('OP_CREATE_ACCOUNT_PRODUCT_CONFIG')")
	public ResponseEntity<PortalRESTResponse<Object>> saveConfig(
			@RequestBody PortalAccountProductConfigDto accountProductConfig, @PathVariable long accountId,
			@PathVariable long productId) {
		HashMap<String, PortalAccountProductConfig> map = new HashMap<String, PortalAccountProductConfig>();
		HttpStatus httpStatus = null;
		PortalRESTResponse<Object> response = null;
		try {
			PortalAccountProductConfig updatedCesAccountProductConfig = accountProductConfigService
					.saveProductConfig(accountProductConfig, productId);
			map.put("account-product-config", updatedCesAccountProductConfig);
			httpStatus = HttpStatus.OK;
			response = new PortalRESTResponse<Object>("Account product config saved successfully", map);
		} catch (ProductNotFoundException e) {
			httpStatus = HttpStatus.NOT_FOUND;
			response = new PortalRESTResponse<Object>(e.getMessage(), null);
		}
		return new ResponseEntity<PortalRESTResponse<Object>>(response, httpStatus);
	}

	@ApiOperation(value = "Update Account Product Config", nickname = "Update Account Product Config", notes = "Endpoint to Update Account Product Config.")
	@RequestMapping(value = ENDPOINT + "{accountId}" + "/products"
			+ "/{productId}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@PreAuthorize("hasAuthority('OP_UPDATE_ACCOUNT_PRODUCT_CONFIG')")
	public ResponseEntity<PortalRESTResponse<Object>> updateConfig(
			@RequestBody PortalAccountProductConfigDto accountProductConfig, @PathVariable long accountId,
			@PathVariable long productId) {
		HashMap<String, PortalAccountProductConfig> map = new HashMap<String, PortalAccountProductConfig>();
		HttpStatus httpStatus = null;
		PortalRESTResponse<Object> response = null;
		try {
			PortalAccountProductConfig updatedPortalAccountProductConfig = accountProductConfigService
					.updateProductConfig(accountProductConfig, productId);
			map.put("account-product-config", updatedPortalAccountProductConfig);
			httpStatus = HttpStatus.OK;
			response = new PortalRESTResponse<Object>("Account product config updated successfully", map);
		} catch (ProductNotFoundException e) {
			httpStatus = HttpStatus.NOT_FOUND;
			response = new PortalRESTResponse<Object>(e.getMessage(), null);
		} catch (AccountNotFoundException e) {
			httpStatus = HttpStatus.NOT_FOUND;
			response = new PortalRESTResponse<Object>(e.getMessage(), null);
		} catch (RuntimeException e) {
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			response = new PortalRESTResponse<Object>(e.getMessage(), null);
		}
		return new ResponseEntity<PortalRESTResponse<Object>>(response, httpStatus);
	}

}
