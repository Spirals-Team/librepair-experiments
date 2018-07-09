
package patterns.mvc;

import patterns.command.MissingCommandException;

/**
 * Controller Interface.
 */
public interface ControllerInterface {

    ControllerInterface execute(final String commandName) throws MissingCommandException;

    ControllerInterface detachView();

    ControllerInterface attachView(final ViewInterface view);

    ControllerInterface detachModel();

    ControllerInterface attachModel(final ModelInterface model);

}
