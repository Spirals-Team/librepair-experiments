
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
    void attachModel(final ModelInterface model);

    /**
     * Detach model from view.
     */
    void detachModel();

    /**
     * Show view.
     */
    void show();

}
