package web_scraping;

import java.util.*;

public class Make {
	String name;
	String img;
	HashSet<String> dealerships;
	ArrayList<Car> cars;
	
	Make(){
		this.name = null;
		this.img = null;
		this.dealerships = new HashSet<String>();
		this.cars = new ArrayList<Car>();
	}
	
	public String toString() {
		return name + " | " + img + " | " + dealerships + " | " + cars;
	}
}
