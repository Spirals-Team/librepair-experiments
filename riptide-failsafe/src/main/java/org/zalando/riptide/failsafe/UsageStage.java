package org.zalando.riptide.failsafe;

import org.zalando.riptide.MethodDetector;
import org.zalando.riptide.Plugin;

public interface UsageStage extends ConfigurationStage, Plugin {
    UsageStage withIdempotentMethodDetector(MethodDetector detector);
    UsageStage withListener(final RetryListener listener);
}
