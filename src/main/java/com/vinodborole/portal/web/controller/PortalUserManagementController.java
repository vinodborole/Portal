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

import com.vinodborole.portal.persistence.model.PortalUser;
import com.vinodborole.portal.service.PortalUserManagementService;
import com.vinodborole.portal.util.PortalRESTResponse;
import com.vinodborole.portal.web.dto.PortalUserDto;
import com.vinodborole.portal.web.error.RoleNotFoundException;
import com.vinodborole.portal.web.error.UserNotFoundException;

import io.swagger.annotations.ApiOperation;

/**
 * @author vinodborole
 *
 */
@RestController
@RequestMapping(value = "/api")
public class PortalUserManagementController {
	public static final String ENDPOINT = "/users";

	@Autowired
	private PortalUserManagementService userManagementService;

	@ApiOperation(value = "Manage roles of a user", nickname = "Manage roles of a user", notes = "Endpoint to Assign/unassign roles to a user.")
	@RequestMapping(value = ENDPOINT
			+ "/{id}/roles", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@PreAuthorize("hasAuthority('OP_MANAGE_USER_ROLE')")
	public ResponseEntity<PortalRESTResponse<Object>> manageUserforRole(@RequestBody PortalUserDto userDto,
			@PathVariable long userId) {
		HashMap<String, PortalUser> map = new HashMap<String, PortalUser>();
		HttpStatus httpStatus = null;
		PortalRESTResponse<Object> response = null;
		try {
			PortalUser user = userManagementService.manageUserRole(userDto, userId);
			map.put("user", user);
			response = new PortalRESTResponse<Object>("Successfully managed users roles", map);
			httpStatus = HttpStatus.OK;
		} catch (UserNotFoundException e) {
			httpStatus = HttpStatus.CONFLICT;
			response = new PortalRESTResponse<Object>(e.getMessage(), map);
		} catch (RoleNotFoundException ex) {
			httpStatus = HttpStatus.CONFLICT;
			response = new PortalRESTResponse<Object>(ex.getMessage(), map);
		}
		return new ResponseEntity<PortalRESTResponse<Object>>(response, httpStatus);
	}

	@ApiOperation(value = "Manage account of a user", nickname = "Manage account of a user", notes = "Endpoint to Assign/unassign account of a user.")
	@RequestMapping(value = ENDPOINT
			+ "/{id}/account", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@PreAuthorize("hasAuthority('OP_MANAGE_USER_ACCOUNT')")
	public ResponseEntity<PortalRESTResponse<Object>> manageUserforAccount(@RequestBody PortalUserDto userDto,
			@PathVariable long userId) {
		HashMap<String, PortalUser> map = new HashMap<String, PortalUser>();
		HttpStatus httpStatus = null;
		PortalRESTResponse<Object> response = null;
		try {
			PortalUser user = userManagementService.manageUserAccount(userDto, userId);
			map.put("user", user);
			httpStatus = HttpStatus.OK;
			response = new PortalRESTResponse<Object>("Successfully managed users account", map);
		} catch (UserNotFoundException e) {
			httpStatus = HttpStatus.CONFLICT;
			response = new PortalRESTResponse<Object>(e.getMessage(), map);
		} catch (RoleNotFoundException ex) {
			httpStatus = HttpStatus.CONFLICT;
			response = new PortalRESTResponse<Object>(ex.getMessage(), map);
		}
		return new ResponseEntity<PortalRESTResponse<Object>>(response, httpStatus);
	}

	@ApiOperation(value = "Get all users", nickname = "Get all users", notes = "Endpoint to Get all users.")
	@RequestMapping(value = ENDPOINT, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@PreAuthorize("hasAuthority('OP_READ_USER')")
	public ResponseEntity<PortalRESTResponse<Object>> getUsers() {
		HashMap<String, List<PortalUser>> map = new HashMap<String, List<PortalUser>>();
		HttpStatus httpStatus = null;
		PortalRESTResponse<Object> response = null;
		List<PortalUser> roles = userManagementService.getAllUsers();
		map.put("users", roles);
		httpStatus = HttpStatus.OK;
		response = new PortalRESTResponse<Object>("", map);
		return new ResponseEntity<PortalRESTResponse<Object>>(response, httpStatus);
	}

}
