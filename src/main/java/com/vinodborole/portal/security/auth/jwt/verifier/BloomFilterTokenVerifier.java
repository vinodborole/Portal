package com.vinodborole.portal.security.auth.jwt.verifier;

import org.springframework.stereotype.Component;

/**
 * BloomFilterTokenVerifier
 * 
 * @author vinod borole
 *
 * 
 */
@Component
public class BloomFilterTokenVerifier implements IPortalTokenVerifier {
    @Override
    public boolean verify(String jti) {
        return true;
    }
}
