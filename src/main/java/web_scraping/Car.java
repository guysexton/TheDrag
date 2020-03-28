package web_scraping;

public class Car{
	String name, url, vin, dealership, make;
	Integer price;

	Car(){
		this.name = null;
		this.url = null;
		this.vin = null;
		this.dealership = null;
		this.make = null;
		this.price = null;
	}
	
	Car(String name, String url, String vin, String dealership, String make, Integer price){
		this.name = name;
		this.url = url;
		this.vin = vin;
		this.dealership = dealership;
		this.make = make;
		this.price = price;
	}
	
	public String toString() {
		return name + " with " + vin + " at " + dealership + " for " + price + " (URL: " + url + ")";
	}
}