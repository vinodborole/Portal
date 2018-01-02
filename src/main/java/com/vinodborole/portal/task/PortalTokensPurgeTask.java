/**
 * 
 */
package com.vinodborole.portal.task;

import java.time.Instant;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vinodborole.portal.persistence.dao.PortalPasswordResetTokenRepository;
import com.vinodborole.portal.persistence.dao.PortalVerificationTokenRepository;

/**
 * @author vinodborole
 *
 */
@Service
@Transactional
public class PortalTokensPurgeTask {
	@Autowired
	PortalVerificationTokenRepository tokenRepository;

	@Autowired
	PortalPasswordResetTokenRepository passwordTokenRepository;

	@Scheduled(cron = "${portal.purge.cron.expression}")
	public void purgeExpired() {

		Date now = Date.from(Instant.now());

		passwordTokenRepository.deleteAllExpiredSince(now);
		tokenRepository.deleteAllExpiredSince(now);
	}
}
