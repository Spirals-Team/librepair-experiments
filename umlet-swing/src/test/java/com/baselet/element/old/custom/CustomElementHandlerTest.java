package com.baselet.element.old.custom;

import com.baselet.custom.CustomElementPanel;
import com.baselet.element.interfaces.GridElement;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;

public class CustomElementHandlerTest {

    @InjectMocks
    private CustomElementHandler customElementHandler;

    @Mock
    private CustomElement customElement;


    @Before
    public void setUp() throws Exception {

        customElementHandler = mock(CustomElementHandler.class);
    }

    @Test
    public void testNewEntity() {

        ArgumentCaptor<String> valueCapture = ArgumentCaptor.forClass(String.class);
        doNothing().when(customElementHandler).newEntity(valueCapture.capture());
        customElementHandler.newEntity("captured");

        assertEquals("captured", valueCapture.getValue());
    }

    @Test
    public void testEditEntity() {

        customElement = new CustomElement() {
            @Override
            public void paint() {

            }
        };

        ArgumentCaptor<CustomElement> valueCapture = ArgumentCaptor.forClass(CustomElement.class);
        doNothing().when(customElementHandler).editEntity(valueCapture.capture());
        customElementHandler.editEntity(customElement);

        assertEquals(customElement, valueCapture.getValue());
    }
}

