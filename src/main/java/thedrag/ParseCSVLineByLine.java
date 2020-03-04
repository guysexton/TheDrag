package thedrag;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;

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
		    } 
		    catch (Exception e) { 
		        e.printStackTrace(); 
		    } 
		}
	}