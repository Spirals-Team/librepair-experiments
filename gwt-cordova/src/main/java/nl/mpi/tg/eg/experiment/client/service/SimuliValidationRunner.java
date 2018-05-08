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
package nl.mpi.tg.eg.experiment.client.service;

import com.google.gwt.user.client.Timer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import nl.mpi.tg.eg.experiment.client.model.GeneratedStimulus;
import nl.mpi.tg.eg.frinex.common.model.Stimulus;
import nl.mpi.tg.eg.frinex.common.model.StimulusSelector;

/**
 * @since Sep 27, 2017 1:42:59 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public abstract class SimuliValidationRunner {

    public void calculate(GeneratedStimulus.Tag tag) {
        final Set<String> calculatedStimuliSet = new HashSet<>();
        final Set<String> stimuliSet = new HashSet<>();
        final HashMap<String, Integer> transionTable = new HashMap<>();
        final String eventTag = "R0-4";
        final StimulusSelector[] selectionTags = new StimulusSelector[]{new StimulusSelector("v1", tag)};
        final StimulusSelector[] randomTags = new StimulusSelector[]{};

//final MetadataField stimulusAllocationField= metadataFieldProvider.stimuliMetadataField;
        final int maxStimulusCount = 23;
        final Integer minStimuliPerTag = 1;
        final Integer maxStimuliPerTag = 100;
        final boolean randomise = true;
        final int repeatCount = 1;
        final int repeatRandomWindow = 6;
        final int adjacencyThreshold = 3;
        final String storedStimulusList = "";
        final List<Stimulus.Tag> allocatedTags = new ArrayList<>();
//        final List<StimulusSelector> allocatedTags = new ArrayList<>(Arrays.asList(selectionTags));
        for (StimulusSelector selector : selectionTags) {
            allocatedTags.add(selector.getTag());
        }
        final int cyclesToRun = 1000000;
        appendOutput("cyclesToRun: " + cyclesToRun);
        final Timer timer = new Timer() {
            int sampleCount = 0;

            @Override
            public void run() {

//        for (int sampleCount = 0; sampleCount < cyclesToRun; sampleCount++) {
                final StimulusProvider stimulusProvider = new StimulusProvider();
                stimulusProvider.getSubset(allocatedTags, maxStimulusCount, randomise, repeatCount, repeatRandomWindow, adjacencyThreshold, storedStimulusList, 0);
                final String loadedStimulusString = stimulusProvider.generateStimuliStateSnapshot();
//            appendOutput(loadedStimulusString);
                if (calculatedStimuliSet.add(loadedStimulusString)) {
                    appendUniqueStimuliList(loadedStimulusString);
                }
                sampleCount(sampleCount + 1);
                uniqueCount(calculatedStimuliSet.size());
                String currentStimulus = null;
//                String nextStimulus = stimulusProvider.getCurrentStimulus().getImage() + "_" + stimulusProvider.getCurrentStimulus().getCode();
                String nextStimulus = stimulusProvider.getCurrentStimulus().getUniqueId();
                stimuliSet.add(nextStimulus);
                String currentPair = currentStimulus + ":" + nextStimulus;
                transionTable.put(currentPair, (transionTable.containsKey(currentPair)) ? transionTable.get(currentPair) + 1 : 1);
//            appendOutput(currentPair);
                while (stimulusProvider.hasNextStimulus(1)) {
                    currentStimulus = nextStimulus;
                    stimulusProvider.nextStimulus(1);
//                    nextStimulus = stimulusProvider.getCurrentStimulus().getImage() + "_" + stimulusProvider.getCurrentStimulus().getCode();
                    nextStimulus = stimulusProvider.getCurrentStimulus().getUniqueId();
                    stimuliSet.add(nextStimulus);
                    currentPair = currentStimulus + ":" + nextStimulus;
                    transionTable.put(currentPair, (transionTable.containsKey(currentPair)) ? transionTable.get(currentPair) + 1 : 1);
//                appendOutput(currentPair);
                }
                currentPair = nextStimulus + ":" + null;
                transionTable.put(currentPair, (transionTable.containsKey(currentPair)) ? transionTable.get(currentPair) + 1 : 1);
//            appendOutput(currentPair);
                sampleCount++;
                int minTransition = cyclesToRun;
                int maxTransition = 0;
                int totalTransition = 0;
                int entryIndex = 0;
                for (String transionTablePair : transionTable.keySet()) {
                    final Integer transionCount = transionTable.get(transionTablePair);
                    totalTransition += transionCount;
                    minTransition = (minTransition > transionCount) ? transionCount : minTransition;
                    maxTransition = (maxTransition < transionCount) ? transionCount : maxTransition;
//                    appendOutput(transionTablePair + " : " + transionCount);
                    transitionTableValue(0, entryIndex, transionTablePair);
                    transitionTableValue(1, entryIndex, transionCount.toString());
                    entryIndex++;
                }
                outputTableValue(0, 0, "transionTableSize: " + transionTable.size());
                outputTableValue(0, 1, "minTransition: " + minTransition);
                outputTableValue(0, 2, "maxTransition: " + maxTransition);
                outputTableValue(0, 3, "totalTransition: " + totalTransition);
                outputTableValue(0, 4, "expectedTransition: " + (totalTransition / transionTable.size()));
                outputTableValue(0, 5, "uniqueStimuliCount: " + stimuliSet.size());
//                outputTableValue(0, 6, "cyclesRun: " + (cyclesToRun) + " uniqueCount: " + calculatedStimuliSet.size());
                if (sampleCount < cyclesToRun) {
                    schedule(1);
                } else {
                    for (String uniqueStimulus : stimuliSet) {
                        appendOutput(uniqueStimulus);
                    }
                }
            }
        };
        timer.schedule(1);
    }

    public abstract void appendOutput(String outputString);

    public abstract void sampleCount(int outputString);

    public abstract void uniqueCount(int outputString);

    public abstract void appendUniqueStimuliList(String outputString);

    public abstract void transitionTableValue(int column, int row, String value);

    public abstract void outputTableValue(int column, int row, String value);
}
