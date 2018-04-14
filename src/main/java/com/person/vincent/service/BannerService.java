package com.person.vincent.service;

import com.person.vincent.domain.Banner;
import com.person.vincent.mapper.BannerMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Author: vincent
 * Date: 2018-04-10 15:17:00
 * Comment:
 */

@Service
public class BannerService {

    @Resource
    private BannerMapper mapper;

    public List<Banner> banners() {
        return mapper.all();
    }
}
