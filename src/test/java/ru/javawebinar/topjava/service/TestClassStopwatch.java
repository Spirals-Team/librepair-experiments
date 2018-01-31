package ru.javawebinar.topjava.service;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/** @author danis.tazeev@gmail.com */
final class TestClassStopwatch implements TestRule, TestMethodStopwatch.TestMethodFinishObserver {
    private static final Logger log = LoggerFactory.getLogger(TestClassStopwatch.class);
    private final Map<String, Long> methodDurations = new ConcurrentHashMap<>();
    private Class<?> testClass;

    @Override
    public Statement apply(final Statement base, Description description) {
        testClass = description.getTestClass();
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                try {
                    base.evaluate();
                } finally {
                    StringBuilder sb = new StringBuilder();
                    sb.append(testClass.getName()).append(':');
                    methodDurations.forEach((k, v) ->
                            sb.append('\n').append('\t').append(k).append(" : ").append(v).append(" ms"));
                    log.debug(sb.toString());
                }
            }
        };
    }

    @Override
    public void testMethodFinished(Description desc, long duration) {
        assert desc != null;
        methodDurations.put(desc.getMethodName(), duration);
    }
}
