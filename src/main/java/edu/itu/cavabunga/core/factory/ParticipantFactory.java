package edu.itu.cavabunga.core.factory;

import edu.itu.cavabunga.core.entity.Participant;
import edu.itu.cavabunga.core.entity.participant.ParticipantType;

public interface ParticipantFactory {
    Participant createParticipant(ParticipantType participantType);
}
