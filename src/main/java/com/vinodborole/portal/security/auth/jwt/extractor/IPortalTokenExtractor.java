package com.vinodborole.portal.security.auth.jwt.extractor;

/**
 * Implementations of this interface should always return raw base-64 encoded
 * representation of JWT Token.
 * 
 * @author vinod borole
 *
 * 
 */
public interface IPortalTokenExtractor {
    public String extract(String payload);
}
