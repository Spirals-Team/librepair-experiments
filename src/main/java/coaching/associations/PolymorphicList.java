
package coaching.associations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * PolymorphicList Class.
 */
public class PolymorphicList {

    /** provides logging. */
    private static final Logger LOG = LoggerFactory.getLogger(PolymorphicList.class);

    /** The integer list. */
    private final List<Integer> integerList = new ArrayList<>();

    /**
     * Instantiates a new polymorphic list.
     */
    public PolymorphicList() {
        super();
        LOG.info("PolymorphicList");
    }

    /**
     * Integer object.
     *
     * @param integerIn
     *            the integer in
     * @return this for fluent interface.
     */
    public PolymorphicList add(final Integer integerIn) {
        LOG.info("add({}", integerIn);
        this.integerList.add(integerIn);
        return this;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String.format("PolymorphicList [integerList=%s]", this.integerList);
    }

}
