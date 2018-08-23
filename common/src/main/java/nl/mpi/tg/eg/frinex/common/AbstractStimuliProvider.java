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
package nl.mpi.tg.eg.frinex.common;

import java.util.ArrayList;
import java.util.List;
import nl.mpi.tg.eg.frinex.common.listener.TimedStimulusListener;
import nl.mpi.tg.eg.frinex.common.model.Stimulus;

/**
 * @since Oct 27, 2017 2:05:44 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public abstract class AbstractStimuliProvider implements StimuliProvider {

    @Deprecated
    public AbstractStimuliProvider() {
    }

    public AbstractStimuliProvider(final Stimulus[] stimulusArray) {
    }

    @Override
    public List<Stimulus> getMatchingStimuli(String matchingRegex) {
        throw new UnsupportedOperationException("getMatchingStimuli");
    }

    @Override
    public void getSdCardSubset(ArrayList<String> directoryTagArray, List<String[]> directoryList, TimedStimulusListener simulusLoadedListener, TimedStimulusListener simulusErrorListener, String storedStimulusList, int currentStimuliIndex) {
        throw new UnsupportedOperationException("getSdCardSubset");
    }

    @Override
    public Stimulus getStimuliFromString(String stimuliString) {
        throw new UnsupportedOperationException("getStimuliFromString");
    }

    @Override
    public void getSubset(List<Stimulus.Tag> selectionTags, String stimuliStateSnapshot, int currentStimuliIndex) {
        initialiseStimuliState(stimuliStateSnapshot);
    }

    @Override
    public void getSubset(List<Stimulus.Tag> selectionTags, String stimuliStateSnapshot, List<Stimulus> stimulusListCopy) {
        initialiseStimuliState(stimuliStateSnapshot);
    }

    @Override
    public void pushCurrentStimulusToEnd() {
        throw new UnsupportedOperationException("pushCurrentStimulusToEnd");
    }

    @Override
    public List<Stimulus> getDistractorList(int maxStimuli) {
        throw new UnsupportedOperationException("getDistractorList");
    }
}
