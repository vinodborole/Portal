package com.vinodborole.portal.security.auth.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.vinodborole.portal.util.PortalEmailUtil;

@Component
public class PortalRegistrationListener implements ApplicationListener<PortalOnRegistrationCompleteEvent> {

	@Autowired
	private PortalEmailUtil emailUtil;

	@Override
	public void onApplicationEvent(final PortalOnRegistrationCompleteEvent event) {
		emailUtil.confirmRegistration(event.getUser(), event.getAppUrl());
	}

}
