package com.person.vincent.controller;

import com.person.vincent.domain.Product;
import com.person.vincent.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * Author: vincent
 * Date: 2018-04-06 18:07:00
 * Comment:
 */

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    @Resource
    private ProductService service;

    @GetMapping(value = {"", "/"})
    public List<Product> index(@RequestParam("theme_id") Long themeId) {
        return service.findByThemeId(themeId);
    }
}
