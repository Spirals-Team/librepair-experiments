
package patterns.builder;

/**
 * BuilderTwo Class.
 */
public class BuilderTwo extends AbstractBuilder {

    /*
     * (non-Javadoc)
     *
     * @see patterns.gof.creational.builder.AbstractBuilder#build()
     */
    @Override
    public Part build() {
        return new PartTwo();
    }

}
