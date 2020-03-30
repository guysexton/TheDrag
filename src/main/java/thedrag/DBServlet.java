package thedrag;

import static com.mongodb.client.model.Filters.eq;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class DBServlet {
	MongoClientURI uri;
	MongoClient mongoClient;
	MongoDatabase db;
	
	public DBServlet() {
		uri = new MongoClientURI(
			    "mongodb+srv://jdwalsh21:BI6SfPDyhGX8ihAU@thedragapiscrapes-2duen.gcp.mongodb.net/test?authSource=admin&replicaSet=TheDragAPIScrapes-shard-0&readPreference=primary&appname=MongoDB%20Compass&ssl=true");
	
		mongoClient = new MongoClient(uri);
			
		db = mongoClient.getDatabase("The_Drag");
	}
	
	public Object getMakeAttribute(String makeName, String attribute) {
		MongoCollection col = db.getCollection("makes");
		
		Document doc = (Document) col.find(eq("_id", makeName)).first();
		
		if(doc == null)
			return null;
		
		return ((Document)doc.get("query")).get(attribute);
	}
	
	public Object getDealershipAttribute(String dealershipName, String attribute) {
		MongoCollection col = db.getCollection("makes");
		
		Document doc = (Document) col.find(eq("_id", dealershipName)).first();
		
		if(doc == null)
			return null;
		
		return ((Document)doc.get("query")).get(attribute);
	}
	
	public Object getCarAttribute(String vin, String attribute) {
		MongoCollection col = db.getCollection("cars");
		
		Document doc = (Document) col.find(eq("_id", vin)).first();
		
		if(doc == null)
			return null;
		
		return ((Document)doc.get("query")).get(attribute);
	}
	
	/*public static void main(String[] args) {
		DBServlet dbservlet = new DBServlet();
	}*/
}
