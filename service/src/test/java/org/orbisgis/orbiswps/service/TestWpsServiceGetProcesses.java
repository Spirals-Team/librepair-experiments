/*
 * OrbisWPS contains a set of libraries to build a Web Processing Service (WPS)
 * compliant with the 2.0 specification.
 *
 * OrbisWPS is part of the OrbisGIS platform
 *
 * OrbisGIS is a java GIS application dedicated to research in GIScience.
 * OrbisGIS is developed by the GIS group of the DECIDE team of the
 * Lab-STICC CNRS laboratory, see <http://www.lab-sticc.fr/>.
 *
 * The GIS group of the DECIDE team is located at :
 *
 * Laboratoire Lab-STICC – CNRS UMR 6285
 * Equipe DECIDE
 * UNIVERSITÉ DE BRETAGNE-SUD
 * Institut Universitaire de Technologie de Vannes
 * 8, Rue Montaigne - BP 561 56017 Vannes Cedex
 *
 * OrbisWPS is distributed under GPL 3 license.
 *
 * Copyright (C) 2015-2017 CNRS (Lab-STICC UMR CNRS 6285)
 *
 *
 * OrbisWPS is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * OrbisWPS is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * OrbisWPS. If not, see <http://www.gnu.org/licenses/>.
 *
 * For more information, please consult: <http://www.orbisgis.org/>
 * or contact directly:
 * info_at_ orbisgis.org
 */
package org.orbisgis.orbiswps.service;

import net.opengis.wps._2_0.ProcessOffering;
import net.opengis.wps._2_0.ProcessOfferings;
import org.junit.Assert;
import org.junit.Test;
import org.orbisgis.orbiswps.service.model.JaxbContainer;
import org.orbisgis.orbiswps.service.operations.WPS_1_0_0_Operations;
import org.orbisgis.orbiswps.service.operations.WPS_2_0_Operations;
import org.orbisgis.orbiswps.service.operations.WPS_1_0_0_ServerProperties;
import org.orbisgis.orbiswps.service.operations.WPS_2_0_ServerProperties;
import org.orbisgis.orbiswps.serviceapi.WpsService;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * This test class perform tests about groovy wps scripts.
 * It loads several script in the wpsService and then test the DescribeProcess request.
 *
 * @author Sylvain PALOMINOS
 * @author Erwan Bocher
 */
public class TestWpsServiceGetProcesses {
    private WpsService wpsService;

    /**
     * Test the JDBCTable script DescribeProcess request.
     * @throws javax.xml.bind.JAXBException
     * @throws java.io.IOException
     */
    @Test
    public void testJDBCTableScript() throws JAXBException {
        //Start the wpsService
        initWpsService();
        Unmarshaller unmarshaller = JaxbContainer.JAXBCONTEXT.createUnmarshaller();
        //Build the DescribeProcess object
        File describeProcessFile = new File(this.getClass().getResource("JDBCTableDescribeProcess.xml").getFile());
        Object describeProcess = unmarshaller.unmarshal(describeProcessFile);
        //Marshall the DescribeProcess object into an OutputStream
        Marshaller marshaller = JaxbContainer.JAXBCONTEXT.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        marshaller.marshal(describeProcess, out);
        //Write the OutputStream content into an Input stream before sending it to the wpsService
        InputStream in = new DataInputStream(new ByteArrayInputStream(out.toByteArray()));
        ByteArrayOutputStream xml = (ByteArrayOutputStream) wpsService.callOperation(in);
        //Get back the result of the DescribeProcess request as a BufferReader
        InputStream resultXml = new ByteArrayInputStream(xml.toByteArray());
        //Unmarshall the result and check that the object is the same as the resource unmashalled xml.
        Object resultObject = unmarshaller.unmarshal(resultXml);
        File f = new File(this.getClass().getResource("JDBCTableProcessOfferings.xml").getFile());
        Object resourceObject = unmarshaller.unmarshal(f);

        String message = "Error on unmarshalling the WpsService answer, the object is not the one expected.\n\n";
        Assert.assertTrue(message, resultObject != null && resultObject instanceof ProcessOfferings);
        ProcessOfferings pos = (ProcessOfferings)resultObject;
        Assert.assertTrue(message, pos.getProcessOffering() != null && pos.getProcessOffering().size() == 1);
        ProcessOffering po = pos.getProcessOffering().get(0);
        Assert.assertTrue(message, po.isSetProcess());

    }

    /**
     * Test the JDBCColumn script DescribeProcess request.
     * @throws javax.xml.bind.JAXBException
     * @throws java.io.IOException
     */
    @Test
    public void testJDBCColumnScript() throws JAXBException, IOException {
        //Start the wpsService
        initWpsService();
        Unmarshaller unmarshaller = JaxbContainer.JAXBCONTEXT.createUnmarshaller();
        //Build the DescribeProcess object
        File describeProcessFile = new File(this.getClass().getResource("JDBCColumnDescribeProcess.xml").getFile());
        Object describeProcess = unmarshaller.unmarshal(describeProcessFile);
        //Marshall the DescribeProcess object into an OutputStream
        Marshaller marshaller = JaxbContainer.JAXBCONTEXT.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        marshaller.marshal(describeProcess, out);
        //Write the OutputStream content into an Input stream before sending it to the wpsService
        InputStream in = new DataInputStream(new ByteArrayInputStream(out.toByteArray()));
        ByteArrayOutputStream xml = (ByteArrayOutputStream) wpsService.callOperation(in);
        //Get back the result of the DescribeProcess request as a BufferReader
        InputStream resultXml = new ByteArrayInputStream(xml.toByteArray());
        //Unmarshall the result and check that the object is the same as the resource unmashalled xml.
        Object resultObject = unmarshaller.unmarshal(resultXml);
        
        String message = "Error on unmarshalling the WpsService answer, the object is not the one expected.\n\n";
        Assert.assertTrue(message, resultObject != null && resultObject instanceof ProcessOfferings);
        ProcessOfferings pos = (ProcessOfferings)resultObject;
        Assert.assertTrue(message, pos.getProcessOffering() != null && pos.getProcessOffering().size() == 1);
        ProcessOffering po = pos.getProcessOffering().get(0);
        Assert.assertTrue(message, po.isSetProcess());
    }

    /**
     * Test the JDBCColumn script DescribeProcess request.
     * @throws javax.xml.bind.JAXBException
     * @throws java.io.IOException
     */
    @Test
    public void testJDBCValueScript() throws JAXBException, IOException {
        //Start the wpsService
        initWpsService();
        Unmarshaller unmarshaller = JaxbContainer.JAXBCONTEXT.createUnmarshaller();
        //Build the DescribeProcess object
        File describeProcessFile = new File(this.getClass().getResource("JDBCValueDescribeProcess.xml").getFile());
        Object describeProcess = unmarshaller.unmarshal(describeProcessFile);
        //Marshall the DescribeProcess object into an OutputStream
        Marshaller marshaller = JaxbContainer.JAXBCONTEXT.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        marshaller.marshal(describeProcess, out);
        //Write the OutputStream content into an Input stream before sending it to the wpsService
        InputStream in = new DataInputStream(new ByteArrayInputStream(out.toByteArray()));
        ByteArrayOutputStream xml = (ByteArrayOutputStream) wpsService.callOperation(in);
        //Get back the result of the DescribeProcess request as a BufferReader
        InputStream resultXml = new ByteArrayInputStream(xml.toByteArray());
        //Unmarshall the result and check that the object is the same as the resource unmashalled xml.
        Object resultObject = unmarshaller.unmarshal(resultXml);
        File f = new File(this.getClass().getResource("JDBCValueProcessOfferings.xml").getFile());
        Object resourceObject = unmarshaller.unmarshal(f);

        String message = "Error on unmarshalling the WpsService answer, the object is not the one expected.\n\n";
        Assert.assertTrue(message, resultObject != null && resultObject instanceof ProcessOfferings);
        ProcessOfferings pos = (ProcessOfferings)resultObject;
        Assert.assertTrue(message, pos.getProcessOffering() != null && pos.getProcessOffering().size() == 1);
        ProcessOffering po = pos.getProcessOffering().get(0);
        Assert.assertTrue(message, po.isSetProcess());
    }

    /**
     * Test the Enumeration script DescribeProcess request.
     * @throws javax.xml.bind.JAXBException
     * @throws java.io.IOException
     */
    @Test
    public void testEnumerationScript() throws JAXBException, IOException {
        //Start the wpsService
        initWpsService();
        Unmarshaller unmarshaller = JaxbContainer.JAXBCONTEXT.createUnmarshaller();
        //Build the DescribeProcess object
        File describeProcessFile = new File(this.getClass().getResource("EnumerationDescribeProcess.xml").getFile());
        Object describeProcess = unmarshaller.unmarshal(describeProcessFile);
        //Marshall the DescribeProcess object into an OutputStream
        Marshaller marshaller = JaxbContainer.JAXBCONTEXT.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        marshaller.marshal(describeProcess, out);
        //Write the OutputStream content into an Input stream before sending it to the wpsService
        InputStream in = new DataInputStream(new ByteArrayInputStream(out.toByteArray()));
        ByteArrayOutputStream xml = (ByteArrayOutputStream) wpsService.callOperation(in);
        //Get back the result of the DescribeProcess request as a BufferReader
        InputStream resultXml = new ByteArrayInputStream(xml.toByteArray());
        //Unmarshall the result and check that the object is the same as the resource unmashalled xml.
        Object resultObject = unmarshaller.unmarshal(resultXml);
        File f = new File(this.getClass().getResource("EnumerationProcessOfferings.xml").getFile());
        Object resourceObject = unmarshaller.unmarshal(f);

        String message = "Error on unmarshalling the WpsService answer, the object is not the one expected.\n\n";
        Assert.assertTrue(message, resultObject != null && resultObject instanceof ProcessOfferings);
        ProcessOfferings pos = (ProcessOfferings)resultObject;
        Assert.assertTrue(message, pos.getProcessOffering() != null && pos.getProcessOffering().size() == 1);
        ProcessOffering po = pos.getProcessOffering().get(0);
        Assert.assertTrue(message, po.isSetProcess());
    }

    /**
     * Test the GeometryData script DescribeProcess request.
     * @throws javax.xml.bind.JAXBException
     * @throws java.io.IOException
     */
    @Test
    public void testGeometryDataScript() throws JAXBException, IOException {
        //Start the wpsService
        initWpsService();
        Unmarshaller unmarshaller = JaxbContainer.JAXBCONTEXT.createUnmarshaller();
        //Build the DescribeProcess object
        File describeProcessFile = new File(this.getClass().getResource("GeometryDataDescribeProcess.xml").getFile());
        Object describeProcess = unmarshaller.unmarshal(describeProcessFile);
        //Marshall the DescribeProcess object into an OutputStream
        Marshaller marshaller = JaxbContainer.JAXBCONTEXT.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        marshaller.marshal(describeProcess, out);
        //Write the OutputStream content into an Input stream before sending it to the wpsService
        InputStream in = new DataInputStream(new ByteArrayInputStream(out.toByteArray()));
        ByteArrayOutputStream xml = (ByteArrayOutputStream) wpsService.callOperation(in);
        //Get back the result of the DescribeProcess request as a BufferReader
        InputStream resultXml = new ByteArrayInputStream(xml.toByteArray());
        //Unmarshall the result and check that the object is the same as the resource unmashalled xml.
        Object resultObject = unmarshaller.unmarshal(resultXml);
        File f = new File(this.getClass().getResource("GeometryDataProcessOfferings.xml").getFile());
        Object resourceObject = unmarshaller.unmarshal(f);

        String message = "Error on unmarshalling the WpsService answer, the object is not the one expected.\n\n";
        Assert.assertTrue(message, resultObject != null && resultObject instanceof ProcessOfferings);
        ProcessOfferings pos = (ProcessOfferings)resultObject;
        Assert.assertTrue(message, pos.getProcessOffering() != null && pos.getProcessOffering().size() == 1);
        ProcessOffering po = pos.getProcessOffering().get(0);
        Assert.assertTrue(message, po.isSetProcess());
    }

    /**
     * Test the RawData script DescribeProcess request.
     * @throws javax.xml.bind.JAXBException
     * @throws java.io.IOException
     */
    @Test
    public void testRawDataScript() throws JAXBException, IOException {
        //Start the wpsService
        initWpsService();
        Unmarshaller unmarshaller = JaxbContainer.JAXBCONTEXT.createUnmarshaller();
        //Build the DescribeProcess object
        File describeProcessFile = new File(this.getClass().getResource("RawDataDescribeProcess.xml").getFile());
        Object describeProcess = unmarshaller.unmarshal(describeProcessFile);
        //Marshall the DescribeProcess object into an OutputStream
        Marshaller marshaller = JaxbContainer.JAXBCONTEXT.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        marshaller.marshal(describeProcess, out);
        //Write the OutputStream content into an Input stream before sending it to the wpsService
        InputStream in = new DataInputStream(new ByteArrayInputStream(out.toByteArray()));
        ByteArrayOutputStream xml = (ByteArrayOutputStream) wpsService.callOperation(in);
        //Get back the result of the DescribeProcess request as a BufferReader
        InputStream resultXml = new ByteArrayInputStream(xml.toByteArray());
        //Unmarshall the result and check that the object is the same as the resource unmashalled xml.
        Object resultObject = unmarshaller.unmarshal(resultXml);
        File f = new File(this.getClass().getResource("RawDataProcessOfferings.xml").getFile());
        Object resourceObject = unmarshaller.unmarshal(f);

        String message = "Error on unmarshalling the WpsService answer, the object is not the one expected.\n\n";
        Assert.assertTrue(message, resultObject != null && resultObject instanceof ProcessOfferings);
        ProcessOfferings pos = (ProcessOfferings)resultObject;
        Assert.assertTrue(message, pos.getProcessOffering() != null && pos.getProcessOffering().size() == 1);
        ProcessOffering po = pos.getProcessOffering().get(0);
        Assert.assertTrue(message, po.isSetProcess());
    }

    /**
     * Initialise a wpsService to test the scripts.
     * The initialised wpsService can't execute the processes.
     */
    private void initWpsService() {
        if (wpsService == null) {
            //Start the WpsService
            WpsServiceImpl localWpsService = new WpsServiceImpl();
            localWpsService.addWpsOperations(new WPS_1_0_0_Operations(localWpsService.getProcessManagerImpl(), new WPS_1_0_0_ServerProperties(), null));
            localWpsService.addWpsOperations(new WPS_2_0_Operations(localWpsService.getProcessManagerImpl(), new WPS_2_0_ServerProperties(), null));
            //Try to load the groovy scripts
            try {
                URL url = this.getClass().getResource("JDBCTable.groovy");
                if (url != null) {
                    File f = new File(url.toURI());
                    localWpsService.addProcess(f);
                }
                url = this.getClass().getResource("JDBCColumn.groovy");
                if (url != null) {
                    File f = new File(url.toURI());
                    localWpsService.addProcess(f);
                }
                url = this.getClass().getResource("JDBCValue.groovy");
                if (url != null) {
                    File f = new File(url.toURI());
                    localWpsService.addProcess(f);
                }
                url = this.getClass().getResource("Enumeration.groovy");
                if (url != null) {
                    File f = new File(url.toURI());
                    localWpsService.addProcess(f);
                }
                url = this.getClass().getResource("GeometryData.groovy");
                if (url != null) {
                    File f = new File(url.toURI());
                    localWpsService.addProcess(f);
                }
                url = this.getClass().getResource("RawData.groovy");
                if (url != null) {
                    File f = new File(url.toURI());
                    localWpsService.addProcess(f);
                }
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            wpsService = localWpsService;
        }
    }
}
