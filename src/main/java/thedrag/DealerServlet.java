package thedrag;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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

public class DealerServlet extends DBServlet {
	
	
	public DealerServlet() {
		uri = new MongoClientURI(
			    "mongodb+srv://jdwalsh21:BI6SfPDyhGX8ihAU@thedragapiscrapes-2duen.gcp.mongodb.net/test?authSource=admin&replicaSet=TheDragAPIScrapes-shard-0&readPreference=primary&appname=MongoDB%20Compass&ssl=true");

		mongoClient = new MongoClient(uri);

		db = mongoClient.getDatabase("The_Drag");
	
		dealerNames = new ArrayList<String>();
		MongoCollection col = db.getCollection("dealerships");
		List<Document> allDealerDocs = getAllDocuments(col);
		for(Document doc : allDealerDocs) {
			dealerNames.add((String)doc.get("_id"));
		}
		Collections.sort(dealerNames);
	}

	@Override
	public Object getAttribute(String name, String attribute) {
		MongoCollection col = db.getCollection("dealerships"); //"dealerships"

		Document doc = (Document) col.find(eq("_id", name)).first();

		if(doc == null)
			return null;

		return ((Document)doc.get("query")).get(attribute);
	}
	
	public List<String> dealerSearch(List<String> list,String term){
		List<String> rtn  = new ArrayList<String>();

		for(String s : list) {
			if(s.toLowerCase().contains(term.toLowerCase()))
				rtn.add(s);

		}

		return rtn;
	}
	
}
