package com.digdeep.infog.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="infoguser")
public class User {
	
	@Id
	private String username;
	
	private String password;
	
	private String email;
	
	@ManyToMany(cascade=CascadeType.ALL)
	@JoinTable(name="usergroup", joinColumns={@JoinColumn(name="userId")}, inverseJoinColumns={@JoinColumn(name="groupId")})
	private List<Group> group;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Group> getGroup() {
		return group;
	}

	public void setGroup(List<Group> group) {
		this.group = group;
	}


	
	
}
