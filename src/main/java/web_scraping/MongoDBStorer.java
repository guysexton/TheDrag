package web_scraping;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.bson.Document;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import  com.mongodb.client.FindIterable; 
import  com.mongodb.client.MongoDatabase; 
import  com.mongodb.client.MongoCollection; 
import  com.mongodb.MongoClient;
import  com.mongodb.BasicDBObject; 
import  com.mongodb.MongoClientURI; 
import  com.mongodb.ServerAddress; 
import  com.mongodb.MongoCredential; 
import  com.mongodb.MongoClientOptions; 

import static com.mongodb.client.model.Filters.eq;

public class MongoDBStorer {
	public static void main(String[] args){
		try {

			//System.out.print(System.getProperty("user.dir"));
			/*
			HashMap<String, Dealership> dealerMap = new ObjectMapper().readValue(new File("src/main/java/web_scraping/dealerships.json"), HashMap.class);

			List<Document> documents = new ArrayList<>();
			
			for(Map.Entry<String, Dealership> kv : dealerMap.entrySet()) {
				Document doc = new Document();
				doc.put("_id", kv.getKey());
				doc.put("query", kv.getValue());

				documents.add(doc);
			}
			
			MongoClientURI uri = new MongoClientURI(
				    "mongodb+srv://jdwalsh21:BI6SfPDyhGX8ihAU@thedragapiscrapes-2duen.gcp.mongodb.net/test?authSource=admin&replicaSet=TheDragAPIScrapes-shard-0&readPreference=primary&appname=MongoDB%20Compass&ssl=true");

			MongoClient mongoClient = new MongoClient(uri);
				
			MongoDatabase db = mongoClient.getDatabase("The_Drag");
			MongoCollection col = db.getCollection("dealerships");
			col.insertMany(documents);*/
			
			/*
			HashMap<String, Make> makeMap = new ObjectMapper().readValue(new File("src/main/java/web_scraping/makes.json"), HashMap.class);

			List<Document> documents = new ArrayList<>();
			
			for(Map.Entry<String, Make> kv : makeMap.entrySet()) {
				Document doc = new Document();
				doc.put("_id", kv.getKey());
				doc.put("query", kv.getValue());

				documents.add(doc);
			}
			
			MongoClientURI uri = new MongoClientURI(
				    "mongodb+srv://jdwalsh21:BI6SfPDyhGX8ihAU@thedragapiscrapes-2duen.gcp.mongodb.net/test?authSource=admin&replicaSet=TheDragAPIScrapes-shard-0&readPreference=primary&appname=MongoDB%20Compass&ssl=true");

			MongoClient mongoClient = new MongoClient(uri);
				
			MongoDatabase db = mongoClient.getDatabase("The_Drag");
			MongoCollection col = db.getCollection("makes");
			col.insertMany(documents);*/
			
			/*
			HashMap<String, Car> carMap = new ObjectMapper().readValue(new File("src/main/java/web_scraping/cars.json"), HashMap.class);

			List<Document> documents = new ArrayList<>();
			
			for(Map.Entry<String, Car> kv : carMap.entrySet()) {
				Document doc = new Document();
				doc.put("_id", kv.getKey());
				doc.put("query", kv.getValue());

				documents.add(doc);
			}
			
			MongoClientURI uri = new MongoClientURI(
				    "mongodb+srv://jdwalsh21:BI6SfPDyhGX8ihAU@thedragapiscrapes-2duen.gcp.mongodb.net/test?authSource=admin&replicaSet=TheDragAPIScrapes-shard-0&readPreference=primary&appname=MongoDB%20Compass&ssl=true");

			MongoClient mongoClient = new MongoClient(uri);
				
			MongoDatabase db = mongoClient.getDatabase("The_Drag");
			MongoCollection col = db.getCollection("cars");
			col.insertMany(documents);*/
			
			MongoClientURI uri = new MongoClientURI(
				    "mongodb+srv://jdwalsh21:BI6SfPDyhGX8ihAU@thedragapiscrapes-2duen.gcp.mongodb.net/test?authSource=admin&replicaSet=TheDragAPIScrapes-shard-0&readPreference=primary&appname=MongoDB%20Compass&ssl=true");

			MongoClient mongoClient = new MongoClient(uri);
				
			MongoDatabase db = mongoClient.getDatabase("The_Drag");
			MongoCollection col = db.getCollection("makes");
			
			System.out.println(col.find(eq("_id", "Honda")).first());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
