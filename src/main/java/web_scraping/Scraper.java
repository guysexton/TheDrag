package web_scraping;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public abstract class Scraper {
	private static OkHttpClient client;
	private static Set<String> carUrls = new HashSet<String>();
	private static Map<String, Car> cars = new HashMap<String, Car>();
	private static Map<String, Dealership> dealerships = new HashMap<String, Dealership>();
	private static Map<String, Make> makes = new HashMap<String, Make>();
	private static ArrayList<String> makeIds = new ArrayList<>();
	private static HashSet<String> dealers = new HashSet<String>();
	
	public static OkHttpClient getClient() {
		return client;
	}

	public static void setClient(OkHttpClient client) {
		Scraper.client = client;
	}

	public static Set<String> getCarUrls() {
		return carUrls;
	}

	public static void setCarUrls(Set<String> carUrls) {
		Scraper.carUrls = carUrls;
	}

	public static Map<String, Car> getCars() {
		return cars;
	}

	public static void setCars(Map<String, Car> cars) {
		Scraper.cars = cars;
	}

	public static Map<String, Dealership> getDealerships() {
		return dealerships;
	}

	public static void setDealerships(Map<String, Dealership> dealerships) {
		Scraper.dealerships = dealerships;
	}

	public static Map<String, Make> getMakes() {
		return makes;
	}

	public static void setMakes(Map<String, Make> makes) {
		Scraper.makes = makes;
	}

	public static ArrayList<String> getMakeIds() {
		return makeIds;
	}

	public static void setMakeIds(ArrayList<String> makeIds) {
		Scraper.makeIds = makeIds;
	}

	public static HashSet<String> getDealers() {
		return dealers;
	}

	public static void setDealers(HashSet<String> dealers) {
		Scraper.dealers = dealers;
	}

	//Run main to web scrape
	public static void main(String[] args) {
		client = new OkHttpClient.Builder().readTimeout(30, TimeUnit.SECONDS).callTimeout(30, TimeUnit.SECONDS).build();  // socket timeout
		
		try {
	
			//Scrapes all makeIds			 
			MakeScraper.scrapeMakeIDs("https://www.cars.com/", client);
			
			//scrape carlogos.org
			MakeScraper.scrapeCarLogos(client);

			// Scrapes dealer urls
			DealershipScraper.scrapeDealerURLs("https://www.cars.com/dealers/buy/78705/?rd=30&sortBy=DISTANCE&order=ASC&page=1&perPage=250", client);
			
			DealershipScraper.scrapeFromDealerUrls(client);
			
			// Scrapes car urls
			CarScraper.scrapeCarURLs(client);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		System.out.println("\nSUCCESSFULLY SCRAPED ALL MAKES\n");

		//Scrapes from car URLs
		CarScraper.scrapeFromCars(client);
		
		//Writing to a JSON file with Jackson Library
		ConvertToJSON.convertToJSON(dealerships, cars, makes);

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