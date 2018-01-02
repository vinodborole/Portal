package com.vinodborole.portal.persistence.dao;

import java.util.Date;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.vinodborole.portal.persistence.model.PortalPasswordResetToken;
import com.vinodborole.portal.persistence.model.PortalUser;

public interface PortalPasswordResetTokenRepository extends JpaRepository<PortalPasswordResetToken, Long> {

	PortalPasswordResetToken findByToken(String token);

	PortalPasswordResetToken findByUser(PortalUser user);

	Stream<PortalPasswordResetToken> findAllByExpiryDateLessThan(Date now);

	void deleteByExpiryDateLessThan(Date now);

	@Modifying
	@Query(value = "DELETE FROM portal_password_reset_token t WHERE t.expiry_date <= ?1", nativeQuery = true)
	void deleteAllExpiredSince(Date now);
}
