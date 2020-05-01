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

public abstract class DBServlet {
	public MongoClientURI uri;
	public MongoClient mongoClient;
	public MongoDatabase db;
	public List<String> dealerNames;
	public List<String> carVins;
	public List<String> makeNames;
	public TreeSet<String> makeMarkets;
	
	public DBServlet() {
	}
	
	
	public abstract Object getAttribute(String name, String attribute);
	
	public String buildCard(String s) {return null;}

	protected static List<Document> getAllDocuments(MongoCollection<Document> col) {
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

}
