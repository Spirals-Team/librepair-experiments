package net.coljate.util.complexity;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author Ollie
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TimeComplexity {

    /**
     * @return average or expected complexity.
     */
    Complexity value() default Complexity.UNKNOWN;

    /**
     * @return best case complexity, or the average complexity if unspecified.
     */
    Complexity bestCase() default Complexity.UNKNOWN;

    /**
     * @return worst case complexity, or the average complexity if unspecified.
     */
    Complexity worstCase() default Complexity.UNKNOWN;

    /**
     * @return true if the complexity is determined from the method call(s) made, which should be between the worst-best
     * and worst-worst case.
     */
    boolean computed() default false;

}
