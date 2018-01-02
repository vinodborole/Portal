package com.vinodborole.portal.persistence.dao;

import java.util.Date;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.vinodborole.portal.persistence.model.PortalUser;
import com.vinodborole.portal.persistence.model.PortalVerificationToken;

public interface PortalVerificationTokenRepository extends JpaRepository<PortalVerificationToken, Long> {

	PortalVerificationToken findByToken(String token);

	PortalVerificationToken findByUser(PortalUser user);

	Stream<PortalVerificationToken> findAllByExpiryDateLessThan(Date now);

	void deleteByExpiryDateLessThan(Date now);

	@Modifying
	@Query(value = "DELETE FROM portal_verification_token t WHERE t.expiry_date <= ?1", nativeQuery = true)
	void deleteAllExpiredSince(Date now);
}
