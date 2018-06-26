
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
    public Part build() {
        return new PartOne();
    }

}
