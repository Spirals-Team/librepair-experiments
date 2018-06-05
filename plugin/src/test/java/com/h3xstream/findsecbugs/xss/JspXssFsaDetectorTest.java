/**
 * Find Security Bugs
 * Copyright (c) Philippe Arteau, All rights reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3.0 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library.
 */
package com.h3xstream.findsecbugs.xss;

import com.h3xstream.findbugs.test.BaseDetectorTest;
import com.h3xstream.findbugs.test.EasyBugReporter;
import com.h3xstream.findsecbugs.FindSecBugsGlobalConfig;
import org.testng.annotations.*;

import java.util.Arrays;

import static org.mockito.Mockito.*;

/**
 * Before running theses tests cases, jsp files need to be compiled.
 *
 * <pre>mvn clean test-compile</pre>
 */
public class JspXssFsaDetectorTest extends BaseDetectorTest {

    @BeforeMethod
    public void beforeTest() {
        FindSecBugsGlobalConfig.getInstance().setReportPotentialXssWrongContext(true);
        FindSecBugsGlobalConfig.getInstance().setDebugPrintInvocationVisited(true);
        FindSecBugsGlobalConfig.getInstance().setDebugTaintState(true); 
    }

    @AfterMethod
    public void afterTest() {
        FindSecBugsGlobalConfig.getInstance().setReportPotentialXssWrongContext(false);
    }

    @Test
    public void test() throws Exception {
        //Locate test code
        String[] files = {
                getJspFilePath("xss/xss_fsa.jsp")
        };

        //Run the analysis
        EasyBugReporter reporter = spy(new SecurityReporter());
        
        String customConfigFile = FindSecBugsGlobalConfig.getInstance().getCustomConfigFile();
        String path = this.getClass().getResource("/com/h3xstream/findsecbugs/xss/CustomConfig.txt").getPath();
        FindSecBugsGlobalConfig.getInstance().setCustomConfigFile(path);
        
        
        analyze(files, reporter);

        verify(reporter, times(0)).doReportBug(bugDefinition().bugType("XSS_JSP_PRINT").build());
    }
}

