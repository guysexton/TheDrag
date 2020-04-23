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
	
	ArrayList<String> stringRes;

	public NHTSAmakeServlet(String make) throws IOException, org.json.simple.parser.ParseException {
		try {
			client = new OkHttpClient();
			json = run("https://vpic.nhtsa.dot.gov/api/vehicles/GetVehicleTypesForMake/" + make + "?format=json", client);
			jsonParser = new JSONParser();
	        obj = (JSONObject) jsonParser.parse(json);
	        arr = (JSONArray) obj.get("Results");
	        Iterator<JSONObject> iterator = arr.iterator();
	        stringRes = new ArrayList<>();
	        while (iterator.hasNext()) {
	            stringRes.add((String) iterator.next().get("VehicleTypeName"));

	        }

		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	public String getVehicleTypeName() {
		try {
			String list="";
			if(stringRes.size()>0) {
				for(int i=0;i<stringRes.size();i++) {
					if(i!=stringRes.size()-1)
						list+= (String)stringRes.get(i)+", ";
					else
						list+= (String)stringRes.get(i);
				}
			}
			else
				list+="N/A";
			return list;
		}
		catch(Exception e) {
			return "NHTSA Server API Error";
		}
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
