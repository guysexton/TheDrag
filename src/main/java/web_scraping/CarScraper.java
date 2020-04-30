package web_scraping;

import java.io.IOException;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import okhttp3.OkHttpClient;

public class CarScraper extends Scraper{
	
	public static void scrapeCarURLs(OkHttpClient client) throws IOException {
		for(int j = 1; j < getMakeIds().size(); j++) {
			String makeIdAgain = getMakeIds().get(j);
			
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
					if(getCarUrls().contains(url)) {
						numDuplicates++;
						System.out.println(url);
					}
					else
						getCarUrls().add(url);
				}	
				i++;
			}
			System.out.println(makeIdAgain + ": " + getCarUrls().size() + " results");			
		}
	}
	
	public static void scrapeFromCars(OkHttpClient client) {
		int k = 1;
		for(String carUrl : getCarUrls()) {
			try {
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
					getDealerships().get(newCar.getDealership()).getCars().add(newCar.getVin()); //adds car to dealership car list
				}
				
				
				
				//Get car price
				instances = Jsoup.parse(doc).getElementsByClass("vehicle-info__price-display");
				for (Element instance : instances) { // should only loop once
					newCar.setPrice(Integer.parseInt(instance.text().replace("$", "").replace(",", "")));
				}
				
				
				Set<String> makeName = getMakes().keySet();
				for(String make : makeName) {					//loop until it finds matching make
					String[] carName = newCar.getName().split(" ");
					if(carName[1].equals(make)){				//if car has the make in its name
						getMakes().get(make).getCars().add(newCar.getVin());	//then make gets that car added
						newCar.setMake(make);						//car gets the make
						getDealerships().get(newCar.getDealership()).getMakes().add(make);		//dealership that already has the car get the make
						getMakes().get(make).getDealerships().add(newCar.getDealership());		//make gets that dealership added to the make
						Integer numCars = getMakes().get(make).getNumCars();
						numCars++;
						getMakes().get(make).setNumCars(numCars);
						getMakes().get(make).setNumDealerships(getMakes().get(make).getDealerships().size());
					}
				}
				
				getCars().put(newCar.getVin(), newCar);
				//System.out.println(dealerships.get(newCar.dealership));
				//System.out.println(makes.get(newCar.make));
				//System.out.println("Scraped car: " + newCar);
				
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			System.out.println("Finished scraping car page: " + carUrl + " | Counter: " + k);
			k++;
		}
		
		System.out.println(getCars().size());
	}
}