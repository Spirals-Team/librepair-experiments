
package patterns.builder;

/**
 * BuilderOne Class.
 */
public class BuilderOne extends AbstractBuilder {

    /*
     * (non-Javadoc)
     *
     * @see patterns.gof.creational.builder.AbstractBuilder#build()
     */
    @Override
    public AbstractPart build() {
        return new PartOne();
    }

    @Override
    public AbstractPart build(final String partName) {
        return new PartOne(partName);
    }

}
