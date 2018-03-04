package edu.itu.cavabunga.core.services;

import edu.itu.cavabunga.core.entity.Participant;
import edu.itu.cavabunga.core.entity.ParticipantProperty;

public interface ParticipantPropertyService {
    ParticipantProperty createParticipantProperty();

    ParticipantProperty getParticipantPropertyByParticipant(Participant participant);

    ParticipantProperty getParticipantPropertyById(Long id);

    void saveParticipantProperty(ParticipantProperty participantProperty);

    public void deleteParticipantProperty(ParticipantProperty participantProperty);
}
