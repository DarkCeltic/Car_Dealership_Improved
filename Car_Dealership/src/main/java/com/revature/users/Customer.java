package com.revature.users;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.revature.load_page.LoadPage;
import com.revature.pojo.Car;
import com.revature.pojo.Fleet;
import com.revature.pojo.Users;

public class Customer implements Serializable {
	private static Logger log = Logger.getRootLogger();
	/**
	 * 
	 */
	private static final long serialVersionUID = -2902279992146909132L;
	private String firstName;
	private String lastName;
	private String username;
	private String password;
	private ArrayList<Car> myCars = new ArrayList<Car>();

//	public public Customer() {
//		super();
//	}

	public Customer(String firstName, String lastName, String username, String password) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
	}

	public Customer() {
		// TODO Auto-generated constructor stub
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

	public ArrayList<Car> getMyCars() {
		return myCars;
	}

	public void setMyCars(Car car) {
		myCars.add(car);
	}

	@Override
	public String toString() {
		return "Customer [firstName=" + firstName + ", lastName=" + lastName + ", username=" + username + ", myCars="
				+ myCars + "]";
	}

	public void CustomerOptions(Users users, Fleet fleet) {
		String option;
		Scanner input = new Scanner(System.in);
		System.out.println("Welcome " + this.firstName + " to your account.");
		System.out.println("Type 1 to see a list of vehicles available for sale");
		System.out.println("Type 2 to bid on a vehicle that you are interested in");
		System.out.println("Type 3 to see vehicles that you own");
		System.out.println("Type 4 to see how many payments that you have left on your vehicles");
		System.out.println("Type 5 to go back to the main login screen.");
		option = input.nextLine();
		customerDecision(users, fleet, option);
	}

	public void customerDecision(Users users, Fleet fleet, String option) {
		Scanner input = new Scanner(System.in);
		switch (option) {
		// List vehicles
		case "1":
			System.out.format("%5s %-35s %-35s | %-10s | %-15s | %-10s%n", "Year", "Make", "Model", "Mileage", "Price",
					"VIN #");
			for (Car c : fleet.getFleet()) {
				if (c.isSold() == false) {
					System.out.println(c);
				}
			}
			System.out.println();
			CustomerOptions(users, fleet);
			break;
		// Bid on Vehicle
		case "2":
			bid(users, fleet);
			System.out.println();
			CustomerOptions(users, fleet);
			break;
		// Vehicles customer owns
		case "3":
			if (myCars.isEmpty()) {
				System.out.println("You do not own any vehicles yet.");
			} else {
				System.out.format("%5s %-35s %-35s | %-10s | %-15s | %-10s%n", "Year", "Make", "Model", "Mileage",
						"Price", "VIN #");
				for (Car car : myCars) {
					System.out.println(car.toString());
				}
				System.out.println();
			}
			CustomerOptions(users, fleet);
			break;
		// How many payments left on vehicles
		case "4":
			if (myCars.isEmpty()) {
				System.out.println("You have not purchased any vehicles yet");
			} else {
				System.out.format("%5s %-35s %-35s | %-20s | %-15s%n", "Year", "Make", "Model", "Remaining Payments",
						"Monthly Payment");
				for (Car c : myCars) {
					System.out.format("%5s %-35s %-35s | %-20s | %-15s%n", c.getYear(), c.getMake(), c.getModel(),
							c.getRemainingPayments(), c.getMonthlyPayments());
				}
			}
			System.out.println();
			CustomerOptions(users, fleet);
			break;
		// Goto the login screen
		case "5":
			LoadPage login = new LoadPage();
			login.loginPage(users, fleet);
			break;
		default:
			System.out.println("That is not a vaild option, please try again");
			CustomerOptions(users, fleet);
			break;
		}
	}

	public void bid(Users users, Fleet fleet) {
		Scanner input = new Scanner(System.in);
		String carVIN, ofP;
		boolean validVIN = false;
		boolean tooHighPrice = false;
		boolean negativeTest = false;
		Double offerPrice = 0.0;
		do {
			validVIN = false;
			System.out.println("What is the VIN of the vehicle that you want to bid on");
			carVIN = input.nextLine();
			if (carVIN.isBlank())
				System.out.println("Enter a valid VIN");
			for (Car c : fleet.getFleet()) {
				if (!c.getVIN().equals(carVIN)) {
					validVIN = true;
				} else {
					break;
				}
			}
			if (validVIN)
				System.out.println("Enter a valid VIN");
		} while (carVIN.isBlank() || validVIN);
		do {
			tooHighPrice = false;
			negativeTest = false;
			System.out.println("How much do you want to offer for the vehicle");
			ofP = input.nextLine();
			if (ofP.isBlank() || ofP.equals("0") || ofP.equals("0.0"))
				System.out.println("Please enter a valid Price.");
			else {
				offerPrice = Double.parseDouble(ofP);
				for (Car c : fleet.getFleet()) {
					if (c.getVIN().equals(carVIN)) {
						if (c.getPrice() < offerPrice) {
							System.out.println("The price you entered is more than the value of the vehicle");
							tooHighPrice = true;
						} else if (offerPrice < 0) {
							System.out.println("Enter a valid price");
							negativeTest = true;
						}
					}
				}
			}
		} while (ofP.isBlank() || ofP.equals("0") || ofP.equals("0.0") || tooHighPrice || negativeTest);

		for (Car c : fleet.getFleet()) {
			if (c.getVIN().equals(carVIN)) {
				c.getOffers().put(this.username, offerPrice);
				log.info(this.username + " made an offer on " + c.toString());
				break;
			}
		}
	}
}
