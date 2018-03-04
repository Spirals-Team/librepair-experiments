package edu.itu.cavabunga.core.services;

import edu.itu.cavabunga.core.entity.Participant;
import edu.itu.cavabunga.core.entity.participant.ParticipantType;
import edu.itu.cavabunga.core.factory.ParticipantFactory;
import edu.itu.cavabunga.core.repository.ParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParticipantServiceImpl implements ParticipantService {
    @Autowired
    private ParticipantRepository participantRepository;

    @Autowired
    private ParticipantFactory participantFactory;

    @Override
    public Participant createParticipant(String userName, ParticipantType participantType){
        Participant temp = participantFactory.createParticipant(participantType);
        temp.setUserName(userName);
        participantRepository.save(temp);
        return temp;
    }

    @Override
    public void saveParticipant(Participant participant){
        participantRepository.save(participant);
    }

    @Override
    public Participant getParticipantByUserName(String user_name){
        return participantRepository.findByUserName(user_name);
    }

    @Override
    public Participant getParticipantById(Long id){
        return participantRepository.findOne(id);
    }

    @Override
    public List<Participant> getAllParticipant(){
        return participantRepository.findAll();
    }

    @Override
    public List<Participant> getAllParticipantByType(ParticipantType participantType){
        return participantRepository.findByType(participantType.toString());
    }

    @Override
    public void deleteParticipantById(Long id){
        participantRepository.delete(id);
    }

    @Override
    public void deleteParticipantByUserName(String userName){
        participantRepository.deleteByUserName(userName);
    }

    @Override
    public Long countParticipantById(Long participantId){
        return participantRepository.countParticipantById(participantId);
    }

    @Override
    public Long countParticipantByUserName(String userName){
        return participantRepository.countParticipantByUserName(userName);
    }
}
