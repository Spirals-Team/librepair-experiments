package com.tangly.controller;

import com.tangly.bean.ResponseBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author tangly
 * @date 2018/4/17
 */
@Controller
@Api(description = "页面跳转")
public class IndexController {

    /**
     * 重定向到主题模板
     * @return
     */
    @GetMapping("/")
    @ApiOperation(value = "访问首页")
    public String index() {
        return "index";
    }


    @ApiOperation(value = "跳转到登录页面")
    @GetMapping(value = "login")
    public String login() {
        Subject s = SecurityUtils.getSubject();
        return s.isRemembered() || s.isAuthenticated() ? "redirect:/" : "login";
    }

    @ApiOperation(value = "跳转到登出页面")
    @GetMapping(value = "logout")
    public String logout() {
        SecurityUtils.getSubject().logout();
        return "logout";
    }

    @ApiOperation(value = "跳转到WebSocket测试页面")
    @GetMapping("/websocket")
    public String websocket() {
        return "websocket";
    }

    @GetMapping(path = "/401")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    @ApiOperation(value = "访问无权限跳转")
    public ResponseBean unauthorized(@RequestParam(required = false) String err) {
        return new ResponseBean(401, "无权访问或登录过期", err);
    }
}
