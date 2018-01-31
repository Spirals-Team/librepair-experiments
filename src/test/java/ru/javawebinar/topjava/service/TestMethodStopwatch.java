package ru.javawebinar.topjava.service;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/** @author danis.tazeev@gmail.com */
final class TestMethodStopwatch implements TestRule {
    private static final Logger log = LoggerFactory.getLogger(TestMethodStopwatch.class);
    private final TestMethodFinishObserver observer;

    @FunctionalInterface
    interface TestMethodFinishObserver {
        void testMethodFinished(Description desc, long duration);
    }

    TestMethodStopwatch() {
        this((desc, duration) -> {});
    }

    TestMethodStopwatch(TestMethodFinishObserver observer) {
        Objects.requireNonNull(observer, "observer");
        this.observer = observer;
    }

    private final class StopWatchStatement extends Statement {
        private final Statement base;
        private final Description desc;
        private final String signature;
        private final long startedAt;

        private StopWatchStatement(Statement base, Description desc) {
            assert base != null;
            assert desc != null;
            this.base = base;
            this.desc = desc;
            this.signature = desc.getClassName() + '.' + desc.getMethodName();
            this.startedAt = System.currentTimeMillis();
        }

        @Override
        public void evaluate() throws Throwable {
            try {
                base.evaluate();
            } finally {
                long duration = System.currentTimeMillis() - startedAt;
                log.debug(signature + " : " + (duration) + " ms");
                observer.testMethodFinished(desc, duration);
            }
        }
    }

    @Override
    public Statement apply(Statement base, Description desc) {
        return new StopWatchStatement(base, desc);
    }
}
