
package coaching.types;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Examples of Java Object Types.
 *  @author         martin.spamer.
 *  @version        0.1 - first release.
 *  Created         07-Oct-2004 - 09:20:22
 */
public class ObjectTypes {

    /** The largest byte. */
    // integers object types
    private final Byte largestByte = Byte.MAX_VALUE;

    /** The smallest byte. */
    private final Byte smallestByte = Byte.MIN_VALUE;

    /** The largest short. */
    private final Short largestShort = Short.MAX_VALUE;

    /** The smallest short. */
    private final Short smallestShort = Short.MIN_VALUE;

    /** The largest integer. */
    private final Integer largestInteger = Integer.MAX_VALUE;

    /** The smallest integer. */
    private final Integer smallestInteger = Integer.MIN_VALUE;

    /** The largest long. */
    private final Long largestLong = Long.MAX_VALUE;

    /** The smallestt long. */
    private final Long smallesttLong = Long.MIN_VALUE;

    /** The largest float. */
    // real number object types.
    private final Float largestFloat = Float.MAX_VALUE;

    /** The smallest float. */
    private final Float smallestFloat = Float.MIN_VALUE;

    /** The largest double. */
    private final Double largestDouble = Double.MAX_VALUE;

    /** The smallest double. */
    private final Double smallestDouble = Double.MIN_VALUE;

    /** The a char. */
    // other primitive types
    private final Character aChar = 'A';

    /** The true boolean. */
    private final Boolean trueBoolean = true;

    /** The false boolean. */
    private final Boolean falseBoolean = false;

    /** provides logging. */
    protected final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    /**
     * Display.
     */
    public void display() {
        // display them all
        this.log.info("largest byte value : {}", this.largestByte);
        this.log.info("smallest byte value : {}", this.smallestByte);

        this.log.info("largest short value is {}", this.largestShort);
        this.log.info("smallest short value is {}", this.smallestShort);

        this.log.info("largest integer value is {}", this.largestInteger);
        this.log.info("smallest integer value is {}", this.smallestInteger);

        this.log.info("largest long value is {}", this.largestLong);
        this.log.info("smallest long value is {}", this.smallesttLong);

        this.log.info("largest float value is {}", this.largestFloat);
        this.log.info("smallest float value is {}", this.smallestFloat);

        this.log.info("largest double value is {}", this.largestDouble);
        this.log.info("smallest double value is {}", this.smallestDouble);

        if (Character.isUpperCase(this.aChar)) {
            this.log.info("The character {} is upper case.", this.aChar);
        }

        if (this.trueBoolean) {
            this.log.info("aBooleanTrue has tested as true : {}", this.trueBoolean);
        }

        if (this.falseBoolean == false) {
            this.log.info("aFalseBoolean has tested as false value : {}", this.falseBoolean);
        }
    }
}
