package edu.boisestate.cs471.controller;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import edu.boisestate.cs471.model.Model;
import edu.boisestate.cs471.util.interfaces.IViewUpdateListener;

public class ControllerTest {

    private Model mockModel;
    private Controller ctrl;

    @Before
    public void setUp() {
        mockModel = Mockito.mock(Model.class);
        ctrl = new Controller(mockModel);
    }


    @Test
    public final void testInitialization() {
        // Make sure the controller registered itself as a listener.
        Mockito.verify(mockModel).setSelectionListener(ctrl);
        assertEquals(mockModel, ctrl.getModel());
    }

    @Test
    public final void testRegisterViewListener() {
        IViewUpdateListener viewListener = Mockito.mock(IViewUpdateListener.class);
        ctrl.registerListener(viewListener);
        Mockito.verify(viewListener).onDynamicTextChanged();
        Mockito.verify(viewListener).onSortDataRangeChanged(25);
        Mockito.verify(viewListener).onSortDataUpdated();
    }

    @Test
    public final void testUpdateSampelSize() {
        int size = 42;
        ctrl.updateSampleSize(size);
        Mockito.verify(mockModel).updateSampleSize(size);
    }


    @Test
    public final void testTestTest() {
        assertEquals(99, ctrl.testme());
    }
}
