/**
 * 
 */
package com.vinodborole.portal.service;

import com.vinodborole.portal.persistence.model.PortalPasswordResetToken;
import com.vinodborole.portal.persistence.model.PortalUser;
import com.vinodborole.portal.persistence.model.PortalVerificationToken;
import com.vinodborole.portal.web.error.TokenExpiredExcepetion;

/**
 * @author vinodborole
 *
 */
public interface IPortalTokenService {

	public PortalVerificationToken createVerificationTokenForUser(final PortalUser user);

	public PortalVerificationToken generateNewVerificationToken(final String existingVerificationToken);

	public PortalVerificationToken getVerificationToken(final String VerificationToken);

	public String validateVerificationToken(final String token);

	public PortalPasswordResetToken createPasswordResetTokenForUser(final PortalUser user);

	public PortalPasswordResetToken getPasswordResetToken(final String token);

	public String validatePasswordResetToken(final String token) throws TokenExpiredExcepetion;

}
