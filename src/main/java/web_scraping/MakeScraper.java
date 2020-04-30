package web_scraping;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import okhttp3.OkHttpClient;

public class MakeScraper extends Scraper{
	
	public static void scrapeMakeIDs(String link, OkHttpClient client) throws IOException {
		String mdoc = run(link, client);
		Elements options = Jsoup.parse(mdoc).getElementsByAttributeValue("name", "makeId").get(0).children();
		String makeId = null;
		for (Element option : options) {
			makeId = option.val();
			getMakeIds().add(makeId);
		}
	}
	
	public static void scrapeCarLogos(OkHttpClient client) throws IOException {
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
				m.setUrl(url);
				m.setName(name);
				m.setMarket(market);
				m.setYears(years);
				m.setImg(image);
				getMakes().put(name, m);
			}
		}
	}
}