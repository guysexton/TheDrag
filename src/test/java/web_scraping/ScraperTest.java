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
					m.setUrl(url);
					m.setName(name);
					m.setMarket(market);
					m.setYears(years);
					m.setImg(image);
					makes.put(name, m);
				}
			}
			assertEquals("https://www.carlogos.org/car-brands/tesla-logo.html", makes.get("Tesla").getUrl());
			assertEquals("Tesla", makes.get("Tesla").getName());
			assertEquals("https://www.carlogos.org/car-logos/tesla-logo.png", makes.get("Tesla").getImg());
			assertEquals("Luxury Electric Vehicles", makes.get("Tesla").getMarket());
			assertEquals("2003-Present", makes.get("Tesla").getYears());
			assertEquals("Ranz", makes.get("Ranz").getName());
			assertEquals("https://www.carlogos.org/car-logos/ranz-logo.png", makes.get("Ranz").getImg());
			assertEquals("Electric Cars", makes.get("Ranz").getMarket());
			assertEquals("2013-Present", makes.get("Ranz").getYears());
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
			
			carUrls.add("https://www.cars.com/vehicledetail/detail/808982752/overview/");
			
			for(String carUrl : carUrls) {
					String doc = run(carUrl, client);
					Car newCar = new Car();
					
					//Get URL
					newCar.setUrl(carUrl);
					
					//Get car name
					Elements instances = Jsoup.parse(doc).getElementsByClass("cui-heading-2--secondary vehicle-info__title");
					for(Element instance : instances) {
						newCar.setName(instance.text());
					}
						
					//Get car image
					instances = Jsoup.parse(doc).getElementsByClass("media-gallery__display-item media-gallery__display-item--image");
					for(Element instance : instances) {
						newCar.setImg(instance.attr("src"));
					}		
					
					//Get car price
					instances = Jsoup.parse(doc).getElementsByClass("vehicle-info__price-display");
					for (Element instance : instances) { // should only loop once
						newCar.setPrice(Integer.parseInt(instance.text().replace("$", "").replace(",", "")));
						//System.out.println(newCar.price);
					}
					
					assertEquals("https://www.cars.com/vehicledetail/detail/808982752/overview/", newCar.getUrl());
					assertEquals("2020 Toyota Corolla Hatchback Nightshade", newCar.getName());
					assertEquals("https://www.cstatic-images.com/supersized/in/v1/420787/JTND4RBE3L3096630/e33dccccc1a78923aab61d5a8b32960c.jpg", newCar.getImg());
					assertEquals(22312, newCar.getPrice());
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
			
			carUrls.add("https://www.cars.com/vehicledetail/detail/808982752/overview/");
			
			for(String carUrl : carUrls) {
					String doc = run(carUrl, client);
					Car newCar = new Car();
					
					
					//Get car VIN and MPGs
					Elements instances = Jsoup.parse(doc).getElementsByClass("vdp-details-basics__item");
					for(Element instance : instances) {
						if(instance.select("strong").text().equals("VIN:")) {
							newCar.setVin(instance.select("span[ng-non-bindable]").text());							
						}
						if(instance.select("strong").text().equals("City MPG:")) {
							newCar.setMpg(instance.select("span[ng-non-bindable]").text() + "city/");							
						}
						if(instance.select("strong").text().equals("Highway MPG:")) {
							newCar.setMpg(newCar.getMpg() + instance.select("span[ng-non-bindable]").text() + "hwy");							
						}
					}
					
					assertEquals("JTND4RBE3L3096630", newCar.getVin());
					assertEquals("32city/41hwy", newCar.getMpg());
					
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
			m.setName("Toyota");
			makes.put("Toyota", m);
			
			carUrls.add("https://www.cars.com/vehicledetail/detail/808982752/overview/");
			
			for(String carUrl : carUrls) {
					String doc = run(carUrl, client);
					Car newCar = new Car();
					
					//Get car name
					Elements instances = Jsoup.parse(doc).getElementsByClass("cui-heading-2--secondary vehicle-info__title");
					for(Element instance : instances) {
						newCar.setName(instance.text());
					}
					
					//Get car dealership
					instances = Jsoup.parse(doc).getElementsByClass("vdp-dealer-location");
					for (Element instance : instances) { // should only loop once
						newCar.setDealership(instance.getElementsByClass("vdp-dealer-info__title").text()); //adds dealership info to car					
					}
					
					Set<String> makeName = makes.keySet();
					for(String make : makeName) {					//loop until it finds matching make
						String[] carName = newCar.getName().split(" ");
						if(carName[1].equals(make)){				//if car has the make in its name
							newCar.setMake(make);						//car gets the make
						}
					}
					
					
					assertEquals("Charles Maund Toyota", newCar.getDealership());
					assertEquals("Toyota", newCar.getMake());
					
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
			m.setName("Dodge");
			makes.put("Dodge", m);
			Dealership d = new Dealership();
			d.setName("Nyle Maxwell Pre-Owned Supercenter");
			d.setImg("https://www.cstatic-images.com/logo-dealer/12dd3dfc61eb5dfc036c36a42a367520.jpg");
			d.setAddress("13817 Research Blvd Austin, TX 78750");
			d.setPhoneNum("New (512) 861-0095");
			d.setWebsite("http://www.nylemaxwellcdjr.com/?utm_source=cars.com&utm_medium=referral");
			dealerships.put(d.getName(), d);
			
			carUrls.add("https://www.cars.com/vehicledetail/detail/805677938/overview/");
			
			for(String carUrl : carUrls) {
					String doc = run(carUrl, client);
					Car newCar = new Car();
					
					newCar.setUrl(carUrl);
					
					//Get car name
					Elements instances = Jsoup.parse(doc).getElementsByClass("cui-heading-2--secondary vehicle-info__title");
					for(Element instance : instances) {
						newCar.setName(instance.text());
					}
					
					//Get car image
					instances = Jsoup.parse(doc).getElementsByClass("media-gallery__display-item media-gallery__display-item--image");
					for(Element instance : instances) {
						newCar.setImg(instance.attr("src"));
					}
					
					//Get car VIN and MPGs
					instances = Jsoup.parse(doc).getElementsByClass("vdp-details-basics__item");
					String carVin = "FAILED";
					for(Element instance : instances) {
						if(instance.select("strong").text().equals("VIN:")) {
							newCar.setVin(instance.select("span[ng-non-bindable]").text());							
						}
						if(instance.select("strong").text().equals("City MPG:")) {
							newCar.setMpg(instance.select("span[ng-non-bindable]").text() + "city/");							
						}
						if(instance.select("strong").text().equals("Highway MPG:")) {
							newCar.setMpg(newCar.getMpg() + instance.select("span[ng-non-bindable]").text() + "hwy");							
						}
					}
					
					
					//Get car dealership
					instances = Jsoup.parse(doc).getElementsByClass("vdp-dealer-location");
					for (Element instance : instances) { // should only loop once
						newCar.setDealership(instance.getElementsByClass("vdp-dealer-info__title").text()); //adds dealership info to car					
						dealerships.get(newCar.getDealership()).getCars().add(newCar.getVin()); //adds car to dealership car list
					}
					
					
					
					//Get car price
					instances = Jsoup.parse(doc).getElementsByClass("vehicle-info__price-display");
					for (Element instance : instances) { // should only loop once
						newCar.setPrice(Integer.parseInt(instance.text().replace("$", "").replace(",", "")));
					}
					
					
					Set<String> makeName = makes.keySet();
					for(String make : makeName) {					//loop until it finds matching make
						String[] carName = newCar.getName().split(" ");
						if(carName[1].equals(make)){				//if car has the make in its name
							makes.get(make).getCars().add(newCar.getVin());	//then make gets that car added
							newCar.setMake(make);						//car getts the make
							dealerships.get(newCar.getDealership()).getMakes().add(make);		//dealership that already has the car get the make
							makes.get(make).getDealerships().add(newCar.getDealership());		//make gets that dealership added to the make
							Integer numCars = makes.get(make).getNumCars();
							numCars++;
							makes.get(make).setNumCars(numCars);
							makes.get(make).setNumDealerships(makes.get(make).getDealerships().size());
						}
					}
					
					//Car
					cars.put(newCar.getVin(), newCar);
					assertEquals("https://www.cars.com/vehicledetail/detail/805677938/overview/", cars.get(newCar.getVin()).getUrl());
					assertEquals("2020 Dodge Grand Caravan SE", cars.get(newCar.getVin()).getName());
					assertEquals("https://www.cstatic-images.com/supersized/in/v1/444790/2C4RDGBG2LR195348/306f047c94328f16399179c91c411288.jpg", 
							cars.get(newCar.getVin()).getImg());
					assertEquals(27040, cars.get(newCar.getVin()).getPrice());
					assertEquals("2C4RDGBG2LR195348", cars.get(newCar.getVin()).getVin());
					assertEquals("17city/25hwy", cars.get(newCar.getVin()).getMpg());
					assertEquals("Nyle Maxwell Pre-Owned Supercenter", cars.get(newCar.getVin()).getDealership());
					assertEquals("Dodge", cars.get(newCar.getVin()).getMake());
					
					//Dealer
					assertEquals("Nyle Maxwell Pre-Owned Supercenter", dealerships.get(newCar.getDealership()).getName());
					assertEquals("2C4RDGBG2LR195348", dealerships.get(newCar.getDealership()).getCars().get(0));
					assertTrue(dealerships.get(newCar.getDealership()).getMakes().contains("Dodge"));
					//System.out.println(makes.get(newCar.make));
					
					//Make
					assertEquals("Dodge", makes.get(newCar.getMake()).getName());
					assertEquals("2C4RDGBG2LR195348", makes.get(newCar.getMake()).getCars().get(0));
					assertTrue(makes.get(newCar.getMake()).getDealerships().contains("Nyle Maxwell Pre-Owned Supercenter"));	
					assertEquals(1, makes.get(newCar.getMake()).getNumCars());
					assertEquals(1, makes.get(newCar.getMake()).getNumDealerships());
					
					
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
			m.setName("Dodge");
			makes.put("Dodge", m);
			Dealership d = new Dealership();
			d.setName("Nyle Maxwell Pre-Owned Supercenter");
			d.setAddress("13817 Research Blvd Austin, TX 78750");
			d.setPhoneNum("New (512) 861-0095");
			d.setWebsite("http://www.nylemaxwellcdjr.com/?utm_source=cars.com&utm_medium=referral");
			dealerships.put(d.getName(), d);
			
			carUrls.add("https://www.cars.com/vehicledetail/detail/805677938/overview/");
			carUrls.add("https://www.cars.com/vehicledetail/detail/806473915/overview/");
			for(String carUrl : carUrls) {
					String doc = run(carUrl, client);
					Car newCar = new Car();
					
					newCar.setUrl(carUrl);
					
					//Get car name
					Elements instances = Jsoup.parse(doc).getElementsByClass("cui-heading-2--secondary vehicle-info__title");
					for(Element instance : instances) {
						newCar.setName(instance.text());
					}
					
					//Get car image
					instances = Jsoup.parse(doc).getElementsByClass("media-gallery__display-item media-gallery__display-item--image");
					for(Element instance : instances) {
						newCar.setImg(instance.attr("src"));
					}
					
					//Get car VIN and MPGs
					instances = Jsoup.parse(doc).getElementsByClass("vdp-details-basics__item");
					String carVin = "FAILED";
					for(Element instance : instances) {
						if(instance.select("strong").text().equals("VIN:")) {
							newCar.setVin(instance.select("span[ng-non-bindable]").text());							
						}
						if(instance.select("strong").text().equals("City MPG:")) {
							newCar.setMpg(instance.select("span[ng-non-bindable]").text() + "city/");							
						}
						if(instance.select("strong").text().equals("Highway MPG:")) {
							newCar.setMpg(newCar.getMpg() + instance.select("span[ng-non-bindable]").text() + "hwy");							
						}
					}
					
					
					//Get car dealership
					instances = Jsoup.parse(doc).getElementsByClass("vdp-dealer-location");
					for (Element instance : instances) { // should only loop once
						newCar.setDealership(instance.getElementsByClass("vdp-dealer-info__title").text()); //adds dealership info to car					
						dealerships.get(newCar.getDealership()).getCars().add(newCar.getVin()); //adds car to dealership car list
					}
					
					
					
					//Get car price
					instances = Jsoup.parse(doc).getElementsByClass("vehicle-info__price-display");
					for (Element instance : instances) { // should only loop once
						newCar.setPrice(Integer.parseInt(instance.text().replace("$", "").replace(",", "")));
					}
					
					
					Set<String> makeName = makes.keySet();
					for(String make : makeName) {					//loop until it finds matching make
						String[] carName = newCar.getName().split(" ");
						if(carName[1].equals(make)){				//if car has the make in its name
							makes.get(make).getCars().add(newCar.getVin());	//then make gets that car added
							newCar.setMake(make);						//car getts the make
							dealerships.get(newCar.getDealership()).getMakes().add(make);		//dealership that already has the car get the make
							makes.get(make).getDealerships().add(newCar.getDealership());		//make gets that dealership added to the make
							Integer numCars = makes.get(make).getNumCars();
							numCars++;
							makes.get(make).setNumCars(numCars);
							makes.get(make).setNumDealerships(makes.get(make).getDealerships().size());
						}
					}
					
					cars.put(newCar.getVin(), newCar);
			}
			
			//Car 2020 Dodge Grand Caravan SE
			assertEquals("https://www.cars.com/vehicledetail/detail/805677938/overview/", cars.get("2C4RDGBG2LR195348").getUrl());
			assertEquals("2020 Dodge Grand Caravan SE", cars.get("2C4RDGBG2LR195348").getName());
			assertEquals("https://www.cstatic-images.com/supersized/in/v1/444790/2C4RDGBG2LR195348/306f047c94328f16399179c91c411288.jpg", 
					cars.get("2C4RDGBG2LR195348").getImg());
			assertEquals(27040, cars.get("2C4RDGBG2LR195348").getPrice());
			assertEquals("2C4RDGBG2LR195348", cars.get("2C4RDGBG2LR195348").getVin());
			assertEquals("17city/25hwy", cars.get("2C4RDGBG2LR195348").getMpg());
			assertEquals("Nyle Maxwell Pre-Owned Supercenter", cars.get("2C4RDGBG2LR195348").getDealership());
			assertEquals("Dodge", cars.get("2C4RDGBG2LR195348").getMake());
			
			//2020 Dodge Charger R/T
			assertEquals("https://www.cars.com/vehicledetail/detail/806473915/overview/", cars.get("2C3CDXCT1LH153894").getUrl());
			assertEquals("2020 Dodge Charger R/T", cars.get("2C3CDXCT1LH153894").getName());
			assertEquals("https://www.cstatic-images.com/supersized/in/v1/444790/2C3CDXCT1LH153894/cea173a68d2534f4ea6305d91924d721.jpg", 
					cars.get("2C3CDXCT1LH153894").getImg());
			assertEquals(33172, cars.get("2C3CDXCT1LH153894").getPrice());
			assertEquals("2C3CDXCT1LH153894", cars.get("2C3CDXCT1LH153894").getVin());
			assertEquals("16city/25hwy", cars.get("2C3CDXCT1LH153894").getMpg());
			assertEquals("Nyle Maxwell Pre-Owned Supercenter", cars.get("2C3CDXCT1LH153894").getDealership());
			assertEquals("Dodge", cars.get("2C3CDXCT1LH153894").getMake());
			
			//Dealer
			assertEquals("Nyle Maxwell Pre-Owned Supercenter", dealerships.get(cars.get("2C4RDGBG2LR195348").getDealership()).getName());
			assertEquals("Nyle Maxwell Pre-Owned Supercenter", dealerships.get(cars.get("2C3CDXCT1LH153894").getDealership()).getName());
			assertEquals("2C3CDXCT1LH153894", dealerships.get(cars.get("2C3CDXCT1LH153894").getDealership()).getCars().get(0));
			assertEquals("2C4RDGBG2LR195348", dealerships.get(cars.get("2C3CDXCT1LH153894").getDealership()).getCars().get(1));
			assertEquals("2C3CDXCT1LH153894", dealerships.get(cars.get("2C4RDGBG2LR195348").getDealership()).getCars().get(0));
			assertEquals("2C4RDGBG2LR195348", dealerships.get(cars.get("2C4RDGBG2LR195348").getDealership()).getCars().get(1));
			assertTrue(dealerships.get(cars.get("2C3CDXCT1LH153894").getDealership()).getMakes().contains("Dodge"));
			assertTrue(dealerships.get(cars.get("2C4RDGBG2LR195348").getDealership()).getMakes().contains("Dodge"));
			
			//Make
			assertEquals("Dodge", makes.get(cars.get("2C3CDXCT1LH153894").getMake()).getName());
			assertEquals("Dodge", makes.get(cars.get("2C4RDGBG2LR195348").getMake()).getName());
			assertEquals("2C3CDXCT1LH153894", makes.get(cars.get("2C3CDXCT1LH153894").getMake()).getCars().get(0));
			assertEquals("2C4RDGBG2LR195348", makes.get(cars.get("2C3CDXCT1LH153894").getMake()).getCars().get(1));
			assertEquals("2C3CDXCT1LH153894", makes.get(cars.get("2C4RDGBG2LR195348").getMake()).getCars().get(0));
			assertEquals("2C4RDGBG2LR195348", makes.get(cars.get("2C4RDGBG2LR195348").getMake()).getCars().get(1));
			assertTrue(makes.get(cars.get("2C3CDXCT1LH153894").getMake()).getDealerships().contains("Nyle Maxwell Pre-Owned Supercenter"));
			assertTrue(makes.get(cars.get("2C4RDGBG2LR195348").getMake()).getDealerships().contains("Nyle Maxwell Pre-Owned Supercenter"));
			assertEquals(2, makes.get(cars.get("2C3CDXCT1LH153894").getMake()).getNumCars());
			assertEquals(1, makes.get(cars.get("2C3CDXCT1LH153894").getMake()).getNumDealerships());
			assertEquals(2, makes.get(cars.get("2C4RDGBG2LR195348").getMake()).getNumCars());
			assertEquals(1, makes.get(cars.get("2C4RDGBG2LR195348").getMake()).getNumDealerships());
			
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
			m.setName("Dodge");
			makes.put("Dodge", m);
			
			Make j = new Make();
			j.setName("Jeep");
			makes.put("Jeep", j);
			
			Dealership d = new Dealership();
			d.setName("Nyle Maxwell Pre-Owned Supercenter");
			d.setAddress("13817 Research Blvd Austin, TX 78750");
			d.setPhoneNum("New (512) 861-0095");
			d.setWebsite("http://www.nylemaxwellcdjr.com/?utm_source=cars.com&utm_medium=referral");
			dealerships.put(d.getName(), d);
			
			carUrls.add("https://www.cars.com/vehicledetail/detail/805677938/overview/");
			carUrls.add("https://www.cars.com/vehicledetail/detail/795026331/overview/");
			for(String carUrl : carUrls) {
					String doc = run(carUrl, client);
					Car newCar = new Car();
					
					newCar.setUrl(carUrl);
					
					//Get car name
					Elements instances = Jsoup.parse(doc).getElementsByClass("cui-heading-2--secondary vehicle-info__title");
					for(Element instance : instances) {
						newCar.setName(instance.text());
					}
					
					//Get car image
					instances = Jsoup.parse(doc).getElementsByClass("media-gallery__display-item media-gallery__display-item--image");
					for(Element instance : instances) {
						newCar.setImg(instance.attr("src"));
					}
					
					//Get car VIN and MPGs
					instances = Jsoup.parse(doc).getElementsByClass("vdp-details-basics__item");
					String carVin = "FAILED";
					for(Element instance : instances) {
						if(instance.select("strong").text().equals("VIN:")) {
							newCar.setVin(instance.select("span[ng-non-bindable]").text());							
						}
						if(instance.select("strong").text().equals("City MPG:")) {
							newCar.setMpg(instance.select("span[ng-non-bindable]").text() + "city/");							
						}
						if(instance.select("strong").text().equals("Highway MPG:")) {
							newCar.setMpg(newCar.getMpg() + instance.select("span[ng-non-bindable]").text() + "hwy");							
						}
					}
					
					
					//Get car dealership
					instances = Jsoup.parse(doc).getElementsByClass("vdp-dealer-location");
					for (Element instance : instances) { // should only loop once
						newCar.setDealership(instance.getElementsByClass("vdp-dealer-info__title").text()); //adds dealership info to car					
						dealerships.get(newCar.getDealership()).getCars().add(newCar.getVin()); //adds car to dealership car list
					}
					
					
					
					//Get car price
					instances = Jsoup.parse(doc).getElementsByClass("vehicle-info__price-display");
					for (Element instance : instances) { // should only loop once
						newCar.setPrice(Integer.parseInt(instance.text().replace("$", "").replace(",", "")));
					}
					
					
					Set<String> makeName = makes.keySet();
					for(String make : makeName) {					//loop until it finds matching make
						String[] carName = newCar.getName().split(" ");
						if(carName[1].equals(make)){				//if car has the make in its name
							makes.get(make).getCars().add(newCar.getVin());	//then make gets that car added
							newCar.setMake(make);						//car getts the make
							dealerships.get(newCar.getDealership()).getMakes().add(make);		//dealership that already has the car get the make
							makes.get(make).getDealerships().add(newCar.getDealership());		//make gets that dealership added to the make
							Integer numCars = makes.get(make).getNumCars();
							numCars++;
							makes.get(make).setNumCars(numCars);
							makes.get(make).setNumDealerships(makes.get(make).getDealerships().size());
						}
					}
					
					cars.put(newCar.getVin(), newCar);
			}
			
			//Car 2020 Dodge Grand Caravan SE
			assertEquals("https://www.cars.com/vehicledetail/detail/805677938/overview/", cars.get("2C4RDGBG2LR195348").getUrl());
			assertEquals("2020 Dodge Grand Caravan SE", cars.get("2C4RDGBG2LR195348").getName());
			assertEquals("https://www.cstatic-images.com/supersized/in/v1/444790/2C4RDGBG2LR195348/306f047c94328f16399179c91c411288.jpg", 
					cars.get("2C4RDGBG2LR195348").getImg());
			assertEquals(27040, cars.get("2C4RDGBG2LR195348").getPrice());
			assertEquals("2C4RDGBG2LR195348", cars.get("2C4RDGBG2LR195348").getVin());
			assertEquals("17city/25hwy", cars.get("2C4RDGBG2LR195348").getMpg());
			assertEquals("Nyle Maxwell Pre-Owned Supercenter", cars.get("2C4RDGBG2LR195348").getDealership());
			assertEquals("Dodge", cars.get("2C4RDGBG2LR195348").getMake());
			
			//2020 Jeep Cherokee Latitude Plus
			assertEquals("https://www.cars.com/vehicledetail/detail/795026331/overview/", cars.get("1C4PJLLB5LD560863").getUrl());
			assertEquals("2020 Jeep Cherokee Latitude Plus", cars.get("1C4PJLLB5LD560863").getName());
			assertEquals("https://www.cstatic-images.com/supersized/in/v1/444790/1C4PJLLB5LD560863/532cab5a3e04d64eb372092e4c97a1b3.jpg", 
					cars.get("1C4PJLLB5LD560863").getImg());
			assertEquals(25881, cars.get("1C4PJLLB5LD560863").getPrice());
			assertEquals("1C4PJLLB5LD560863", cars.get("1C4PJLLB5LD560863").getVin());
			assertEquals("22city/31hwy", cars.get("1C4PJLLB5LD560863").getMpg());
			assertEquals("Nyle Maxwell Pre-Owned Supercenter", cars.get("1C4PJLLB5LD560863").getDealership());
			assertEquals("Jeep", cars.get("1C4PJLLB5LD560863").getMake());
			
			//Dealer
			assertEquals("Nyle Maxwell Pre-Owned Supercenter", dealerships.get(cars.get("2C4RDGBG2LR195348").getDealership()).getName());
			assertEquals("Nyle Maxwell Pre-Owned Supercenter", dealerships.get(cars.get("1C4PJLLB5LD560863").getDealership()).getName());
			assertEquals("1C4PJLLB5LD560863", dealerships.get(cars.get("1C4PJLLB5LD560863").getDealership()).getCars().get(0));
			assertEquals("2C4RDGBG2LR195348", dealerships.get(cars.get("1C4PJLLB5LD560863").getDealership()).getCars().get(1));
			assertEquals("1C4PJLLB5LD560863", dealerships.get(cars.get("2C4RDGBG2LR195348").getDealership()).getCars().get(0));
			assertEquals("2C4RDGBG2LR195348", dealerships.get(cars.get("2C4RDGBG2LR195348").getDealership()).getCars().get(1));
			assertTrue(dealerships.get(cars.get("1C4PJLLB5LD560863").getDealership()).getMakes().contains("Dodge"));
			assertTrue(dealerships.get(cars.get("1C4PJLLB5LD560863").getDealership()).getMakes().contains("Jeep"));
			assertTrue(dealerships.get(cars.get("2C4RDGBG2LR195348").getDealership()).getMakes().contains("Dodge"));
			assertTrue(dealerships.get(cars.get("2C4RDGBG2LR195348").getDealership()).getMakes().contains("Jeep"));
			
			//Make
			assertEquals("Jeep", makes.get(cars.get("1C4PJLLB5LD560863").getMake()).getName());
			assertEquals("Dodge", makes.get(cars.get("2C4RDGBG2LR195348").getMake()).getName());
			assertEquals("1C4PJLLB5LD560863", makes.get(cars.get("1C4PJLLB5LD560863").getMake()).getCars().get(0));
			assertEquals("2C4RDGBG2LR195348", makes.get(cars.get("2C4RDGBG2LR195348").getMake()).getCars().get(0));
			assertTrue(makes.get(cars.get("1C4PJLLB5LD560863").getMake()).getDealerships().contains("Nyle Maxwell Pre-Owned Supercenter"));	
			assertTrue(makes.get(cars.get("2C4RDGBG2LR195348").getMake()).getDealerships().contains("Nyle Maxwell Pre-Owned Supercenter"));
			assertEquals(1, makes.get(cars.get("1C4PJLLB5LD560863").getMake()).getNumCars());
			assertEquals(1, makes.get(cars.get("1C4PJLLB5LD560863").getMake()).getNumDealerships());
			assertEquals(1, makes.get(cars.get("2C4RDGBG2LR195348").getMake()).getNumCars());
			assertEquals(1, makes.get(cars.get("2C4RDGBG2LR195348").getMake()).getNumDealerships());
			
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
