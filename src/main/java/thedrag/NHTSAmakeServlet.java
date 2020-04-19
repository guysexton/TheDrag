package thedrag;
import java.io.IOException;
import javax.servlet.http.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
public class NHTSAmakeServlet {
	OkHttpClient client;
	String json;
	JSONObject obj;
	JSONParser jsonParser;
	JSONArray arr;
	JSONObject results;
	
	public NHTSAmakeServlet(String make) throws IOException, org.json.simple.parser.ParseException {
		try {
			OkHttpClient client = new OkHttpClient();
			String json = run("https://vpic.nhtsa.dot.gov/api/vehicles/GetVehicleTypesForMake/" + make + "?format=json", client);
			jsonParser = new JSONParser();
			JSONObject obj;
	        obj = (JSONObject) jsonParser.parse(json);
	        JSONArray arr = (JSONArray) obj.get("Results");
	        Iterator<JSONObject> iterator = arr.iterator();
	        while (iterator.hasNext()) {
	            results = iterator.next();
	            //System.out.println(result.get("VehicleTypeName"));
	        }

		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getVehicleTypeName() {
		return (String)results.get("VehicleTypeName");
	}
	
	static String run(String url, OkHttpClient client) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
	}
	
	public static void main(String[] args) {
	}
}