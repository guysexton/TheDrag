package web_scraping;

public class Car{
	String url, vin, dealership;
	Integer price;

	Car(){
		this.url = null;
		this.vin = null;
		this.dealership = null;
		this.price = null;
	}
	
	Car(String url, String vin, String dealership, Integer price){
		this.url = url;
		this.vin = vin;
		this.dealership = dealership;
		this.price = price;
	}
	
	public String toString() {
		return vin + " at " + dealership + " for " + price + " (URL: " + url + ")";
	}
}