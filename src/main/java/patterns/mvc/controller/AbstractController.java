
package patterns.mvc.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import patterns.command.CommandFactory;
import patterns.command.MissingCommandException;
import patterns.mvc.ControllerInterface;
import patterns.mvc.ModelInterface;
import patterns.mvc.ViewInterface;

/**
 * AbstractController Class.
 */
public abstract class AbstractController implements ControllerInterface {

    /** provides logging. */
    protected final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    /** The commands. */
    protected CommandFactory commands = new CommandFactory();

    /** The model. */
    protected ModelInterface model;

    /** The view. */
    protected ViewInterface view;

    /**
     * Attach model.
     *
     * @param model
     *            the model
     */
    public void attachModel(final ModelInterface model) {
        this.model = model;
    }

    /**
     * Detach model.
     */
    public void detachModel() {
        this.model = null;
    }

    /**
     * Attach view.
     *
     * @param view
     *            the view
     */
    public void attachView(final ViewInterface view) {
        this.view = view;
    }

    /**
     * Detach view.
     */
    public void detachView() {
        this.view = null;
    }

    /**
     * Execute.
     *
     * @param commandName
     *            the command name
     * @return the abstract controller
     * @throws MissingCommandException
     *             the missing command exception
     */
    public AbstractController execute(final String commandName) throws MissingCommandException {
        this.commands.execute(commandName);
        return this;
    }

}
