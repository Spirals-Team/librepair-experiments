
package coaching.resources;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * CurrentWorkingDirectory Class.
 */
public final class CurrentWorkingDirectory {

    /** provides logging */
    private static final Logger LOG = LoggerFactory.getLogger(CurrentWorkingDirectory.class);

    /**
     * Instantiates a new current working directory.
     */
    public CurrentWorkingDirectory() {
        mark(this);
    }

    /**
     * Mark.
     *
     * @param that
     *            the that
     * @return the string
     */
    public String mark(final Object that) {
        String absolutePath = null;
        try {
            final String className = that.getClass().getSimpleName();
            final String fileName = String.format("./target/%s.CurrentWorkingDirectory", className);
            final File file = new File(fileName);
            try {
                file.createNewFile();
                absolutePath = file.getAbsolutePath();
                LOG.info(absolutePath);
            } catch (final Exception exception) {
                LOG.error(exception.toString());
            }
        } catch (final Exception exception) {
            LOG.error(exception.toString());
        }
        return absolutePath;
    }
}
