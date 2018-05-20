package edu.itu.cavabunga.core.service;

import edu.itu.cavabunga.core.entity.Participant;
import edu.itu.cavabunga.core.entity.participant.ParticipantType;
import edu.itu.cavabunga.core.factory.ParticipantFactory;
import edu.itu.cavabunga.core.repository.ParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * {@inheritDoc}
 */
@Service
public class ParticipantServiceImpl implements ParticipantService {

    private ParticipantFactory participantFactory;

    private ParticipantRepository participantRepository;

    /**
     * constructor for dependency injection
     *
     * @param participantFactory to inject ParticipantFactory
     * @param participantRepository to inject ParticipantRepository
     */
    @Autowired
    public ParticipantServiceImpl(
            ParticipantFactory participantFactory,
            ParticipantRepository participantRepository
    ) {
        this.participantFactory = participantFactory;
        this.participantRepository = participantRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Participant createParticipant(String userName, ParticipantType participantType){
        Participant newParticipant = participantFactory.createParticipant(participantType);
        newParticipant.setUserName(userName);
        participantRepository.save(newParticipant);
        return newParticipant;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveParticipant(Participant participant){
        participantRepository.save(participant);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Participant> getParticipantByUserName(String user_name){
        return participantRepository.findByUserName(user_name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Participant> getParticipantById(Long id){
        return participantRepository.findById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Participant> getAllParticipant(){
        return participantRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteParticipantById(Long id){
        participantRepository.delete(id);
    }
}
