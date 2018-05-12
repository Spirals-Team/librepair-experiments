package com.tangly.controller;

import com.alibaba.fastjson.JSONException;
import com.tangly.bean.ResponseBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局处理Spring Boot的抛出异常。
 * @author tangly
 */
@RestControllerAdvice
@Slf4j
public class RestControllerAdvisor {

    /**
     * 捕捉shiro未登录的异常
     * @return
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthenticatedException.class)
    public ResponseBean handleUnauthenticated(UnauthenticatedException e) {
        log.error(e.getMessage());
        return new ResponseBean(401, "请先登录", null);
    }

    /**
     * 捕捉shiro无权访问的异常
     * @return
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseBean handle403(UnauthorizedException e) {
        return new ResponseBean(403, "无权访问", e.getMessage());
    }


    /**
     * 捕捉shiro其它异常
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(ShiroException.class)
    public ResponseBean handle401(ShiroException e) {
        log.error("shiro",e);
        return new ResponseBean(401, e.getMessage(), null);
    }

    @ExceptionHandler(JSONException.class)
    public ResponseBean jsonException(JSONException e){
        log.error("参数格式错误",e);
        return new ResponseBean(HttpStatus.BAD_REQUEST.value(), "参数格式错误" , e.getMessage());
    }

    /**
     * 捕获所有参数校验异常
     * @param e
     * @return
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseBean illegalArgument(IllegalArgumentException e){
        return new ResponseBean(HttpStatus.ACCEPTED.value(),e.getMessage(),null);
    }


    /**
     * 捕捉其余所有异常
     * @param request
     * @param ex
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseBean globalException(HttpServletRequest request, Throwable ex) {
        log.error("捕获全局异常 ",ex);
        return new ResponseBean(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), null);
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}