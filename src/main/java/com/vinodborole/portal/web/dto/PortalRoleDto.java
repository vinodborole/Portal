/**
 * 
 */
package com.vinodborole.portal.web.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * @author vinodborole
 *
 */
public class PortalRoleDto {

	private String name;

	private List<String> privileges = new ArrayList<String>();

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the privileges
	 */
	public List<String> getPrivileges() {
		return privileges;
	}

	/**
	 * @param privileges
	 *            the privileges to set
	 */
	public void setPrivileges(List<String> privileges) {
		this.privileges = privileges;
	}

}
