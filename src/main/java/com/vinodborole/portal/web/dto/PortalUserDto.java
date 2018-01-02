/**
 * 
 */
package com.vinodborole.portal.web.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author vinodborole
 *
 */
public class PortalUserDto {
	@NotNull
	@Size(min = 1)
	private String firstName;
	@NotNull
	@Size(min = 1)
	private String lastName;
	private String username;
	private String password;
	private String accoutName;

	private List<String> roleNames = new ArrayList<String>();

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName
	 *            the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName
	 *            the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the accoutName
	 */
	public String getAccoutName() {
		return accoutName;
	}

	/**
	 * @param accoutName
	 *            the accoutName to set
	 */
	public void setAccoutName(String accoutName) {
		this.accoutName = accoutName;
	}

	/**
	 * @return the roleNames
	 */
	public List<String> getRoleNames() {
		return roleNames;
	}

	/**
	 * @param roleNames
	 *            the roleNames to set
	 */
	public void setRoleNames(List<String> roleNames) {
		this.roleNames = roleNames;
	}

}
