package jmri.jmrit.beantable;

import apps.gui.GuiLafPreferencesManager;
import java.awt.GraphicsEnvironment;
import javax.swing.JFrame;
import jmri.InstanceManager;
import jmri.Sensor;
import jmri.util.JUnitUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.netbeans.jemmy.operators.JFrameOperator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Tests for the jmri.jmrit.beantable.SensorTableAction class.
 *
 * @author Paul Bender Copyright (C) 2017
 */
public class SensorTableActionTest extends AbstractTableActionBase {

    @Test
    public void testCTor() {
        Assert.assertNotNull("exists", a);
    }

    @Override
    public String getTableFrameName() {
        return Bundle.getMessage("TitleSensorTable");
    }

    @Override
    @Test
    public void testGetClassDescription() {
        Assert.assertEquals("Sensor Table Action class description", "Sensor Table", a.getClassDescription());
    }

    /**
     * Check the return value of includeAddButton. The table generated by this
     * action includes an Add Button.
     */
    @Override
    @Test
    public void testIncludeAddButton() {
        Assert.assertTrue("Default include add button", a.includeAddButton());
    }

    /**
     * Check graphic state presentation.
     *
     * @since 4.7.4
     */
    @Test
    public void testAddAndInvoke() {
        Assume.assumeFalse(GraphicsEnvironment.isHeadless());

        a.actionPerformed(null); // show table
        // create 2 sensors and see if they exist
        Sensor is1 = InstanceManager.sensorManagerInstance().provideSensor("IS1");
        Sensor is2 = InstanceManager.sensorManagerInstance().provideSensor("IS2");
        try {
            is1.setKnownState(Sensor.ACTIVE);
            is2.setKnownState(Sensor.INACTIVE);
        } catch (jmri.JmriException reason) {
            log.warn("Exception flipping sensor is1: " + reason);
        }

        // set graphic state column display preference to false, read by createModel()
        InstanceManager.getDefault(GuiLafPreferencesManager.class).setGraphicTableState(false);

        SensorTableAction _sTable;
        _sTable = new SensorTableAction();
        Assert.assertNotNull("found SensorTable frame", _sTable);

        // set to true, use icons
        InstanceManager.getDefault(GuiLafPreferencesManager.class).setGraphicTableState(true);
        SensorTableAction _s1Table;
        _s1Table = new SensorTableAction();
        Assert.assertNotNull("found SensorTable1 frame", _s1Table);

        _s1Table.addPressed(null);
        JFrame af = JFrameOperator.waitJFrame(Bundle.getMessage("TitleAddSensor"), true, true);
        Assert.assertNotNull("found Add frame", af);
        // close AddPane
        _s1Table.cancelPressed(null);
        // more Sensor Add pane tests in SensorTableWindowTest

        // clean up
        JFrame f = a.getFrame();
        if (f != null) {
            JUnitUtil.dispose(f);
        }
        JUnitUtil.dispose(af);
        _sTable.dispose();
        _s1Table.dispose();
    }

    // The minimal setup for log4J
    @Override
    @Before
    public void setUp() {
        JUnitUtil.setUp();
        jmri.util.JUnitUtil.initInternalSensorManager();
        a = new SensorTableAction();
    }

    @Override
    @After
    public void tearDown() {
        a.dispose();
        a = null;
        JUnitUtil.tearDown();
    }

    private final static Logger log = LoggerFactory.getLogger(SensorTableActionTest.class);

}
