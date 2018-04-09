package com.app.services;

import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.app.entities.Agent;

@Service
public class AgentInfoService {
		
	public Agent findById(Agent agentInfo) {
		Agent response;
		
		try {
			HttpHeaders header = new HttpHeaders();
			header.setContentType(MediaType.APPLICATION_JSON);

			JSONObject request = new JSONObject();
			request.put("id", agentInfo.getId());
	        request.put("password", agentInfo.getPassword());
			request.put("kind", agentInfo.getKind());

			HttpEntity<String> entity = new HttpEntity<String>(request.toString(), header);
			response = this.getResponseAgentInfo("http://localhost:8080/restAgentInfo", HttpMethod.POST, entity);
		
		} catch (Exception e) {
			return null;
		}
		
        return response;
	}

	public boolean verifyAgent(Agent agentInfo) {
		HttpStatus response;
		
		try {
			HttpHeaders header = new HttpHeaders();
			header.setContentType(MediaType.APPLICATION_JSON);

			JSONObject request = new JSONObject();
			request.put("id", agentInfo.getId());
	        request.put("password", agentInfo.getPassword());
			request.put("kind", agentInfo.getKind());

			HttpEntity<String> entity = new HttpEntity<String>(request.toString(), header);
			response = this.getResponseStatus("http://localhost:8080/restLogin", HttpMethod.POST, entity);
		
		} catch (Exception e) {
			return false;
		}
		
        return response.equals(HttpStatus.OK);
	}
	
	public HttpStatus getResponseStatus(String url, HttpMethod method, HttpEntity<String> entity) {
		ResponseEntity<String> response = new RestTemplate().exchange(url, method, entity, String.class);
        return response.getStatusCode();
	}
	
	public Agent getResponseAgentInfo(String url, HttpMethod method, HttpEntity<String> entity) {
		ResponseEntity<Agent> response = new RestTemplate().exchange(url, method, entity, Agent.class);
        return response.getBody();
	}
}
