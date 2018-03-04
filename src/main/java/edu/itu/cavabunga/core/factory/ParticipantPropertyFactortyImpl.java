package edu.itu.cavabunga.core.factory;

import edu.itu.cavabunga.core.entity.ParticipantProperty;
import org.springframework.stereotype.Repository;

@Repository
public class ParticipantPropertyFactortyImpl implements ParticipantPropertyFactorty {
    @Override
    public ParticipantProperty create(){
        return new ParticipantProperty();
    }
}
