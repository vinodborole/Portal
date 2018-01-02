package com.vinodborole.portal.persistence.model.token;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;

/**
 * 
 * @author vinod borole
 *
 * 
 */
public class PortalUserContext {
	private final String username;
	private final List<GrantedAuthority> authorities;

	private PortalUserContext(String username, List<GrantedAuthority> authorities) {
		this.username = username;
		this.authorities = authorities;
	}

	public static PortalUserContext create(String username, List<GrantedAuthority> authorities) {
		if (StringUtils.isBlank(username))
			throw new IllegalArgumentException("Username is blank: " + username);
		return new PortalUserContext(username, authorities);
	}

	public String getUsername() {
		return username;
	}

	public List<GrantedAuthority> getAuthorities() {
		return authorities;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((authorities == null) ? 0 : authorities.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PortalUserContext other = (PortalUserContext) obj;
		if (authorities == null) {
			if (other.authorities != null)
				return false;
		} else if (!authorities.equals(other.authorities))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

}
