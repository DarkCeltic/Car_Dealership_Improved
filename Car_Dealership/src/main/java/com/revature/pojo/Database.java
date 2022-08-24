package com.revature.pojo;

import java.io.Serializable;

public class Database implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4389762516703326584L;
	private Users users;
	private Fleet fleet;

	public Database(Users users, Fleet fleet) {
		super();
		this.users = users;
		this.fleet = fleet;
	}

	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	public Fleet getFleet() {
		return fleet;
	}

	public void setFleet(Fleet fleet) {
		this.fleet = fleet;
	}

}
