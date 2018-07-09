
package patterns.mvc.controller;

import patterns.mvc.ControllerInterface;
import patterns.mvc.ModelInterface;
import patterns.mvc.ViewInterface;

/**
 * Example Controller class from the Model View Controller (MVC).
 */
public final class Controller extends AbstractController implements ControllerInterface {

    public Controller() {
        super();
    }

    public Controller(final ModelInterface model, final ViewInterface view) {
        super(model, view);
    }

}
