/*
 * Copyright 2005-2017 Dozer Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.dozer.builder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.xml.sax.SAXException;

import org.apache.commons.io.IOUtils;
import org.dozer.builder.model.MappingsDefinition;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

@Ignore
public class DefaultModelJAXBContextFactoryTest {

    @Test
    public void canConstruct() throws JAXBException {
        DefaultModelJAXBContextFactory factory = new DefaultModelJAXBContextFactory();

        assertNotNull(factory);
    }

    @Test
    public void testCurrentMappingXML() throws JAXBException, IOException, SAXException {
        DefaultModelJAXBContextFactory<MappingsDefinition> factory = new DefaultModelJAXBContextFactory<MappingsDefinition>();

        File folder = new File("/Users/garethah/Documents/github/garethahealy/dozer/core/src/test/resources/mappings");
        File[] listOfFiles = folder.listFiles();

        for (File file : listOfFiles) {
            if (file.isFile()) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                IOUtils.copy(new FileInputStream(file), baos);
                byte[] bytes = baos.toByteArray();

                factory.validateXML(bytes);

                MappingsDefinition mapping = factory.readXML(bytes, MappingsDefinition.class);

                assertNotNull(mapping);
            }
        }
    }
}
