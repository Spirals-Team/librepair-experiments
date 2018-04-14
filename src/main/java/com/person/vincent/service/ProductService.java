package com.person.vincent.service;

import com.person.vincent.domain.Product;
import com.person.vincent.mapper.ProductMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Author: vincent
 * Date: 2018-04-10 15:20:00
 * Comment:
 */

@Service
public class ProductService {

    @Resource
    private ProductMapper mapper;

    public List<Product> products() {
        return mapper.all();
    }

    public List<Product> findByThemeId(Long id) {
        return mapper.findByThemeId(id);
    }

    public List<Product> findByThemeIds(Long[] themeIds) {
        return mapper.findByThemeIds(themeIds);
    }

    public Product find(Long id) {
        return mapper.find(id);
    }
}
