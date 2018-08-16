package com.baselet.element.old.custom;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CustomElementCompilerTest {

    @InjectMocks
    private CustomElementCompiler customElementCompiler;

    @Mock
    private ErrorHandler errorHandler;

    @Before
    public void setUp() throws Exception {
        customElementCompiler = mock(CustomElementCompiler.class);
        errorHandler = mock(ErrorHandler.class);
    }

    @Test
    public void testCustomElementsDisabled() {

        String code = "code";
        String errorMessage = "Custom Elements are disabled\nEnabled them in the Options\n" +
                "Only open them from trusted\nsources to avoid malicious code execution!";

        when(customElementCompiler.genEntity(code, errorHandler)).thenThrow(new Exception(errorMessage));
    }
}

