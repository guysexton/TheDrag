package web_scraping;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import okhttp3.OkHttpClient;



public class DealershipScraper extends Scraper{
	
	public static void scrapeDealerURLs(String link, OkHttpClient client) throws IOException {
		String ddoc = run(link, client);
		Elements urls = Jsoup.parse(ddoc).getElementsByClass("dealer-contact");
		String s = null;
		for (Element url : urls) {
			s = "https://www.cars.com" + url.select("a").attr("href");
			getDealers().add(s);
		}
	}
	
	public static void scrapeFromDealerUrls(OkHttpClient client) {
		try {	
			for(String url : getDealers()) {
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
		d.setName(Jsoup.parse(doc).getElementsByClass("seller-name cui-alpha dealer-review__seller-name").text());
		d.setImg(Jsoup.parse(doc).getElementsByClass("dealer__logo").attr("src"));
		d.setAddress(Jsoup.parse(doc).getElementsByClass("dealer-update__streetAddress").text());
		d.setPhoneNum(Jsoup.parse(doc).getElementsByClass("dealer-update__contact-numbers--new").text() + " " 
							+ Jsoup.parse(doc).getElementsByClass("dealer-update__contact-numbers--used").text());
		d.setWebsite(Jsoup.parse(doc).getElementsByClass("dealer-update-website-link").attr("href"));
		d.setHours(Jsoup.parse(doc).getElementsByClass("dpp-update__sales-hours-operation").text());
		Elements e = Jsoup.parse(doc).getElementsByClass("about-dealership-section__container");
		String about = null;
		for(Element e1: e) {
			about = e1.getElementsByClass("cui-section__accordion-preview").text()
			 	+ e1.getElementsByClass("cui-section__accordion-content").text();
		}
		d.setAbout(about);
		getDealerships().put(d.getName(), d);
	}
}