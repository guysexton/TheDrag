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
public class NHTSACarServlet extends NHTSAServlet {
	
	public NHTSACarServlet(String vin) throws IOException, org.json.simple.parser.ParseException {
		try {
			setClient(new OkHttpClient());
			setJson(run("https://vpic.nhtsa.dot.gov/api/vehicles/decodevinvalues/" + vin + "?format=json", getClient()));
			setJsonParser(new JSONParser());
			setObj((JSONObject) getJsonParser().parse(getJson()));
			setArr((JSONArray) getObj().get("Results"));
			setResults((JSONObject) getArr().get(0));
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}