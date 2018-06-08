/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.metamodel.spring;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.apache.metamodel.DataContext;
import org.apache.metamodel.csv.CsvDataContext;
import org.apache.metamodel.util.FileResource;
import org.apache.metamodel.util.Resource;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:examples/csv-using-filename.xml")
public class CsvSpecifiedConfigurationFromFilenameTest {

    @Autowired
    private DataContext dataContext;

    @Test
    public void testReadDataContext() {
        assertEquals(CsvDataContext.class, dataContext.getClass());

        CsvDataContext csv = (CsvDataContext) dataContext;
        Resource resource = csv.getResource();

        assertEquals(FileResource.class, resource.getClass());

        assertEquals("example.csv", resource.getName());

        assertEquals("[\"foo\", bar]", Arrays.toString(csv.getDefaultSchema().getTable(0).getColumnNames().toArray()));

        Assert.assertFalse(csv.getConfiguration().isMultilineValues());
    }
}
