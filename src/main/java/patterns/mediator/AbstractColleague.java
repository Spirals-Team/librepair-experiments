
package patterns.mediator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Abstact class for Colleague.
 */
public abstract class AbstractColleague implements ColleagueInterface {

    /** provides logging. */
    protected final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

}
