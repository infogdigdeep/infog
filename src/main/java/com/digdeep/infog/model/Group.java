package com.digdeep.infog.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="infoggroup")
public class Group {

	
	@ManyToMany (cascade=CascadeType.ALL)
	@JoinTable(name="usergroup", joinColumns={@JoinColumn(name="groupId")}, inverseJoinColumns={@JoinColumn(name="userId")})
	private List<User> users;
	
	@Id
	private String groupname;


	public List<User> getUsers() {
		return users;
	}


	public void setUsers(List<User> users) {
		this.users = users;
	}


	public String getGroupname() {
		return groupname;
	}


	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}
	
	
	
}
