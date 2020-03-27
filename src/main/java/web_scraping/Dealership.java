package web_scraping;

import java.util.*;

public class Dealership {
	String name;
	String img;
	String make;
	ArrayList<String> models = new ArrayList<String>();
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
	
	public String getMake() {
		return make;
	}
	
	public ArrayList<String> getModels(){
		return models;
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
		return name +  " | " + address + " | " + phoneNum + " | " + website;
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
