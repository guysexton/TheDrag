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

public class DBServlet {
	MongoClientURI uri;
	MongoClient mongoClient;
	MongoDatabase db;

	public List<String> dealerNames;
	public List<String> makeNames;
	public List<String> carVins;
	public TreeSet<String> makeMarkets;

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
		Collections.sort(dealerNames);

		makeNames = new ArrayList<String>();
		makeMarkets = new TreeSet<String>();
		col = db.getCollection("makes");
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

	// Searching methods  ***************************************************************************


	public List<String> dealerSearch(List<String> list,String term){
		List<String> rtn  = new ArrayList<String>();

		for(String s : list) {
			if(s.toLowerCase().contains(term.toLowerCase()))
				rtn.add(s);

		}

		return rtn;
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
	
	public List<String> makeSearch(List<String> list,String term){
		List<String> rtn  = new ArrayList<String>();

		for(String s : list) {
			if(s.toLowerCase().contains(term.toLowerCase()))
				rtn.add(s);

		}

		return rtn;
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

	// *********************************************************************************************


	// *********************************************//
	// Car Search Template //
	/*
	 * Returns an ArrayList with the cars that match the search term
	 */
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
	// *********************************************//

	public static void main(String[] args) {
		DBServlet dbservlet = new DBServlet();
		System.out.println(dbservlet.makeMarkets);
		//System.out.println(dbservlet.getDealershipAttribute("Covert Cadillac", "address"));
	}
}
