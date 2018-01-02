package com.vinodborole.portal.security.config;

import java.security.Key;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.vinodborole.portal.persistence.model.token.IPortalJwtToken;

import io.jsonwebtoken.impl.crypto.MacProvider;

@Configuration
@ConfigurationProperties(prefix = "portal.security.jwt")
public class PortalJwtSettings {

	/**
	 * {@link IPortalJwtToken} will expire after this time.
	 */
	private Integer tokenExpirationTime;

	/**
	 * Token issuer.
	 */
	private String tokenIssuer;

	/**
	 * Key is used to sign {@link IPortalJwtToken}.
	 */
	private String tokenSigningKey;

	private Key autoGeneratedTokenSigningKey = MacProvider.generateKey();

	/**
	 * {@link IPortalJwtToken} can be refreshed during this timeframe.
	 */
	private Integer refreshTokenExpTime;

	/**
	 * @return the autoGeneratedTokenSigningKey
	 */
	public Key getAutoGeneratedTokenSigningKey() {
		return autoGeneratedTokenSigningKey;
	}

	public Integer getRefreshTokenExpTime() {
		return refreshTokenExpTime;
	}

	public void setRefreshTokenExpTime(Integer refreshTokenExpTime) {
		this.refreshTokenExpTime = refreshTokenExpTime;
	}

	public Integer getTokenExpirationTime() {
		return tokenExpirationTime;
	}

	public void setTokenExpirationTime(Integer tokenExpirationTime) {
		this.tokenExpirationTime = tokenExpirationTime;
	}

	public String getTokenIssuer() {
		return tokenIssuer;
	}

	public void setTokenIssuer(String tokenIssuer) {
		this.tokenIssuer = tokenIssuer;
	}

	public String getTokenSigningKey() {
		return tokenSigningKey;
	}

	public void setTokenSigningKey(String tokenSigningKey) {
		this.tokenSigningKey = tokenSigningKey;
	}
}