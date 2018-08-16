package com.baselet.diagram.io;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import javax.swing.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class OpenFileChooserTest {

    @InjectMocks
    OpenFileChooser openFileChooser;

    @InjectMocks
    JFileChooser jFileChooser;

    @InjectMocks
    Frame frame;

    @Mock
    private List<String> files;

    @Before
    public void setUp() throws Exception {
        openFileChooser = mock(OpenFileChooser.class);
        jFileChooser = mock(JFileChooser.class);
        frame = mock(Frame.class);
        files = new ArrayList();
    }

    @Test
    public void getFilesToOpen() {

        String absolutePath = "usr/name/file.ufx";
        files.add(absolutePath);

        when(openFileChooser.getFilesToOpen(frame)).thenReturn(files);
        assertEquals(files.get(0), absolutePath);
    }

    @Test
    public void testFileFilterConstraint() {

        ArgumentCaptor<Boolean> valueCapture = ArgumentCaptor.forClass(Boolean.class);
        doNothing().when(jFileChooser).setAcceptAllFileFilterUsed(valueCapture.capture());
        jFileChooser.setAcceptAllFileFilterUsed(false);

        assertEquals(false, valueCapture.getValue());
    }
}