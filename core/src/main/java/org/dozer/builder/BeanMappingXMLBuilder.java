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

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.SAXException;

import org.apache.commons.io.IOUtils;
import org.dozer.MappingException;
import org.dozer.builder.model.MappingsDefinition;
import org.dozer.classmap.Configuration;
import org.dozer.classmap.MappingFileData;
import org.dozer.util.MappingValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BeanMappingXMLBuilder implements BeanMappingsBuilder {

    private static final Logger LOG = LoggerFactory.getLogger(BeanMappingXMLBuilder.class);

    private final List<MappingsDefinition> mappingsDefinitions = new ArrayList<MappingsDefinition>();
    private final List<String> classpathFiles;
    private final DefaultModelJAXBContextFactory<MappingsDefinition> factory;

    public BeanMappingXMLBuilder() {
        this(new ArrayList<String>());
    }

    public BeanMappingXMLBuilder(List<String> classpathFiles) {
        this.factory = new DefaultModelJAXBContextFactory<MappingsDefinition>();
        this.classpathFiles = classpathFiles;
    }

    public void load(List<String> classpathFiles) throws IOException, SAXException {
        for (String path : classpathFiles) {
            load(path);
        }
    }

    public void load(String classpathFile) throws IOException, SAXException {
        load(MappingValidator.validateURL(classpathFile));
    }

    public void load(URL path) throws IOException, SAXException {
        try (Reader stream = new BufferedReader(new InputStreamReader(path.openStream()))) {
            LOG.info("Using URL [" + path + "] to load custom xml mappings");

            load(stream);
        }
    }

    /**
     * Load the XML file from the stream
     *
     * @param stream caller must close stream
     * @throws IOException  if mapping fails due to reading the XML file
     * @throws SAXException if mapping fails due to XML parsing issue
     */
    public void load(Reader stream) throws IOException, SAXException {
        //Copy the stream so we can validate and parse it
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        IOUtils.copy(stream, baos, Charset.forName("UTF-8"));
        byte[] bytes = baos.toByteArray();

        factory.validateXML(bytes);

        MappingsDefinition result = factory.readXML(bytes, MappingsDefinition.class);
        if (result != null) {
            mappingsDefinitions.add(result);

            LOG.info("Successfully loaded custom xml mappings");
        }
    }

    @Override
    public List<MappingFileData> convert() {
        if (mappingsDefinitions.size() <= 0 && classpathFiles.size() > 0) {
            //Constructor has been used and no load methods have been called.

            try {
                load(classpathFiles);
            } catch (IOException e) {
                throw new MappingException("Failed to load XML mapping: " + e.getMessage(), e);
            } catch (SAXException e) {
                throw new MappingException("Failed to load XML mapping: " + e.getMessage(), e);
            }
        }

        List<MappingFileData> answer = new ArrayList<MappingFileData>();
        for (MappingsDefinition mappingsDefinition : mappingsDefinitions) {
            Configuration configuration = null;
            if (mappingsDefinition.getConfiguration() != null) {
                configuration = mappingsDefinition.getConfiguration().convert();
            }

            MappingFileData data = new MappingFileData();
            data.setConfiguration(configuration);
            data.getClassMaps().addAll(mappingsDefinition.build(configuration));

            answer.add(data);
        }

        return answer;
    }
}
