package edu.itu.cavabunga.core.services;

import edu.itu.cavabunga.core.entity.Participant;
import edu.itu.cavabunga.core.entity.ParticipantProperty;
import edu.itu.cavabunga.core.factory.ParticipantPropertyFactorty;
import edu.itu.cavabunga.core.repository.ParticipantPropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParticipantPropertyServiceImpl implements ParticipantPropertyService {
    @Autowired
    private ParticipantPropertyFactorty participantPropertyFactorty;

    @Autowired
    private ParticipantPropertyRepository participantPropertyRepository;

    @Override
    public ParticipantProperty createParticipantProperty(){
        return participantPropertyFactorty.create();
    }

    @Override
    public ParticipantProperty getParticipantPropertyByParticipant(Participant participant){
        return participantPropertyRepository.findByParticipant(participant);
    }

    @Override
    public ParticipantProperty getParticipantPropertyById(Long id){
        return participantPropertyRepository.findOne(id);
    }

    @Override
    public void saveParticipantProperty(ParticipantProperty participantProperty){
        participantPropertyRepository.save(participantProperty);
    }

    @Override
    public void deleteParticipantProperty(ParticipantProperty participantProperty){
        participantPropertyRepository.delete(participantProperty);
    }
}
