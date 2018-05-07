package com.happut.fei.web;

import com.happut.fei.pojo.TBlog;
import com.happut.fei.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/blog")
public class BlogController {

    @Autowired
    private BlogService blogService;

    /**
     * 默认路径
     *
     * @return
     */
    @GetMapping("")
    public String root(@RequestParam(value = "page", required = false) Integer pageNo, Model model) {
        if (pageNo == null) {
            pageNo = 0;
        }

        List<TBlog> pagableBlogs = blogService.getPagableBlogs(pageNo, 10);

        model.addAttribute("blogs", pagableBlogs);
        model.addAttribute("blogNum", blogService.getBlogsCount());

        return "/blog/list";
    }


    /**
     * 默认路径
     *
     * @return
     */
    @GetMapping("/{id}")
    public String ariticle(@PathVariable("id") Integer id, Model model) {
        TBlog blog = blogService.findBlogById(id);

        model.addAttribute("blog", blog);
        return "/blog/article";
    }
}
