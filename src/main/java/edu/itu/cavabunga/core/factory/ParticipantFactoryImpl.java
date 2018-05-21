package edu.itu.cavabunga.core.factory;

import edu.itu.cavabunga.core.entity.Participant;
import edu.itu.cavabunga.core.entity.participant.ParticipantType;
import org.springframework.stereotype.Component;

/**
 * Factory for all participant types
 * @see ParticipantType
 * @see Participant
 */
@Component
public class ParticipantFactoryImpl implements ParticipantFactory {

    /**
     * {@inheritDoc}
     */
    @Override
    public Participant createParticipant(ParticipantType participantType){
        return participantType.create();
    }
}
