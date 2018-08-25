/*
 *  Copyright (c) 2002-2018, Manorrock.com. All Rights Reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 *      1. Redistributions of source code must retain the above copyright
 *         notice, this list of conditions and the following disclaimer.
 *
 *      2. Redistributions in binary form must reproduce the above copyright
 *         notice, this list of conditions and the following disclaimer in the
 *         documentation and/or other materials provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 *  AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 *  ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 *  LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 *  SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 *  INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 *  CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 *  ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 *  POSSIBILITY OF SUCH DAMAGE.
 */
package com.manorrock.piranha;

import java.io.File;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * The JUnit tests for DirectoryResource class.
 *
 * @author Manfred Riem (mriem@manorrock.com)
 */
public class DefaultDirectoryResourceTest {

    /**
     * Test getResource method.
     */
    @Test
    public void testGetResource() {
        DefaultDirectoryResource resource = new DefaultDirectoryResource();
        assertNull(resource.getResource("/resource"));
    }

    /**
     * Test getResourceAsStream method.
     */
    @Test
    public void testGetResourceAsStream() {
        DefaultDirectoryResource resource = new DefaultDirectoryResource();
        assertNull(resource.getResourceAsStream("/resource"));
    }

    /**
     * Test getResourceAsStream method.
     */
    @Test
    public void testGetResourceAsStream2() {
        DefaultDirectoryResource resource = new DefaultDirectoryResource(new File("."));
        assertNotNull(resource.getResourceAsStream("pom.xml"));
    }

    /**
     * Test getRootDirectory method, of class DirectoryResource.
     */
    @Test
    public void testGetRootDirectory() {
        DefaultDirectoryResource resource = new DefaultDirectoryResource();
        resource.setRootDirectory(new File("src/main/java"));
        assertNotNull(resource.getRootDirectory());
    }
}
