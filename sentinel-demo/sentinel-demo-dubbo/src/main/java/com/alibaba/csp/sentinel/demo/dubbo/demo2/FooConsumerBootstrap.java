package com.alibaba.csp.sentinel.demo.dubbo.demo2;

import java.util.Collections;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.alibaba.csp.sentinel.concurrent.NamedThreadFactory;
import com.alibaba.csp.sentinel.demo.dubbo.consumer.ConsumerConfiguration;
import com.alibaba.csp.sentinel.demo.dubbo.consumer.FooServiceConsumer;
import com.alibaba.csp.sentinel.init.InitExecutor;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.SentinelRpcException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;

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

    private static final String RES_KEY = "com.alibaba.csp.sentinel.demo.dubbo.FooService:sayHello(java.lang.String)";
    private static final String INTERFACE_RES_KEY = "com.alibaba.csp.sentinel.demo.dubbo.FooService";

    private static final ExecutorService pool = Executors.newFixedThreadPool(10,
        new NamedThreadFactory("dubbo-consumer-pool"));

    public static void main(String[] args) {
        initFlowRule();
        InitExecutor.doInit();

        AnnotationConfigApplicationContext consumerContext = new AnnotationConfigApplicationContext();
        consumerContext.register(ConsumerConfiguration.class);
        consumerContext.refresh();

        FooServiceConsumer service = consumerContext.getBean(FooServiceConsumer.class);
        for (int i = 0; i < 10; i++) {
            pool.submit(() -> {
                try {
                    String message = service.sayHello("Eric");
                    System.out.println("Success: " + message);
                } catch (SentinelRpcException ex) {
                    System.out.println("Blocked");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
            pool.submit(() -> {
                System.out.println("Another: " + service.doAnother());
            });
        }
    }

    private static void initFlowRule() {
        FlowRule flowRule = new FlowRule();
        flowRule.setResource(RES_KEY);
        flowRule.setCount(5);
        flowRule.setGrade(RuleConstant.FLOW_GRADE_THREAD);
        flowRule.setLimitApp("default");
        FlowRuleManager.loadRules(Collections.singletonList(flowRule));
    }
}
