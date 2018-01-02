/**
 * 
 */
package com.vinodborole.portal.util;

import java.util.Map;

import javax.mail.internet.AddressException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.google.common.collect.ImmutableMap;
import com.vinodborole.portal.email.PortalEmail;
import com.vinodborole.portal.email.PortalEmailClient;
import com.vinodborole.portal.email.PortalEmailTemplate;
import com.vinodborole.portal.persistence.model.PortalPasswordResetToken;
import com.vinodborole.portal.persistence.model.PortalUser;
import com.vinodborole.portal.persistence.model.PortalVerificationToken;
import com.vinodborole.portal.service.IPortalTokenService;

import it.ozimov.springboot.mail.service.exception.CannotSendEmailException;

/**
 * @author vinodborole
 *
 */
@Component
public class PortalEmailUtil {

	@Autowired
	private IPortalTokenService service;

	@Autowired
	private Environment env;

	@Autowired
	private PortalEmailClient emailClient;

	public void confirmRegistration(PortalUser user, String appUrl) {
		PortalVerificationToken token = service.createVerificationTokenForUser(user);
		String verifyEmailURL = appUrl + "/user/registrationConfirm/" + token.getToken();
		Map<String, Object> content = ImmutableMap.of("verifyEmailURL", verifyEmailURL, "name", user.getFirstName());
		PortalEmail verificationEmail = PortalEmail.builder().from(env.getProperty("portal.support.email"))
				.to(user.getUsername()).subject("Portal Registration Confirmation")
				.template(PortalEmailTemplate.VERIFY_ACCOUNT).templateContent(content).build();
		try {
			emailClient.sendMimeEmailWithTemplate(verificationEmail);
		} catch (AddressException | CannotSendEmailException e) {
			e.printStackTrace();
		}
	}

	public void resetPasswordEmail(PortalUser user, String appUrl) {
		PortalPasswordResetToken token = service.createPasswordResetTokenForUser(user);
		String changePasswordURL = appUrl + "/user/changepassword/" + token.getToken();
		Map<String, Object> content = ImmutableMap.of("changePasswordURL", changePasswordURL, "name",
				user.getFirstName());
		PortalEmail resetPasswordEmail = PortalEmail.builder().from(env.getProperty("portal.support.email"))
				.to(user.getUsername()).subject("Portal Reset Password").template(PortalEmailTemplate.RESET_PASSWORD)
				.templateContent(content).build();
		try {
			emailClient.sendMimeEmailWithTemplate(resetPasswordEmail);
		} catch (AddressException | CannotSendEmailException e) {
			e.printStackTrace();
		}
	}
}
