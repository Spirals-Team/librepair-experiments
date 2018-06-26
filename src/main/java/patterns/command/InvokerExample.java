
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
        this.log.info("execute({})", actionName);
        switch (actionName) {
        case "ExampleCommand":
            this.log.trace("case ExampleCommand");
            result = this.exampleCommand.execute(new Parameters());
            break;
        case "CommandSequence":
            this.log.trace("case CommandSequence");
            result = this.commandSequence.execute(new Parameters());
            break;
        case "CompoundCommand":
            this.log.trace("case CompoundCommand");
            result = this.compoundCommand.execute(new Parameters());
            break;
        case "ConditionalCommand":
            this.log.trace("case ConditionalCommand");
            result = this.conditionalCommand.execute(new Parameters());
            break;
        case "SequenceCommand":
            this.log.trace("case SequenceCommand");
            result = this.sequenceCommand.execute(new Parameters());
            break;
        default:
            this.log.trace("unknown command");
            break;
        }
        this.log.info("result = {}", result);
        return result;
    }

}
