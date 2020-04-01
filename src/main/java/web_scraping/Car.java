package web_scraping;

public class Car{
	String name;
	String img;
	String url; 
	String vin; 
	String dealership; 
	String make;
	Integer price;
	String mpg;

	Car(){
		this.name = null;
		this.img = null;
		this.url = null;
		this.vin = null;
		this.dealership = null;
		this.make = null;
		this.price = null;
		this.mpg = null;
	}
	
	Car(String name, String img, String url, String vin, String dealership, String make, Integer price, String mpg){
		this.name = name;
		this.img = img;
		this.url = url;
		this.vin = vin;
		this.dealership = dealership;
		this.make = make;
		this.price = price;
		this.mpg = mpg;
	}
	
	public String toString() {
		return "Car Name: " + name + " | " + "Car Image: " + img + " | " + "Car URL: " + url + " | " + "Car VIN: " + vin + " | " 
					+ "Dealership: " + dealership + " | " + "Make: " + make + " | " + "Price: " + price + " | " + "MPG: " + mpg;
	}
}