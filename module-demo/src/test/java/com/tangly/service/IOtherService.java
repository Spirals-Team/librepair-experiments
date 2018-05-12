package com.tangly.service;


import com.tangly.entity.HelloWorld;

/**
 * 模拟一个其它模块的服务
 * 单元测试中如果要用到其它模块的接口
 * 但是这个其它模块功能尚未开发好，或者此模块功能要反复编译占用时间
 */
public interface IOtherService {

    HelloWorld someOtherMethod(int parma);

}
