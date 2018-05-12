package com.tangly.controller;

import com.github.pagehelper.PageInfo;
import com.tangly.bean.PageRequest;
import com.tangly.bean.ResponseBean;
import com.tangly.entity.HelloWorld;
import com.tangly.entity.UserAuth;
import com.tangly.entity.UserInfo;
import com.tangly.service.IHelloWorldService;
import com.tangly.util.ValidateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * date: 2018/1/2 17:29 <br/>
 *
 * @author tangly
 * @since JDK 1.7
 */
@RestController
@RequestMapping(value = "/helloworld")
@Slf4j
@Api(description = "示例_HelloWorld_增删改查_Demo")
public class HelloWorldController {

    @Autowired
    private IHelloWorldService iHelloWorldService;

    //START----------------------------增删改查CRUD+PAGE-----------------------------------

    @ApiOperation(value = "获取HelloWorld列表", notes = "获取列表")
    @GetMapping(value = {""})
    public ResponseBean getAllList() {
        List<HelloWorld> r = iHelloWorldService.selectAll();
        return new ResponseBean(HttpStatus.OK.value(), "成功", r);

    }

    @ApiOperation(value = "分页获取获取HelloWorld列表", notes = "备注内容：记得传参数")
    @PostMapping(value = {"/page"})
    public ResponseBean geHelloWorldPage(@RequestBody PageRequest pageRequest) {

        PageInfo<HelloWorld> pageInfo = iHelloWorldService.selectByPage(new PageRequest(HelloWorld.class,pageRequest));
        return new ResponseBean(HttpStatus.OK.value(), "成功", pageInfo);
    }

    @ApiOperation(value = "创建HelloWorld实体", notes = "根据HelloWorld对象创建HelloWorld")
    @ApiImplicitParam(name = "helloWorld", value = "id字段由服务端生成，前端不用传", required = true, dataType = "HelloWorld")
    @PostMapping(value = "")
    public ResponseBean postHelloWorld(@RequestBody HelloWorld helloWorld) {
        ValidateUtil.validate(helloWorld);
        helloWorld.setId(null);
        iHelloWorldService.insert(helloWorld);
        return new ResponseBean(HttpStatus.CREATED.value(), "成功", helloWorld);
    }

    @ApiOperation(value = "更新完整HelloWorld实体", notes = "请传完整的helloworld对象")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "helloWorld", value = "HelloWorld完整实体", required = true, dataType = "HelloWorld")
    })
    @PutMapping()
    public ResponseBean putHelloWorld(@RequestBody HelloWorld helloWorld) {
        ValidateUtil.validate(helloWorld);
        iHelloWorldService.updateByPrimaryKey(helloWorld);
        return new ResponseBean(HttpStatus.OK.value(), "成功", helloWorld);
    }

    @ApiOperation(value = "更新HelloWorld实体部分字段", notes = "不传的字段就不更新")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "helloWorld", value = "HelloWorld实体需要更新的字段", required = true, dataType = "HelloWorld")
    })
    @PatchMapping()
    public ResponseBean patchHelloWorld(@RequestBody HelloWorld helloWorld) {
        iHelloWorldService.updateByPrimaryKeySelective(helloWorld);
        return new ResponseBean(HttpStatus.OK.value(), "成功", helloWorld);
    }

    @ApiOperation(value = "获取HelloWorld详细信息", notes = "根据url的id来获取HelloWorld详细信息")
    @ApiImplicitParam(paramType = "path", name = "id", dataType = "Integer")
    @GetMapping(value = "/{id}")
    public ResponseBean getHelloWorld(@PathVariable("id") Integer id) {
        HelloWorld helloWorld = iHelloWorldService.selectByPrimaryKey(id);
        return new ResponseBean(HttpStatus.OK.value(), "成功", helloWorld);
    }

    @ApiOperation(value = "删除HelloWorld", notes = "根据url的id来指定删除对象")
    @ApiImplicitParam(paramType = "path", name = "id", dataType = "Integer")
    @DeleteMapping(value = "/{id}")
    public ResponseBean deleteHelloWorld(@PathVariable("id") Integer id) {
        iHelloWorldService.deleteByPrimaryKey(id);
        return new ResponseBean(HttpStatus.OK.value(), "成功", null);
    }
    //END----------------------------增删改查CRUD+PAGE-----------------------------------


    //START-------------------------------用户权限相关-----------------------------------

    @GetMapping("/}auth/check_current_user")
    @ApiOperation(value = "查看当前登录的用户信息")
    public ResponseBean checkCurrentUser() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            UserAuth userAuth = (UserAuth) subject.getPrincipal();
            if (ObjectUtils.isEmpty(userAuth)) {
                throw new UnauthenticatedException("请重新登录");
            }
            UserInfo userInfo = userAuth.getUserInfo();
            return ResponseBean.success("您已经登录", userInfo);
        } else {
            return ResponseBean.error("您是访客用户");
        }
    }

    @GetMapping("/}auth/require_auth")
    @RequiresAuthentication
    @ApiOperation(value = "需要登录才能访问的接口")
    public ResponseBean requireAuth() {
        return new ResponseBean(200, "账号已经登录", null);
    }

    @GetMapping("/}auth/require_role")
    @RequiresRoles("ROLE_ADMIN")
    @ApiOperation(value = "需要ROLE_ADMIN角色才能访问的接口")
    public ResponseBean requireRole() {
        return new ResponseBean(200, "该账号具有 ROLE_ADMIN 的角色", null);
    }

    @GetMapping("/}auth/require_permission")
    @ApiOperation(value = "需要userInfo:view,userInfo:add权限才能访问的接口")
    @RequiresPermissions(logical = Logical.AND, value = {"userInfo:view", "userInfo:add"})
    public ResponseBean requirePermission() {
        return new ResponseBean(200, "该账号具有 ROLE_ADMIN 角色的以下权限 require userInfo:view,userInfo:add", null);
    }

    //END-------------------------------用户权限相关-----------------------------------

}
