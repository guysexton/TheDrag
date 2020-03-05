package thedrag;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.opencsv.*;

public class ParseCSVLineByLine
{
	   @SuppressWarnings("resource")
	   public static void main(String[] args) throws Exception
	   { 
		   
		    try { 
		    	
		        // Create an object of filereader 
		        // class with CSV file as a parameter. 
		        FileReader filereader = new FileReader("Honda Pilot EX.csv"); 
		  
		        // create csvReader object passing 
		        // file reader as a parameter 
		        CSVReader csvReader = new CSVReader(filereader); 
		        String[] nextRecord; 
		  
		        // we are going to read data line by line 
		        while ((nextRecord = csvReader.readNext()) != null) { 
		            for (String cell : nextRecord) { 
		                System.out.print(cell + "\t"); 
		            } 
		            System.out.println(); 
		        }
		        BufferedReader br = new BufferedReader(new FileReader("Honda Pilot EX.csv"));
		        String line;
		        line = br.readLine();
		        while ((line = br.readLine()) != null) {
		            // use comma as separator
		            String[] cols = line.split(",");
		            String[] dealer = cols[4].split("(?<=by) ");
		            System.out.println(dealer[1]);
		        }
		        
		    	/*
		    	BufferedReader br = new BufferedReader(new FileReader("Honda Pilot EX.csv"));
		        String line;
		        line = br.readLine();
		        boolean present = false;
		        String dealers = "dealers";
				Key dealKey = KeyFactory.createKey("DealKey", dealers);
				Entity dealer = new Entity("Dealer", dealKey);
				DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
				Query subQuery = new Query("Dealer", dealKey);
				List<Entity> dealList = datastore.prepare(subQuery).asList(FetchOptions.Builder.withDefaults());
				while ((line = br.readLine()) != null) {
		            // use comma as separator
					String[] cols = line.split(",");
		            String[] dealerName = cols[4].split("(?<=by) ");
		            dealer.setProperty("name", dealerName[1]);
		            for (Entity d: dealList) {
						if(d.getProperty("name").equals(cols[4])) {
							present = true;
						}
					}
		            if(!present) {
		            	datastore.put(dealer);
		            }
		        }*/
		    } 
		    catch (Exception e) { 
		        e.printStackTrace(); 
		    } 
		}
	}