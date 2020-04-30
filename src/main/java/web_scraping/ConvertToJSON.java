package web_scraping;

import java.io.File;

import java.util.*;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;


public class ConvertToJSON {
	
	public static void convertToJSON(Map<String, Dealership> dealerships, Map<String, Car> cars, Map<String, Make> makes) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
		mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		try {
            mapper.writeValue(new File("dealerships.json"), dealerships);
            mapper.writeValue(new File("cars.json"), cars);
            mapper.writeValue(new File("makes.json"), makes);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
}