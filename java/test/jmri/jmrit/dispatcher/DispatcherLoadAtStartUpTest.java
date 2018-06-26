    package jmri.jmrit.dispatcher;

    import java.awt.GraphicsEnvironment;
import jmri.InstanceManager;
import jmri.util.JUnitUtil;
import org.junit.After;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.netbeans.jemmy.operators.JFrameOperator;

    /**
     * Swing jfcUnit tests for dispatcher train info
     *
     * @author Dave Duchamp
     */
    public class DispatcherLoadAtStartUpTest {

        @Test
        public void testLoadAtStartUp() throws Exception {

            Assume.assumeFalse(GraphicsEnvironment.isHeadless());

            DispatcherFrame d = InstanceManager.getDefault(DispatcherFrame.class);
            d.loadAtStartup();
            // Find new table window by name
            JFrameOperator dw = new JFrameOperator(Bundle.getMessage("TitleDispatcher"));
            // Ask to close Dispatcher window
            dw.requestClose();
            // we still have a reference to the window, so make sure that clears
            JUnitUtil.dispose(d);
        }

        @Before
        public void setUp() {
            JUnitUtil.setUp();
        }

        @After
        public void tearDown() {
            JUnitUtil.tearDown();
        }
    }

