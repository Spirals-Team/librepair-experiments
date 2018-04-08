package com.github.funthomas424242.rades.annotations.lang.java;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;


class JavaModelHelperTest {

    @Test
    public void testCreateValidInstanz() {
        assertNotNull(new JavaModelHelper());
    }


    @Test
    public void testComputeEmptyPackage() {
        assertNull(JavaModelHelper.computePackageName("Hallo"));
    }

    @Test
    public void testComputeValidPackage() {
        assertEquals("test", JavaModelHelper.computePackageName("test.Hallo"));
    }


}