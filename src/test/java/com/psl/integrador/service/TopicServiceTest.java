package com.psl.integrador.service;

import com.psl.integrador.exception.EntityNotFoundException;
import com.psl.integrador.model.Topic;
import com.psl.integrador.repository.TopicRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.psl.integrador.model.enums.Status.OPENED;
import static com.psl.integrador.model.enums.Status.TO_OPEN;
import static com.psl.integrador.model.enums.Status.CLOSED;
import static org.hamcrest.beans.SamePropertyValuesAs.samePropertyValuesAs;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TopicServiceTest {

    @Autowired
    private TopicService topicService;

    @Autowired
    private TopicRepository topicRepository;

    @Test
    public void getTopicsByStatus() {
        Topic t1 = new Topic();
        t1.setName("Topic 1");
        t1.setDescription("Description 1");
        t1.setStatus(TO_OPEN);
        t1 = topicService.add(t1);

        Topic t2 = new Topic();
        t2.setName("Topic 2");
        t2.setDescription("Description 2");
        t2.setStatus(OPENED);
        t2 = topicService.add(t2);

        Topic t3 = new Topic();
        t3.setName("Topic 3");
        t3.setDescription("Description 3");
        t3.setStatus(CLOSED);
        t3 = topicService.add(t3);

        Topic t4 = new Topic();
        t4.setName("Topic 4");
        t4.setDescription("Description 4");
        t4.setStatus(CLOSED);
        t4 = topicService.add(t4);

        assertEquals(2, topicService.getTopicsByStatus(CLOSED.ordinal()).size());

        topicRepository.delete(t1);
        topicRepository.delete(t2);
        topicRepository.delete(t3);
        topicRepository.delete(t4);
       // assertEquals(1, topicService.getTopicsByStatus(OPENED.ordinal()).size());
       // assertEquals(1, topicService.getTopicsByStatus(TO_OPEN.ordinal()).size());
    }

    @Test
    public void add() {
        Topic t1 = new Topic();
        t1.setName("Topic");
        t1.setDescription("Description");
        t1.setStatus(TO_OPEN);

        Topic t = topicService.add(t1);

        assertThat(t1, samePropertyValuesAs(t));

        topicRepository.delete(t);
    }

    @Test
    public void updateStatusExistingTopic() throws Exception{
        Topic t1 = new Topic();
        t1.setStatus(TO_OPEN);
        t1 = topicService.add(t1);
        t1.setStatus(OPENED);

        Topic t2 = new Topic();
        t2.setStatus(TO_OPEN);
        t2 = topicService.add(t2);
        t2.setStatus(CLOSED);

        Topic t3 = new Topic();
        t3.setStatus(OPENED);
        t3 = topicService.add(t3);
        t3.setStatus(CLOSED);

        assertEquals(t1.getStatus(), topicService.update(t1).getStatus());
        assertEquals(t2.getStatus(), topicService.update(t2).getStatus());
        assertEquals(t3.getStatus(), topicService.update(t3).getStatus());

        topicRepository.delete(t1);
        topicRepository.delete(t2);
        topicRepository.delete(t3);
    }

    @Test
    public void updateNoExistingTopic() {
        boolean thrown = false;
        try {
            Topic t = new Topic();
            topicService.update(t);
        }
        catch (EntityNotFoundException e) {
            thrown = true;
        }

        assertTrue(thrown);
    }

    @Test
    public void getTopicById() {
        Topic t1 = new Topic();
        t1.setName("Topic");
        t1.setDescription("Description");
        t1.setStatus(TO_OPEN);
        Topic t2 = topicService.add(t1);

        assertThat(t2, samePropertyValuesAs(topicService.getTopicById(t2.getId())));

        topicRepository.delete(t2);
    }
}
