/**
 * 
 */
package com.vinodborole.portal.web.controller;

import java.security.Principal;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
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

import com.vinodborole.portal.persistence.dao.PortalUserRepository;
import com.vinodborole.portal.persistence.model.PortalUser;
import com.vinodborole.portal.persistence.model.PortalVerificationToken;
import com.vinodborole.portal.persistence.model.token.PortalUserContext;
import com.vinodborole.portal.products.PortalSessionManagement;
import com.vinodborole.portal.security.auth.PortalJwtAuthenticationToken;
import com.vinodborole.portal.security.auth.PortalSession;
import com.vinodborole.portal.security.auth.listener.PortalOnRegistrationCompleteEvent;
import com.vinodborole.portal.service.IPortalTokenService;
import com.vinodborole.portal.service.IPortalUserService;
import com.vinodborole.portal.util.PortalEmailUtil;
import com.vinodborole.portal.util.PortalRESTResponse;
import com.vinodborole.portal.util.PortalUserUtil;
import com.vinodborole.portal.web.dto.PortalPasswordDto;
import com.vinodborole.portal.web.dto.PortalUserDto;
import com.vinodborole.portal.web.error.UserAlreadyExistException;

import io.swagger.annotations.ApiOperation;

/**
 * @author vinodborole
 *
 */
@RestController
@RequestMapping(value = "/api")
public class PortalUserController {
	public static final String ENDPOINT = "/users";

	@Autowired
	private IPortalUserService portalUserService;

	@Autowired
	private ApplicationEventPublisher eventPublisher;

	@Autowired
	private IPortalTokenService portalTokenService;

	@Autowired
	private PortalUserRepository userRepository;

	@Autowired
	private PortalEmailUtil emailUtil;

	@Autowired
	private PortalSessionManagement sessionManagement;

	@Autowired
	private PortalUserUtil userUtil;

	@ApiOperation(value = "Register a new user", nickname = "Register a new user", notes = "Endpoint to Register a new user.")
	@RequestMapping(value = ENDPOINT
			+ "/sign-up", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<PortalRESTResponse<Object>> signUp(HttpServletRequest request,
			@RequestBody PortalUserDto portalUserDto) {
		HashMap<String, String> map = new HashMap<String, String>();
		HttpStatus httpStatus;
		PortalRESTResponse<Object> response;
		try {
			PortalUser portalUser = portalUserService.registerNewAccount(portalUserDto);
			eventPublisher.publishEvent(new PortalOnRegistrationCompleteEvent(portalUser, getAppUrl(request)));
			map.put("userId", String.valueOf(portalUser.getId()));
			httpStatus = HttpStatus.OK;
			response = new PortalRESTResponse<Object>("Portal User Registered Successfully", map);
		} catch (UserAlreadyExistException exception) {
			response = new PortalRESTResponse<Object>("User Already Exists", map);
			httpStatus = HttpStatus.CONFLICT;
		}
		return new ResponseEntity<PortalRESTResponse<Object>>(response, httpStatus);
	}

	@ApiOperation(value = "Confirm User Registration", nickname = "Confirm User Registration", notes = "Endpoint to Confirm User Registration.")
	@RequestMapping(value = "/user/registrationConfirm"
			+ "/{token}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<PortalRESTResponse<Object>> confirmRegistration(@PathVariable String token) {
		HashMap<String, String> map = new HashMap<String, String>();
		HttpStatus httpStatus = null;
		PortalRESTResponse<Object> response = null;
		String result = portalTokenService.validateVerificationToken(token);
		if (result.equals("valid")) {
			response = new PortalRESTResponse<Object>("Email Id confirmed", map);
			httpStatus = HttpStatus.OK;
		} else {
			response = new PortalRESTResponse<Object>("Token expired", map);
			httpStatus = HttpStatus.NOT_FOUND;
		}
		return new ResponseEntity<PortalRESTResponse<Object>>(response, httpStatus);
	}

	@ApiOperation(value = "Confirm User Registration", nickname = "Confirm User Registration", notes = "Endpoint to Confirm User Registration.")
	@RequestMapping(value = "/user/resendRegistrationToken"
			+ "/{token}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<PortalRESTResponse<Object>> resendConfirmRegistration(HttpServletRequest request,
			@PathVariable String token) {
		HashMap<String, String> map = new HashMap<String, String>();
		HttpStatus httpStatus;
		PortalRESTResponse<Object> response;
		PortalVerificationToken newToken = portalTokenService.generateNewVerificationToken(token);
		emailUtil.confirmRegistration(newToken.getUser(), getAppUrl(request));
		response = new PortalRESTResponse<Object>("Registration Confirmation email has been resent", map);
		httpStatus = HttpStatus.OK;
		return new ResponseEntity<PortalRESTResponse<Object>>(response, httpStatus);
	}

	@ApiOperation(value = "Reset User Password", nickname = "Reset User Password", notes = "Endpoint to Reset User Password.")
	@RequestMapping(value = "/user/resetPassword"
			+ "/{email:.+}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<PortalRESTResponse<Object>> resetPassword(HttpServletRequest request,
			@PathVariable(value = "email") String username) {
		HashMap<String, String> map = new HashMap<String, String>();
		HttpStatus httpStatus;
		PortalRESTResponse<Object> response;
		PortalUser user = userRepository.findByUsername(username);
		emailUtil.resetPasswordEmail(user, getAppUrl(request));
		response = new PortalRESTResponse<Object>("An email with reset password link has been sent.", map);
		httpStatus = HttpStatus.OK;
		return new ResponseEntity<PortalRESTResponse<Object>>(response, httpStatus);
	}

	@ApiOperation(value = "Change User Password", nickname = "Change User Password", notes = "Endpoint to Change User Password.")
	@RequestMapping(value = "/user/changepassword"
			+ "/{token}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<PortalRESTResponse<Object>> changePassword(@PathVariable String token) {
		HashMap<String, String> map = new HashMap<String, String>();
		HttpStatus httpStatus;
		PortalRESTResponse<Object> response;
		String tempJwtToken = portalTokenService.validatePasswordResetToken(token);
		map.put("token", tempJwtToken);
		response = new PortalRESTResponse<Object>("Temporary JWT Token to change password", map);
		httpStatus = HttpStatus.OK;
		return new ResponseEntity<PortalRESTResponse<Object>>(response, httpStatus);
	}

	@ApiOperation(value = "Save User Password", nickname = "Save User Password", notes = "Endpoint to Save User Password.")
	@RequestMapping(value = "/user/savePassword", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@PreAuthorize("hasAuthority('CHANGE_PASSWORD_PRIVILEGE')")
	public ResponseEntity<PortalRESTResponse<Object>> savePassword(HttpServletRequest request,
			@RequestBody PortalPasswordDto portalPasswordDto) {
		HashMap<String, String> map = new HashMap<String, String>();
		HttpStatus httpStatus;
		PortalRESTResponse<Object> response;
		PortalUser user = userUtil.getUserInfo(request);
		portalUserService.changeUserPassword(user, portalPasswordDto.getNewPassword());
		response = new PortalRESTResponse<Object>("User password has been successfully changed", map);
		httpStatus = HttpStatus.OK;
		return new ResponseEntity<PortalRESTResponse<Object>>(response, httpStatus);
	}

	@ApiOperation(value = "Get User Session Info", nickname = "Get User Session Inf", notes = "Endpoint to Get User Session Inf.")
	@RequestMapping(value = "/user/session", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<PortalRESTResponse<Object>> getPortalSession(HttpServletRequest request) {
		HashMap<String, PortalSession> map = new HashMap<String, PortalSession>();
		HttpStatus httpStatus;
		PortalRESTResponse<Object> response = null;
		Principal principal = request.getUserPrincipal();
		PortalJwtAuthenticationToken portalJwtAuthenticationToken = (PortalJwtAuthenticationToken) principal;
		PortalUserContext userContext = (PortalUserContext) portalJwtAuthenticationToken.getPrincipal();
		PortalSession session = sessionManagement.getPortalSession(userContext);
		map.put("session", session);
		response = new PortalRESTResponse<Object>("", map);
		httpStatus = HttpStatus.OK;
		return new ResponseEntity<PortalRESTResponse<Object>>(response, httpStatus);
	}

	private String getAppUrl(HttpServletRequest request) {
		return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	}

}
