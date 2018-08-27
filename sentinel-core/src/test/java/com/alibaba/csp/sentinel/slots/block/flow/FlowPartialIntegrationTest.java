/*
 * Copyright 1999-2018 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alibaba.csp.sentinel.slots.block.flow;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.context.ContextUtil;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;

/**
 * @author jialiang.linjl
 */
public class FlowPartialIntegrationTest {

    @Test
    public void testQPSGrade() {
        FlowRule flowRule = new FlowRule();
        flowRule.setResource("testQPSGrade");
        flowRule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        flowRule.setCount(1);
        FlowRuleManager.loadRules(Collections.singletonList(flowRule));

        Entry e = null;
        try {
            e = SphU.entry("testQPSGrade");
        } catch (BlockException e1) {
            assertTrue(false);
        }
        e.exit();

        try {
            e = SphU.entry("testQPSGrade");
            assertTrue(false);
        } catch (BlockException e1) {
            assertTrue(true);
        }
    }

    @Test
    public void testThreadGrade() throws InterruptedException {
        FlowRule flowRule = new FlowRule();
        flowRule.setResource("testThreadGrade");
        flowRule.setGrade(RuleConstant.FLOW_GRADE_THREAD);
        flowRule.setCount(1);
        FlowRuleManager.loadRules(Arrays.asList(flowRule));

        final Object sequence = new Object();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Entry e = null;
                try {
                    e = SphU.entry("testThreadGrade");
                    assertTrue(true);
                    synchronized (sequence) {
                        System.out.println("notify up");
                        sequence.notify();
                    }
                    Thread.sleep(1000);
                } catch (BlockException e1) {
                    assertTrue(false);
                } catch (InterruptedException e1) {
                    assertTrue(false);
                    e1.printStackTrace();
                }
                e.exit();
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();

        Entry e = null;
        synchronized (sequence) {
            System.out.println("sleep");
            sequence.wait();
            System.out.println("wake up");
        }
        try {
            e = SphU.entry("testThreadGrade");
            assertTrue(false);
        } catch (BlockException e1) {
            assertTrue(true);
        }
        System.out.println("done");

    }

    @Test
    public void testOriginFlowRule() {
        // normal
        FlowRule flowRule = new FlowRule();
        flowRule.setResource("testOriginFlowRule");
        flowRule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        flowRule.setCount(0);
        flowRule.setLimitApp("other");

        FlowRule flowRule2 = new FlowRule();
        flowRule2.setResource("testOriginFlowRule");
        flowRule2.setGrade(RuleConstant.FLOW_GRADE_QPS);
        flowRule2.setCount(1);
        flowRule2.setLimitApp("app2");

        FlowRuleManager.loadRules(Arrays.asList(flowRule, flowRule2));

        ContextUtil.enter("node1", "app1");
        Entry e = null;
        try {
            e = SphU.entry("testOriginFlowRule");
            assertTrue(false);
        } catch (BlockException e1) {
            e1.printStackTrace();
            assertTrue(true);
        }
        assertTrue(e == null);

        ContextUtil.exit();

        ContextUtil.enter("node1", "app2");
        e = null;
        try {
            e = SphU.entry("testOriginFlowRule");
            assertTrue(true);
        } catch (BlockException e1) {
            e1.printStackTrace();
            assertTrue(false);
        }

        e.exit();
        ContextUtil.exit();
    }

    @Test
    public void testFlowRule_other() {

        FlowRule flowRule = new FlowRule();
        flowRule.setResource("testOther");
        flowRule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        flowRule.setCount(0);
        flowRule.setLimitApp("other");
        FlowRuleManager.loadRules(Arrays.asList(flowRule));

        Entry e = null;
        try {
            e = SphU.entry("testOther");
            assertTrue(true);
        } catch (BlockException e1) {
            e1.printStackTrace();
            assertTrue(false);
        }

        if (e != null) {
            assertTrue(true);
            e.exit();
        } else {
            assertTrue(false);
        }
    }

    @Test
    public void testStrategy() {

        // normal
        FlowRule flowRule = new FlowRule();
        flowRule.setResource("testStrategy");
        flowRule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        flowRule.setCount(0);
        flowRule.setStrategy(RuleConstant.STRATEGY_DIRECT);
        FlowRuleManager.loadRules(Arrays.asList(flowRule));

        ContextUtil.enter("testStrategy");
        Entry e = null;
        try {
            e = SphU.entry("testStrategy");
            assertTrue(false);
        } catch (BlockException e1) {
            e1.printStackTrace();
            assertTrue(true);
        }

        ContextUtil.exit();

        flowRule = new FlowRule();
        flowRule.setResource("testStrategy");
        flowRule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        flowRule.setCount(0);
        flowRule.setStrategy(RuleConstant.STRATEGY_CHAIN);
        flowRule.setResource("entry2");

        FlowRuleManager.loadRules(Arrays.asList(flowRule));

        e = null;
        ContextUtil.enter("entry1");
        try {
            e = SphU.entry("testStrategy");
            assertTrue(true);
        } catch (BlockException e1) {
            e1.printStackTrace();
            assertTrue(false);
        }
        e.exit();
        ContextUtil.exit();
    }

    @Test
    public void testStrategy_chain() {
        FlowRule flowRule = new FlowRule();
        flowRule.setResource("entry2");
        flowRule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        flowRule.setCount(0);
        flowRule.setStrategy(RuleConstant.STRATEGY_CHAIN);
        flowRule.setRefResource("entry1");

        FlowRuleManager.loadRules(Arrays.asList(flowRule));
        Entry e = null;
        ContextUtil.enter("entry1");
        try {
            e = SphU.entry("entry2");
            assertTrue(false);
        } catch (BlockException e1) {
            e1.printStackTrace();
            assertTrue(true);
        }

        ContextUtil.exit();

        e = null;
        ContextUtil.enter("entry3");
        try {
            e = SphU.entry("entry2");
            assertTrue(true);
        } catch (BlockException e1) {
            assertTrue(false);
        }
        e.exit();

        ContextUtil.exit();

    }
}
