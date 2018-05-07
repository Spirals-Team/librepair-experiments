package com.happut.fei.web;

import com.happut.fei.schedule.BaiduTiebaSign;
import com.happut.fei.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private HomeService homeService;

    @Autowired
    private BaiduTiebaSign baiduTiebaSign;

    @RequestMapping("/index")
    public String hello(Model model) {
        model.addAttribute("blogs", homeService.getTop10Blogs());

        return "/home/index";
    }
}
