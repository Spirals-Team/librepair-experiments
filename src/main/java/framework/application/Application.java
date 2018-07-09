
package framework.application;

import java.util.Arrays;

public class Application extends AbstractApplication {

    /**
     * Instantiates a new application.
     *
     * args
     */
    public Application(final String[] args) {
        super(args);
    }

    /**
     * Execute.
     *
     * @return true, if successful
     */
    public boolean execute() {
        boolean returnValue = false;
        try {
            returnValue = true;
        } catch (final Exception exception) {
            exception.printStackTrace(System.out);
        }
        return returnValue;
    }

    /**
     * main method.
     *
     * arguments
     */
    public static void main(final String[] args) {
        Application.log.trace(System.getProperties().toString());
        Application.log.debug("args[]={}", Arrays.toString(args));

        final Application application = new Application(args);
        if (application.initialisation()) {
            if (application.execute()) {
            } else {
                log.info("application = {}", application.toString());
            }
        }
    }
}
