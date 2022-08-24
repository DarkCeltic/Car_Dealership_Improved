package com.revature.pojo;

import java.io.Serializable;
import java.util.ArrayList;

import com.revature.pojo.Car;

public class Fleet implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1687285853713279674L;
	ArrayList<Car> fleet = new ArrayList<>();

	public void setFleet(ArrayList<Car> fleet) {
		this.fleet = fleet;
	}

	public ArrayList<Car> getFleet() {
		return fleet;
	}

	public void setFleet(Car car) {
		// TODO check if VIN exists
		fleet.add(car);
	}

	public void removeCar(Car car) {
		fleet.remove(car);
	}

}
