package com.psl.integrador.service;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class NotificationServiceImplTest {

    /*@TestConfiguration
    static class NotificationServiceImplTestContextConfiguration {
        @Bean
        public NotificationService notificationService() {
            return new NotificationServiceImpl(collaboratorService);
        }
    }

    @Autowired
    private NotificationService notificationService;

    @MockBean
    private CollaboratorService collaboratorService;

    @Autowired
    private TopicService topicService;

    @Before
    public void setUp() {
        Map<Collaborator, Role> collaborators = new HashMap<>();
        Collaborator collaborator = new Collaborator();
        collaborator.setEmail("col1@samplemail.com");
        collaborators.put(collaborator,Role.STUDENT);
        collaborator = new Collaborator();
        collaborator.setEmail("col2@samplemail.com");
        collaborators.put(collaborator,Role.TEACHER);

        Mockito.when(collaboratorService.getCollaboratorsByTopic(new Topic()))
                .thenReturn(collaborators);
    }

    @Test
    public void sendNotification() {

        Topic topic = new Topic("Topic","Test topic");
        Collaborator[] collaborator = new Collaborator[5];
        for(int c = 0; c < 5; ++c){
            collaborator[c] = new Collaborator();
            collaborator[c].setEmail(c + "@mail.com");
            collaborator[c].addTopicToLearn(topic, Expertise.BEGINNER);
        }
        //Void function, test is only to verify there is no crash
        notificationService.sendNotifications(collaboratorService.getCollaboratorsByTopic(new Topic()),topic,1);
        assertTrue(true);
    }
    */

}
