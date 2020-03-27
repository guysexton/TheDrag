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
	public static HashMap<String, String> dealerships = new HashMap<String, String>();
	
	public static void main(String[] args) {
		client = new OkHttpClient.Builder().readTimeout(30, TimeUnit.SECONDS).callTimeout(30, TimeUnit.SECONDS).build();  // socket timeout
		try {

			/*
			 * Scrapes all makeIds
			 */
			String mdoc = run("https://www.cars.com/", client);
			Elements options = Jsoup.parse(mdoc).getElementsByAttributeValue("name", "makeId").get(0).children();
			ArrayList<String> makes = new ArrayList<>();
			String makeId = null;
			for (Element option : options) {
				makeId = option.val();
				makes.add(makeId);
			}


			/*
			 * Scrapes car urls
			 */
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
		
		
		/*
		 * Scrapes VIN from car URLs
		 */	
		cars = new HashSet<Car>();

		for(String url : carUrls) {
			try {
				String doc = run(url, client);
				Elements instances = Jsoup.parse(doc).getElementsByClass("vdp-details-basics__item");

				for(Element instance : instances) {
					if(instance.select("strong").text().equals("VIN:")) {
						String carVin = instance.select("span[ng-non-bindable]").text();						
						cars.add(new Car(url, carVin));
					}
				}
				
				//Get Dealership name and address
				instances = Jsoup.parse(doc).getElementsByClass("vdp-dealer-location");
				for (Element instance : instances) {
					String dealershipName = instance.getElementsByClass("vdp-dealer-info__title").text();
					String address = instance.getElementsByClass("vdp-dealer-location__address").text();
					dealerships.put(dealershipName, address);
					System.out.println(dealerships);
				}
				
			}
			catch(Exception e) {
				e.printStackTrace();
			}
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

	public static class Car{
		String url, vin;
		Car(String url, String vin){
			this.url = url;
			this.vin = vin;
		}
	}

}
