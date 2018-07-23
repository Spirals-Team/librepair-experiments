/*
 * Copyright (C) 2016 Max Planck Institute for Psycholinguistics, Nijmegen
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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import nl.mpi.tg.eg.experiment.client.listener.CurrentStimulusListener;
import nl.mpi.tg.eg.frinex.common.StimuliProvider;
import nl.mpi.tg.eg.frinex.common.listener.TimedStimulusListener;
import nl.mpi.tg.eg.frinex.common.model.Stimulus;

/**
 * @since Jul 23, 2016 2:14:16 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public class MatchingStimuliGroup {

    private final Stimulus correctStimulus;
    private int stimulusIndex;
    private final List<Stimulus> stimulusArray;
    final boolean randomise;
    final CurrentStimulusListener hasMoreStimulusListener;
    final TimedStimulusListener endOfStimulusListener;

    public MatchingStimuliGroup(final Stimulus correctStimulus, final List<Stimulus> stimuliArray, boolean randomise, CurrentStimulusListener hasMoreStimulusListener, TimedStimulusListener endOfStimulusListener) {
        this.correctStimulus = correctStimulus;
        this.randomise = randomise;
        this.hasMoreStimulusListener = hasMoreStimulusListener;
        this.endOfStimulusListener = endOfStimulusListener;
        stimulusIndex = -1;
        if (!randomise) {
            this.stimulusArray = stimuliArray;
        } else {
            this.stimulusArray = new ArrayList<>();
            while (!stimuliArray.isEmpty()) {
                final int nextIndex = (randomise) ? new Random().nextInt(stimuliArray.size()) : 0;
                stimulusArray.add(stimuliArray.remove(nextIndex));
            }
        }
    }

    public int getStimulusCount() {
        return stimulusArray.size();
    }

    public boolean isCorrect(Stimulus testableStimulus) {
        return correctStimulus.equals(testableStimulus);
    }

    public boolean getNextStimulus(final StimuliProvider stimulusProvider) {
        stimulusIndex++;
        // todo: based on what was in setCurrentStimulus, this would not work
        // stimulusProvider.setCurrentStimulus((stimulusArray.size() <= stimulusIndex) ? correctStimulus : stimulusArray.get(stimulusIndex));
        return (stimulusArray.size() > stimulusIndex);
    }

    public void showNextStimulus(final StimuliProvider stimulusProvider) {
        if ((stimulusArray.size() <= stimulusIndex)) {
            endOfStimulusListener.postLoadTimerFired();
        } else {
            hasMoreStimulusListener.postLoadTimerFired(stimulusProvider, stimulusArray.get(stimulusIndex));
        }
    }
}
