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
	static OkHttpClient client;

	public static void main(String[] args) {
		client = new OkHttpClient.Builder().readTimeout(15, TimeUnit.SECONDS).callTimeout(15, TimeUnit.SECONDS).build();  // socket timeout
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

			Set<String> carUrls = new HashSet<String>();
			boolean getOut = false;
			int numDuplicates = 0;
			int i = 1;
			while(true) {
				System.out.println("Retrieving page " + i + "....");
				String doc = run("https://www.cars.com/for-sale/searchresults.action/?mkId=20088&page=" + i + "&perPage=100&rd=20&searchSource=PAGINATION&sort=relevance&stkTypId=28880&zc=78705", client);
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
			System.out.println(carUrls.size());
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
