
package automation;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertNotNull;

public class CredentialsTest {

    private static final Logger LOG = LoggerFactory.getLogger(CredentialsTest.class);
    private final String platform = "dev";
    private final String tag = "@AUTHORISED";

    @Test
    public void testCredentialsFactoryStatic() {
        final Actor actor = CredentialsFactory.on(this.platform).tagged(this.tag);
        assertNotNull(actor);
        LOG.info(actor.toString());
    }

    @Test
    public void testCredentialsFactory() {
        final CredentialsFactory credentials = new CredentialsFactory();
        assertNotNull(credentials);
        LOG.info(credentials.toString());

        final Actor actor = credentials.onPlatform(this.platform).tagged(this.tag);
        assertNotNull(actor);
        LOG.info(actor.toString());
    }

    @Test
    public void testCredentialsFactoryPlatform() {
        final CredentialsFactory credentials = new CredentialsFactory(this.platform);
        assertNotNull(credentials);
        LOG.info(credentials.toString());

        final Actor actor = credentials.tagged(this.tag);
        assertNotNull(actor);
        LOG.info(actor.toString());
    }

}
