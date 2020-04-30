package web_scraping;

import java.util.*;

public class Make {
	String name;
	String img;
	HashSet<String> dealerships;
	ArrayList<String> cars;
	Integer numCars;
	Integer numDealerships;
	String market;
	String years;
	String url;

	Make(){
		this.name = null;
		this.img = null;
		this.dealerships = new HashSet<String>();
		this.cars = new ArrayList<String>();
		this.numCars = 0;
		this.numDealerships = 0;
		this.market = null;
		this.years = null;
		this.url = null;
	}
	
	public String toString() {
		return "Make: " + name + " | " + "Image: " + img + " | " + "Dealerships: " + dealerships 
				+ " | " + "Number of Dealerships: " + numDealerships + " | " + "Cars: " + cars + " | " + "Number of Cars: " + numCars
				+ " | " + "Market: " + market + " | " + "Years: " + years + " | " + "url: " + url;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public HashSet<String> getDealerships() {
		return dealerships;
	}

	public void setDealerships(HashSet<String> dealerships) {
		this.dealerships = dealerships;
	}

	public ArrayList<String> getCars() {
		return cars;
	}

	public void setCars(ArrayList<String> cars) {
		this.cars = cars;
	}

	public Integer getNumCars() {
		return numCars;
	}

	public void setNumCars(Integer numCars) {
		this.numCars = numCars;
	}

	public Integer getNumDealerships() {
		return numDealerships;
	}

	public void setNumDealerships(Integer numDealerships) {
		this.numDealerships = numDealerships;
	}

	public String getMarket() {
		return market;
	}

	public void setMarket(String market) {
		this.market = market;
	}

	public String getYears() {
		return years;
	}

	public void setYears(String years) {
		this.years = years;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
