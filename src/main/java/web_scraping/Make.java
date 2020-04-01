package web_scraping;

import java.util.*;

public class Make {
	String name;
	String img;
	HashSet<String> dealerships;
	ArrayList<String> cars;
	Integer numCars;
	Integer numDealerships;
	
	Make(){
		this.name = null;
		this.img = null;
		this.dealerships = new HashSet<String>();
		this.cars = new ArrayList<String>();
		this.numCars = 0;
		this.numDealerships = 0;
	}
	
	public String toString() {
		return "Make: " + name + " | " + "Image: " + img + " | " + "Dealerships: " + dealerships 
				+ " | " + "Number of Dealerships: " + numDealerships + " | " + "Cars: " + cars + " | " + "Number of Cars: " + numCars;
	}
}
