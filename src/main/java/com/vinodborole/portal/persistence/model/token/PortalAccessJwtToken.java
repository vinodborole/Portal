package com.vinodborole.portal.persistence.model.token;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.jsonwebtoken.Claims;

/**
 * Raw representation of JWT Token.
 * 
 * @author vinod borole
 *
 *         
 */
public final class PortalAccessJwtToken implements IPortalJwtToken {
    private final String rawToken;
    @JsonIgnore private Claims claims;

    protected PortalAccessJwtToken(final String token, Claims claims) {
        this.rawToken = token;
        this.claims = claims;
    }

    public String getToken() {
        return this.rawToken;
    }

    public Claims getClaims() {
        return claims;
    }
}
