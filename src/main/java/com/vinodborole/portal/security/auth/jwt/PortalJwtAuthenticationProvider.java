package com.vinodborole.portal.security.auth.jwt;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.vinodborole.portal.persistence.model.token.PortalRawAccessJwtToken;
import com.vinodborole.portal.persistence.model.token.PortalUserContext;
import com.vinodborole.portal.security.auth.PortalJwtAuthenticationToken;
import com.vinodborole.portal.security.config.PortalJwtSettings;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

/**
 * An {@link AuthenticationProvider} implementation that will use provided
 * instance of {@link IPortalJwtToken} to perform authentication.
 * 
 * @author vinod borole
 *
 * 
 */
@Component
@SuppressWarnings("unchecked")
public class PortalJwtAuthenticationProvider implements AuthenticationProvider {
    private final PortalJwtSettings jwtSettings;
    
    @Autowired
    public PortalJwtAuthenticationProvider(PortalJwtSettings jwtSettings) {
        this.jwtSettings = jwtSettings;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        PortalRawAccessJwtToken rawAccessToken = (PortalRawAccessJwtToken) authentication.getCredentials();
        Jws<Claims> jwsClaims = rawAccessToken.parseClaims(jwtSettings.getTokenSigningKey());
        String subject = jwsClaims.getBody().getSubject(); 
        List<String> scopes = jwsClaims.getBody().get("scopes", List.class);
        List<GrantedAuthority> authorities = scopes.stream()
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());
        
        PortalUserContext context = PortalUserContext.create(subject, authorities);
        
        return new PortalJwtAuthenticationToken(context, context.getAuthorities());
    } 

    @Override
    public boolean supports(Class<?> authentication) {
        return (PortalJwtAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
