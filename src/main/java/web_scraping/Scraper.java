package web_scraping;

import java.io.IOException;
import java.util.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class Scraper {
	static OkHttpClient client = new OkHttpClient();
	public static void main(String[] args) {
		 try {
			
			/*
			 * Scrapes all makeIds
			 */
			/*
			String doc = run("https://www.cars.com/", client);
			Elements options = Jsoup.parse(doc).getElementsByAttributeValue("name", "makeId").get(0).children();
			ArrayList<String> makes = new ArrayList<>();
			String makeId = null;
			for (Element option : options) {
			        makeId = option.val();
			        makes.add(makeId);
			}
			System.out.println(makes);
			*/
			 
			/*
			 * Scrapes car urls
			 */
			String doc = run("https://www.cars.com/for-sale/searchresults.action/?mkId=20088&page=1&perPage=100&rd=20&searchSource=PAGINATION&sort=relevance&stkTypId=28880&zc=78705", client);
			Elements instances = Jsoup.parse(doc).getElementsByClass("shop-srp-listings__listing-container");
			ArrayList<String> carUrls = new ArrayList<>();
			String url = null;
			for (Element instance : instances) {				
			        url = "https://www.cars.com" + instance.getElementsByClass("shop-srp-listings__listing").attr("href");
			        carUrls.add(url);
			}
			System.out.println(carUrls);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	static String run(String url, OkHttpClient client) throws IOException {
		Request request = new Request.Builder()
				.url(url)
				.build();
		
		try (Response response = client.newCall(request).execute()) {
			return response.body().string();
		}
	}

}
