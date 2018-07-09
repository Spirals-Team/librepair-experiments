
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
    ModelInterface attach(final String key, final AbstractModel model);

    /**
     * Attach.
     *
     * @param key
     *            the key
     * @param view
     *            the view
     */
    ModelInterface attach(final String key, final AbstractView view);

    /**
     * Detach model.
     *
     * @param key
     *            the key
     */
    ModelInterface detachModel(final String key);

    /**
     * Detach view.
     *
     * @param key
     *            the key
     */
    ModelInterface detachView(final String key);

    ModelInterface update();

    ModelInterface update(String key, String value);

}
