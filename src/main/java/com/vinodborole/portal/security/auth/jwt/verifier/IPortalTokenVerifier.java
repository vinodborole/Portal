package com.vinodborole.portal.security.auth.jwt.verifier;

/**
 * 
 * @author vinod borole
 *
 * 
 */
public interface IPortalTokenVerifier {
    public boolean verify(String jti);
}
