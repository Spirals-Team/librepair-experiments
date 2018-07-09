
package patterns.mvc;

/**
 * View Interface.
 */
public interface ViewInterface {

    /**
     * view.
     *
     * @param model
     *            the model
     */
    ViewInterface attachModel(final ModelInterface model);

    /**
     * Detach model from view.
     */
    ViewInterface detachModel();

    /**
     * Show view.
     */
    ViewInterface show();

    ViewInterface show(final ModelInterface model);

}
