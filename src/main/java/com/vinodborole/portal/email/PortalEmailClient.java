/**
 * 
 */
package com.vinodborole.portal.email;

import java.io.UnsupportedEncodingException;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

import it.ozimov.springboot.mail.model.Email;
import it.ozimov.springboot.mail.model.defaultimpl.DefaultEmail;
import it.ozimov.springboot.mail.service.EmailService;
import it.ozimov.springboot.mail.service.exception.CannotSendEmailException;

/**
 * @author vinodborole
 *
 */
@Component
public class PortalEmailClient {

	@Autowired
	private EmailService emailService;

	public void sendEmailWithoutTemplating(PortalEmail portalEmail) throws UnsupportedEncodingException, AddressException {
		final Email email = DefaultEmail.builder().from(new InternetAddress(portalEmail.getFrom()))
				.to(Lists.newArrayList(new InternetAddress(portalEmail.getTo()))).subject(portalEmail.getSubject())
				.body(portalEmail.getBody() == null ? "" : portalEmail.getBody()).encoding("UTF-8").build();
		emailService.send(email);
	}

	public void sendMimeEmailWithTemplate(PortalEmail portalEmail) throws AddressException, CannotSendEmailException {
		final Email email = DefaultEmail.builder().from(new InternetAddress(portalEmail.getFrom()))
				.to(Lists.newArrayList(new InternetAddress(portalEmail.getTo()))).subject(portalEmail.getSubject())
				.body(portalEmail.getBody() == null ? "" : portalEmail.getBody()).encoding("UTF-8").build();
		String template = portalEmail.getTemplate().toString();

		emailService.send(email, template, portalEmail.getTemplateContent());
	}
}
