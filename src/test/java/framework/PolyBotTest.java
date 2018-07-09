
package framework;

import java.util.Properties;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

import coaching.net.Scheduler;

/**
 * PolyBotTest Class.
 */
public class PolyBotTest {

    /** The properties. */
    private final Properties properties = new Properties();

    /**
     * Instantiates a new poly bot test.
     */
    public PolyBotTest() {
        super();
        this.properties.put("botOne", "bucket.BotOne");
        this.properties.put("botTwo", "bucket.BotTwo");
    }

    /**
     * Test poly bot.
     */
    @Test
    public void testPolyBot() {
        final Scheduler polyBot = new Scheduler();
        polyBot.setProperties(this.properties);
        assertNotNull(polyBot);
        polyBot.execute();
    }

    /**
     * Test poly bot properties.
     */
    @Test
    public void testPolyBotProperties() {
        final Scheduler polyBot = new Scheduler(this.properties);
        assertNotNull(polyBot);
        polyBot.execute();
    }

    /**
     * Test poly bot args.
     */
    @Test
    public void testPolyBotArgs() {
        final String[] args = { "botOne=bucket.BotOne", "botTwo=bucket.BotTwo" };
        final Scheduler polyBot = new Scheduler(args);
        assertNotNull(polyBot);
        polyBot.execute();
    }
}
