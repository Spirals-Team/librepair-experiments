
package patterns.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Invoker Example Class.
 */
public class InvokerExample implements InvokerInterface {

    /** provides logging. */
    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    /** static instance of the example command. */
    private final ExampleCommand exampleCommand = new ExampleCommand();

    /** static instance of the command sequence. */
    private final SequenceCommand commandSequence = new SequenceCommand();

    /** static instance of the compound command. */
    private final CompoundCommand compoundCommand = new CompoundCommand();

    /** static instance of the conditional command. */
    private final ConditionalCommand conditionalCommand = new ConditionalCommand();

    /** static instance of the sequence command. */
    private final SequenceCommand sequenceCommand = new SequenceCommand();

    /*
     * (non-Javadoc)
     *
     * @see patterns.command.InvokerInterface#execute(java.lang.String)
     */
    @Override
    public ResultInterface execute(final String actionName) {
        ResultInterface result = null;
        log.info("execute({})", actionName);
        switch (actionName) {
        case "ExampleCommand":
            log.trace("case ExampleCommand");
            result = exampleCommand.execute(new Parameters());
            break;
        case "CommandSequence":
            log.trace("case CommandSequence");
            result = commandSequence.execute(new Parameters());
            break;
        case "CompoundCommand":
            log.trace("case CompoundCommand");
            result = compoundCommand.execute(new Parameters());
            break;
        case "ConditionalCommand":
            log.trace("case ConditionalCommand");
            result = conditionalCommand.execute(new Parameters());
            break;
        case "SequenceCommand":
            log.trace("case SequenceCommand");
            result = sequenceCommand.execute(new Parameters());
            break;
        default:
            log.trace("unknown command");
            break;
        }
        log.info("result = {}", result);
        return result;
    }

}
