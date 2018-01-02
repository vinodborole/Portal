package com.vinodborole.portal.security.auth;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Model intended to be used for REST based authentication.
 * 
 * @author vinod borole
 *
 * 
 */

public class PortalLoginRequest {
    private String username;
    private String password;

    @JsonCreator
    public PortalLoginRequest(@JsonProperty("username") String username, @JsonProperty("password") String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
