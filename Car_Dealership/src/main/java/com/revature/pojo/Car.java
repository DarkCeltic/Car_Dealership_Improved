package com.revature.pojo;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;

import com.revature.users.Customer;

public class Car implements Serializable {
	/**
	 * 
	 */
	Customer cust;
	private static final long serialVersionUID = 5948001344421180853L;
	private boolean sold = false;
	private double price;
	private int remainingPayments = 60;
	private Customer owner;
	private String VIN;
	private String make;
	private String model;
	private String year;
	private String mileage;
	private Map<String, Double> offers = new HashMap<String, Double>();
	private double monthlyPayments;

	public Car(double price, String vIN, String make, String model, String mileage, String year) {
		super();
		this.price = price;
		this.VIN = vIN;
		this.make = make;
		this.model = model;
		this.year = year;
		this.mileage = mileage;
	}

	public boolean isSold() {
		return sold;
	}

	public void setSold(boolean sold) {
		this.sold = sold;
	}

	public String getMileage() {
		return mileage;
	}

	public void setMileage(String mileage) {
		this.mileage = mileage;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public void setRemainingPayments(int i) {
		remainingPayments = i;
	}

	public int getRemainingPayments() {
		return remainingPayments;
	}

	public Customer getOwner() {
		return owner;
	}

	public void setOwner(Customer owner) {
		this.owner = owner;
	}

	public String getVIN() {
		return VIN;
	}

	public void setVIN(String vIN) {
		VIN = vIN;
	}

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public Map<String, Double> getOffers() {
		return offers;
	}

	public void setOffers(String username, Double offer) {

		offers.put(username, offer);
	}

	public String getMonthlyPayments() {
		return String.format("$%.2f", (price / remainingPayments) + (price / remainingPayments) * 0.0445);
	}

	public void setMonthlyPayments(int monthlyPayments) {
		this.monthlyPayments = monthlyPayments;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((VIN == null) ? 0 : VIN.hashCode());
		result = prime * result + ((make == null) ? 0 : make.hashCode());
		result = prime * result + ((model == null) ? 0 : model.hashCode());
		long temp;
		temp = Double.doubleToLongBits(monthlyPayments);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((offers == null) ? 0 : offers.hashCode());
		result = prime * result + ((owner == null) ? 0 : owner.hashCode());
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(price);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + remainingPayments;
		result = prime * result + (sold ? 1231 : 1237);
		result = prime * result + ((year == null) ? 0 : year.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Car other = (Car) obj;
		if (VIN == null) {
			if (other.VIN != null)
				return false;
		} else if (!VIN.equals(other.VIN))
			return false;
		if (make == null) {
			if (other.make != null)
				return false;
		} else if (!make.equals(other.make))
			return false;
		if (model == null) {
			if (other.model != null)
				return false;
		} else if (!model.equals(other.model))
			return false;
		if (Double.doubleToLongBits(monthlyPayments) != Double.doubleToLongBits(other.monthlyPayments))
			return false;
		if (offers == null) {
			if (other.offers != null)
				return false;
		} else if (!offers.equals(other.offers))
			return false;
		if (owner == null) {
			if (other.owner != null)
				return false;
		} else if (!owner.equals(other.owner))
			return false;
		if (Double.doubleToLongBits(price) != Double.doubleToLongBits(other.price))
			return false;
		if (remainingPayments != other.remainingPayments)
			return false;
		if (sold != other.sold)
			return false;
		if (year == null) {
			if (other.year != null)
				return false;
		} else if (!year.equals(other.year))
			return false;
		return true;
	}

	@Override
	public String toString() {
		NumberFormat formatter = NumberFormat.getCurrencyInstance();
		String formattedPrice = formatter.format(price);
		String out = String.format("%5s %-35s %-35s | %-10s | %-15s | %-10s", year, make, model, mileage,
				formattedPrice, VIN);
		return out;
	}

	public void toStringOffer() {
		for (Map.Entry<String, Double> entry : offers.entrySet()) {
			NumberFormat formatter = NumberFormat.getCurrencyInstance();
			String formattedOfferPrice = formatter.format(entry.getValue());
			String formattedPrice = formatter.format(price);
			System.out.format("%-6s | %5s %-35s %-35s | %-15s | %-15s | %-10s%n", entry.getKey(), year, make, model,
					formattedOfferPrice, formattedPrice, VIN);
		}
	}

}
