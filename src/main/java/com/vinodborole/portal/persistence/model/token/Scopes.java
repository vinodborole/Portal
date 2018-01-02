package com.vinodborole.portal.persistence.model.token;

/**
 * Scopes
 * 
 * @author vinod borole
 *
 * 
 */
public enum Scopes {
    REFRESH_TOKEN;
    
    public String authority() {
        return "ROLE_" + this.name();
    }
}
