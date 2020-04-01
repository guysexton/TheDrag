package web_scraping;

import java.util.*;

public class Dealership {
	String name;
	String img;
	HashSet<String> makes = new HashSet<String>();
	ArrayList<String> cars = new ArrayList<String>();
	String address;
	String phoneNum;
	String website;
	
	Dealership(){}
	
	Dealership(String name, String address, String phoneNum, String website){
		this.name = name;
		this.address= address;
		this.phoneNum = phoneNum;
		this.website = website;
	}
	
	public String getImage() {
		return img;
	}
	
	public HashSet<String> getMakes() {
		return makes;
	}
	
	public ArrayList<String> getCars(){
		return cars;
	}
	
	public String getAddress() {
		return address;
	}
	
	public String getPhoneNum() {
		return phoneNum;
	}
	
	public String getWebsite() {
		return website;
	}
	
	public String toString() {
		return "Dealership Name: " + name +  " | " + "Image: " + img + " | " + "Address: " + address + " | " + "Phone Number: " + phoneNum + " | " + "Website: " + website 
				+ " | " + "Cars at Dealership: " + cars + " | " + "Makes at Dealership: " + makes;
	}
	
	public boolean equals(Object o) {
		if (!(o instanceof Dealership)) return false;
		Dealership n = (Dealership)o;
		return name.equals(n.name) && address.equals(n.address);
	}
	
	public int hashCode() {
		return name.hashCode() + address.hashCode();
	}
}
