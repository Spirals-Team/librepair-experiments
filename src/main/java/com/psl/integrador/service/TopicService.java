package com.psl.integrador.service;

import com.psl.integrador.exception.EntityNotFoundException;
import com.psl.integrador.model.Topic;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TopicService {

    List<Topic> getTopicsByStatus(int status);

    Topic add(Topic topic);

    Topic update(Topic topic) throws EntityNotFoundException;

    Topic getTopicById(String id);
}