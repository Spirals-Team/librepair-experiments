package com.person.vincent.service;

import com.person.vincent.domain.Channel;
import com.person.vincent.mapper.ChannelMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Author: vincent
 * Date: 2018-04-10 15:16:00
 * Comment:
 */

@Service
public class ChannelService {

    @Resource
    private ChannelMapper mapper;

    public List<Channel> channels() {
        return mapper.all();
    }
}
