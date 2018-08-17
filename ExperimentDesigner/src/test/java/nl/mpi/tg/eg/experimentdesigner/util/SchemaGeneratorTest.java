/*
 * Copyright (C) 2018 Max Planck Institute for Psycholinguistics, Nijmegen
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package nl.mpi.tg.eg.experimentdesigner.util;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.junit.Test;
import org.xml.sax.SAXException;
import static org.junit.Assert.*;

/**
 * @since May 9, 2018 5:56:27 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public class SchemaGeneratorTest {

    /**
     * Test of createSchemaFile method, of class SchemaGenerator.
     *
     * @throws java.io.IOException
     * @throws java.net.URISyntaxException
     */
    @Test
    public void testCreateSchemaFile() throws IOException, URISyntaxException {
        System.out.println("createSchemaFile");
        final String inputDirectory = "/frinex-rest-output/";
        URI outputDirectoryUri = this.getClass().getResource(inputDirectory).toURI();
        SchemaGenerator instance = new SchemaGenerator();
        final File schemaFile = new File(new File(outputDirectoryUri), "frinex.xsd");
        instance.createSchemaFile(schemaFile);

        // todo: add more configuration files to the test
        String[] fileNameArray = new String[]{
//            "advocas1.xml",
            "french_audio.xml", "hrpretest02.xml", "ld_screensize.xml", "rosselfieldkit.xml",
//            "advocas2.xml",
            "french_conversation.xml", "leeservaring.xml", "parcours01.xml", "sentences_rating_task.xml", "vanuatufieldkit.xml",
            "alloptions.xml",
            "multiparticipant.xml", "playback_preference.xml",
            "sentveri_exp3.xml",
            "wellspringssamoan.xml",
            "antwoordraden.xml", "generic_example.xml",
//            "joseco01.xml",
            "nonwacq.xml", "shawifieldkit.xml", "zinnen_afmaken.xml",
//            "audioas2.xml",
            "online_emotions.xml", "playhouse_study.xml",
            //            "short-for-unittest-to-delete.xml",
            "zinnen_beoordelen.xml",
            "dobes_annotator.xml",
//            "heoexp01.xml", "joseco02.xml",
            "onlinepretest.xml", "shortmultiparticipant01.xml",
//            "engadvocas.xml", 
//            "ravensas.xml", "peabodyas.xml", "peabodyas.xml",
//            "lilbq4.xml",
            "hrpretest.xml", "kinship_example.xml",
//            "ppvt.xml",
            "with_simulus_example.xml",
            //            "synquiz2.xml"
        };
        for (String fileName : fileNameArray) {
            System.out.println(fileName);
            Source xmlFile = new StreamSource(new File(new File(outputDirectoryUri), fileName));
            SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/XML/XMLSchema/v1.1");
            try {
                Schema schema = schemaFactory.newSchema(schemaFile);
                Validator validator = schema.newValidator();
                validator.validate(xmlFile);
            } catch (SAXException saxe) {
                System.out.println(saxe.getMessage());
                fail(fileName + " :" + saxe.getMessage());
            }
        }
    }
}
