package com.vinodborole.portal.security.auth;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import com.vinodborole.portal.persistence.model.token.PortalRawAccessJwtToken;
import com.vinodborole.portal.persistence.model.token.PortalUserContext;


/**
 * An {@link org.springframework.security.core.Authentication} implementation
 * that is designed for simple presentation of JwtToken.
 * 
 * @author vinod borole
 *
 *         
 */
public class PortalJwtAuthenticationToken extends AbstractAuthenticationToken {
    private static final long serialVersionUID = 2877954820905567501L;

    private PortalRawAccessJwtToken rawAccessToken;
    private PortalUserContext userContext;

    public PortalJwtAuthenticationToken(PortalRawAccessJwtToken unsafeToken) {
        super(null);
        this.rawAccessToken = unsafeToken;
        this.setAuthenticated(false);
    }

    public PortalJwtAuthenticationToken(PortalUserContext userContext, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.eraseCredentials();
        this.userContext = userContext;
        super.setAuthenticated(true);
    }

    @Override
    public void setAuthenticated(boolean authenticated) {
        if (authenticated) {
            throw new IllegalArgumentException(
                    "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        }
        super.setAuthenticated(false);
    }

    @Override
    public Object getCredentials() {
        return rawAccessToken;
    }

    @Override
    public Object getPrincipal() {
        return this.userContext;
    }

    @Override
    public void eraseCredentials() {        
        super.eraseCredentials();
        this.rawAccessToken = null;
    }
}
