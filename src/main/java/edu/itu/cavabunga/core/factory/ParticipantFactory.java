package edu.itu.cavabunga.core.factory;

import edu.itu.cavabunga.core.entity.Participant;
import edu.itu.cavabunga.core.entity.participant.ParticipantType;

/**
 * Factory interface for all participant types
 * @see ParticipantType
 * @see Participant
 */
public interface ParticipantFactory {

    /**
     * creates participant in desired type
     *
     * @param participantType type of the new participant
     * @return created participant object
     */
    Participant createParticipant(ParticipantType participantType);
}
