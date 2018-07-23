/*
 * Copyright (C) 2017 Max Planck Institute for Psycholinguistics, Nijmegen
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
package nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.model.vocabulary;

import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.generic.BandStimulus;

/**
 * @since Oct 27, 2017 2:13:03 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public abstract class AdVocAsStimulus extends BandStimulus {

    public AdVocAsStimulus(String uniqueId, String label, String ratingLabels, String correctResponses,  int bandNumber) {
        super(uniqueId, new Tag[0], label, "", 0, "", "", "", ratingLabels, correctResponses, (new Integer(bandNumber)).toString(), bandNumber-1);
    }

    public int getBandNumber() {
        return Integer.parseInt(bandLabel);
    }
    
}
