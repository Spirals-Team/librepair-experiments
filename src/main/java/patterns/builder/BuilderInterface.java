
package patterns.builder;

/**
 * Builder Interface.
 */
public interface BuilderInterface {

    /**
     * Builds the part.
     *
     * @return the part
     */
    AbstractPart build();

    AbstractPart build(final String partName);

}
