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

public class CarServlet extends DBServlet {

	public CarServlet() {
		uri = new MongoClientURI(
			    "mongodb+srv://jdwalsh21:BI6SfPDyhGX8ihAU@thedragapiscrapes-2duen.gcp.mongodb.net/test?authSource=admin&replicaSet=TheDragAPIScrapes-shard-0&readPreference=primary&appname=MongoDB%20Compass&ssl=true");

		mongoClient = new MongoClient(uri);

		db = mongoClient.getDatabase("The_Drag");
		carVins = new ArrayList<String>();
		MongoCollection col = db.getCollection("cars"); //"cars"
		List<Document> allCarDocs = getAllDocuments(col);
		for(Document doc : allCarDocs) {
			carVins.add((String)doc.get("_id"));
		}
	}

	@Override
	public Object getAttribute(String name, String attribute) {
		MongoCollection col = db.getCollection("cars");

		Document doc = (Document) col.find(eq("_id", name)).first();

		if(doc == null)
			return null;

		return ((Document)doc.get("query")).get(attribute);
	}
	
	public List<String> sortCarYearsLowHigh() {
		List<String> to_sort = new ArrayList<String>();

		for (String car : carVins) {
			if (!car.contains("MOVETOU")) {
				to_sort.add(car);
			}
		}

		int size = to_sort.size();

		for (int i = 0; i < size; i++) {
			for (int j = i + 1; j < size; j++) {
				String first = String.valueOf(to_sort.get(i).charAt(9));
				String second = String.valueOf(to_sort.get(j).charAt(9));

				if (first.compareTo(second) > 0) {
					String temp_vin = to_sort.get(i);
					to_sort.set(i, to_sort.get(j));
					to_sort.set(j, temp_vin);
				}
			}
		}

		return to_sort;

	}

	public List<String> sortCarYearsHighLow() {
		List<String> to_sort = new ArrayList<String>();

		for (String car : carVins) {
			if (!car.contains("MOVETOU")) {
				to_sort.add(car);
			}
		}

		int size = to_sort.size();

		for (int i = 0; i < size; i++) {
			for (int j = i + 1; j < size; j++) {
				String first = String.valueOf(to_sort.get(i).charAt(9));
				String second = String.valueOf(to_sort.get(j).charAt(9));

				if (first.compareTo(second) < 0) {
					String temp_vin = to_sort.get(i);
					to_sort.set(i, to_sort.get(j));
					to_sort.set(j, temp_vin);
				}
			}
		}

		return to_sort;

	}
	
	public List<String> filterCarYears(char vin_value){
		List<String> to_filter = new ArrayList<String>();


		for (String car : carVins) {
			if (car.charAt(9) == vin_value && !car.contains("MOVETOU")) {
				to_filter.add(car);
			}
		}

		return to_filter;
	}
	
	public ArrayList<String> carSearch(String searchTerm) {
		ArrayList<String> searchResults = new ArrayList<String>();
		MongoCollection col = db.getCollection("cars");
		List<Document> allCarDocs = getAllDocuments(col);
		
		String lc_searchterm = searchTerm.toLowerCase();
		
		for(Document doc : allCarDocs) {
			String name = ((Document)doc.get("query")).get("name").toString();
			
			String lowercaseName = name.toLowerCase();
			
			if(lowercaseName.contains(lc_searchterm)) {
				searchResults.add((String)doc.get("_id"));
			}
		}
		return searchResults;
	}
	
}