/**
 * 
 */
package com.vinodborole.portal.service;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.vinodborole.portal.persistence.dao.PortalPasswordResetTokenRepository;
import com.vinodborole.portal.persistence.dao.PortalUserRepository;
import com.vinodborole.portal.persistence.dao.PortalVerificationTokenRepository;
import com.vinodborole.portal.persistence.model.PortalPasswordResetToken;
import com.vinodborole.portal.persistence.model.PortalUser;
import com.vinodborole.portal.persistence.model.PortalVerificationToken;
import com.vinodborole.portal.persistence.model.token.PortalJwtTokenFactory;
import com.vinodborole.portal.persistence.model.token.PortalUserContext;
import com.vinodborole.portal.persistence.model.token.IPortalJwtToken;
import com.vinodborole.portal.web.error.TokenExpiredExcepetion;

/**
 * @author vinodborole
 *
 */
@Service
@Transactional
public class PortalTokenService implements IPortalTokenService {

	@Autowired
	private PortalPasswordResetTokenRepository passwordResetTokenRepository;

	@Autowired
	private PortalVerificationTokenRepository verificationTokenRepository;

	@Autowired
	private PortalUserRepository userRepository;

	@Autowired
	private PortalJwtTokenFactory tokenFactory;

	public static final String TOKEN_INVALID = "invalidToken";
	public static final String TOKEN_EXPIRED = "expired";
	public static final String TOKEN_VALID = "valid";

	@Override
	public PortalVerificationToken createVerificationTokenForUser(final PortalUser user) {
		final String token = UUID.randomUUID().toString();
		final PortalVerificationToken myToken = new PortalVerificationToken(token, user);
		return verificationTokenRepository.save(myToken);
	}

	@Override
	public PortalVerificationToken generateNewVerificationToken(final String existingVerificationToken) {
		PortalVerificationToken vToken = verificationTokenRepository.findByToken(existingVerificationToken);
		// Check for token not found
		vToken.updateToken(UUID.randomUUID().toString());
		vToken = verificationTokenRepository.save(vToken);
		return vToken;
	}

	@Override
	public PortalVerificationToken getVerificationToken(final String VerificationToken) {
		return verificationTokenRepository.findByToken(VerificationToken);
	}

	@Override
	public String validateVerificationToken(final String token) {
		final PortalVerificationToken verificationToken = verificationTokenRepository.findByToken(token);
		if (verificationToken == null) {
			return TOKEN_INVALID;
		}
		final PortalUser user = verificationToken.getUser();
		final Calendar cal = Calendar.getInstance();
		if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
			verificationTokenRepository.delete(verificationToken);
			return TOKEN_EXPIRED;
		}
		user.setEnabled(true);
		userRepository.save(user);
		return TOKEN_VALID;
	}

	@Override
	public PortalPasswordResetToken createPasswordResetTokenForUser(final PortalUser user) {
		final String token = UUID.randomUUID().toString();
		final PortalPasswordResetToken myToken = new PortalPasswordResetToken(token, user);
		return passwordResetTokenRepository.save(myToken);
	}

	@Override
	public PortalPasswordResetToken getPasswordResetToken(final String token) {
		return passwordResetTokenRepository.findByToken(token);
	}

	@Override
	public String validatePasswordResetToken(final String token) throws TokenExpiredExcepetion {
		PortalPasswordResetToken portalPasswordResetToken = passwordResetTokenRepository.findByToken(token);
		final Calendar cal = Calendar.getInstance();
		if ((portalPasswordResetToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
			throw new TokenExpiredExcepetion("Token Expired");
		}
		PortalUser user = portalPasswordResetToken.getUser();
		List<GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("CHANGE_PASSWORD_PRIVILEGE"));
		PortalUserContext userContext = PortalUserContext.create(user.getUsername(), authorities);
		try {
			IPortalJwtToken accessToken = tokenFactory.createAccessJwtToken(userContext);
			return accessToken.getToken(); // create temporary access token just for changing password
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

}
