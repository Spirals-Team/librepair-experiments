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
package nl.mpi.tg.eg.experiment.client.listener;

import com.google.gwt.core.client.Duration;
import nl.mpi.tg.eg.frinex.common.listener.TimedStimulusListener;

/**
 * @since Aug 10, 2018 3:10:31 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public class HabituationParadigmListener extends TriggerListener {

    private final boolean isSingleShow;
    private final int thresholdMs;
    private final int maximumShows;
    private int showCounter = 0;
    private Duration lastTrigger = null;

    public HabituationParadigmListener(String listenerId, int threshold, int maximum, TimedStimulusListener triggerListener, final boolean notFirstItem) {
        super(listenerId, threshold, maximum, triggerListener);
        thresholdMs = threshold;
        maximumShows = maximum;
        isSingleShow = notFirstItem;
    }

    @Override
    public void trigger() {
        if (lastTrigger == null) {
            showCounter++;
            lastTrigger = new Duration();
        }
    }

    @Override
    public void reset() {
        if (lastTrigger != null) {
            final int elapsedMillis = lastTrigger.elapsedMillis();
            lastTrigger = null;
            if (elapsedMillis > thresholdMs) {
                if (isSingleShow || showCounter > maximumShows) {
                    triggerListener.postLoadTimerFired();
                }
            }
        }
    }
}
