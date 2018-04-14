package com.person.vincent.controller;

import com.person.vincent.domain.Theme;
import com.person.vincent.service.ThemeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * Author: vincent
 * Date: 2018-04-06 17:23:00
 * Comment:
 */

@RestController
@RequestMapping("/api/v1/themes")
public class ThemeController {

    @Resource
    private ThemeService service;

    @GetMapping(value = {"", "/"})
    public List<Theme> index() {
        return service.themes();
    }
}
