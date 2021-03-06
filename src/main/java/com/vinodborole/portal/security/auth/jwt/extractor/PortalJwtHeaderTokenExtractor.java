package com.vinodborole.portal.security.auth.jwt.extractor;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Component;

/**
 * An implementation of {@link IPortalTokenExtractor} extracts token from
 * Authorization: Bearer scheme.
 * 
 * @author vinod borole
 *
 * 
 */
@Component
public class PortalJwtHeaderTokenExtractor implements IPortalTokenExtractor {
	public static final String HEADER_PREFIX = "Bearer ";

	@Override
	public String extract(String header) {
		if (StringUtils.isBlank(header)) {
			throw new AuthenticationServiceException("Authorization header cannot be blank!");
		}

		if (header.length() < HEADER_PREFIX.length()) {
			throw new AuthenticationServiceException("Invalid authorization header size.");
		}

		return header.substring(HEADER_PREFIX.length(), header.length());
	}
}
