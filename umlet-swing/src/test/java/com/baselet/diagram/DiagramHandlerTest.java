package com.baselet.diagram;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DiagramHandlerTest {

    @InjectMocks
    DiagramHandler diagramHandler;

    @Before
    public void setUp() throws Exception {

        diagramHandler = mock(DiagramHandler.class);
    }

    @Test
    public void setGridSize() {

        ArgumentCaptor<Integer> valueCapture = ArgumentCaptor.forClass(Integer.class);
        doNothing().when(diagramHandler).setGridSize(valueCapture.capture());
        diagramHandler.setGridSize(5);

        assertEquals(Integer.valueOf(5), valueCapture.getValue());
    }

    @Test
    public void setGridAndZoom() {

        ArgumentCaptor<Integer> valueCapture = ArgumentCaptor.forClass(Integer.class);
        doNothing().when(diagramHandler).setGridAndZoom(valueCapture.capture());
        diagramHandler.setGridAndZoom(2);

        assertEquals(Integer.valueOf(2), valueCapture.getValue());
    }
}

