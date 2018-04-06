package com.app.producers;

import java.util.Map;

import org.apache.kafka.common.serialization.Serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AgentSerializer implements Serializer{
    @Override
    public void configure(Map map, boolean b) {

    }

    @Override
    public byte[] serialize(String s, Object agentInfo) {
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            return objectMapper.writeValueAsBytes(agentInfo);
        }catch(JsonProcessingException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void close() {

    }
}
