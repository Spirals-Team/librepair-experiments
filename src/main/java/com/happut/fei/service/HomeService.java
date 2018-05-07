package com.happut.fei.service;

import com.happut.fei.mapper.BlogMapper;
import com.happut.fei.pojo.TBlog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HomeService {

    @Autowired
    private BlogMapper blogMapper;

    public List<TBlog> getTop10Blogs() {
        return blogMapper.getTop10Blogs();
    }

}
