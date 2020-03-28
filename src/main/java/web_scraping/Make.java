package web_scraping;

import java.util.*;

public class Make {
	ArrayList<String> dealerships;
	ArrayList<Car> cars;
	
	Make(){
		this.dealerships = new ArrayList<String>();
		this.cars = new ArrayList<Car>();
	}
	
	public String toString() {
		return dealerships + "/n" + cars;
	}
}
