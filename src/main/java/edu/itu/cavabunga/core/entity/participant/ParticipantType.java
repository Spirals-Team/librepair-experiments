package edu.itu.cavabunga.core.entity.participant;

import edu.itu.cavabunga.core.entity.Participant;

public enum ParticipantType {
    User {
        public Participant create(){
            return new User();
        }
    },
    Group {
        public Participant create(){
            return new Group();
        }
    };

    public Participant create() { return null; }
}
