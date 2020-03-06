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

public class CodeStats extends HttpServlet {

	List<Contributor> contributors;

	public void buildContributors() {

		OkHttpClient client = new OkHttpClient();
		contributors = new ArrayList<Contributor>();

		try {

			String json = "{ \"array\":" + run("https://api.github.com/repos/53Dude/TheDrag/stats/contributors", client) + "}";

			JSONParser jsonParser = new JSONParser();

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
				contributors.add(new Contributor((String) ((JSONObject)contributorsInfo.get("author")).get("login"), adds, deletes, commits));

			}					
		} catch (ParseException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	String run(String url, OkHttpClient client) throws IOException {
		Request request = new Request.Builder()
				.url(url)
				.build();

		try (Response response = client.newCall(request).execute()) {
			return response.body().string();
		}
	}

	public String getListAsString() {
		buildContributors();

		String list = "";
		int allAdds = 0, allDeletes = 0, allCommits = 0;

		for(Contributor cont : contributors) {
			list += cont.toString() + "<br>";
			allAdds += cont.adds;
			allDeletes += cont.deletes;
			allCommits += cont.commits;
		}

		list += "<br>Team stats:" + " Commits: " + allCommits + " Adds: " + allAdds + " Deletes: " + allDeletes;

		return list;
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
			return name + " " + "Commits: " + commits + " Adds: " + adds + " Deletes: " + deletes;
		}

	}
}