package thedrag;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.opencsv.CSVReader;

public class MakeServlet {
	public static Set<String> getMakes(){
		Set<String> makes = new HashSet<String>();
		
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
		            String[] productName = cols[0].split("\\s+");
		            makes.add(productName[1]);
		        }
			}
		    catch (Exception e) { 
		        e.printStackTrace(); 
		    } 
		}
		
		return makes;
	}
	public static void main(String[] args) {
		System.out.println(getMakes());
	}
}