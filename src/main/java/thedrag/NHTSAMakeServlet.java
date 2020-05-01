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
public class NHTSAMakeServlet extends NHTSAServlet {
	
	private ArrayList<String> stringRes;

	public NHTSAMakeServlet(String make) throws IOException, org.json.simple.parser.ParseException {
		try {
			setClient(new OkHttpClient());
			setJson(run("https://vpic.nhtsa.dot.gov/api/vehicles/GetVehicleTypesForMake/" + make + "?format=json", getClient()));
			setJsonParser(new JSONParser());
	        setObj((JSONObject) getJsonParser().parse(getJson()));
	        setArr((JSONArray) getObj().get("Results"));
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public String getNHTSAAttribute(String attribute) {
		try {
			Iterator<JSONObject> iterator = getArr().iterator();
	        stringRes = new ArrayList<>();
	        while (iterator.hasNext()) {
	        	String next = (String) iterator.next().get(attribute);
	        	if(!stringRes.contains(next))
	        		stringRes.add(next);
	        }
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
}
