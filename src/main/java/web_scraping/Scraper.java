package web_scraping;

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

public class Scraper {
	public static OkHttpClient client;
	public static Set<String> carUrls = new HashSet<String>();
	public static Map<String, Car> cars = new HashMap<String, Car>();
	public static Map<String, Dealership> dealerships = new HashMap<String, Dealership>();
	public static Map<String, Make> makes = new HashMap<String, Make>();
	public static ArrayList<String> makeIds = new ArrayList<>();
	public static HashSet<String> dealers = new HashSet<String>();
	
	public static void main(String[] args) {
		client = new OkHttpClient.Builder().readTimeout(30, TimeUnit.SECONDS).callTimeout(30, TimeUnit.SECONDS).build();  // socket timeout
		
		try {
			
			
			//Scrapes all makeIds			 
			String mdoc = run("https://www.cars.com/", client);
			Elements options = Jsoup.parse(mdoc).getElementsByAttributeValue("name", "makeId").get(0).children();
			String makeId = null;
			for (Element option : options) {
				makeId = option.val();
				makeIds.add(makeId);
			}
			
			//scrape carlogos.org
			for(int i = 1; i < 9; i++) {
				String makeDoc = run("https://www.carlogos.org/car-brands/list_1_" + i + ".html", client);
				Elements elements = Jsoup.parse(makeDoc).getElementsByClass("logo-list").select("li");
				for (Element e : elements) {
					String url = "https://www.carlogos.org" + e.select("a").attr("href");
					String name= e.select("a").first().ownText();
					String market = e.select("a").select("span").eq(0).text();
					String years = e.select("a").select("span").eq(1).text();
					String image = "https://www.carlogos.org" + e.select("a").select("img").attr("src");
					Make m = new Make();
					m.url = url;
					m.name = name;
					m.market = market;
					m.years = years;
					m.img = image;
					makes.put(name, m);
				}
			}
			

			// Scrapes dealer urls
			String ddoc = run("https://www.cars.com/dealers/buy/78705/?rd=30&sortBy=DISTANCE&order=ASC&page=1&perPage=250", client);
			Elements urls = Jsoup.parse(ddoc).getElementsByClass("dealer-contact");
			
			String s = null;
			for (Element url : urls) {
				s = "https://www.cars.com" + url.select("a").attr("href");
				dealers.add(s);
			}
			scrapeDealerUrls();
			
			
			
			// Scrapes car urls
			
			for(int j = 1; j < makeIds.size(); j++) {
				String makeIdAgain = makeIds.get(j);
				
				boolean getOut = false;
				int numDuplicates = 0;
				int i = 1;

				while(true) {
					System.out.println("Retrieving page " + i + " for make " + makeIdAgain);
					String doc = run("https://www.cars.com/for-sale/searchresults.action/?mkId=" + makeIdAgain + "&page=" + i + "&perPage=100&rd=20&searchSource=PAGINATION&sort=relevance&stkTypId=28880&zc=78705", client);

					String carNotFound = Jsoup.parse(doc).getElementsByClass("no-results-message").text();
					if(carNotFound.contains("We didn't find cars")) {
						break;
					}

					Elements instances = Jsoup.parse(doc).getElementsByClass("shop-srp-listings__listing-container");
					String url = null;

					if(instances.size() == 0)
						break;

					for (Element instance : instances) {				
						url = "https://www.cars.com" + instance.getElementsByClass("shop-srp-listings__listing").attr("href");
						if(carUrls.contains(url)) {
							numDuplicates++;
							System.out.println(url);
						}
						else
							carUrls.add(url);
					}	
					i++;
				}
				System.out.println(makeIdAgain + ": " + carUrls.size() + " results");
				//carUrls = new HashSet<String>();
				
			} 	
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		System.out.println("\nSUCCESSFULLY SCRAPED ALL MAKES\n");
		
	
		//Scrapes from car URLs
		 	
		//carUrls.add("https://www.cars.com/vehicledetail/detail/805364103/overview/");
		int k = 1;
		for(String carUrl : carUrls) {
			try {
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
				//System.out.println(dealerships.get(newCar.dealership));
				//System.out.println(makes.get(newCar.make));
				//System.out.println("Scraped car: " + newCar);
				
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			System.out.println("Finished scrapeing car page: " + carUrl + " | Counter: " + k);
			k++;
		}
		
		System.out.println(cars.size());
		
		//Testing writing to a JSON file with Jackson Library
		ObjectMapper mapper = new ObjectMapper();
		mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
		mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		try {
            mapper.writeValue(new File("dealerships.json"), dealerships);
            mapper.writeValue(new File("cars.json"), cars);
            mapper.writeValue(new File("makes.json"), makes);
        } catch (Exception e) {
            e.printStackTrace();
        }

	}

	public static void scrapeDealerUrls() {
		try {	
			for(String url : dealers) {
				System.out.println("Retrieving page " + url);
				String doc = run(url, client);
				addDealership(doc);
			} 
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	//Adds name, address, phone number, and website url to a dealership instance
	public static void addDealership(String doc) {
		Dealership d = new Dealership();
		d.name = Jsoup.parse(doc).getElementsByClass("seller-name cui-alpha dealer-review__seller-name").text();
		d.img = Jsoup.parse(doc).getElementsByClass("dealer__logo").attr("src");
		d.address = Jsoup.parse(doc).getElementsByClass("dealer-update__streetAddress").text();
		d.phoneNum = Jsoup.parse(doc).getElementsByClass("dealer-update__contact-numbers--new").text() + " " 
							+ Jsoup.parse(doc).getElementsByClass("dealer-update__contact-numbers--used").text();
		d.website = Jsoup.parse(doc).getElementsByClass("dealer-update-website-link").attr("href");
		d.hours = Jsoup.parse(doc).getElementsByClass("dpp-update__sales-hours-operation").text();
		Elements e = Jsoup.parse(doc).getElementsByClass("about-dealership-section__container");
		String about = null;
		for(Element e1: e) {
			about = e1.getElementsByClass("cui-section__accordion-preview").text()
			 	+ e1.getElementsByClass("cui-section__accordion-content").text();
		}
		d.about = about;
		dealerships.put(d.name, d);
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
