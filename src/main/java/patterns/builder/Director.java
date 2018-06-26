
package patterns.builder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Director Class.
 */
public class Director {

    /** provides logging. */
    private static final Logger LOG = LoggerFactory.getLogger(Director.class);

    /** The builders. */
    private final List<AbstractBuilder> builders = new ArrayList<>();

    /**
     * Adds part builder.
     *
     * @param builder
     *            the builder to be added.
     * @return true, if successful, otherwise false., otherwise false.
     */
    public boolean add(final AbstractBuilder builder) {
        return this.builders.add(builder);
    }

    /**
     * Construct Product.
     *
     * @return the product
     */
    public Product constructProduct() {
        final BuilderOne builderOne = new BuilderOne();
        final Part partOne = builderOne.build();

        final BuilderTwo builderTwo = new BuilderTwo();
        final Part partTwo = builderTwo.build();

        return new Product(partOne, partTwo);
    }

    /**
     * Construct Product.
     */
    public void buildAll() {
        for (final BuilderInterface builder : this.builders) {
            final Part part = builder.build();
            LOG.info("part={}", part);
        }
    }

}
