package com.vinodborole.portal.persistence.model;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "portal_verification_token")
public class PortalVerificationToken {

	private static final int EXPIRATION = 60 * 24;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String token;

	@OneToOne(targetEntity = PortalUser.class)
	@JoinColumn(nullable = false, name = "USER_ID")
	private PortalUser user;

	private Date expiryDate;

	public PortalVerificationToken() {
		super();
	}

	public PortalVerificationToken(final String token) {
		super();

		this.token = token;
		this.expiryDate = calculateExpiryDate(EXPIRATION);
	}

	public PortalVerificationToken(final String token, final PortalUser user) {
		super();

		this.token = token;
		this.user = user;
		this.expiryDate = calculateExpiryDate(EXPIRATION);
	}

	public Long getId() {
		return id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(final String token) {
		this.token = token;
	}

	public PortalUser getUser() {
		return user;
	}

	public void setUser(final PortalUser user) {
		this.user = user;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(final Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	private Date calculateExpiryDate(final int expiryTimeInMinutes) {
		final Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(new Date().getTime());
		cal.add(Calendar.MINUTE, expiryTimeInMinutes);
		return new Date(cal.getTime().getTime());
	}

	public void updateToken(final String token) {
		this.token = token;
		this.expiryDate = calculateExpiryDate(EXPIRATION);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((expiryDate == null) ? 0 : expiryDate.hashCode());
		result = prime * result + ((token == null) ? 0 : token.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final PortalVerificationToken other = (PortalVerificationToken) obj;
		if (expiryDate == null) {
			if (other.expiryDate != null) {
				return false;
			}
		} else if (!expiryDate.equals(other.expiryDate)) {
			return false;
		}
		if (token == null) {
			if (other.token != null) {
				return false;
			}
		} else if (!token.equals(other.token)) {
			return false;
		}
		if (user == null) {
			if (other.user != null) {
				return false;
			}
		} else if (!user.equals(other.user)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("Token [String=").append(token).append("]").append("[Expires").append(expiryDate).append("]");
		return builder.toString();
	}

}
