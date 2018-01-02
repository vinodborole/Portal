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

import com.vinodborole.portal.persistence.model.PortalPrivilege;
import com.vinodborole.portal.persistence.model.PortalRole;
import com.vinodborole.portal.service.PortalRolePrivilegeManagementService;
import com.vinodborole.portal.util.PortalRESTResponse;
import com.vinodborole.portal.web.dto.PortalRoleDto;
import com.vinodborole.portal.web.error.PrivilegeNotFoundException;
import com.vinodborole.portal.web.error.RoleAlreadyExistException;
import com.vinodborole.portal.web.error.RoleNotFoundException;

import io.swagger.annotations.ApiOperation;

/**
 * @author vinodborole
 *
 */
@RestController
@RequestMapping(value = "/api")
public class PortalRolePrivilegeManagementController {

	public static final String ENDPOINT_ROLES = "/roles";
	public static final String ENDPOINT_PRIVILEGE = "/priviliges";

	@Autowired
	private PortalRolePrivilegeManagementService rolePrivilegeManagementService;

	@ApiOperation(value = "Add a new role", nickname = "Add a new role", notes = "Endpoint to Add a new role.")
	@RequestMapping(value = ENDPOINT_ROLES, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@PreAuthorize("hasAuthority('OP_CREATE_ROLE')")
	public ResponseEntity<PortalRESTResponse<Object>> addRole(@RequestBody PortalRoleDto roleDto) {
		HashMap<String, PortalRole> map = new HashMap<String, PortalRole>();
		HttpStatus httpStatus = null;
		PortalRESTResponse<Object> response = null;
		try {
			PortalRole role = rolePrivilegeManagementService.addRole(roleDto);
			map.put("role", role);
			httpStatus = HttpStatus.OK;
			response = new PortalRESTResponse<Object>("Portal Role added Successfully", map);
		} catch (RoleAlreadyExistException e) {
			httpStatus = HttpStatus.CONFLICT;
			response = new PortalRESTResponse<Object>(e.getMessage(), map);
		}
		return new ResponseEntity<PortalRESTResponse<Object>>(response, httpStatus);
	}

	@ApiOperation(value = "Get a role", nickname = "Get a role", notes = "Endpoint to Get a role.")
	@RequestMapping(value = ENDPOINT_ROLES
			+ "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@PreAuthorize("hasAuthority('OP_READ_ROLE')")
	public ResponseEntity<PortalRESTResponse<Object>> getRole(@PathVariable long id) {
		HashMap<String, PortalRole> map = new HashMap<String, PortalRole>();
		HttpStatus httpStatus = null;
		PortalRESTResponse<Object> response = null;
		try {
			PortalRole role = rolePrivilegeManagementService.getRole(id);
			map.put("role", role);
			response = new PortalRESTResponse<Object>("", map);
			httpStatus = HttpStatus.OK;
		} catch (RoleNotFoundException e) {
			httpStatus = HttpStatus.CONFLICT;
			response = new PortalRESTResponse<Object>(e.getMessage(), map);
		}
		return new ResponseEntity<PortalRESTResponse<Object>>(response, httpStatus);
	}

	@ApiOperation(value = "Get all roles", nickname = "Get all roles", notes = "Endpoint to Get all roles.")
	@RequestMapping(value = ENDPOINT_ROLES, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@PreAuthorize("hasAuthority('OP_READ_ROLE')")
	public ResponseEntity<PortalRESTResponse<Object>> getRoles() {
		HashMap<String, List<PortalRole>> map = new HashMap<String, List<PortalRole>>();
		HttpStatus httpStatus = null;
		PortalRESTResponse<Object> response = null;
		List<PortalRole> roles = rolePrivilegeManagementService.getAllRoles();
		map.put("roles", roles);
		httpStatus = HttpStatus.OK;
		response = new PortalRESTResponse<Object>("", map);
		return new ResponseEntity<PortalRESTResponse<Object>>(response, httpStatus);
	}

	@ApiOperation(value = "Delete a role", nickname = "Delete a role", notes = "Endpoint to Delete a role.")
	@RequestMapping(value = ENDPOINT_ROLES
			+ "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@PreAuthorize("hasAuthority('OP_DELETE_ROLE')")
	public ResponseEntity<PortalRESTResponse<Object>> deleteRole(@PathVariable long id) {
		HashMap<String, PortalRole> map = new HashMap<String, PortalRole>();
		HttpStatus httpStatus = null;
		PortalRESTResponse<Object> response = null;
		try {
			rolePrivilegeManagementService.deleteRole(id);
			response = new PortalRESTResponse<Object>("Portal Role deleted Successfully", map);
			httpStatus = HttpStatus.OK;
		} catch (RoleNotFoundException e) {
			httpStatus = HttpStatus.CONFLICT;
			response = new PortalRESTResponse<Object>(e.getMessage(), map);
		}
		return new ResponseEntity<PortalRESTResponse<Object>>(response, httpStatus);
	}

	@ApiOperation(value = "Get a privilege", nickname = "Get a privilege", notes = "Endpoint to Get a privilege.")
	@RequestMapping(value = ENDPOINT_PRIVILEGE
			+ "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@PreAuthorize("hasAuthority('OP_READ_PRIVILEGE')")
	public ResponseEntity<PortalRESTResponse<Object>> getPrivilege(@PathVariable long id) {
		HashMap<String, PortalPrivilege> map = new HashMap<String, PortalPrivilege>();
		HttpStatus httpStatus = null;
		PortalRESTResponse<Object> response = null;
		try {
			PortalPrivilege privilege = rolePrivilegeManagementService.getPrivilige(id);
			map.put("privilege", privilege);
			response = new PortalRESTResponse<Object>("", map);
			httpStatus = HttpStatus.OK;
		} catch (PrivilegeNotFoundException e) {
			httpStatus = HttpStatus.CONFLICT;
			response = new PortalRESTResponse<Object>(e.getMessage(), map);
		}
		return new ResponseEntity<PortalRESTResponse<Object>>(response, httpStatus);
	}

	@ApiOperation(value = "Get all privileges", nickname = "Get all privileges", notes = "Endpoint to Get all privileges.")
	@RequestMapping(value = ENDPOINT_PRIVILEGE, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@PreAuthorize("hasAuthority('OP_READ_PRIVILEGE')")
	public ResponseEntity<PortalRESTResponse<Object>> getPrivileges() {
		HashMap<String, List<PortalPrivilege>> map = new HashMap<String, List<PortalPrivilege>>();
		HttpStatus httpStatus = null;
		PortalRESTResponse<Object> response = null;
		List<PortalPrivilege> privileges = rolePrivilegeManagementService.getAllPriviliges();
		map.put("privileges", privileges);
		httpStatus = HttpStatus.OK;
		response = new PortalRESTResponse<Object>("", map);
		return new ResponseEntity<PortalRESTResponse<Object>>(response, httpStatus);
	}

	@ApiOperation(value = "Manage privilege to a role", nickname = "Manage privilege to a role", notes = "Endpoint to Assign/unassign privileges to a role.")
	@RequestMapping(value = ENDPOINT_ROLES + "/{id}"
			+ ENDPOINT_PRIVILEGE, method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@PreAuthorize("hasAuthority('OP_MANAGE_PRIVILEGE_ROLE')")
	public ResponseEntity<PortalRESTResponse<Object>> managePrivilegeforRole(@RequestBody PortalRoleDto roleDto,
			@PathVariable long roleid) {
		HashMap<String, PortalRole> map = new HashMap<String, PortalRole>();
		HttpStatus httpStatus = null;
		PortalRESTResponse<Object> response = null;
		try {
			PortalRole role = rolePrivilegeManagementService.assignPrivilegeToRole(roleDto, roleid);
			map.put("role", role);
			httpStatus = HttpStatus.OK;
			response = new PortalRESTResponse<Object>("Successfully managed privileges to role", map);
		} catch (RoleNotFoundException e) {
			httpStatus = HttpStatus.CONFLICT;
			response = new PortalRESTResponse<Object>(e.getMessage(), map);
		} catch (PrivilegeNotFoundException ex) {
			httpStatus = HttpStatus.CONFLICT;
			response = new PortalRESTResponse<Object>(ex.getMessage(), map);
		}
		return new ResponseEntity<PortalRESTResponse<Object>>(response, httpStatus);
	}

}
