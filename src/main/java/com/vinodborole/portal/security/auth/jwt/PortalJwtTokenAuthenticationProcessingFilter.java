package com.vinodborole.portal.security.auth.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.vinodborole.portal.persistence.model.token.PortalRawAccessJwtToken;
import com.vinodborole.portal.security.auth.PortalJwtAuthenticationToken;
import com.vinodborole.portal.security.auth.jwt.extractor.IPortalTokenExtractor;
import com.vinodborole.portal.security.config.PortalWebSecurityConfig;

/**
 * Performs validation of provided JWT Token.
 * 
 * @author vinod borole
 *
 * 
 */
public class PortalJwtTokenAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {
    private final AuthenticationFailureHandler failureHandler;
    private final IPortalTokenExtractor tokenExtractor;
    
    @Autowired
    public PortalJwtTokenAuthenticationProcessingFilter(AuthenticationFailureHandler failureHandler, 
            IPortalTokenExtractor tokenExtractor, RequestMatcher matcher) {
        super(matcher);
        this.failureHandler = failureHandler;
        this.tokenExtractor = tokenExtractor;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException { 
        String tokenPayload = request.getHeader(PortalWebSecurityConfig.AUTHENTICATION_HEADER_NAME);
        PortalRawAccessJwtToken token = new PortalRawAccessJwtToken(tokenExtractor.extract(tokenPayload));
        return getAuthenticationManager().authenticate(new PortalJwtAuthenticationToken(token));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authResult);
        SecurityContextHolder.setContext(context);
        chain.doFilter(request, response);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException failed) throws IOException, ServletException {
        SecurityContextHolder.clearContext();
        failureHandler.onAuthenticationFailure(request, response, failed);
    }
}
