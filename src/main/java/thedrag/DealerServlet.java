package thedrag;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.opencsv.CSVReader;

public class DealerServlet {
	public static Set<String> getDealers(){
		Set<String> dealers = new HashSet<String>();
		
		File folder = new File("./");
		File[] listOfFiles = folder.listFiles();
		ArrayList<String> CSVNames = new ArrayList<String>();

		for (int i = 0; i < listOfFiles.length; i++) {
		  if (listOfFiles[i].isFile() && listOfFiles[i].getName().endsWith(".csv")) 
		    CSVNames.add(listOfFiles[i].getName());
		}
		
		for(String CSVName : CSVNames) {
			try {	  
		        BufferedReader br = new BufferedReader(new FileReader(CSVName));
		        String line;
		        line = br.readLine();
		        while ((line = br.readLine()) != null) {
		            // use comma as separator
		            String[] cols = line.split(",");
		            String[] dealer = cols[4].split("(?<=by) ");
		            dealers.add(dealer[1].replaceAll("\"", "")); //remove unnecessary quotes
		        }
			}
		    catch (Exception e) { 
		        e.printStackTrace(); 
		    } 
		}
		
		return dealers;
	}
	public static void main(String[] args) {
		
	        //JSON parser object to parse read file
	        JSONParser jsonParser = new JSONParser();
	         
	        try (FileReader reader = new FileReader("dealerships.json"))
	        {
	            //Read JSON file
	        	Object obj = jsonParser.parse(reader);
	        	JSONArray userList = new JSONArray();
	        	userList.add(obj);
	        	
	            System.out.println(userList);
	            
	            userList.forEach( emp -> parseEmployeeObject( (JSONObject) emp ) );
	           
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } catch (ParseException e) {
	            e.printStackTrace();
	        }
	    
	}
	
	 private static void parseEmployeeObject(JSONObject dealer) 
	    {
	        //Get dealer object within list
	        JSONObject dealerObject = (JSONObject) dealer.get("Sold by Toyota of Cedar Park");
	        System.out.println(dealerObject);
	    }
}
