package edu.itu.cavabunga.core.service;

import edu.itu.cavabunga.core.entity.Participant;
import edu.itu.cavabunga.core.entity.participant.ParticipantType;

import java.util.List;
import java.util.Optional;

/**
 * it makes all the operation related to the participant
 */
public interface ParticipantService {
    Participant createParticipant(String user_name, ParticipantType participantType);

    /**
     * saves given participant
     *
     * @param participant participant record to save
     */
    void saveParticipant(Participant participant);

    /**
     * It returns related participant if given username exist
     *
     * @param user_name user name of the participant
     * @return related participant record or null
     */
    Optional<Participant> getParticipantByUserName(String user_name);

    /**
     * It returns related participant if given id exist
     *
     * @param id id of the participant
     * @return related participant record or null
     */
    Optional<Participant> getParticipantById(Long id);

    /**
     * Returns all the participant records
     *
     * @return list of the all participants
     */
    List<Participant> getAllParticipant();

    /**
     * It deletes record of the given participant id
     *
     * @param id participant id to delete
     */
    void deleteParticipantById(Long id);
}
