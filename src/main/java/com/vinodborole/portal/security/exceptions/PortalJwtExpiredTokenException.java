package com.vinodborole.portal.security.exceptions;

import org.springframework.security.core.AuthenticationException;

import com.vinodborole.portal.persistence.model.token.IPortalJwtToken;


/**
 * 
 * @author vinod borole
 *
 * 
 */
public class PortalJwtExpiredTokenException extends AuthenticationException {
    private static final long serialVersionUID = -5959543783324224864L;
    
    private IPortalJwtToken token;

    public PortalJwtExpiredTokenException(String msg) {
        super(msg);
    }

    public PortalJwtExpiredTokenException(IPortalJwtToken token, String msg, Throwable t) {
        super(msg, t);
        this.token = token;
    }

    public String token() {
        return this.token.getToken();
    }
}
