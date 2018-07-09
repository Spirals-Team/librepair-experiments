
package patterns.mvc.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import patterns.command.CommandFactory;
import patterns.command.MissingCommandException;
import patterns.mvc.ControllerInterface;
import patterns.mvc.ModelInterface;
import patterns.mvc.ViewInterface;

/**
 * An example abstract MVC Controller class.
 */
public abstract class AbstractController implements ControllerInterface {

    /** provides logging. */
    protected final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    /** provides command instances. */
    protected CommandFactory commands = new CommandFactory();

    /** The Model. */
    protected ModelInterface model;

    /** The View. */
    protected ViewInterface view;

    public AbstractController() {
    }

    public AbstractController(final ModelInterface model, final ViewInterface view) {
        this.model = model;
        this.view = view;
    }

    /**
     * Attach a Model.
     */
    @Override
    public ControllerInterface attachModel(final ModelInterface model) {
        this.model = model;
        return this;
    }

    /**
     * Detach model.
     */
    @Override
    public ControllerInterface detachModel() {
        this.model = null;
        return this;
    }

    /**
     * Attach view.
     *
     * @param view
     *            the view
     */
    @Override
    public ControllerInterface attachView(final ViewInterface view) {
        this.view = view;
        return this;
    }

    /**
     * Detach view.
     */
    @Override
    public ControllerInterface detachView() {
        this.view = null;
        return this;
    }

    public ControllerInterface execute() throws MissingCommandException {
        this.commands.execute();
        return this;
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
    @Override
    public ControllerInterface execute(final String commandName) throws MissingCommandException {
        this.commands.execute(commandName);
        return this;
    }

}
