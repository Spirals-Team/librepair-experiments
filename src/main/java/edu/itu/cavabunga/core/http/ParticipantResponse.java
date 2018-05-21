package edu.itu.cavabunga.core.http;

import edu.itu.cavabunga.core.entity.Participant;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Response object that contains requested participants in data field
 * @see Participant
 */
@Getter
@Setter
public class ParticipantResponse extends Response {

    /**
     * list of requested participant
     */
    private List<Participant> data = new ArrayList <>();

    /**
     * Response object constructor that contains multiple participants in data field
     *
     * @param code application status code
     * @param message application status message
     * @param data response participant
     */
    public ParticipantResponse(Integer code, String message, List<Participant> data) {
        super(code, message);
        this.data = data;
    }

    /**
     * Response object constructor that contains single participant in data field
     *
     * @param code application status code
     * @param message application status message
     * @param data response participant
     */
    public ParticipantResponse(Integer code, String message, Participant data) {
        super(code, message);
        this.data.add(data);
    }
}
