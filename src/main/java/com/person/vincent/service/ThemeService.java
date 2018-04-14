package com.person.vincent.service;

import com.person.vincent.domain.Product;
import com.person.vincent.domain.Theme;
import com.person.vincent.mapper.ProductMapper;
import com.person.vincent.mapper.ThemeMapper;
import com.person.vincent.util.LongUtils;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.ArrayUtils;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * Author: vincent
 * Date: 2018-04-10 15:25:00
 * Comment:
 */

@Service
public class ThemeService {

    @Resource
    private ThemeMapper themeMapper;

    @Resource
    private ProductMapper productMapper;

    public List<Theme> themes() {
        List<Theme> themes = themeMapper.all();
        themes.forEach(Theme::getProducts);
        return themes;
    }
}
