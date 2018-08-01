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
import java.util.Map;
import nl.mpi.tg.eg.frinex.common.listener.TimedStimulusListener;
import nl.mpi.tg.eg.frinex.common.model.Stimulus;

/**
 * @since May 24, 2017 11:48:44 AM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public interface StimuliProvider {

//    void getAll();
    /**
     * getCurrentStimulus returns the currently selected stimulus
     *
     * @return Stimulus
     */
    Stimulus getCurrentStimulus();

    String getCurrentStimulusUniqueId();

    /**
     * getCurrentStimulusIndex returns the current stimulus index, this will be
     * called at times and the result will be stored between sessions
     *
     * @return stimuliIndex
     */
    int getCurrentStimulusIndex();

    /**
     * generateStimuliStateSnapshot called at various times during the screens
     * existence and is used to capture the current state of the StimuliProvider
     *
     * @return stimuliStateSnapshot
     */
    String generateStimuliStateSnapshot();

    String getHtmlStimuliReport();

    Map<String, String> getStimuliReport(String reportType);

    List<Stimulus> getDistractorList(int maxStimuli);

    List<Stimulus> getMatchingStimuli(final String matchingRegex);

    void getSdCardSubset(final ArrayList<String> directoryTagArray, final List<String[]> directoryList, final TimedStimulusListener simulusLoadedListener, final TimedStimulusListener simulusErrorListener, final String storedStimulusList, final int currentStimuliIndex);

    Stimulus getStimuliFromString(final String stimuliString);

    void getSubset(final List<Stimulus.Tag> selectionTags, final String storedStimulusList, final int currentStimuliIndex);

    void getSubset(final List<Stimulus.Tag> selectionTags, final String storedStimulusList, List<Stimulus> stimulusListCopy);

    /**
     * getTotalStimuli returns the total number of stimuli
     *
     * @return stimuliIndex
     */
    int getTotalStimuli();

    /**
     * hasNextStimulus tests if the stimulus indicated by the provided increment
     * exists
     *
     * @param increment
     * @return true if the stimulus exists
     */
    boolean hasNextStimulus(final int increment);

    /**
     * initialiseStimuliState called by the system when a screen starts and
     * allows the state to be restored from a previous snapshot if available
     *
     * @param stimuliStateSnapshot
     */
    void initialiseStimuliState(String stimuliStateSnapshot);

    /**
     * nextStimulus selects the stimulus indicated by the provided increment
     *
     * @param increment
     */
    void nextStimulus(final int increment);

    void pushCurrentStimulusToEnd();

    /**
     * setCurrentStimuliIndex selects the stimulus indicated by the provided
     * stimuliIndex, this will be called when the screen starts and will be
     * passed the previous stimuliIndex if previous session data exists
     *
     * @param stimuliIndex
     */
    void setCurrentStimuliIndex(int stimuliIndex);
}
