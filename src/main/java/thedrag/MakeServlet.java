package thedrag;

import static com.mongodb.client.model.Filters.eq;

import java.util.*;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.operation.OrderBy;

public class MakeServlet extends DBServlet {
	
	
	public MakeServlet() {
		uri = new MongoClientURI(
			    "mongodb+srv://jdwalsh21:BI6SfPDyhGX8ihAU@thedragapiscrapes-2duen.gcp.mongodb.net/test?authSource=admin&replicaSet=TheDragAPIScrapes-shard-0&readPreference=primary&appname=MongoDB%20Compass&ssl=true");

		mongoClient = new MongoClient(uri);

		db = mongoClient.getDatabase("The_Drag");
		makeNames = new ArrayList<String>();
		makeMarkets = new TreeSet<String>();
		MongoCollection col = db.getCollection("makes");
		List<Document> allMakeDocs = getAllDocuments(col);
		for(Document doc : allMakeDocs) {
			makeNames.add((String)doc.get("_id"));
			String marketName = ((Document)doc.get("query")).get("market").toString();
			if(marketName.equals("1959-2015")) {
				makeMarkets.add(((Document)doc.get("query")).get("years").toString());
			} else {
				makeMarkets.add(marketName);
			}
		}
	}
	

	@Override
	public Object getAttribute(String name, String attribute) {
		MongoCollection col = db.getCollection("makes"); //"makes"

		Document doc = (Document) col.find(eq("_id", name)).first();

		if(doc == null)
			return null;

		return ((Document)doc.get("query")).get(attribute);
	}
	
	public ArrayList<String> makeMarketFilter(String marketFilter) {
		ArrayList<String> filterResults = new ArrayList<String>();
		MongoCollection col = db.getCollection("makes");
		List<Document> allMakeDocs = getAllDocuments(col);
		
		for(Document doc : allMakeDocs) {
			String market = ((Document)doc.get("query")).get("market").toString();
			if(market.equals("1959-2015")) {
				market = ((Document)doc.get("query")).get("years").toString();
			}
			if(market.equals(marketFilter)) {
				filterResults.add((String)doc.get("_id"));
			}
		}
		return filterResults;
	}
	
	public List<String> makeSearch(List<String> list,String term){
		List<String> rtn  = new ArrayList<String>();

		for(String s : list) {
			if(s.toLowerCase().contains(term.toLowerCase()))
				rtn.add(s);

		}

		return rtn;
	}
	
	@Override
	public String buildCard(String s) {
		String name = getAttribute(s, "name").toString();
		String slug=name.replace(" ","_");
		String listing= "<li class='card np-element np-hover col-4 make-card' style='margin: 20px;height:375px;' >"+
				  "<a href='/html/make-instance.jsp?make=" + slug + "' style='margin:0px;display:block;width:100%;height:100%;'>"+
					"<h3 style='text-align: center;'>" + name + "</h3>";
				
		String image = getAttribute(s, "img").toString();
		String numCars = getAttribute(s, "numCars").toString();
		ArrayList<String> dealers = (ArrayList<String>)getAttribute(s,"dealerships");
		int numDealerships = dealers.size();
		
		String market = getAttribute(s,"market").toString();
		String years = getAttribute(s,"years").toString();
		String url = getAttribute(s,"url").toString();
		
		
		if(!image.equals(""))
			listing += "<div class='np-img-wrapper' width='30px' height='30px'>" + "<img class='np-img-expand' src='" + image + "' width='inherit' height='inherit' style='margin: 10px'></div>";
		if(!market.equals(""))
			listing += "<p><strong>Market:</strong> " + market + "</p>";
		if(!years.equals(""))
			listing += "<p><strong>Years sold in:</strong> " + years + "</p>"; 
		if(!url.equals("")){
			listing += "<a href='" + url + "'><strong><u>Make Website</u></strong></a>";
		}
		 
		listing += "</a> </li>";
		
		return listing;
	}
	
}