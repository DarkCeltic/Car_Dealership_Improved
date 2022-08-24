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

class CustomerTest {

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
	Car car = new Car(10000, "541285", "Ford", "Focus", "102500", "2002"); 
	@After
	void tearDown() throws Exception {
	}

	@Test
	public void bidTest() {
		
		String input = "541285\n8000\n";
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		users.setCustomers(cus);
		fleet.setFleet(car);
		cus.bid(users, fleet);
		
		assertEquals("Testing if the car bid is added", true,car.getOffers().containsKey("DMal"));
		
	}
	
}
