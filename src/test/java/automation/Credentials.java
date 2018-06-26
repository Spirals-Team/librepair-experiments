
package automation;

public class Credentials {

    public Credentials() {
    }

    public Credentials(final String platform) {
        // TODO load credentials for platform
        // e.g. "classpath:dev/credentials.csv"
    }

    public static Credentials on(final String platform) {
        return new Credentials().platformIs(platform);
    }

    public Credentials platformIs(final String platform) {
        return this;
    }

    public Actor with(final String tag) {
        return new Actor();
    }

}
