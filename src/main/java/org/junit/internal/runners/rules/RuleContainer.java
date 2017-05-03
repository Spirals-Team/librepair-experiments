package org.junit.internal.runners.rules;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.IdentityHashMap;
import java.util.List;
import org.junit.rules.MethodRule;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.AnnotatedValueListener;
import org.junit.runners.model.FrameworkMember;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

public abstract class RuleContainer implements AnnotatedValueListener {
    private final IdentityHashMap<Object, Integer> orderValues = new IdentityHashMap<Object, Integer>();

    protected abstract Integer getOrder(FrameworkMember member);

    public void acceptValue(FrameworkMember member, Object value) {
        if (!orderValues.containsKey(value)) {
            final Integer order = getOrder(member);
            if (order != null) {
                orderValues.put(value, order);
            }
        }
    }

    private final List<RuleEntry> ruleEntries = new ArrayList<RuleEntry>();

    public void add(MethodRule methodRule) {
        ruleEntries.add(new MethodRuleEntry(methodRule, orderValues.get(methodRule)));
    }

    public void add(TestRule testRule) {
        ruleEntries.add(new TestRuleEntry(testRule, orderValues.get(testRule)));
    }

    public void sort() {
        Collections.sort(ruleEntries, new Comparator<RuleEntry>() {
            public int compare(RuleEntry o1, RuleEntry o2) {
                int result = compareInt(o1.order, o2.order);
                return result != 0 ? result : o1.getInterfacePriority() - o2.getInterfacePriority();
            }

            private int compareInt(int a, int b) {
                return (a < b) ? 1 : (a == b ? 0 : -1);
            }
        });
    }

    public Statement apply(FrameworkMethod method, Description description, Object target,
                           Statement statement) {
        Statement result = statement;
        for (RuleEntry ruleEntry : ruleEntries) {
            if (ruleEntry instanceof TestRuleEntry) {
                result = ((TestRuleEntry) ruleEntry).rule.apply(result, description);
            } else {
                result = ((MethodRuleEntry) ruleEntry).rule.apply(result, method, target);
            }
        }
        return result;
    }

    private abstract static class RuleEntry<T> {
        final T rule;
        final int order;

        RuleEntry(T rule, Integer order) {
            this.rule = rule;
            this.order = order != null ? order.intValue() : -1;
        }

        public int getInterfacePriority() {
            return 0;
        }
    }

    private static class TestRuleEntry extends RuleEntry<TestRule> {
        TestRuleEntry(TestRule rule, Integer order) {
            super(rule, order);
        }

        @Override
        public int getInterfacePriority() {
            return 1;
        }
    }

    private static class MethodRuleEntry extends RuleEntry<MethodRule> {
        MethodRuleEntry(MethodRule rule, Integer order) {
            super(rule, order);
        }
    }
}
