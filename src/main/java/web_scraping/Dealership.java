package web_scraping;

import java.util.*;

public class Dealership {
	private String name;
	private String img;
	private HashSet<String> makes = new HashSet<String>();
	private ArrayList<String> cars = new ArrayList<String>();
	private String address;
	private String phoneNum;
	private String website;
	private String hours;
	private String about;

	Dealership(){}
	
	Dealership(String name, String address, String phoneNum, String website, String hours, String about){
		this.name = name;
		this.address= address;
		this.phoneNum = phoneNum;
		this.website = website;
		this.hours = hours;
		this.about = about;
	}
	
	public String toString() {
		return "Dealership Name: " + name +  " | " + "Image: " + img + " | " + "Address: " + address + " | " + "Phone Number: " + phoneNum + " | " + "Website: " + website 
				+ " | " + "Cars at Dealership: " + cars + " | " + "Makes at Dealership: " + makes + " | " 
					+ "Operation Hours: " + hours + " | " + "About: " + about;
	}
	
	public boolean equals(Object o) {
		if (!(o instanceof Dealership)) return false;
		Dealership n = (Dealership)o;
		return name.equals(n.name) && address.equals(n.address);
	}
	
	public int hashCode() {
		return name.hashCode() + address.hashCode();
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

	public HashSet<String> getMakes() {
		return makes;
	}

	public void setMakes(HashSet<String> makes) {
		this.makes = makes;
	}

	public ArrayList<String> getCars() {
		return cars;
	}

	public void setCars(ArrayList<String> cars) {
		this.cars = cars;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getHours() {
		return hours;
	}

	public void setHours(String hours) {
		this.hours = hours;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}
}
