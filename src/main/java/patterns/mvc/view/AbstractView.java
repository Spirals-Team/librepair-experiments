
package patterns.mvc.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import patterns.mvc.ModelInterface;
import patterns.mvc.ViewInterface;
import patterns.mvc.model.Model;

/**
 * AbstractView Class.
 */
public abstract class AbstractView implements ViewInterface {

    /** provides logging. */
    protected final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    /** The model. */
    protected ModelInterface model = null;

    /**
     * Instantiates a new abstract view.
     */
    public AbstractView() {
        super();
        this.model = new Model();
    }

    /*
     * (non-Javadoc)
     *
     * @see patterns.mvc.ViewInterface#attachModel(patterns.mvc.ModelInterface)
     */
    @Override
    public ViewInterface attachModel(final ModelInterface model) {
        this.model = model;
        return this;
    }

    /*
     * (non-Javadoc)
     *
     * @see patterns.mvc.ViewInterface#detach()
     */
    @Override
    public ViewInterface detachModel() {
        this.model = null;
        return this;
    }

    /*
     * (non-Javadoc)
     *
     * @see patterns.mvc.view.AbstractView#showView()
     */
    @Override
    public ViewInterface show() {
        this.log.info("{}.showView", this.getClass().getSimpleName());
        return this;
    }

}
