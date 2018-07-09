
package patterns.builder;

/**
 * Part class.
 */
public abstract class AbstractPart {

    private final String partName;

    public AbstractPart() {
        super();
        this.partName = this.getClass().getSimpleName();
    }

    public AbstractPart(final String partName) {
        super();
        this.partName = partName;
    }

    @Override
    public String toString() {
        return String.format("%s", this.getClass().getSimpleName());
    }

}
