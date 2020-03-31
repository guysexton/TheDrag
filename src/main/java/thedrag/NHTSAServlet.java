package thedrag;

import java.io.IOException;
import java.text.ParseException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.jsoup.Connection.Response;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class NHTSAServlet {
	OkHttpClient client;
	String json;
	JSONObject obj;
	
	public NHTSAServlet() throws IOException, org.json.simple.parser.ParseException {
		OkHttpClient client = new OkHttpClient();
		String json = run("https://vpic.nhtsa.dot.gov/api/vehicles/decodevinvalues/2C4RC1GG5LR187589?format=json", client);
		JSONParser jsonParser = new JSONParser();

		JSONObject obj;
		obj = (JSONObject) jsonParser.parse(json);
		JSONArray arr = (JSONArray) obj.get("Results");
		JSONObject results = (JSONObject) arr.get(0);
		System.out.println(results.get("BodyClass"));

	}
	
	private static String getBodyClass() {
		JSONArray arr = (JSONArray) obj.get("Results");
		JSONObject results = (JSONObject) arr.get(0);
		return null;
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
