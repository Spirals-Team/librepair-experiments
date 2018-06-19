package org.apache.nifi.bootstrap.util;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class JavaVersionUtilTest {

    @Test
    public void testGetMajorVersion() throws IOException {
        int javaMajorVersion = JavaVersionUtil.getMajorVersion(this.getClass());
        Assert.assertTrue("Expected major version number to be greater than 0.", javaMajorVersion > 0);
    }
}