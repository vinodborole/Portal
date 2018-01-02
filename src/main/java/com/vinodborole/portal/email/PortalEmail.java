package com.vinodborole.portal.email;

import java.util.Map;

import com.vinodborole.portal.common.Buildable;
import com.vinodborole.portal.email.builder.PortalEmailBuilder;

/** 
 * @author vinodborole
 *
 */
public class PortalEmail implements Buildable<PortalEmailBuilder> {

	private String from;
	private String to;
	private String subject;
	private String body;
	private PortalEmailTemplate template;
	private Map<String, Object> templateContent;
	
	/**
	 * @return the from
	 */
	public String getFrom() {
		return from;
	}

	/**
	 * @return the to
	 */
	public String getTo() {
		return to;
	}

	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * @return the body
	 */
	public String getBody() {
		return body;
	}

	/**
	 * @return the template
	 */
	public PortalEmailTemplate getTemplate() {
		return template;
	}
	
	/**
	 * @return the templateContent
	 */
	public Map<String, Object> getTemplateContent() {
		return templateContent;
	}

	public static PortalEmailBuilder builder() {
		return new PortalEmailConcreteBuilder();
	}
	
	@Override
	public PortalEmailBuilder toBuilder() { 
		return new PortalEmailConcreteBuilder(this);
	}  
    
	public static class PortalEmailConcreteBuilder implements PortalEmailBuilder {

		private PortalEmail email;
		
		PortalEmailConcreteBuilder() {
			this(new PortalEmail());
		}
		
		public PortalEmailConcreteBuilder(PortalEmail cesEmail) {
			this.email=cesEmail;
		}

		@Override
		public PortalEmail build() {
			return email;
		}

		@Override
		public PortalEmailBuilder from(PortalEmail in) {
			email = (PortalEmail) in;
			return this;
		}

		@Override
		public PortalEmailBuilder from(String from) {
			email.from=from;
			return this;
		}

		@Override
		public PortalEmailBuilder to(String to) {
			email.to=to;
			return this;
		}

		@Override
		public PortalEmailBuilder subject(String subject) {
			email.subject=subject;
			return this;
		}

		@Override
		public PortalEmailBuilder body(String body) {
			email.body=body;
			return this;
		}

		@Override
		public PortalEmailBuilder template(PortalEmailTemplate template) {
			email.template=template;
			return this;
		}

		@Override
		public PortalEmailBuilder templateContent(Map<String, Object> content) {
			email.templateContent=content;
			return this;
		}
	}
}
