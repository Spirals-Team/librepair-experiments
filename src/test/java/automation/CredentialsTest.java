
package automation;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class CredentialsTest {

    @Test
    public void testCredentials() {
        final Credentials credentials = new Credentials();
        assertNotNull(credentials);
    }

    @Test
    public void testStaticCredentials() {
        final String platform = "@dev";
        final String tag = "@tag";
        final Credentials credentials = new Credentials(platform);
        final Actor actor = Credentials.on(platform).with(tag);
        assertNotNull(actor);
    }

    @Test
    public void testFluentCredentials() {
        final String platform = "@dev";
        final String tag = "@tag";
        final Credentials credentials = new Credentials();
        final Actor actor = Credentials.on(platform).with(tag);
        assertNotNull(actor);
    }

}
