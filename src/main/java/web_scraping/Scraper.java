package web_scraping;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class Scraper {
	public static OkHttpClient client;
	public static Set<String> carUrls = new HashSet<String>();
	public static Set<Car> cars;
	public static Set<Dealership> dealerships = new HashSet<Dealership>();
	public static ArrayList<String> makes = new ArrayList<>();
	public static ArrayList<String> dealers = new ArrayList<String>();
	
	public static void main(String[] args) {
		client = new OkHttpClient.Builder().readTimeout(30, TimeUnit.SECONDS).callTimeout(30, TimeUnit.SECONDS).build();  // socket timeout
		
		try {

			
			//Scrapes all makeIds			 
			String mdoc = run("https://www.cars.com/", client);
			Elements options = Jsoup.parse(mdoc).getElementsByAttributeValue("name", "makeId").get(0).children();
			
			String makeId = null;
			for (Element option : options) {
				makeId = option.val();
				makes.add(makeId);
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
			
			for(int j = 1; j < makes.size(); j++) {
				String makeIdAgain = makes.get(j);
				
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
				carUrls = new HashSet<String>();
			} 
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		System.out.println("\nSUCCESSFULLY SCRAPED ALL MAKES\n");
		System.exit(0);
		
		
		
		//Scrapes from car URLs
		 	
		cars = new HashSet<Car>();
		for(String url : carUrls) {
			try {
				String doc = run(url, client);
				Elements instances = Jsoup.parse(doc).getElementsByClass("vdp-details-basics__item");

				//Scrape VINs
				for(Element instance : instances) {
					if(instance.select("strong").text().equals("VIN:")) {
						String carVin = instance.select("span[ng-non-bindable]").text();						
						cars.add(new Car(url, carVin));
					}
				}
				
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}

	}

	public static void scrapeDealerUrls() {
		try {	
			for(int i = 0; i < dealers.size(); i++) {
				String url = "https://www.cars.com/dealers/22731/cag-first-texas-honda/";
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
		d.phoneNum = Jsoup.parse(doc).getElementsByClass("dealer-update__contact-numbers--new").text();
		d.website = Jsoup.parse(doc).getElementsByClass("dealer-update-website-link").attr("href");
		dealerships.add(d);
		/*
		//Get Dealership name and address
		Dealership d = new Dealership();
		Elements instances = Jsoup.parse(doc).getElementsByClass("vdp-dealer-location");
		for (Element instance : instances) {
			String dealershipName = instance.getElementsByClass("vdp-dealer-info__title").text();
			String address = instance.getElementsByClass("vdp-dealer-location__address").text();
			d.name = dealershipName;
			d.address = address;
		}
		
		//Get Dealership phone number
		instances = Jsoup.parse(doc).getElementsByClass("vdp-cap-seller-exp__phone");
		for (Element instance : instances) {
			String phoneNumber = instance.getElementsByClass("dni-replace-8669105682").text();
			d.phoneNum = phoneNumber;
		}
		
		//Get Dealership website
		instances = Jsoup.parse(doc).getElementsByClass("vdp-dealer-links");
		Element instance = instances.get(0).child(1);
		String website = instance.attr("href");
		d.website = website;
		
		
		dealerships.add(d);
		System.out.println(dealerships);
		*/
	}

	public static String run(String url, OkHttpClient client) throws IOException {
		Request request = new Request.Builder()
				.url(url)
				.build();

		try (Response response = client.newCall(request).execute()) {
			return response.body().string();
		}
	}

	public static class Car{
		String url, vin;
		Car(String url, String vin){
			this.url = url;
			this.vin = vin;
		}
	}

}
