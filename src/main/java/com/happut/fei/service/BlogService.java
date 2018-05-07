package com.happut.fei.service;

import com.happut.fei.mapper.BlogMapper;
import com.happut.fei.pojo.TBlog;
import com.happut.fei.support.MarkdownUtils;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogService {

    @Autowired
    private BlogMapper blogMapper;

    public TBlog findBlogById(Integer id) {
        TBlog blog = blogMapper.findBlogById(id);
        blog.setCContent(MarkdownUtils.parseMarkdown2html(blog.getCContent()));
        return blog;
    }

    public List<TBlog> getPagableBlogs(Integer pageNo, Integer pageSize) {
        List<TBlog> pagableBlogs = blogMapper.getPagableBlogs(pageNo, pageSize);

        for (TBlog blog : pagableBlogs) {
            String content = StringUtils.left(Jsoup.parse(MarkdownUtils.parseMarkdown2html(blog.getCContent())).text(), 100);
            if (StringUtils.length(content) == 100) {
                blog.setCContent(content + "......");
            } else {
                blog.setCContent(content);
            }
        }


        return pagableBlogs;
    }

    public long getBlogsCount() {
        return blogMapper.getBlogsCount();
    }
}
