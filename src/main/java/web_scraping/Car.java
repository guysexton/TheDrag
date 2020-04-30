package web_scraping;

public class Car{
	private String name;
	private String img;
	private String url; 
	private String vin; 
	private String dealership; 
	private String make;
	private Integer price;
	private String mpg;

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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public String getDealership() {
		return dealership;
	}

	public void setDealership(String dealership) {
		this.dealership = dealership;
	}

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public String getMpg() {
		return mpg;
	}

	public void setMpg(String mpg) {
		this.mpg = mpg;
	}
}