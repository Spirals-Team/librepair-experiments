
package patterns.builder;

/**
 * Part Class.
 */
public abstract class Part {

    @Override
    public String toString() {
        return String.format("%s", this.getClass().getSimpleName());
    }

}
