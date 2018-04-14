package com.person.vincent.controller;

import com.person.vincent.domain.Channel;
import com.person.vincent.service.ChannelService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * Author: vincent
 * Date: 2018-04-06 16:55:00
 * Comment:
 */

@RestController
@RequestMapping("/api/v1/channels")
public class ChannelController {

    @Resource
    private ChannelService service;

    @GetMapping(value = {"", "/"})
    public List<Channel> index() {
        return service.channels();
    }
}
