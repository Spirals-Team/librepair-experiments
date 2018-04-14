package com.person.vincent.controller;

import com.person.vincent.domain.Banner;
import com.person.vincent.service.BannerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * Author: vincent
 * Date: 2018-04-03 13:50:00
 * Comment:
 */

@RestController
@RequestMapping("/api/v1/banners")
public class BannerController {

    @Resource
    private BannerService service;

    @GetMapping(value = {"", "/"})
    public List<Banner> index() {
        return service.banners();
    }
}
