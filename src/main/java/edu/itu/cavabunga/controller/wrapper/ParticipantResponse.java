package edu.itu.cavabunga.controller.wrapper;

import edu.itu.cavabunga.core.entity.Participant;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ParticipantResponse {
    private Integer status;
    private String message;
    private List<Participant> data = new ArrayList<Participant>();

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Participant> getData() {
        return data;
    }

    public void setData(List<Participant> data) {
        this.data = data;
    }

    public void addData(Participant participant){
        this.data.add(participant);
    }
    public void setDataNull(){
        this.data = null;
    }

    public ParticipantResponse createParticipantResponseForList(Integer code, String message, List<Participant> participants){
        ParticipantResponse result = new ParticipantResponse();
        result.setStatus(code);
        result.setMessage(message);
        result.setData(participants);
        return result;
    }

    public ParticipantResponse createParticipantReponseForSingle(Integer code, String message, Participant participant){
        ParticipantResponse result = new ParticipantResponse();
        result.setStatus(code);
        result.setMessage(message);
        result.addData(participant);
        return result;
    }

}
