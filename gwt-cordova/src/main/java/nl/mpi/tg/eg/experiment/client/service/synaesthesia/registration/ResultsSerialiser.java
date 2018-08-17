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
package nl.mpi.tg.eg.experiment.client.service.synaesthesia.registration;

import java.util.Date;
import java.util.SortedSet;
import nl.mpi.tg.eg.experiment.client.model.MetadataField;
import nl.mpi.tg.eg.frinex.common.model.Stimulus;
import nl.mpi.tg.eg.experiment.client.model.UserResults;
import nl.mpi.tg.eg.experiment.client.model.colour.ColourData;
import nl.mpi.tg.eg.experiment.client.model.colour.StimulusResponse;
import nl.mpi.tg.eg.experiment.client.model.colour.StimulusResponseGroup;

/**
 * @since Oct 31, 2014 3:48:38 PM (creation date)
 * @author Peter Withers <p.withers@psych.ru.nl>
 */
public abstract class ResultsSerialiser {

//    private final MetadataFields mateadataFields = GWT.create(MetadataFields.class);
    public String serialise(UserResults userResults, MetadataField postName_email) {
        StringBuilder stringBuilder = new StringBuilder();
        for (StimulusResponseGroup responseGroup : userResults.getStimulusResponseGroups()) {
            serialise(stringBuilder, responseGroup, userResults.getUserData().getMetadataValue(postName_email));
        }
        return stringBuilder.toString();
    }

    public String serialise(StimulusResponseGroup responseGroup, String uuid) {
        StringBuilder stringBuilder = new StringBuilder();
        serialise(stringBuilder, responseGroup, uuid);
        return stringBuilder.toString();
    }

    public void serialise(StringBuilder stringBuilder, StimulusResponseGroup responseGroup, String postName_email) {
        final SortedSet<Stimulus> stimuliList = responseGroup.getStimuli();
        for (Stimulus stimulus : stimuliList) {
            for (StimulusResponse response : responseGroup.getResults(stimulus)) {
                stringBuilder.append(postName_email);
                stringBuilder.append(getSeparator());
                stringBuilder.append(responseGroup.getPostName());
                stringBuilder.append(getSeparator());
                stringBuilder.append(stimulus.getLabel());
                stringBuilder.append(getSeparator());
                stringBuilder.append(formatDate(response.getTime()));
                stringBuilder.append(getSeparator());
                stringBuilder.append(response.getDurationMs());
                stringBuilder.append(getSeparator());
                final ColourData colour = response.getColour();
                if (colour != null) {
                    stringBuilder.append(colour.getHexValue());
                    stringBuilder.append(getSeparator());
                    stringBuilder.append(colour.getRed());
                    stringBuilder.append(getSeparator());
                    stringBuilder.append(colour.getGreen());
                    stringBuilder.append(getSeparator());
                    stringBuilder.append(colour.getBlue());
                } else {
                    stringBuilder.append(getSeparator());
                    stringBuilder.append(getSeparator());
                    stringBuilder.append(getSeparator());
                }
                stringBuilder.append(getRowSeparator());
            }
        }

    }

    protected abstract String formatDate(Date date);

    protected abstract String getSeparator();
    
    protected abstract String getRowSeparator();
}
