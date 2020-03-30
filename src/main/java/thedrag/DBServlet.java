package thedrag;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

public class DBServlet {
	MongoClientURI uri;
	MongoClient mongoClient;
	MongoDatabase db;
	
	public List<String> dealerNames;
	public List<String> makeNames;
	public List<String> carVins;
	
	public DBServlet() {
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
		
		makeNames = new ArrayList<String>();
		col = db.getCollection("makes");
		List<Document> allMakeDocs = getAllDocuments(col);
		for(Document doc : allMakeDocs) {
			makeNames.add((String)doc.get("_id"));
		}
		
		carVins = new ArrayList<String>();
		col = db.getCollection("cars");
		List<Document> allCarDocs = getAllDocuments(col);
		for(Document doc : allCarDocs) {
			carVins.add((String)doc.get("_id"));
		}
				
	}
	
	public Object getMakeAttribute(String makeName, String attribute) {
		MongoCollection col = db.getCollection("makes");
		
		Document doc = (Document) col.find(eq("_id", makeName)).first();
		
		if(doc == null)
			return null;
		
		return ((Document)doc.get("query")).get(attribute);
	}
	
	public Object getDealershipAttribute(String dealershipName, String attribute) {
		MongoCollection col = db.getCollection("dealerships");
		
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
	
	private static List<Document> getAllDocuments(MongoCollection<Document> col) {
		List<Document> allDocs = new ArrayList<Document>();
		
        // Performing a read operation on the collection.
        FindIterable<Document> fi = col.find();
        MongoCursor<Document> cursor = fi.iterator();
        try {
            while(cursor.hasNext()) {               
                allDocs.add(cursor.next());
            }
        } finally {
            cursor.close();
        }
        
        return allDocs;
    }
	
	public int getDealerPgNumbers() {
		return((int)Math.ceil(dealerNames.size()/10));
	}
	
	public String getJSDealerArray(int page) {
		int startI = (page-1)*10;
		int endI =Math.min((((page-1)*10)+10),dealerNames.size());
		
		String working = "[";
		
		for(int i=startI;i<endI;i++) {
		working+= "{ \"name\" : ";
		working+= "\"" + dealerNames.get(i) + "\" , \"img\" : ";
		working+= "\"" + getDealershipAttribute(dealerNames.get(i),"img") + "\" , \"makes\" : ";
		working+= "\"" + getDealershipAttribute(dealerNames.get(i),"makes") + "\" , \"cars\" : ";
		working+= "\"" + getDealershipAttribute(dealerNames.get(i),"cars") + "\" , \"address\" : ";
		working+= "\"" + getDealershipAttribute(dealerNames.get(i),"address") + "\" , \"phoneNum\" : ";
		working+= "\"" + getDealershipAttribute(dealerNames.get(i),"phoneNum") + "\" , \"website\" : ";
		working+= "\"" + getDealershipAttribute(dealerNames.get(i),"website") + "\" , \"image\" : ";
		working+= "\"" + getDealershipAttribute(dealerNames.get(i),"image") + "\" },";
		}
		
		working=working.substring(0,working.length()-1);
		working += "]";
		
		return working;
		
	}
	
	
	public static void main(String[] args) {
		DBServlet dbservlet = new DBServlet();
		
		System.out.println(dbservlet.getDealershipAttribute("Covert Cadillac", "address"));
	}
}
