package web_scraping;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

class ScraperTest {
	public static OkHttpClient client = new OkHttpClient.Builder().readTimeout(30, TimeUnit.SECONDS).callTimeout(30, TimeUnit.SECONDS).build();;


	
	public static HashSet<String> dealers = new HashSet<String>();
	

	@Test
	void testScrapeMakeIds() {
		String mdoc;
		try {
			ArrayList<String> makeIds = new ArrayList<>();
			mdoc = run("https://www.cars.com/", client);
			Elements options = Jsoup.parse(mdoc).getElementsByAttributeValue("name", "makeId").get(0).children();
			String makeId = null;
			for (Element option : options) {
				makeId = option.val();
				makeIds.add(makeId);
			}
			assertEquals("20001", makeIds.get(1));
		} 	
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void testScrapeMakes() {
		try {
			Map<String, Make> makes = new HashMap<String, Make>();
			for(int i = 1; i < 9; i++) {
				String makeDoc = run("https://www.carlogos.org/car-brands/list_1_" + i + ".html", client);
				Elements elements = Jsoup.parse(makeDoc).getElementsByClass("logo-list").select("li");
				for (Element e : elements) {
					String url = "https://www.carlogos.org" + e.select("a").attr("href");
					String name= e.select("a").first().ownText();
					String market = e.select("a").select("span").eq(0).text();
					String years = e.select("a").select("span").eq(1).text();
					String image = "https://www.carlogos.org" + e.select("a").select("img").attr("src");
					System.out.println(url);
					System.out.println(name);
					System.out.println(image);
					System.out.println(market);
					System.out.println(years);
					Make m = new Make();
					m.url = url;
					m.name = name;
					m.market = market;
					m.years = years;
					m.img = image;
					makes.put(name, m);
				}
			}
			assertEquals("https://www.carlogos.org/car-brands/tesla-logo.html", makes.get("Tesla").url);
			assertEquals("Tesla", makes.get("Tesla").name);
			assertEquals("https://www.carlogos.org/car-logos/tesla-logo.png", makes.get("Tesla").img);
			assertEquals("Luxury Electric Vehicles", makes.get("Tesla").market);
			assertEquals("2003-Present", makes.get("Tesla").years);
			assertEquals("Ranz", makes.get("Ranz").name);
			assertEquals("https://www.carlogos.org/car-logos/ranz-logo.png", makes.get("Ranz").img);
			assertEquals("Electric Cars", makes.get("Ranz").market);
			assertEquals("2013-Present", makes.get("Ranz").years);
		} 	
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	void testScrapeDealershipName() {
		try {
			String doc = run("https://www.cars.com/dealers/2208445/covert-chrysler-dodge-jeep-ram/", client);
			assertEquals("Covert Chrysler Dodge Jeep & Ram", Jsoup.parse(doc).getElementsByClass("seller-name cui-alpha dealer-review__seller-name").text());
		} 	
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void testScrapeDealershipImage() {
		try {
			String doc = run("https://www.cars.com/dealers/2208445/covert-chrysler-dodge-jeep-ram/", client);
			assertEquals("https://www.cstatic-images.com/logo-dealer/2208445.jpg", Jsoup.parse(doc).getElementsByClass("dealer__logo").attr("src"));
		} 	
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void testScrapeDealershipAddress() {
		try {
			String doc = run("https://www.cars.com/dealers/2208445/covert-chrysler-dodge-jeep-ram/", client);
			assertEquals("8107 Research Blvd Austin, TX 78758", Jsoup.parse(doc).getElementsByClass("dealer-update__streetAddress").text());
		} 	
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void testScrapeDealershipPhoneNumber() {
		try {
			String doc = run("https://www.cars.com/dealers/2208445/covert-chrysler-dodge-jeep-ram/", client);
			assertEquals("New (512) 402-8526 Used (512) 961-5108", Jsoup.parse(doc).getElementsByClass("dealer-update__contact-numbers--new").text() + " " + Jsoup.parse(doc).getElementsByClass("dealer-update__contact-numbers--used").text());
			System.out.println(Jsoup.parse(doc).getElementsByClass("dealer-update__contact-numbers--new").text() + " " 
					+ Jsoup.parse(doc).getElementsByClass("dealer-update__contact-numbers--used").text());
		} 	
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void testScrapeDealershipWebsite() {
		try {
			String doc = run("https://www.cars.com/dealers/2208445/covert-chrysler-dodge-jeep-ram/", client);
			assertEquals("https://www.covertchryslerdodgejeepram.com?utm_source=cars.com&utm_medium=referral", Jsoup.parse(doc).getElementsByClass("dealer-update-website-link").attr("href"));
		} 	
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void testScrapeDealershipHours() {
		try {
			String doc = run("https://www.cars.com/dealers/2208445/covert-chrysler-dodge-jeep-ram/", client);
			assertEquals("Sales 8:00 AM - 8:00 PM", Jsoup.parse(doc).getElementsByClass("dpp-update__sales-hours-operation").text());
			System.out.println(Jsoup.parse(doc).getElementsByClass("dpp-update__sales-hours-operation").text());
		} 	
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void testScrapeDealershipAbout() {
		try {
			String doc = run("https://www.cars.com/dealers/2208445/covert-chrysler-dodge-jeep-ram/", client);
			Elements e = Jsoup.parse(doc).getElementsByClass("about-dealership-section__container");
			String about = null;
			for(Element e1: e) {
				about = e1.getElementsByClass("cui-section__accordion-preview").text()
				 	+ e1.getElementsByClass("cui-section__accordion-content").text();
			}
			System.out.println(about);
			assertEquals("We are the newest member to the Covert Auto Group. We are located at the corner of 183 and North Lamar.Covert Auto Grouphas been family owned and operated since 1909. Serving the Austin area for over 100 years!", about);
		} 	
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void testScrapeCarNameURLImagePrice() {
		try {
			Set<String> carUrls = new HashSet<String>();
			Map<String, Car> cars = new HashMap<String, Car>();
			
			carUrls.add("https://www.cars.com/vehicledetail/detail/805364103/overview/");
			
			for(String carUrl : carUrls) {
					String doc = run(carUrl, client);
					Car newCar = new Car();
					
					//Get URL
					newCar.url = carUrl;
					
					//Get car name
					Elements instances = Jsoup.parse(doc).getElementsByClass("cui-heading-2--secondary vehicle-info__title");
					for(Element instance : instances) {
						newCar.name = instance.text();
					}
						
					//Get car image
					instances = Jsoup.parse(doc).getElementsByClass("media-gallery__display-item media-gallery__display-item--image");
					for(Element instance : instances) {
						newCar.img = instance.attr("src");
					}		
					
					//Get car price
					instances = Jsoup.parse(doc).getElementsByClass("vehicle-info__price-display");
					for (Element instance : instances) { // should only loop once
						newCar.price = Integer.parseInt(instance.text().replace("$", "").replace(",", ""));
					}
					
					assertEquals("https://www.cars.com/vehicledetail/detail/805364103/overview/", newCar.url);
					assertEquals("2020 Dodge Charger SRT Hellcat", newCar.name);
					assertEquals("https://www.cstatic-images.com/supersized/in/v1/444790/2C3CDXL95LH144894/3c02b68537f0dd929550588e1b943a89.jpg", newCar.img);
					assertEquals(72345, newCar.price);
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	@Test
	void testScrapeVINandMPG() {
		try {
			Set<String> carUrls = new HashSet<String>();
			Map<String, Car> cars = new HashMap<String, Car>();
			
			carUrls.add("https://www.cars.com/vehicledetail/detail/805364103/overview/");
			
			for(String carUrl : carUrls) {
					String doc = run(carUrl, client);
					Car newCar = new Car();
					
					
					//Get car VIN and MPGs
					Elements instances = Jsoup.parse(doc).getElementsByClass("vdp-details-basics__item");
					for(Element instance : instances) {
						if(instance.select("strong").text().equals("VIN:")) {
							newCar.vin = instance.select("span[ng-non-bindable]").text();							
						}
						if(instance.select("strong").text().equals("City MPG:")) {
							newCar.mpg = instance.select("span[ng-non-bindable]").text() + "city/";							
						}
						if(instance.select("strong").text().equals("Highway MPG:")) {
							newCar.mpg += instance.select("span[ng-non-bindable]").text() + "hwy";							
						}
					}
					
					assertEquals("2C3CDXL95LH144894", newCar.vin);
					assertEquals("12city/21hwy", newCar.mpg);
					
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}	
	
	@Test
	void testScrapeCarDealerAndCarMake() {
		try {
			Set<String> carUrls = new HashSet<String>();
			Map<String, Car> cars = new HashMap<String, Car>();
			Map<String, Dealership> dealerships = new HashMap<String, Dealership>();
			Map<String, Make> makes = new HashMap<String, Make>();

			Make m = new Make();
			m.name = "Dodge";
			m.img = "https://www.carlogos.org/logo/Dodge-logo.png";
			makes.put("Dodge", m);
			
			carUrls.add("https://www.cars.com/vehicledetail/detail/805364103/overview/");
			
			for(String carUrl : carUrls) {
					String doc = run(carUrl, client);
					Car newCar = new Car();
					
					//Get car name
					Elements instances = Jsoup.parse(doc).getElementsByClass("cui-heading-2--secondary vehicle-info__title");
					for(Element instance : instances) {
						newCar.name = instance.text();
					}
					
					//Get car dealership
					instances = Jsoup.parse(doc).getElementsByClass("vdp-dealer-location");
					for (Element instance : instances) { // should only loop once
						newCar.dealership = instance.getElementsByClass("vdp-dealer-info__title").text(); //adds dealership info to car					
					}
					
					Set<String> makeName = makes.keySet();
					for(String make : makeName) {					//loop until it finds matching make
						String[] carName = newCar.name.split(" ");
						if(carName[1].equals(make)){				//if car has the make in its name
							newCar.make = make;						//car getts the make
						}
					}
					
					
					assertEquals("Nyle Maxwell Pre-Owned Supercenter", newCar.dealership);
					assertEquals("Dodge", newCar.make);
					
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}	
	
	@Test
	void testScrapeInterConnected() {
		try {
			Set<String> carUrls = new HashSet<String>();
			Map<String, Car> cars = new HashMap<String, Car>();
			Map<String, Dealership> dealerships = new HashMap<String, Dealership>();
			Map<String, Make> makes = new HashMap<String, Make>();

			Make m = new Make();
			m.name = "Dodge";
			m.img = "https://www.carlogos.org/logo/Dodge-logo.png";
			makes.put("Dodge", m);
			Dealership d = new Dealership();
			d.name = "Nyle Maxwell Pre-Owned Supercenter";
			d.img = "https://www.cstatic-images.com/logo-dealer/12dd3dfc61eb5dfc036c36a42a367520.jpg";
			d.address = "13817 Research Blvd Austin, TX 78750";
			d.phoneNum = "New (512) 861-0095";
			d.website = "http://www.nylemaxwellcdjr.com/?utm_source=cars.com&utm_medium=referral";
			dealerships.put(d.name, d);
			
			carUrls.add("https://www.cars.com/vehicledetail/detail/805364103/overview/");
			
			for(String carUrl : carUrls) {
					String doc = run(carUrl, client);
					Car newCar = new Car();
					
					newCar.url = carUrl;
					
					//Get car name
					Elements instances = Jsoup.parse(doc).getElementsByClass("cui-heading-2--secondary vehicle-info__title");
					for(Element instance : instances) {
						newCar.name = instance.text();
					}
					
					//Get car image
					instances = Jsoup.parse(doc).getElementsByClass("media-gallery__display-item media-gallery__display-item--image");
					for(Element instance : instances) {
						newCar.img = instance.attr("src");
					}
					
					//Get car VIN and MPGs
					instances = Jsoup.parse(doc).getElementsByClass("vdp-details-basics__item");
					String carVin = "FAILED";
					for(Element instance : instances) {
						if(instance.select("strong").text().equals("VIN:")) {
							newCar.vin = instance.select("span[ng-non-bindable]").text();							
						}
						if(instance.select("strong").text().equals("City MPG:")) {
							newCar.mpg = instance.select("span[ng-non-bindable]").text() + "city/";							
						}
						if(instance.select("strong").text().equals("Highway MPG:")) {
							newCar.mpg += instance.select("span[ng-non-bindable]").text() + "hwy";							
						}
					}
					
					
					//Get car dealership
					instances = Jsoup.parse(doc).getElementsByClass("vdp-dealer-location");
					for (Element instance : instances) { // should only loop once
						newCar.dealership = instance.getElementsByClass("vdp-dealer-info__title").text(); //adds dealership info to car					
						dealerships.get(newCar.dealership).cars.add(newCar.vin); //adds car to dealership car list
					}
					
					
					
					//Get car price
					instances = Jsoup.parse(doc).getElementsByClass("vehicle-info__price-display");
					for (Element instance : instances) { // should only loop once
						newCar.price = Integer.parseInt(instance.text().replace("$", "").replace(",", ""));
					}
					
					
					Set<String> makeName = makes.keySet();
					for(String make : makeName) {					//loop until it finds matching make
						String[] carName = newCar.name.split(" ");
						if(carName[1].equals(make)){				//if car has the make in its name
							makes.get(make).cars.add(newCar.vin);	//then make gets that car added
							newCar.make = make;						//car getts the make
							dealerships.get(newCar.dealership).makes.add(make);		//dealership that already has the car get the make
							makes.get(make).dealerships.add(newCar.dealership);		//make gets that dealership added to the make
							makes.get(make).numCars++;
							makes.get(make).numDealerships = makes.get(make).dealerships.size();
						}
					}
					
					//Car
					cars.put(newCar.vin, newCar);
					assertEquals("https://www.cars.com/vehicledetail/detail/805364103/overview/", cars.get(newCar.vin).url);
					assertEquals("2020 Dodge Charger SRT Hellcat", cars.get(newCar.vin).name);
					assertEquals("https://www.cstatic-images.com/supersized/in/v1/444790/2C3CDXL95LH144894/3c02b68537f0dd929550588e1b943a89.jpg", 
							cars.get(newCar.vin).img);
					assertEquals(72345, cars.get(newCar.vin).price);
					assertEquals("2C3CDXL95LH144894", cars.get(newCar.vin).vin);
					assertEquals("12city/21hwy", cars.get(newCar.vin).mpg);
					assertEquals("Nyle Maxwell Pre-Owned Supercenter", cars.get(newCar.vin).dealership);
					assertEquals("Dodge", cars.get(newCar.vin).make);
					
					//Dealer
					assertEquals("Nyle Maxwell Pre-Owned Supercenter", dealerships.get(newCar.dealership).name);
					assertEquals("2C3CDXL95LH144894", dealerships.get(newCar.dealership).cars.get(0));
					assertTrue(dealerships.get(newCar.dealership).makes.contains("Dodge"));
					//System.out.println(makes.get(newCar.make));
					
					//Make
					assertEquals("Dodge", makes.get(newCar.make).name);
					assertEquals("2C3CDXL95LH144894", makes.get(newCar.make).cars.get(0));
					assertTrue(makes.get(newCar.make).dealerships.contains("Nyle Maxwell Pre-Owned Supercenter"));	
					assertEquals(1, makes.get(newCar.make).numCars);
					assertEquals(1, makes.get(newCar.make).numDealerships);
					
					
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}	
	
	@Test
	void testScrapeMultipleCarsSameMake() {
		try {
			Set<String> carUrls = new HashSet<String>();
			Map<String, Car> cars = new HashMap<String, Car>();
			Map<String, Dealership> dealerships = new HashMap<String, Dealership>();
			Map<String, Make> makes = new HashMap<String, Make>();

			Make m = new Make();
			m.name = "Dodge";
			m.img = "https://www.carlogos.org/logo/Dodge-logo.png";
			makes.put("Dodge", m);
			Dealership d = new Dealership();
			d.name = "Nyle Maxwell Pre-Owned Supercenter";
			d.img = "https://www.cstatic-images.com/logo-dealer/12dd3dfc61eb5dfc036c36a42a367520.jpg";
			d.address = "13817 Research Blvd Austin, TX 78750";
			d.phoneNum = "New (512) 861-0095";
			d.website = "http://www.nylemaxwellcdjr.com/?utm_source=cars.com&utm_medium=referral";
			dealerships.put(d.name, d);
			
			carUrls.add("https://www.cars.com/vehicledetail/detail/805364103/overview/");
			carUrls.add("https://www.cars.com/vehicledetail/detail/806704066/overview/");
			for(String carUrl : carUrls) {
					String doc = run(carUrl, client);
					Car newCar = new Car();
					
					newCar.url = carUrl;
					
					//Get car name
					Elements instances = Jsoup.parse(doc).getElementsByClass("cui-heading-2--secondary vehicle-info__title");
					for(Element instance : instances) {
						newCar.name = instance.text();
					}
					
					//Get car image
					instances = Jsoup.parse(doc).getElementsByClass("media-gallery__display-item media-gallery__display-item--image");
					for(Element instance : instances) {
						newCar.img = instance.attr("src");
					}
					
					//Get car VIN and MPGs
					instances = Jsoup.parse(doc).getElementsByClass("vdp-details-basics__item");
					String carVin = "FAILED";
					for(Element instance : instances) {
						if(instance.select("strong").text().equals("VIN:")) {
							newCar.vin = instance.select("span[ng-non-bindable]").text();							
						}
						if(instance.select("strong").text().equals("City MPG:")) {
							newCar.mpg = instance.select("span[ng-non-bindable]").text() + "city/";							
						}
						if(instance.select("strong").text().equals("Highway MPG:")) {
							newCar.mpg += instance.select("span[ng-non-bindable]").text() + "hwy";							
						}
					}
					
					
					//Get car dealership
					instances = Jsoup.parse(doc).getElementsByClass("vdp-dealer-location");
					for (Element instance : instances) { // should only loop once
						newCar.dealership = instance.getElementsByClass("vdp-dealer-info__title").text(); //adds dealership info to car					
						dealerships.get(newCar.dealership).cars.add(newCar.vin); //adds car to dealership car list
					}
					
					
					
					//Get car price
					instances = Jsoup.parse(doc).getElementsByClass("vehicle-info__price-display");
					for (Element instance : instances) { // should only loop once
						newCar.price = Integer.parseInt(instance.text().replace("$", "").replace(",", ""));
					}
					
					
					Set<String> makeName = makes.keySet();
					for(String make : makeName) {					//loop until it finds matching make
						String[] carName = newCar.name.split(" ");
						if(carName[1].equals(make)){				//if car has the make in its name
							makes.get(make).cars.add(newCar.vin);	//then make gets that car added
							newCar.make = make;						//car getts the make
							dealerships.get(newCar.dealership).makes.add(make);		//dealership that already has the car get the make
							makes.get(make).dealerships.add(newCar.dealership);		//make gets that dealership added to the make
							makes.get(make).numCars++;
							makes.get(make).numDealerships = makes.get(make).dealerships.size();
						}
					}
					
					cars.put(newCar.vin, newCar);
			}
			
			//Car 2020 Dodge Charger SRT Hellcat
			assertEquals("https://www.cars.com/vehicledetail/detail/805364103/overview/", cars.get("2C3CDXL95LH144894").url);
			assertEquals("2020 Dodge Charger SRT Hellcat", cars.get("2C3CDXL95LH144894").name);
			assertEquals("https://www.cstatic-images.com/supersized/in/v1/444790/2C3CDXL95LH144894/3c02b68537f0dd929550588e1b943a89.jpg", 
					cars.get("2C3CDXL95LH144894").img);
			assertEquals(72345, cars.get("2C3CDXL95LH144894").price);
			assertEquals("2C3CDXL95LH144894", cars.get("2C3CDXL95LH144894").vin);
			assertEquals("12city/21hwy", cars.get("2C3CDXL95LH144894").mpg);
			assertEquals("Nyle Maxwell Pre-Owned Supercenter", cars.get("2C3CDXL95LH144894").dealership);
			assertEquals("Dodge", cars.get("2C3CDXL95LH144894").make);
			
			//2020 Dodge Charger R/T
			assertEquals("https://www.cars.com/vehicledetail/detail/806704066/overview/", cars.get("2C3CDXCT3LH153895").url);
			assertEquals("2020 Dodge Charger R/T", cars.get("2C3CDXCT3LH153895").name);
			assertEquals("https://www.cstatic-images.com/supersized/in/v1/444790/2C3CDXCT3LH153895/c99bb91729f2b24b5bbb3a06ac5356f5.jpg", 
					cars.get("2C3CDXCT3LH153895").img);
			assertEquals(37675, cars.get("2C3CDXCT3LH153895").price);
			assertEquals("2C3CDXCT3LH153895", cars.get("2C3CDXCT3LH153895").vin);
			assertEquals("16city/25hwy", cars.get("2C3CDXCT3LH153895").mpg);
			assertEquals("Nyle Maxwell Pre-Owned Supercenter", cars.get("2C3CDXCT3LH153895").dealership);
			assertEquals("Dodge", cars.get("2C3CDXCT3LH153895").make);
			
			//Dealer
			assertEquals("Nyle Maxwell Pre-Owned Supercenter", dealerships.get(cars.get("2C3CDXL95LH144894").dealership).name);
			assertEquals("Nyle Maxwell Pre-Owned Supercenter", dealerships.get(cars.get("2C3CDXCT3LH153895").dealership).name);
			assertEquals("2C3CDXL95LH144894", dealerships.get(cars.get("2C3CDXL95LH144894").dealership).cars.get(0));
			assertEquals("2C3CDXCT3LH153895", dealerships.get(cars.get("2C3CDXL95LH144894").dealership).cars.get(1));
			assertEquals("2C3CDXL95LH144894", dealerships.get(cars.get("2C3CDXCT3LH153895").dealership).cars.get(0));
			assertEquals("2C3CDXCT3LH153895", dealerships.get(cars.get("2C3CDXCT3LH153895").dealership).cars.get(1));
			assertTrue(dealerships.get(cars.get("2C3CDXL95LH144894").dealership).makes.contains("Dodge"));
			assertTrue(dealerships.get(cars.get("2C3CDXCT3LH153895").dealership).makes.contains("Dodge"));
			
			//Make
			assertEquals("Dodge", makes.get(cars.get("2C3CDXL95LH144894").make).name);
			assertEquals("Dodge", makes.get(cars.get("2C3CDXCT3LH153895").make).name);
			assertEquals("2C3CDXL95LH144894", makes.get(cars.get("2C3CDXL95LH144894").make).cars.get(0));
			assertEquals("2C3CDXCT3LH153895", makes.get(cars.get("2C3CDXL95LH144894").make).cars.get(1));
			assertEquals("2C3CDXL95LH144894", makes.get(cars.get("2C3CDXCT3LH153895").make).cars.get(0));
			assertEquals("2C3CDXCT3LH153895", makes.get(cars.get("2C3CDXCT3LH153895").make).cars.get(1));
			assertTrue(makes.get(cars.get("2C3CDXL95LH144894").make).dealerships.contains("Nyle Maxwell Pre-Owned Supercenter"));
			assertTrue(makes.get(cars.get("2C3CDXCT3LH153895").make).dealerships.contains("Nyle Maxwell Pre-Owned Supercenter"));
			assertEquals(2, makes.get(cars.get("2C3CDXL95LH144894").make).numCars);
			assertEquals(1, makes.get(cars.get("2C3CDXL95LH144894").make).numDealerships);
			assertEquals(2, makes.get(cars.get("2C3CDXCT3LH153895").make).numCars);
			assertEquals(1, makes.get(cars.get("2C3CDXCT3LH153895").make).numDealerships);
			
		}
		catch (IOException e) {
			e.printStackTrace();
		}	
	}	
	
	@Test
	void testScrapeMultipleCarsDiffMake() {
		try {
			Set<String> carUrls = new HashSet<String>();
			Map<String, Car> cars = new HashMap<String, Car>();
			Map<String, Dealership> dealerships = new HashMap<String, Dealership>();
			Map<String, Make> makes = new HashMap<String, Make>();

			Make m = new Make();
			m.name = "Dodge";
			m.img = "https://www.carlogos.org/logo/Dodge-logo.png";
			makes.put("Dodge", m);
			
			Make j = new Make();
			j.name = "Jeep";
			makes.put("Jeep", j);
			
			Dealership d = new Dealership();
			d.name = "Nyle Maxwell Pre-Owned Supercenter";
			d.img = "https://www.cstatic-images.com/logo-dealer/12dd3dfc61eb5dfc036c36a42a367520.jpg";
			d.address = "13817 Research Blvd Austin, TX 78750";
			d.phoneNum = "New (512) 861-0095";
			d.website = "http://www.nylemaxwellcdjr.com/?utm_source=cars.com&utm_medium=referral";
			dealerships.put(d.name, d);
			
			carUrls.add("https://www.cars.com/vehicledetail/detail/805364103/overview/");
			carUrls.add("https://www.cars.com/vehicledetail/detail/799715831/overview/");
			for(String carUrl : carUrls) {
					String doc = run(carUrl, client);
					Car newCar = new Car();
					
					newCar.url = carUrl;
					
					//Get car name
					Elements instances = Jsoup.parse(doc).getElementsByClass("cui-heading-2--secondary vehicle-info__title");
					for(Element instance : instances) {
						newCar.name = instance.text();
					}
					
					//Get car image
					instances = Jsoup.parse(doc).getElementsByClass("media-gallery__display-item media-gallery__display-item--image");
					for(Element instance : instances) {
						newCar.img = instance.attr("src");
					}
					
					//Get car VIN and MPGs
					instances = Jsoup.parse(doc).getElementsByClass("vdp-details-basics__item");
					String carVin = "FAILED";
					for(Element instance : instances) {
						if(instance.select("strong").text().equals("VIN:")) {
							newCar.vin = instance.select("span[ng-non-bindable]").text();							
						}
						if(instance.select("strong").text().equals("City MPG:")) {
							newCar.mpg = instance.select("span[ng-non-bindable]").text() + "city/";							
						}
						if(instance.select("strong").text().equals("Highway MPG:")) {
							newCar.mpg += instance.select("span[ng-non-bindable]").text() + "hwy";							
						}
					}
					
					
					//Get car dealership
					instances = Jsoup.parse(doc).getElementsByClass("vdp-dealer-location");
					for (Element instance : instances) { // should only loop once
						newCar.dealership = instance.getElementsByClass("vdp-dealer-info__title").text(); //adds dealership info to car					
						dealerships.get(newCar.dealership).cars.add(newCar.vin); //adds car to dealership car list
					}
					
					
					
					//Get car price
					instances = Jsoup.parse(doc).getElementsByClass("vehicle-info__price-display");
					for (Element instance : instances) { // should only loop once
						newCar.price = Integer.parseInt(instance.text().replace("$", "").replace(",", ""));
					}
					
					
					Set<String> makeName = makes.keySet();
					for(String make : makeName) {					//loop until it finds matching make
						String[] carName = newCar.name.split(" ");
						if(carName[1].equals(make)){				//if car has the make in its name
							makes.get(make).cars.add(newCar.vin);	//then make gets that car added
							newCar.make = make;						//car getts the make
							dealerships.get(newCar.dealership).makes.add(make);		//dealership that already has the car get the make
							makes.get(make).dealerships.add(newCar.dealership);		//make gets that dealership added to the make
							makes.get(make).numCars++;
							makes.get(make).numDealerships = makes.get(make).dealerships.size();
						}
					}
					
					cars.put(newCar.vin, newCar);
			}
			
			//Car 2020 Dodge Charger SRT Hellcat
			assertEquals("https://www.cars.com/vehicledetail/detail/805364103/overview/", cars.get("2C3CDXL95LH144894").url);
			assertEquals("2020 Dodge Charger SRT Hellcat", cars.get("2C3CDXL95LH144894").name);
			assertEquals("https://www.cstatic-images.com/supersized/in/v1/444790/2C3CDXL95LH144894/3c02b68537f0dd929550588e1b943a89.jpg", 
					cars.get("2C3CDXL95LH144894").img);
			assertEquals(72345, cars.get("2C3CDXL95LH144894").price);
			assertEquals("2C3CDXL95LH144894", cars.get("2C3CDXL95LH144894").vin);
			assertEquals("12city/21hwy", cars.get("2C3CDXL95LH144894").mpg);
			assertEquals("Nyle Maxwell Pre-Owned Supercenter", cars.get("2C3CDXL95LH144894").dealership);
			assertEquals("Dodge", cars.get("2C3CDXL95LH144894").make);
			
			//2020 Jeep Wrangler Unlimited Sahara
			assertEquals("https://www.cars.com/vehicledetail/detail/799715831/overview/", cars.get("1C4HJXEG5LW188403").url);
			assertEquals("2020 Jeep Wrangler Unlimited Sahara", cars.get("1C4HJXEG5LW188403").name);
			assertEquals("https://www.cstatic-images.com/supersized/in/v1/444790/1C4HJXEG5LW188403/ee34d906a7a29ac0fb3aef95e133afff.jpg", 
					cars.get("1C4HJXEG5LW188403").img);
			assertEquals(47790, cars.get("1C4HJXEG5LW188403").price);
			assertEquals("1C4HJXEG5LW188403", cars.get("1C4HJXEG5LW188403").vin);
			assertEquals("17city/23hwy", cars.get("1C4HJXEG5LW188403").mpg);
			assertEquals("Nyle Maxwell Pre-Owned Supercenter", cars.get("1C4HJXEG5LW188403").dealership);
			assertEquals("Jeep", cars.get("1C4HJXEG5LW188403").make);
			
			//Dealer
			assertEquals("Nyle Maxwell Pre-Owned Supercenter", dealerships.get(cars.get("2C3CDXL95LH144894").dealership).name);
			assertEquals("Nyle Maxwell Pre-Owned Supercenter", dealerships.get(cars.get("1C4HJXEG5LW188403").dealership).name);
			assertEquals("2C3CDXL95LH144894", dealerships.get(cars.get("2C3CDXL95LH144894").dealership).cars.get(0));
			assertEquals("1C4HJXEG5LW188403", dealerships.get(cars.get("2C3CDXL95LH144894").dealership).cars.get(1));
			assertEquals("2C3CDXL95LH144894", dealerships.get(cars.get("1C4HJXEG5LW188403").dealership).cars.get(0));
			assertEquals("1C4HJXEG5LW188403", dealerships.get(cars.get("1C4HJXEG5LW188403").dealership).cars.get(1));
			assertTrue(dealerships.get(cars.get("2C3CDXL95LH144894").dealership).makes.contains("Dodge"));
			assertTrue(dealerships.get(cars.get("2C3CDXL95LH144894").dealership).makes.contains("Jeep"));
			assertTrue(dealerships.get(cars.get("1C4HJXEG5LW188403").dealership).makes.contains("Dodge"));
			assertTrue(dealerships.get(cars.get("1C4HJXEG5LW188403").dealership).makes.contains("Jeep"));
			
			//Make
			assertEquals("Dodge", makes.get(cars.get("2C3CDXL95LH144894").make).name);
			assertEquals("Jeep", makes.get(cars.get("1C4HJXEG5LW188403").make).name);
			assertEquals("2C3CDXL95LH144894", makes.get(cars.get("2C3CDXL95LH144894").make).cars.get(0));
			assertEquals("1C4HJXEG5LW188403", makes.get(cars.get("1C4HJXEG5LW188403").make).cars.get(0));
			assertTrue(makes.get(cars.get("2C3CDXL95LH144894").make).dealerships.contains("Nyle Maxwell Pre-Owned Supercenter"));	
			assertTrue(makes.get(cars.get("1C4HJXEG5LW188403").make).dealerships.contains("Nyle Maxwell Pre-Owned Supercenter"));
			assertEquals(1, makes.get(cars.get("2C3CDXL95LH144894").make).numCars);
			assertEquals(1, makes.get(cars.get("2C3CDXL95LH144894").make).numDealerships);
			assertEquals(1, makes.get(cars.get("1C4HJXEG5LW188403").make).numCars);
			assertEquals(1, makes.get(cars.get("1C4HJXEG5LW188403").make).numDealerships);
			
		}
		catch (IOException e) {
			e.printStackTrace();
		}	
	}	
	public static String run(String url, OkHttpClient client) throws IOException {
		Request request = new Request.Builder()
				.url(url)
				.build();

		try (Response response = client.newCall(request).execute()) {
			return response.body().string();
		}
	}

}
