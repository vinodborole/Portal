package com.vinodborole.portal.persistence.model.token;

import java.security.Key;
import java.util.List;
import java.util.Optional;

import org.springframework.security.authentication.BadCredentialsException;

 
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

/**
 * RefreshToken
 * 
 * @author vinod borole
 *
 * 
 */
@SuppressWarnings("unchecked")
public class PortalRefreshToken implements IPortalJwtToken {
    private Jws<Claims> claims;

    private PortalRefreshToken(Jws<Claims> claims) {
        this.claims = claims;
    }

    /**
     * Creates and validates Refresh token 
     * 
     * @param token
     * @param signingKey
     * 
     * @throws BadCredentialsException
     * @throws PortalJwtExpiredTokenException
     * 
     * @return
     */
    public static Optional<PortalRefreshToken> create(PortalRawAccessJwtToken token, Key signingKey) {
        Jws<Claims> claims = token.parseClaims(signingKey);

        List<String> scopes = claims.getBody().get("scopes", List.class);
        if (scopes == null || scopes.isEmpty() 
                || !scopes.stream().filter(scope -> Scopes.REFRESH_TOKEN.authority().equals(scope)).findFirst().isPresent()) {
            return Optional.empty();
        }

        return Optional.of(new PortalRefreshToken(claims));
    }

    @Override
    public String getToken() {
        return null;
    }

    public Jws<Claims> getClaims() {
        return claims;
    }
    
    public String getJti() {
        return claims.getBody().getId();
    }
    
    public String getSubject() {
        return claims.getBody().getSubject();
    }
}
