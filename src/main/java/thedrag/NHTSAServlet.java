package thedrag;
import java.io.IOException;
import javax.servlet.http.*;
import java.util.List;
import java.util.ArrayList;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
public class NHTSAServlet {
	OkHttpClient client;
	String json;
	JSONObject obj;
	JSONParser jsonParser;
	JSONArray arr;
	JSONObject results;
	public NHTSAServlet(String vin) throws IOException, org.json.simple.parser.ParseException {
		try {
			OkHttpClient client = new OkHttpClient();
			String json = run("https://vpic.nhtsa.dot.gov/api/vehicles/decodevinvalues/" + vin + "?format=json", client);
			jsonParser = new JSONParser();
			obj = (JSONObject) jsonParser.parse(json);
			arr = (JSONArray) obj.get("Results");
			results = (JSONObject) arr.get(0);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getBodyClass() {
		return (String)results.get("BodyClass");
	}
	
	public String getHP() {
		return (String)results.get("EngineHP");
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