package edu.itu.cavabunga.core.services;

import edu.itu.cavabunga.core.entity.Participant;
import edu.itu.cavabunga.core.entity.participant.ParticipantType;

import java.util.List;

public interface ParticipantService {
    Participant createParticipant(String user_name, ParticipantType participantType);

    void saveParticipant(Participant participant);

    Participant getParticipantByUserName(String user_name);

    Participant getParticipantById(Long id);

    List<Participant> getAllParticipant();

    List<Participant> getAllParticipantByType(ParticipantType participantType);

    void deleteParticipantById(Long id);

    void deleteParticipantByUserName(String userName);

    Long countParticipantById(Long participantId);

    Long countParticipantByUserName(String userName);
}
