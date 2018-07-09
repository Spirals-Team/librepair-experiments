
package patterns.mvc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import patterns.command.MissingCommandException;
import patterns.mvc.controller.Controller;
import patterns.mvc.model.AbstractModel;
import patterns.mvc.model.Model;
import patterns.mvc.view.AbstractView;
import patterns.mvc.view.View;

/**
 * Model View Controller Test class.
 */
public class MvcTest {

    /**
     * Unit Test the MVC Model class.
     */
    @Test
    public void testModel() {
        final AbstractModel model = new Model();
        assertNotNull("Model cannot be null", model);
        assertEquals(model, model.update());
    }

    /**
     * Unit Test the MVC View class.
     */
    @Test
    public void testView() {
        final AbstractView view = new View();
        assertNotNull("View cannot be null", view);
        assertEquals(view, view.show());
    }

    /**
     * Unit Test the MVC Controller class.
     *
     * @throws MissingCommandException
     */
    @Test
    public void testController() throws MissingCommandException {
        final Controller controller = new Controller();
        assertNotNull("Controller cannot be null", controller);
        assertNotNull(controller.execute());
    }

    /**
     * Test run the MVC pattern. create Controller. Add Model and View,
     * initialise
     * model.
     *
     * @throws MissingCommandException
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testBuildMvc() throws MissingCommandException {
        final ControllerInterface controller = new Controller();
        assertNotNull("controller  cannot be null", controller);

        final ControllerInterface execute = controller.execute("ExampleCommand");
        assertNotNull("execute cannot be null", execute);
    }

    @Test
    public void testSetupMvc() throws MissingCommandException {
        final ControllerInterface controller = new Controller();
        assertNotNull("controller  cannot be null", controller);

        final AbstractModel model = new Model();
        assertNotNull("Model cannot be null", model);
        assertEquals(controller, controller.attachModel(model));

        final AbstractView view = new View();
        assertNotNull("view cannot be null", view);
        assertEquals(controller, controller.attachView(view));

        final ControllerInterface execute = controller.execute("ExampleCommand");
        assertNotNull("execute cannot be null", execute);
    }

    @Test
    public void testMvcConstructor() throws MissingCommandException {
        final AbstractModel model = new Model();
        assertNotNull("Model cannot be null", model);

        final AbstractView view = new View();
        assertNotNull("view cannot be null", view);

        final ControllerInterface controller = new Controller(model, view);
        assertNotNull("controller  cannot be null", controller);

        final ControllerInterface execute = controller.execute("ExampleCommand");
        assertNotNull("execute cannot be null", execute);
    }
}
