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

import com.vinodborole.portal.persistence.model.PortalProduct;
import com.vinodborole.portal.service.PortalProductService;
import com.vinodborole.portal.util.PortalRESTResponse;
import com.vinodborole.portal.web.dto.PortalProductDto;
import com.vinodborole.portal.web.error.ProductAlreadyExistException;
import com.vinodborole.portal.web.error.ProductNotFoundException;
import com.vinodborole.portal.web.error.ProductTypeNotFoundException;

import io.swagger.annotations.ApiOperation;

/**
 * @author vinodborole
 *
 */
@RestController
@RequestMapping(value = "/api")
public class PortalProductController {
	public static final String ENDPOINT = "/products";

	@Autowired
	private PortalProductService portalProductService;

	@ApiOperation(value = "Add a new product", nickname = "Add a new product", notes = "Endpoint to Add a new product.")
	@RequestMapping(value = ENDPOINT, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@PreAuthorize("hasAuthority('OP_CREATE_PRODUCT')")
	public ResponseEntity<PortalRESTResponse<Object>> addProduct(@RequestBody PortalProductDto portalProductDto) {
		HashMap<String, PortalProduct> map = new HashMap<String, PortalProduct>();
		HttpStatus httpStatus = null;
		PortalRESTResponse<Object> response = null;
		try {
			PortalProduct portalProduct = portalProductService.addNewProduct(portalProductDto);
			map.put("product", portalProduct);
			httpStatus = HttpStatus.OK;
			response = new PortalRESTResponse<Object>("Portal Product added Successfully", map);
		} catch (ProductAlreadyExistException e) {
			httpStatus = HttpStatus.CONFLICT;
			response = new PortalRESTResponse<Object>(e.getMessage(), map);
		} catch (ProductTypeNotFoundException ex) {
			httpStatus = HttpStatus.NOT_FOUND;
			response = new PortalRESTResponse<Object>(ex.getMessage(), map);
		}
		return new ResponseEntity<PortalRESTResponse<Object>>(response, httpStatus);
	}

	@ApiOperation(value = "Update a product", nickname = "Update a product", notes = "Endpoint to Update a product.")
	@RequestMapping(value = ENDPOINT
			+ "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@PreAuthorize("hasAuthority('OP_UPDATE_PRODUCT')")
	public ResponseEntity<PortalRESTResponse<Object>> updateProduct(@PathVariable long id,
			@RequestBody PortalProductDto portalProductDto) {
		HashMap<String, PortalProduct> map = new HashMap<String, PortalProduct>();
		HttpStatus httpStatus = null;
		PortalRESTResponse<Object> response = null;
		try {
			PortalProduct portalProduct = portalProductService.getProduct(id);
			portalProduct.setProductDesc(portalProductDto.getProductDesc());
			map.put("product", portalProductService.updateProduct(portalProduct));
			httpStatus = HttpStatus.OK;
			response = new PortalRESTResponse<Object>("Portal Product updated Successfully", map);
			return new ResponseEntity<PortalRESTResponse<Object>>(response, httpStatus);
		} catch (ProductNotFoundException ex) {
			httpStatus = HttpStatus.NOT_FOUND;
			response = new PortalRESTResponse<Object>(ex.getMessage(), map);
			return new ResponseEntity<PortalRESTResponse<Object>>(response, httpStatus);
		}

	}

	@ApiOperation(value = "Delete a product", nickname = "Delete a product", notes = "Endpoint to Delete a product.")
	@RequestMapping(value = ENDPOINT
			+ "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@PreAuthorize("hasAuthority('OP_DELETE_PRODUCT')")
	public ResponseEntity<PortalRESTResponse<Object>> deleteProduct(@PathVariable long id) {
		HttpStatus httpStatus = null;
		PortalRESTResponse<Object> response = null;
		try {
			PortalProduct portalProduct = portalProductService.getProduct(id);
			portalProductService.deleteProduct(portalProduct);
			httpStatus = HttpStatus.OK;
			response = new PortalRESTResponse<Object>("Portal Product deleted Successfully", null);
			return new ResponseEntity<PortalRESTResponse<Object>>(response, httpStatus);
		} catch (ProductNotFoundException ex) {
			httpStatus = HttpStatus.NOT_FOUND;
			response = new PortalRESTResponse<Object>(ex.getMessage(), null);
			return new ResponseEntity<PortalRESTResponse<Object>>(response, httpStatus);
		}
	}

	@ApiOperation(value = "Get all the products", nickname = "Get all the products", notes = "Endpoint to Get all the products.")
	@RequestMapping(value = ENDPOINT, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@PreAuthorize("hasAuthority('OP_READ_PRODUCT')")
	public ResponseEntity<PortalRESTResponse<Object>> getAllProducts() {
		HashMap<String, List<PortalProduct>> map = new HashMap<String, List<PortalProduct>>();
		map.put("products", portalProductService.getAllProducts());
		PortalRESTResponse<Object> response = new PortalRESTResponse<Object>("", map);
		return new ResponseEntity<PortalRESTResponse<Object>>(response, HttpStatus.OK);
	}

	@ApiOperation(value = "Get product Info", nickname = "Get product Info", notes = "Endpoint to Get product Info.")
	@RequestMapping(value = ENDPOINT + "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@PreAuthorize("hasAuthority('OP_READ_PRODUCT')")
	public ResponseEntity<PortalRESTResponse<Object>> getProductInfo(@PathVariable long id) {
		HashMap<String, PortalProduct> map = new HashMap<String, PortalProduct>();
		HttpStatus httpStatus = null;
		PortalRESTResponse<Object> response = null;
		try {
			map.put("product", portalProductService.getProduct(id));
			httpStatus = HttpStatus.OK;
			response = new PortalRESTResponse<Object>("", map);
			return new ResponseEntity<PortalRESTResponse<Object>>(response, httpStatus);
		} catch (ProductNotFoundException ex) {
			httpStatus = HttpStatus.NOT_FOUND;
			response = new PortalRESTResponse<Object>(ex.getMessage(), map);
			return new ResponseEntity<PortalRESTResponse<Object>>(response, httpStatus);
		}
	}
}
