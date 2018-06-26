
package patterns.command;

import coaching.context.ContextInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Abstract Command class from GOF Command Pattern.
 */
public abstract class AbstractCommand implements CommandInterface {

    /** The context. */
    private final ContextInterface context = CommandContext.getInstance();

    /** provides logging. */
    protected final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    /** The result. */
    protected ResultInterface result = null;

    /**
     * Execute.
     *
     * @return the result interface
     */
    public ResultInterface execute() {
        this.log.debug("{}.execute", this.getClass().getSimpleName());
        this.result = new Result(Result.PASS);
        return this.result;
    }

    /*
     * (non-Javadoc)
     *
     * @see patterns.command.CommandInterface#execute(patterns.command.
     * ParametersInterface)
     */
    @Override
    public ResultInterface execute(final ParametersInterface commandParameters) {
        this.log.debug("{}.execute", this.getClass().getSimpleName());
        this.log.debug("context={}", this.context);
        commandParameters.setParameter("result", "pass");
        this.result = new Result(Result.PASS);
        return this.result;
    }

    /**
     * Undo.
     *
     * @return the result interface
     */
    public ResultInterface undo() {
        this.log.debug("{}.undo", this.getClass().getSimpleName());
        this.result = new Result(Result.FAIL);
        return this.result;
    }

    /*
     * (non-Javadoc)
     *
     * @see patterns.command.CommandInterface#undo(patterns.command.
     * ParametersInterface)
     */
    @Override
    public ResultInterface undo(final ParametersInterface commandParameters) {
        this.log.debug("{}.undo", this.getClass().getSimpleName());
        commandParameters.setParameter("result", "pass");
        this.result = new Result(Result.FAIL);
        return this.result;
    }

    /*
     * (non-Javadoc)
     *
     * @see patterns.command.AbstractCommand#result()
     */
    @Override
    public Boolean getResult() {
        return this.result.getResult();
    }

}
