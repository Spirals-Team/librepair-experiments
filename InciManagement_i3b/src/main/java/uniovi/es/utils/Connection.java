package uniovi.es.utils;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.http.HttpStatus;

import com.google.gson.Gson;

public class Connection {

	private static String agentsURL = "http://localhost:8085/checkAgent";

	public enum Response {
		VALID_AGENT, NOT_VALID_AGENT, OTHER
	}

	public static Response checkAgent(AgentLogin agentLogin) throws Exception {
		Gson gson = new Gson();
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpPost request = new HttpPost(agentsURL);
		StringEntity params = new StringEntity(gson.toJson(agentLogin));
		request.addHeader("content-type", "application/json");
		request.setEntity(params);
		HttpResponse response = httpClient.execute(request);
		if (response.getStatusLine().getStatusCode() == HttpStatus.OK.value()) {
			return Response.VALID_AGENT;
		} else if (response.getStatusLine().getStatusCode() == HttpStatus.NOT_FOUND.value()) {
			return Response.NOT_VALID_AGENT;
		} else {
			return Response.OTHER;
		}

	}
}
