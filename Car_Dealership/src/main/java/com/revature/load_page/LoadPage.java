package com.revature.load_page;

import java.util.Scanner;

import org.apache.log4j.Logger;

import com.revature.dao.*;
import com.revature.pojo.Database;
import com.revature.pojo.Fleet;
import com.revature.pojo.Users;
import com.revature.users.Customer;
import com.revature.users.Employee;

public class LoadPage {

	DatabaseDAO dat = new DatabaseSerializationDAO();
	Scanner input = new Scanner(System.in);
	private static Logger log = Logger.getRootLogger();

	public void loginPage(Users users, Fleet fleet) {

		String option = "";

		System.out.println(fleet.getFleet());

		while (!option.equals("3")) {
			System.out.println("Welcome to the Tattoine Used Starfighter Emporium");
			System.out.println("Please select an option to continue.");
			System.out.println("Type 1 to login.");
			System.out.println("Type 2 to Register for an account");
			System.out.println("Type 3 to exit this website");

			option = input.nextLine();
			switch (option) {
			// Login
			case "1":
				login(users, fleet);
				break;
			// Register
			case "2":
				register(users, fleet);
				break;
			// Exit
			case "3":
				saveData(users, fleet);
				input.close();
				System.out.println("Thank you for checking out Tattoine Used Starfighter Emporium, please come again!");
				System.exit(0);
				break;
			default:
				System.out.println("That is not an option. Please try again.");
				break;
			}
		}
	}

	public void login(Users users, Fleet fleet) {
		String username = "", password = "";

		String option = "";
		System.out.println("Welcome to the login page.");
		System.out.println("Please select from an option below.");
		System.out.println("Type 1 if you are a customer.");
		System.out.println("Type 2 if you are an Employee. ");
		System.out.println("Type 3 if you want to exit to the previous screen");
		option = input.nextLine();
		if (option.equals("1") || option.equals("2")) {
			System.out.println("Enter your username");
			username = input.nextLine();
			System.out.println("Enter your password");
			password = input.nextLine();
		}
		switch (option) {
		// Customer Login
		case "1":
			for (Customer cus : users.getCustomers()) {
				System.out.print(cus.getUsername());
				System.out.println("\t " + cus.getPassword());
				if (cus.getUsername().equalsIgnoreCase(username) && cus.getPassword().equals(password)) {
					log.info(username + " logged in");
					cus.CustomerOptions(users, fleet);
				}
			}
			log.info("Sorry, your information was incorrect");
			break;
		// Employee Login
		case "2":
			for (Employee emp : users.getEmployees()) {
				System.out.print(emp.getUsername());
				System.out.println("\t " + emp.getPassword());
				if (emp.getUsername().equalsIgnoreCase(username) && emp.getPassword().equals(password)) {
					log.info(username + " logged in");
					emp.employeeDecision(users, fleet);
				}
			}
			log.info("Sorry, your information was incorrect");
			break;
		// Previous Screen
		case "3":
			loginPage(users, fleet);
			break;
		default:
			System.out.println("That is not an option. Please try again.");
			login(users, fleet);
			break;
		}
	}

	public void register(Users users, Fleet fleet) {
		input = new Scanner(System.in);
		boolean usrnmeUnavailable = false;
		String first, last, userName, password;
		System.out.println("Welcome to the registration page");
		do {
			System.out.println("Please enter your first name");
			first = input.nextLine();
			if (first.isBlank())
				System.out.println("Please enter a value");
		} while (first.isBlank());
		do {
			System.out.println("Please enter your last name");
			last = input.nextLine();
			if (last.isBlank())
				System.out.println("Please enter a value");
		} while (last.isBlank());
		do {
			usrnmeUnavailable = false;
			System.out.println("Please enter a Username");
			userName = input.nextLine();
			if (userName.isBlank())
				System.out.println("Please enter a value");
			for (Customer cus : users.getCustomers()) {
				if (userName.equals(cus.getUsername())) {
					System.out.println("This username already exists, please try again");
					usrnmeUnavailable = true;
				}

			}
		} while (userName.isBlank() || usrnmeUnavailable);
		do {
			System.out.println("Please enter a password, this is case sensitive.");
			password = input.nextLine();
			if (password.isBlank())
				System.out.println("Please enter a value");
		} while (password.isBlank());
		Customer cust = new Customer(first, last, userName, password);
		users.setCustomers(cust);
		log.info(cust + " created");
		System.out.println("\nYour account has been created " + cust.getFirstName());

		cust.CustomerOptions(users, fleet);
	}

	public void saveData(Users user, Fleet fleet) {
		Database data = new Database(user, fleet);
		dat.createDatabase(data);
	}

	public void loadData() {
		Database data;
		data = dat.loadDatabase();
		loginPage(data.getUsers(), data.getFleet());
	}
}
