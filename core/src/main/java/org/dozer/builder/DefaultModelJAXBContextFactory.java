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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.SAXException;

import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;

import org.dozer.builder.model.MappingsDefinition;
import org.dozer.loader.xml.DozerResolver;

public class DefaultModelJAXBContextFactory<T> {

    private static final String XSD = "http://dozermapper.github.io/schema/bean-mapping.xsd";
    private final XmlMapper xmlMapper;

    public DefaultModelJAXBContextFactory() {
        JacksonXmlModule module = new JacksonXmlModule();
        module.setDefaultUseWrapper(false);

        this.xmlMapper = new XmlMapper(module);
        this.xmlMapper.registerModule(new JaxbAnnotationModule());
    }

    private Schema newSchema() throws SAXException {
        DozerResolver resolver = new DozerResolver();
        InputStream xsd = resolver.resolveEntity(null, XSD).getByteStream();
        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = factory.newSchema(new StreamSource(xsd));

        return schema;
    }

    public void validateXML(byte[] bytes) throws SAXException, IOException {
        StreamSource streamSource = new StreamSource(new ByteArrayInputStream(bytes));
        streamSource.setSystemId(XSD);

        newSchema().newValidator().validate(streamSource);
    }

    public T readXML(byte[] bytes, Class<T> type) throws IOException {
        return xmlMapper.readValue(new ByteArrayInputStream(bytes), type);
    }
}
