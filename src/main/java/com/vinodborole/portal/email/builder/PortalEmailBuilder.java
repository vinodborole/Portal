/**
 * 
 */
package com.vinodborole.portal.email.builder;

import java.util.Map;

import com.vinodborole.portal.common.Buildable.Builder;
import com.vinodborole.portal.email.PortalEmail;
import com.vinodborole.portal.email.PortalEmailTemplate;

/**
 * @author vinodborole
 *
 */
public interface PortalEmailBuilder extends Builder<PortalEmailBuilder, PortalEmail> {
	PortalEmailBuilder from(String from);
	PortalEmailBuilder to(String to);
	PortalEmailBuilder subject(String subject);
	PortalEmailBuilder body(String body);
	PortalEmailBuilder template(PortalEmailTemplate template);
	PortalEmailBuilder templateContent(Map<String, Object> content);
}
