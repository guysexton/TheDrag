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
	
	@Override
	public String buildCard(String s) {
		
		String name = getAttribute(s, "name").toString();
		  String slug = name.replace('&','$').replace(' ','_').replace("'",".")+"~";
		  
		  String listing= "<li class='card np-element np-hover col-4 dealer-card' style='margin: 20px;height:275px;' >"+
					"<a href='/html/view-dealer.jsp?dealership=" + slug + "' style='margin:0px;display:block;width:100%;height:100%;'>"+
					"<h3 style='text-align: center;'>" + name + "</h3>";
				
		String image = getAttribute(s, "img").toString();
		String address = getAttribute(s, "address").toString();
		String phoneNum = getAttribute(s, "phoneNum").toString();
		String website = getAttribute(s, "website").toString();
		
		listing += "<div class='np-img-wrapper' width='50px' height='50px' style='display:block;'>";
		if(!image.equals(""))
			listing += "<img class='np-img-expand' src='" + image + "' width='inherit' height='inherit' style='margin: 10px'>";
		listing+="</div>";
			
		if(!address.equals(""))
			listing += "<p><strong>Address:</strong> " + address + "</p>";
		else
			listing += "<p><strong>Address:</strong> N/A </p>";
			
		if(!phoneNum.equals(" "))
			listing += "<p><strong>Phone:</strong> " + phoneNum + "</p>";
		else
			listing += "<p><strong>Phone:</strong> N/A </p>";
			
		if(!website.equals(""))
			listing += "<a href='" + website + "'><strong>Visit Dealer Website</strong></a>";
		else
			listing += "<p><strong>No Dealer Website Listed</strong></p>";
			
		listing += "</a> </li>";
		
		return listing;
		
	}
	
}
