
package patterns.mvc.view;

import patterns.mvc.ModelInterface;
import patterns.mvc.ViewInterface;

/**
 * An example View class from the Model View Controller (MVC).
 */
public class View extends AbstractView implements ViewInterface {

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

    @Override
    public ViewInterface show(final ModelInterface model) {
        this.log.info("{}.showView({})", this.getClass().getSimpleName(), model);
        return this;
    }
}
