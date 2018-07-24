/*
 * Copyright (C) 2014 Language In Interaction
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
package nl.ru.languageininteraction.synaesthesia.client.service;

import nl.mpi.tg.eg.experiment.client.service.synaesthesia.registration.ResultsSerialiser;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import nl.mpi.tg.eg.experiment.client.exception.StimulusError;
import nl.mpi.tg.eg.experiment.client.model.MetadataField;
import nl.mpi.tg.eg.frinex.common.model.Stimulus;
import nl.mpi.tg.eg.experiment.client.model.UserData;
import nl.mpi.tg.eg.experiment.client.model.UserResults;
import nl.mpi.tg.eg.experiment.client.model.colour.ColourData;
import nl.mpi.tg.eg.experiment.client.model.colour.StimulusResponse;
import nl.mpi.tg.eg.experiment.client.model.colour.StimulusResponseGroup;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @since Oct 31, 2014 4:33:24 PM (creation date)
 * @author Peter Withers <p.withers@psych.ru.nl>
 */
public class ResultsSerialiserTest {

    private Stimulus getStimulus(final String label) {
        return new Stimulus() {
            @Override
            public boolean isCorrect(String value) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public String getCode() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public String getLabel() {
                return label;
            }

            @Override
            public int getPauseMs() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public String getUniqueId() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public boolean hasImage() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public boolean hasAudio() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public boolean hasVideo() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public boolean hasRatingLabels() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public String getRatingLabels() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public boolean hasCorrectResponses() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public String getCorrectResponses() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public String getAudio() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public String getImage() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public String getVideo() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public List<?> getTags() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public int compareTo(Stimulus o) {
                return this.getLabel().compareTo(o.getLabel());
            }

            @Override
            public int hashCode() {
                int hash = 7;
                hash = 79 * hash + Objects.hashCode(this.getLabel());
                return hash;
            }

            @Override
            public boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (obj == null) {
                    return false;
                }
                if (getClass() != obj.getClass()) {
                    return false;
                }
                final Stimulus other = (Stimulus) obj;
                if (!Objects.equals(this.getLabel(), other.getLabel())) {
                    return false;
                }
                return true;
            }
        };
    }

    /**
     * Test of serialise method, of class ResultsSerialiser.
     */
    @Test
    public void testSerialise() throws StimulusError {
        System.out.println("serialise");
        UserResults userResults = new UserResults(new UserData());
        final String postName_email = "postName_email";
        final MetadataField metadataFieldEmail = new MetadataField(postName_email, postName_email, postName_email, postName_email, postName_email);
        userResults.getUserData().setMetadataValue(metadataFieldEmail, "postName@email");
        final ArrayList<Stimulus> arrayList1 = new ArrayList<>();
        arrayList1.add(getStimulus("a1"));
        arrayList1.add(getStimulus("b1"));
        arrayList1.add(getStimulus("c1"));
        arrayList1.add(getStimulus("d1"));
        arrayList1.add(getStimulus("e1"));
        arrayList1.add(getStimulus("f1"));
//        final StimulusResponseGroup stimuli1 = new StimulusResponseGroup("test-group-1", "test-group-1");
        final ArrayList<Stimulus> arrayList2 = new ArrayList<>();
        arrayList2.add(getStimulus("a2"));
        arrayList2.add(getStimulus("b2"));
        arrayList2.add(getStimulus("c2"));
        arrayList2.add(getStimulus("d2"));
        arrayList2.add(getStimulus("e2"));
        arrayList2.add(getStimulus("f2"));
        final StimulusResponseGroup stimulusResponseGroup1 = new StimulusResponseGroup("test-group-1", "test-group-1");
        userResults.addStimulusResponseGroup(stimulusResponseGroup1);
        for (Stimulus stimulus : arrayList1) {
            stimulusResponseGroup1.addResponse(stimulus, new StimulusResponse(new ColourData(255, 255, 255), new Date(123445), 23));
            stimulusResponseGroup1.addResponse(stimulus, new StimulusResponse(new ColourData(0, 0, 0), new Date(12321), 21));
            stimulusResponseGroup1.addResponse(stimulus, new StimulusResponse(new ColourData(128, 127, 126), new Date(123445), 20));
        }
        final StimulusResponseGroup stimulusResponseGroup2 = new StimulusResponseGroup("test-group-2", "test-group-2");
        userResults.addStimulusResponseGroup(stimulusResponseGroup2);
        for (Stimulus stimulus : arrayList2) {
            stimulusResponseGroup2.addResponse(stimulus, new StimulusResponse(new ColourData(255, 255, 0), new Date(9999999), 141));
            stimulusResponseGroup2.addResponse(stimulus, new StimulusResponse(new ColourData(255, 0, 255), new Date(33333), 121));
            stimulusResponseGroup2.addResponse(stimulus, new StimulusResponse(new ColourData(0, 255, 255), new Date(111111), 12));
        }
        ResultsSerialiser instance = new ResultsSerialiser() {
            @Override
            protected String formatDate(Date date) {
                return date.toString();
            }

            @Override
            protected String getSeparator() {
                return "\t";
            }

            @Override
            protected String getRowSeparator() {
                return "\n";
            }
        };
        String expResult = "postName@email	test-group-1	a1	Thu Jan 01 01:02:03 CET 1970	23.0	#ffffff	255	255	255\n"
                + "postName@email	test-group-1	a1	Thu Jan 01 01:00:12 CET 1970	21.0	#000000	0	0	0\n"
                + "postName@email	test-group-1	a1	Thu Jan 01 01:02:03 CET 1970	20.0	#807f7e	128	127	126\n"
                + "postName@email	test-group-1	b1	Thu Jan 01 01:02:03 CET 1970	23.0	#ffffff	255	255	255\n"
                + "postName@email	test-group-1	b1	Thu Jan 01 01:00:12 CET 1970	21.0	#000000	0	0	0\n"
                + "postName@email	test-group-1	b1	Thu Jan 01 01:02:03 CET 1970	20.0	#807f7e	128	127	126\n"
                + "postName@email	test-group-1	c1	Thu Jan 01 01:02:03 CET 1970	23.0	#ffffff	255	255	255\n"
                + "postName@email	test-group-1	c1	Thu Jan 01 01:00:12 CET 1970	21.0	#000000	0	0	0\n"
                + "postName@email	test-group-1	c1	Thu Jan 01 01:02:03 CET 1970	20.0	#807f7e	128	127	126\n"
                + "postName@email	test-group-1	d1	Thu Jan 01 01:02:03 CET 1970	23.0	#ffffff	255	255	255\n"
                + "postName@email	test-group-1	d1	Thu Jan 01 01:00:12 CET 1970	21.0	#000000	0	0	0\n"
                + "postName@email	test-group-1	d1	Thu Jan 01 01:02:03 CET 1970	20.0	#807f7e	128	127	126\n"
                + "postName@email	test-group-1	e1	Thu Jan 01 01:02:03 CET 1970	23.0	#ffffff	255	255	255\n"
                + "postName@email	test-group-1	e1	Thu Jan 01 01:00:12 CET 1970	21.0	#000000	0	0	0\n"
                + "postName@email	test-group-1	e1	Thu Jan 01 01:02:03 CET 1970	20.0	#807f7e	128	127	126\n"
                + "postName@email	test-group-1	f1	Thu Jan 01 01:02:03 CET 1970	23.0	#ffffff	255	255	255\n"
                + "postName@email	test-group-1	f1	Thu Jan 01 01:00:12 CET 1970	21.0	#000000	0	0	0\n"
                + "postName@email	test-group-1	f1	Thu Jan 01 01:02:03 CET 1970	20.0	#807f7e	128	127	126\n"
                + "postName@email	test-group-2	a2	Thu Jan 01 03:46:39 CET 1970	141.0	#ffff00	255	255	0\n"
                + "postName@email	test-group-2	a2	Thu Jan 01 01:00:33 CET 1970	121.0	#ff00ff	255	0	255\n"
                + "postName@email	test-group-2	a2	Thu Jan 01 01:01:51 CET 1970	12.0	#00ffff	0	255	255\n"
                + "postName@email	test-group-2	b2	Thu Jan 01 03:46:39 CET 1970	141.0	#ffff00	255	255	0\n"
                + "postName@email	test-group-2	b2	Thu Jan 01 01:00:33 CET 1970	121.0	#ff00ff	255	0	255\n"
                + "postName@email	test-group-2	b2	Thu Jan 01 01:01:51 CET 1970	12.0	#00ffff	0	255	255\n"
                + "postName@email	test-group-2	c2	Thu Jan 01 03:46:39 CET 1970	141.0	#ffff00	255	255	0\n"
                + "postName@email	test-group-2	c2	Thu Jan 01 01:00:33 CET 1970	121.0	#ff00ff	255	0	255\n"
                + "postName@email	test-group-2	c2	Thu Jan 01 01:01:51 CET 1970	12.0	#00ffff	0	255	255\n"
                + "postName@email	test-group-2	d2	Thu Jan 01 03:46:39 CET 1970	141.0	#ffff00	255	255	0\n"
                + "postName@email	test-group-2	d2	Thu Jan 01 01:00:33 CET 1970	121.0	#ff00ff	255	0	255\n"
                + "postName@email	test-group-2	d2	Thu Jan 01 01:01:51 CET 1970	12.0	#00ffff	0	255	255\n"
                + "postName@email	test-group-2	e2	Thu Jan 01 03:46:39 CET 1970	141.0	#ffff00	255	255	0\n"
                + "postName@email	test-group-2	e2	Thu Jan 01 01:00:33 CET 1970	121.0	#ff00ff	255	0	255\n"
                + "postName@email	test-group-2	e2	Thu Jan 01 01:01:51 CET 1970	12.0	#00ffff	0	255	255\n"
                + "postName@email	test-group-2	f2	Thu Jan 01 03:46:39 CET 1970	141.0	#ffff00	255	255	0\n"
                + "postName@email	test-group-2	f2	Thu Jan 01 01:00:33 CET 1970	121.0	#ff00ff	255	0	255\n"
                + "postName@email	test-group-2	f2	Thu Jan 01 01:01:51 CET 1970	12.0	#00ffff	0	255	255\n";
        String result = instance.serialise(userResults, metadataFieldEmail);
        assertEquals(expResult, result);
    }

}
