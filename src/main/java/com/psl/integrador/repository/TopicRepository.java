package com.psl.integrador.repository;

import com.psl.integrador.model.Topic;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicRepository extends MongoRepository<Topic, String> {

    List<Topic> findTopicByStatusOrderByCreatedAtDesc(Enum status);

    Topic findTopicById(String id);

}