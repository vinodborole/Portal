package com.vinodborole.portal.persistence.model;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "PORTAL_ROLE")
public class PortalRole {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToMany(mappedBy = "roles")
	@JsonBackReference
	private Collection<PortalUser> users;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "PORTAL_ROLES_PRIVILEGES", joinColumns = @JoinColumn(name = "ROLE_ID", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "PRIVILEGE_ID", referencedColumnName = "ID"))
	@JsonManagedReference
	private Collection<PortalPrivilege> privileges;

	private String name;

	public PortalRole() {
		super();
	}

	public PortalRole(final String name) {
		super();
		this.name = name;
	}

	//

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public Collection<PortalUser> getUsers() {
		return users;
	}

	public void setUsers(final Collection<PortalUser> users) {
		this.users = users;
	}

	public Collection<PortalPrivilege> getPrivileges() {
		return privileges;
	}

	public void setPrivileges(final Collection<PortalPrivilege> privileges) {
		this.privileges = privileges;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		final PortalRole role = (PortalRole) obj;
		if (!role.equals(role.name)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("Role [name=").append(name).append("]").append("[id=").append(id).append("]");
		return builder.toString();
	}
}