/**
 * 
 */
package com.vinodborole.portal.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vinodborole.portal.persistence.dao.PortalUserRepository;
import com.vinodborole.portal.persistence.model.PortalUser;
import com.vinodborole.portal.persistence.model.token.PortalUserContext;
import com.vinodborole.portal.security.auth.PortalJwtAuthenticationToken;

/**
 * @author vinodborole
 *
 */
@Component
public class PortalUserUtil {

	@Autowired
	private PortalUserRepository userRepository;

	public PortalUser getUserInfo(HttpServletRequest request) {
		PortalJwtAuthenticationToken jwtToken = (PortalJwtAuthenticationToken) request.getUserPrincipal();
		PortalUserContext userContext = (PortalUserContext) jwtToken.getPrincipal();
		PortalUser user = userRepository.findByUsername(userContext.getUsername());
		return user;
	}

}
