package com.tangly.service.impl;

import com.tangly.base.BaseService;
import com.tangly.entity.HelloWorld;
import com.tangly.service.IHelloWorldService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author tangly
 * @since JDK 1.7
 */
@Service
@Slf4j
public class HelloWorldService extends BaseService<HelloWorld> implements IHelloWorldService {

}
