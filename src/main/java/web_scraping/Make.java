package web_scraping;

import java.util.*;

public class Make {
	String name;
	HashSet<String> dealerships;
	ArrayList<Car> cars;
	
	Make(){
		this.name = null;
		this.dealerships = new HashSet<String>();
		this.cars = new ArrayList<Car>();
	}
	
	public String toString() {
		return name + " | " + dealerships + " | " + cars;
	}
}
