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

import nl.mpi.tg.eg.frinex.common.listener.TimedStimulusListener;

/**
 * @since Jan 30, 2018 4:29:59 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public class TriggerListener {

    final private String listenerId;
    final private int threshold;
    final private int maximum;
    final protected TimedStimulusListener triggerListener;
    private int maximumCounter = 0;
    private int thresholdCounter = 0;

    public TriggerListener(String listenerId, int threshold, int maximum, TimedStimulusListener triggerListener) {
        this.listenerId = listenerId;
        this.threshold = threshold;
        this.maximum = maximum;
        this.triggerListener = triggerListener;
    }

    public String getListenerId() {
        return listenerId;
    }

    public void reset() {
    }

    public void trigger() {
        thresholdCounter++;
        boolean noMaximum = maximum <= 0;
        if (thresholdCounter >= threshold && (noMaximum || maximumCounter <= maximum)) {
            maximumCounter++;
            triggerListener.postLoadTimerFired();
        }
    }
}
