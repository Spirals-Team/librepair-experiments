package com.alibaba.csp.sentinel.demo.dubbo.demo1;


import com.alibaba.csp.sentinel.demo.dubbo.consumer.ConsumerConfiguration;
import com.alibaba.csp.sentinel.demo.dubbo.consumer.FooServiceConsumer;
import com.alibaba.csp.sentinel.init.InitExecutor;
import com.alibaba.csp.sentinel.slots.block.SentinelRpcException;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Please add the following VM arguments:
 * <pre>
 * -Djava.net.preferIPv4Stack=true
 * -Dcsp.sentinel.api.port=8721
 * -Dproject.name=dubbo-consumer-demo
 * </pre>
 *
 * @author Eric Zhao
 */
public class FooConsumerBootstrap {

    public static void main(String[] args) {
        InitExecutor.doInit();

        AnnotationConfigApplicationContext consumerContext = new AnnotationConfigApplicationContext();
        consumerContext.register(ConsumerConfiguration.class);
        consumerContext.refresh();

        FooServiceConsumer service = consumerContext.getBean(FooServiceConsumer.class);

        for (int i = 0; i < 15; i++) {
           try {
                    String message = service.sayHello("Eric");
                    System.out.println("Success: " + message);
                } catch (SentinelRpcException ex) {
                    System.out.println("Blocked");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
        }
    }
}
