package com.psl.integrador.service;

import com.psl.integrador.exception.EntityNotFoundException;
import com.psl.integrador.model.Topic;
import com.psl.integrador.model.enums.NotificationType;
import com.psl.integrador.model.enums.Status;
import com.psl.integrador.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class TopicServiceImpl implements TopicService {

    private static final Logger LOGGER = Logger.getLogger(TopicServiceImpl.class.getName());

    private final TopicRepository topicRepository;
    private final NotificationService notificationService;

    @Autowired
    public TopicServiceImpl(TopicRepository topicRepository, NotificationService notificationService) {
        this.topicRepository = topicRepository;
        this.notificationService = notificationService;
    }

    @Override
    public List<Topic> getTopicsByStatus(int status) {
        // Check if status exists in enum
        if (status < 0 || Status.values().length - 1 < status)
            throw new ArrayIndexOutOfBoundsException(status);

        return topicRepository.findTopicByStatusOrderByCreatedAtDesc(Status.values()[status]);
    }

    @Override
    public Topic add(Topic topic) {
        return topicRepository.insert(topic);
    }

    @Override
    public Topic update(Topic topic) throws EntityNotFoundException {

        Topic dbTopic = topicRepository.findTopicById(topic.getId());

        if (dbTopic == null)
            throw new EntityNotFoundException(String.format("Topic with id: %s was not found", topic.getId()));

        if (topic.getStatus() != dbTopic.getStatus()) {
            NotificationType notificationType;
            if (dbTopic.getStatus() == Status.TO_OPEN && topic.getStatus() == Status.OPENED) {
                notificationType = NotificationType.OPEN;
            } else if (dbTopic.getStatus() == Status.TO_OPEN && topic.getStatus() == Status.CLOSED) {
                notificationType = NotificationType.NEVER_OPENED;
            } else {
                notificationType = NotificationType.CLOSED;
            }
            dbTopic.setStatus(topic.getStatus());

            //Send notifications to all collaborators
            sendNotifications(topic, notificationType);
        }

        dbTopic.setChat(topic.getChat());

        return topicRepository.save(dbTopic);
    }

    @Override
    public Topic getTopicById(String id) {
        return topicRepository.findTopicById(id);
    }

    private void sendNotifications(Topic topic, NotificationType notificationType) {

        new Thread(() -> {
            try {
                Thread.sleep(1000);
                notificationService.sendNotifications(topic, notificationType);

            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, e.toString());
            }
        }).start();


    }

}