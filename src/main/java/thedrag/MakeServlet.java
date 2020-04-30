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
	
}