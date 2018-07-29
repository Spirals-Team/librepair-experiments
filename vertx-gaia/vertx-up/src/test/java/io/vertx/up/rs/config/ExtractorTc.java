package io.vertx.up.rs.config;

import io.vertx.core.DeploymentOptions;
import io.vertx.ext.unit.TestContext;
import io.vertx.quiz.example.User;
import io.vertx.up.eon.ZeroValue;
import io.vertx.up.micro.ZeroHttpAgent;
import io.vertx.up.rs.Extractor;
import io.vertx.zero.exception.EventSourceException;
import io.zero.epic.Ut;
import io.zero.quiz.UpBase;
import org.junit.Test;

public class ExtractorTc extends UpBase {

    @Test
    public void testExtractAgent(final TestContext context) {
        final Extractor<DeploymentOptions> extractor =
                Ut.singleton(AgentExtractor.class);
        final DeploymentOptions options =
                extractor.extract(ZeroHttpAgent.class);
        context.assertEquals(ZeroValue.DEFAULT_GROUP, options.getIsolationGroup());
        context.assertEquals(ZeroValue.DEFAULT_HA, options.isHa());
        context.assertEquals(ZeroValue.DEFAULT_INSTANCES, options.getInstances());
    }

    @Test(expected = EventSourceException.class)
    public void testExtractEndpoint() {
        this.extractor().extract(this.getClass());
    }

    @Test
    public void testEvent1() {
        this.extractor().extract(User.class);
    }
}
