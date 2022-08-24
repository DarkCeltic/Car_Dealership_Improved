package com.revature.dao;

import com.revature.pojo.Database;

public interface DatabaseDAO {

	public void createDatabase(Database data);

	public Database loadDatabase();
}
