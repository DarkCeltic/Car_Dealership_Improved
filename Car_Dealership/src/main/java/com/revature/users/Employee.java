package com.revature.users;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.revature.load_page.LoadPage;
import com.revature.pojo.Car;
import com.revature.pojo.Fleet;
import com.revature.pojo.Users;

public class Employee implements Serializable {
	private static Logger log = Logger.getRootLogger();
	/**
	 * 
	 */
	private static final long serialVersionUID = -5370445995563054109L;
	private String firstName;
	private String lastName;
	private String username;
	private String password;

	public Employee() {
		super();
	}

	public Employee(String firstName, String lastName, String username, String password) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
	}

	@Override
	public String toString() {
		return "Employee [firstName=" + firstName + ", lastName=" + lastName + ", username=" + username + "]";
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

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

	public void EmployeeOptions() {
		System.out.println("Welcome " + this.firstName + " to your account.");
		System.out.println("Type 1 to see a list of vehicles");
		System.out.println("Type 2 to see offers for vehicles");
		System.out.println("Type 3 to accept or reject an offer for a vehicle");
		System.out.println("Type 4 to add a vehicle");
		System.out.println("Type 5 to remove a vehicle");
		System.out.println("Type 6 to view all payments");
		System.out.println("Type 7 to return to the login page.");
	}

	public void employeeDecision(Users users, Fleet fleet) {
		Scanner input = new Scanner(System.in);
		EmployeeOptions();
		String option = input.nextLine();
		while (!option.equals("7")) {
			switch (option) {
			// List Vehicles
			case "1":
				System.out.format("%5s %-35s %-35s | %-10s | %-15s | %-10s%n", "Year", "Make", "Model", "Mileage",
						"Price", "VIN #");
				for (Car c : fleet.getFleet()) {
					System.out.println(c);
				}
				EmployeeOptions();
				option = input.nextLine();
				break;
			// See Offers
			case "2":
				System.out.format("%-6s | %5s %-35s %-35s |%-15s | %-15s | %-10s%n", "User", "Year", "Make", "Model",
						"Offer Price", "Original Price", "VIN #");
				for (Car c : fleet.getFleet()) {
					if (!c.getOffers().isEmpty()) {
						c.toStringOffer();
					}
				}
				System.out.println();
				EmployeeOptions();
				option = input.nextLine();
				break;
			// Accept or Reject offer
			case "3":
				System.out.println("To accept an offer Type 1");
				System.out.println("To reject an offer Type 2");
				System.out.println("To retrun to the previous menu Type 3");
				option = input.nextLine();
				fleet = acceptOrRejectoffer(option, users, fleet);
				System.out.println();
				EmployeeOptions();
				option = input.nextLine();
				break;
			// Add Vehicle
			case "4":
				addVehicle(users, fleet);
				System.out.println();
				EmployeeOptions();
				option = input.nextLine();
				break;
			// Remove Vehicle
			case "5":
				fleet = removeVehicle(users, fleet);
				System.out.println();
				EmployeeOptions();
				option = input.nextLine();
				break;
			// View All Payments
			case "6":
				System.out.format("%6s | %5s %-35s %-35s | %-20s | %-15s%n", "User", "Year", "Make", "Model",
						"Remaining Payments", "Monthly Payment");
				for (Customer e : users.getCustomers()) {
					if (!e.getMyCars().isEmpty()) {
						for (Car c : e.getMyCars()) {
							System.out.format("%6s | %5s %-35s %-35s | %-20s | %-15s%n", e.getUsername(), c.getYear(),
									c.getMake(), c.getModel(), c.getRemainingPayments(), c.getMonthlyPayments());
						}
					}
				}
				System.out.println();
				EmployeeOptions();
				option = input.nextLine();
				break;
			default:
				System.out.println("That is not a valid option, please select again");
				break;
			}
		}
		LoadPage login = new LoadPage();
		login.loginPage(users, fleet);
	}

	public Fleet acceptOrRejectoffer(String option, Users users, Fleet fleet) {
		Scanner input = new Scanner(System.in);
		String vin, username;
		System.out.println("What is the VIN of the vehicle offer");
		vin = input.nextLine();
		System.out.println("What is the username of the vehicle offer");
		username = input.nextLine();
		switch (option) {
		// Accept Offer
		case "1":
			for (Car c : fleet.getFleet()) {
				if (c.getVIN().equals(vin)) {
					for (Customer cus : users.getCustomers()) {
						if (cus.getUsername().equalsIgnoreCase(username)) {
							c.setSold(true);
							c.setPrice(c.getOffers().get(username));
							cus.setMyCars(c);
							c.setOwner(cus);
							c.getOffers().clear();
							fleet.removeCar(c);
							log.info(c.toString() + " was sold to customer " + cus.toString());
							return fleet;
						}
					}
				}
			}
			System.out.println("There is no vehicle with VIN " + vin);
			return fleet;
		// Remove Offer
		case "2":
			for (Car c : fleet.getFleet()) {
				if (c.getOffers().containsKey(username)) {
					c.getOffers().remove(username);
					log.info("Offers were removed for user " + username);
				}
			}
			return fleet;
		case "3":
			break;
		default:
			System.out.println("That is not a valid option, please try again");
			break;
		}
		return fleet;
	}

	public Fleet addVehicle(Users users, Fleet fleet) {
		Scanner input = new Scanner(System.in);
		double price = 0.0;
		boolean negative = false;
		String vIN, make, model, mileage, year, pce;
		do {
			System.out.println("Please enter the VIN of the vehicle.");
			vIN = input.nextLine();
			for (Car c : fleet.getFleet()) {
				if (c.getVIN().equals(vIN)) {
					System.out.println("There is already a vehicle with this VIN, please try again");
				}
			}
			if (vIN.isBlank())
				System.out.println("Please enter a valid VIN.");
		} while (vIN.isBlank());
		do {
			System.out.println("Please enter the Year of the vehicle.");
			year = input.nextLine();
			if (year.isBlank())
				System.out.println("Please enter a valid Year.");
		} while (year.isBlank());
		do {
			System.out.println("Please enter the Make of the vehicle.");
			make = input.nextLine();
			if (make.isBlank())
				System.out.println("Please enter a valid Make.");
		} while (make.isBlank());
		do {
			System.out.println("Please enter the Model of the vehicle.");
			model = input.nextLine();
			if (model.isBlank())
				System.out.println("Please enter a valid Model.");
		} while (model.isBlank());
		do {
			negative = false;
			System.out.println("Please enter the Mileage of the vehicle.");
			mileage = input.nextLine();
			if (mileage.isBlank())
				System.out.println("Please enter a valid Mileage.");
			else if (Double.parseDouble(mileage) < 0) {
				System.out.println("Please enter a valid Mileage.");
				negative = true;
			}
		} while (mileage.isBlank() || negative);
		do {
			System.out.println("Please enter the Price of the vehicle.");
			pce = input.nextLine();
			if (pce.isBlank() || pce.equals("0") || pce.equals("0.0"))
				System.out.println("Please enter a valid Price.");
			else
				price = Double.parseDouble(pce);
		} while (pce.isBlank() || pce.equals("0") || price <= 0.0);
		Car c = new Car(price, vIN, make, model, mileage, year);
		fleet.setFleet(c);
		log.info(c.toString() + " was created by " + this.username);
		return fleet;
	}

	public Fleet removeVehicle(Users users, Fleet fleet) {
		Scanner input = new Scanner(System.in);
		String vin;
		System.out.println("Please enter the VIN of the vehicle you would like to remove");
		vin = input.nextLine();
		for (Car c : fleet.getFleet()) {
			if (c.getVIN().equals(vin)) {
				fleet.removeCar(c);
				log.info(c.toString() + " was removed by employee " + this.username);
				return fleet;
			}
		}
		System.out.println("The car with VIN " + vin + " was not found");
		return fleet;
	}
}