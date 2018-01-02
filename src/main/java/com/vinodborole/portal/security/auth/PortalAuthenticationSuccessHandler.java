package com.vinodborole.portal.security.auth;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vinodborole.portal.persistence.dao.PortalUserRepository;
import com.vinodborole.portal.persistence.model.token.IPortalJwtToken;
import com.vinodborole.portal.persistence.model.token.PortalJwtTokenFactory;
import com.vinodborole.portal.persistence.model.token.PortalUserContext;
import com.vinodborole.portal.products.IPortalProductSession;
import com.vinodborole.portal.products.PortalProductType;
import com.vinodborole.portal.products.PortalSessionManagement;

/**
 * 
 * 
 * @author vinod borole
 *
 * 
 */
@Component
public class PortalAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
	private final ObjectMapper mapper;
	private final PortalJwtTokenFactory tokenFactory;

	@Autowired
	private PortalSessionManagement productSessionManagement;

	@Autowired
	private PortalUserRepository userRepository;

	@Autowired
	public PortalAuthenticationSuccessHandler(final ObjectMapper mapper, final PortalJwtTokenFactory tokenFactory) {
		this.mapper = mapper;
		this.tokenFactory = tokenFactory;
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		PortalUserContext userContext = (PortalUserContext) authentication.getPrincipal();

		IPortalJwtToken accessToken = tokenFactory.createAccessJwtToken(userContext);
		IPortalJwtToken refreshToken = tokenFactory.createRefreshToken(userContext);

		Map<String, String> tokenMap = new HashMap<String, String>();
		tokenMap.put("token", accessToken.getToken());
		tokenMap.put("refreshToken", refreshToken.getToken());

		PortalSession cesSession = generatePortalSession(userContext, accessToken, refreshToken);
		productSessionManagement.setPortalSession(cesSession);
		response.setStatus(HttpStatus.OK.value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		mapper.writeValue(response.getWriter(), tokenMap);

		clearAuthenticationAttributes(request);
	}

	private PortalSession generatePortalSession(PortalUserContext userContext, IPortalJwtToken accessToken,
			IPortalJwtToken refreshToken) throws JsonParseException, JsonMappingException, IOException {
		PortalSession portalSession = new PortalSession();
		Map<PortalProductType, IPortalProductSession> productSession = productSessionManagement
				.getProductSessions(userContext);
		portalSession.setUserContext(userContext);
		portalSession.setProductSession(productSession);
		portalSession.setToken(accessToken.getToken());
		portalSession.setRefreshToken(refreshToken.getToken());
		return portalSession;
	}

	/**
	 * Removes temporary authentication-related data which may have been stored in
	 * the session during the authentication process..
	 * 
	 */
	protected final void clearAuthenticationAttributes(HttpServletRequest request) {
		HttpSession session = request.getSession(false);

		if (session == null) {
			return;
		}

		session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
	}
}
