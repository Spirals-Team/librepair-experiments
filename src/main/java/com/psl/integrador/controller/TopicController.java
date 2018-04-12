package com.psl.integrador.controller;

import com.psl.integrador.exception.EntityNotFoundException;
import com.psl.integrador.model.Topic;
import com.psl.integrador.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/topics")
public class TopicController {

    private final TopicService topicService;

    @Autowired
    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @PostMapping
    public Topic createTopic(@RequestBody @Valid Topic topic) {
        return topicService.add(topic);
    }

    @PatchMapping
    public Topic updateTopic(@RequestBody @Valid Topic topic) throws EntityNotFoundException {
        return topicService.update(topic);
    }

    @GetMapping("/findByStatus")
    public List<Topic> getTopicsByStatus(@RequestParam("status") int status) {
        return topicService.getTopicsByStatus(status);
    }
}