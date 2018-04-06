package com.app.services;

import org.springframework.stereotype.Service;

@Service
public class TopicsService {
	
    private String[] topics={"ACCIDENT","FIRE","ALTERCATION","MEDICAL_EMERGENCY","METEOROLOGICAL_PHENOMENON","OTHER"};
    public String[] getTopics(){
        return topics.clone();
    }
    
}
