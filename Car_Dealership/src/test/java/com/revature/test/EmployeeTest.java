package com.revature.test;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;

import com.revature.pojo.Car;
import com.revature.pojo.Fleet;
import com.revature.pojo.Users;
import com.revature.users.Customer;
import com.revature.users.Employee;

class EmployeeTest {

	@BeforeClass
	static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	static void tearDownAfterClass() throws Exception {
	}

	@Before
	void setUp() throws Exception {
	}

	Users users = new Users();
	Fleet fleet = new Fleet();
	Customer cus = new Customer("David", "Malcom", "DMal", "test");
	Employee emp = new Employee("Truman", "Burbank", "TBank", "watched");
	Car car = new Car(10000, "541285", "Ford", "Focus", "102500", "2002");

	@After
	void tearDown() throws Exception {
	}

	@Test
	public void acceptOffer() {

		String input = "541285\nDMal\n";
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);

		users.setCustomers(cus);
		users.setEmployees(emp);
		car.setOffers(cus.getUsername(), 9000.0);
		fleet.setFleet(car);
		emp.acceptOrRejectoffer("1", users, fleet);
		assertEquals("This will check if sold is set to true", true, car.isSold());
		assertEquals("The price from the offer should be the new price", 9000.0, car.getPrice(), 0);
		assertEquals("The customer car array should not be empty", false, cus.getMyCars().isEmpty());
		assertEquals("Owner should be David", "David", car.getOwner().getFirstName());
		assertEquals("The car should be removed from the fleet", false, fleet.getFleet().contains(car));
		assertEquals("This checks to make sure that all of the offers were removed", true, car.getOffers().isEmpty());

//		assertEquals("Trying to see if customer was added to arraylist",true,  users.getCustomers().contains(cus));
	}

	@Test
	public void rejectOffer() {
		String input = "541285\nDMal\n";
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);

		users.setCustomers(cus);
		users.setEmployees(emp);
		car.setOffers(cus.getUsername(), 9000.0);
		fleet.setFleet(car);
		emp.acceptOrRejectoffer("2", users, fleet);
		assertEquals("This is testing that the username has been removed from the offers", false,
				car.getOffers().containsKey("DMal"));
	}

	@Test
	public void addVehicleTest() {

		String input = "541285\nFord\nFocus\n1528546\n2001\n10000";
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);

		emp.addVehicle(users, fleet);
		assertEquals("This is testing if a vehicle has been created and added", false, fleet.getFleet().isEmpty());
	}
	
	@Test
	public void removeVehicleTest() {
		String input = "541285\n";
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Car car = new Car(10000, "541285", "Ford", "Focus", "102500", "2002");
		fleet.setFleet(car);
		
		assertEquals("This is testing if a vehicle has been removed", false,fleet.getFleet().contains(car.getVIN()));
	}
}
