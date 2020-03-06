package thedrag;

import java.io.IOException;

import javax.servlet.http.*;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import thedrag.CodeStats.Contributor;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class IssueStats {
	OkHttpClient client = new OkHttpClient();
	List<Issue> issuesList;

	public IssueStats() {
		try {
			issuesList = new ArrayList<Issue>();
			
			String json = "{ \"array\":" + run("https://api.github.com/repos/53Dude/TheDrag/issues?state=all", client) + "}";

			JSONParser jsonParser = new JSONParser();			
			JSONObject obj = (JSONObject) jsonParser.parse(json);			
			JSONArray arr = (JSONArray) obj.get("array");
			
			for(Object thisObj : arr) {
				JSONObject issue = (JSONObject) thisObj;
				
				Issue thisIssue = new Issue((String)issue.get("state"));
				
				JSONArray assignees = (JSONArray) issue.get("assignees");
				
				for(Object assigneeData : assignees) {
					JSONObject assigneeDataObj = (JSONObject) assigneeData;
					
					thisIssue.addAssignee((String)assigneeDataObj.get("login"));
				}
				
				issuesList.add(thisIssue);
			}

		} catch (ParseException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	public String getListAsString() {
		String listString = "";
		
		int numIssues = 0, numOpen = 0, numClosed = 0;
		Map<String, Integer> issueMap = new HashMap<String, Integer>();
		
		
		for(Issue issue : issuesList) {
			numIssues++;
			
			for(String assignee : issue.assignees) {
				if(issueMap.get(assignee) == null) {
					issueMap.put(assignee, 0);
				}
				issueMap.put(assignee, issueMap.get(assignee) + 1);
			}
			
			if(issue.status.equals("open"))
				numOpen++;
			else if(issue.status.equals("closed"))
				numClosed++;
		}
		
		for(Map.Entry<String, Integer> hackerman : issueMap.entrySet()) {
			listString += hackerman.getKey() + ": " + hackerman.getValue() + " issues assigned<br>";
		}
		
		listString += "<br>Total issues: " + numIssues + " (" + numOpen + " open, " + numClosed + " closed)<br>";
		
		
		return listString;
	}
	
	String run(String url, OkHttpClient client) throws IOException {
		Request request = new Request.Builder()
				.url(url)
				.build();

		try (Response response = client.newCall(request).execute()) {
			return response.body().string();
		}
	}
	
	class Issue{
		String status;
		List<String> assignees;
		
		Issue(String status){
			this.status = status;
			this.assignees = new ArrayList<String>();
		}
		
		void addAssignee(String assignee) {
			assignees.add(assignee);
		}
	}
}
