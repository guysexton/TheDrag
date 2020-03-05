package thedrag;

import java.io.IOException;
import javax.servlet.http.*;

import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class GithubStatsServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		resp.setContentType("text/plain");

		OkHttpClient client = new OkHttpClient();

		String json = "{ \"array\":" + run("https://api.github.com/repos/53Dude/TheDrag/stats/contributors", client) + "}";
		
		JSONParser jsonParser = new JSONParser();
		try {
			
			JSONObject obj = (JSONObject) jsonParser.parse(json);
			
			JSONArray arr = (JSONArray) obj.get("array");
			
			for(Object thisObj : arr) {
				JSONObject contributorsInfo = (JSONObject) thisObj;
				
				
				long adds = 0, deletes = 0, commits = 0;
				
				for(Object weekData : (JSONArray)contributorsInfo.get("weeks")) {
					JSONObject thisWeek = (JSONObject) weekData;
					
					adds += (Long)thisWeek.get("a");
					deletes += (Long)thisWeek.get("d");
					commits += (Long)thisWeek.get("c");
				}
				Contributor thisContributor = new Contributor((String) ((JSONObject)contributorsInfo.get("author")).get("login"), adds, deletes, commits);
				
				resp.getWriter().println(thisContributor);
			}		
			
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

		//resp.getWriter().println(json);
	}

	String run(String url, OkHttpClient client) throws IOException {
		Request request = new Request.Builder()
				.url(url)
				.build();

		try (Response response = client.newCall(request).execute()) {
			return response.body().string();
		}
	}
	
	
	class Contributor{
		long adds, deletes, commits;
		String name;
		
		Contributor(){}
		
		Contributor(String name, long adds, long deletes, long commits){
			this.name = name;
			this.adds = adds;
			this.deletes = deletes;
			this.commits = commits;			
		}
		
		public String toString() {
			return name + "\n" + "Commits: " + commits + "\nAdds: " + adds + "\nDeletes: " + deletes + "\n";
		}
		
	}
}