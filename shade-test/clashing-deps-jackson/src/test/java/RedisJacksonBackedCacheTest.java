import clashingdeps.jackson.cache.Cache;
import clashingdeps.jackson.cache.RedisJacksonBackedCache;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.rnorth.ducttape.unreliables.Unreliables;
import org.testcontainers.containers.GenericContainer;
import redis.clients.jedis.Jedis;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.rnorth.visibleassertions.VisibleAssertions.*;

/**
 * Integration test for Redis-backed cache implementation.
 */
public class RedisJacksonBackedCacheTest {

    @Rule
    public GenericContainer redis = new GenericContainer("redis:3.0.6")
            .withExposedPorts(6379)
            .waitingFor(new RedisWaitStrategy());
    private Cache cache;

    @Before
    public void setUp() throws Exception {
        Jedis jedis = new Jedis(redis.getContainerIpAddress(), redis.getMappedPort(6379));

        cache = new RedisJacksonBackedCache(jedis, "test");
    }

    @Test
    public void testFindingAnInsertedValue() {
        cache.put("foo", "FOO");
        Optional<String> foundObject = cache.get("foo", String.class);

        assertTrue("When an object in the cache is retrieved, it can be found",
                foundObject.isPresent());
        assertEquals("When we put a String in to the cache and retrieve it, the value is the same",
                "FOO",
                foundObject.get());
    }

    @Test
    public void testNotFindingAValueThatWasNotInserted() {
        Optional<String> foundObject = cache.get("bar", String.class);

        assertFalse("When an object that's not in the cache is retrieved, nothing is found",
                foundObject.isPresent());
    }

    private class RedisWaitStrategy extends GenericContainer.AbstractWaitStrategy {
        @Override
        protected void waitUntilReady() {
            //noinspection LoopStatementThatDoesntLoop
            Unreliables.retryUntilSuccess(10, TimeUnit.SECONDS, () ->
            {
                Jedis jedis = new Jedis(redis.getContainerIpAddress(), redis.getMappedPort(6379));
                return jedis.ping();
            });
        }
    }
}
