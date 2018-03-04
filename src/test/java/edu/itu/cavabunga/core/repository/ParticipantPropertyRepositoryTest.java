package edu.itu.cavabunga.core.repository;

import edu.itu.cavabunga.core.entity.Participant;
import edu.itu.cavabunga.core.entity.ParticipantProperty;
import edu.itu.cavabunga.core.entity.participant.ParticipantType;
import edu.itu.cavabunga.core.factory.ParticipantFactory;
import edu.itu.cavabunga.core.factory.ParticipantPropertyFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ParticipantPropertyRepositoryTest {
    @Autowired
    ParticipantRepository participantRepository;

    @Autowired
    ParticipantFactory participantFactory;

    @Autowired
    ParticipantPropertyRepository participantPropertyRepository;

    @Autowired
    ParticipantPropertyFactory participantPropertyFactory;

    private Participant testUser;

    @Before
    public void setUp(){
        Participant testUser = participantFactory.createParticipant(ParticipantType.User);
        testUser.setUserName("testuser");
        participantRepository.save(testUser);
        this.testUser = participantRepository.findByUserName("testuser");

        ParticipantProperty participantProperty = participantPropertyFactory.create();
        participantProperty.setParticipant(this.testUser);
        participantPropertyRepository.save(participantProperty);
    }

    @Test
    public void findByParticipantTest(){
        assertThat(participantPropertyRepository.findByParticipant(this.testUser), (instanceOf(ParticipantProperty.class)));
    }
}
