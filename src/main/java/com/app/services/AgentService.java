package com.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.entities.Agent;
import com.app.repositories.AgentInfoRepository;

@Service
public class AgentService {

	@Autowired
	AgentInfoRepository agentInfoRepository;

	public Agent findById(String id) {
		return agentInfoRepository.findById(id);
	}

	public String getLocation(Agent agentInfo) {
		// TODO
		return "";
	}
}
