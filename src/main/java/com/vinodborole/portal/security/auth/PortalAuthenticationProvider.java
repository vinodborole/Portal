package com.vinodborole.portal.security.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.vinodborole.portal.persistence.dao.PortalUserRepository;
import com.vinodborole.portal.persistence.model.PortalPrivilege;
import com.vinodborole.portal.persistence.model.PortalRole;
import com.vinodborole.portal.persistence.model.PortalUser;
import com.vinodborole.portal.persistence.model.token.PortalUserContext;
import com.vinodborole.portal.security.auth.listener.PortalLoginAttemptService;

/**
 * 
 * @author vinod borole
 *
 * 
 */
@Component
public class PortalAuthenticationProvider implements AuthenticationProvider {
	private BCryptPasswordEncoder encoder;
	@Autowired
	private PortalUserRepository repository;

	@Autowired
	private PortalLoginAttemptService loginAttemptService;
	@Autowired
	private HttpServletRequest request;

	@Autowired
	public void setProjectRepository(final BCryptPasswordEncoder encoder) {
		this.encoder = encoder;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		Assert.notNull(authentication, "No authentication data provided");
		String ip = getClientIP();

		if (loginAttemptService.isBlocked(ip)) {
			throw new LockedException(
					"Login attempt exceeded. User account locked temporarily. Please try again tomorrow.");
		}

		String username = (String) authentication.getPrincipal();
		String password = (String) authentication.getCredentials();

		PortalUser user = repository.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("User not found: " + username);
		}

		if (!encoder.matches(password, user.getPassword())) {
			throw new BadCredentialsException("Authentication Failed. Username or Password not valid.");
		}

		if (user.getRoles() == null)
			throw new InsufficientAuthenticationException("User has no roles assigned");

		List<GrantedAuthority> authorities = getAuthorities(user.getRoles());

		PortalUserContext userContext = PortalUserContext.create(user.getUsername(), authorities);

		return new UsernamePasswordAuthenticationToken(userContext, null, userContext.getAuthorities());
	}

	private String getClientIP() {
		String xfHeader = request.getHeader("X-Forwarded-For");
		if (xfHeader == null) {
			return request.getRemoteAddr();
		}
		return xfHeader.split(",")[0];
	}

	private List<GrantedAuthority> getAuthorities(Collection<PortalRole> roles) {

		return getGrantedAuthorities(getPrivileges(roles));
	}

	private List<String> getPrivileges(Collection<PortalRole> roles) {
		List<String> privileges = new ArrayList<>();
		List<PortalPrivilege> collection = new ArrayList<>();
		for (PortalRole role : roles) {
			collection.addAll(role.getPrivileges());
		}
		for (PortalPrivilege item : collection) {
			privileges.add(item.getName());
		}
		return privileges;
	}

	private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		for (String privilege : privileges) {
			authorities.add(new SimpleGrantedAuthority(privilege));
		}
		return authorities;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
	}

}
