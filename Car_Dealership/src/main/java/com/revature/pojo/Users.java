package com.revature.pojo;

import java.io.Serializable;
import java.util.ArrayList;

import com.revature.users.Customer;
import com.revature.users.Employee;

public class Users implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3603195485041015456L;

	ArrayList<Customer> customers = new ArrayList<Customer>();
	ArrayList<Employee> employees = new ArrayList<Employee>();

	public ArrayList<Customer> getCustomers() {
		return customers;
	}

	public void setCustomers(Customer cus) {
		customers.add(cus);
	}

	public ArrayList<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(Employee emp) {
		employees.add(emp);
	}
}
