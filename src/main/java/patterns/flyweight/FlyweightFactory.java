
package patterns.flyweight;

import java.util.ArrayList;
import java.util.List;

/**
 * A factory for creating FlyWeight objects.
 */
public final class FlyweightFactory {

    /** The list. */
    private final List<FlyWeight> list = new ArrayList<>();

    /**
     * Creates the flyweight object.
     *
     * @return the fly weight
     */
    public FlyWeight create() {
        final FlyWeight flyWeight = new FlyWeight("CommonState");
        this.list.add(flyWeight);
        return flyWeight;
    }

}
