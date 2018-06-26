
package patterns.mvc;

import patterns.mvc.model.AbstractModel;
import patterns.mvc.view.AbstractView;

/**
 * Model Interface.
 */
public interface ModelInterface {

    /**
     * Attach.
     *
     * @param key
     *            the key
     * @param model
     *            the model
     */
    void attach(final String key, final AbstractModel model);

    /**
     * Attach.
     *
     * @param key
     *            the key
     * @param view
     *            the view
     */
    void attach(final String key, final AbstractView view);

    /**
     * Detach model.
     *
     * @param key
     *            the key
     */
    void detachModel(final String key);

    /**
     * Detach view.
     *
     * @param key
     *            the key
     */
    void detachView(final String key);

}
