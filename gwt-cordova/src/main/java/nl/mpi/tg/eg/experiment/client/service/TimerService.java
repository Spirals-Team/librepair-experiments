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
package nl.mpi.tg.eg.experiment.client.service;

import java.util.HashMap;
import java.util.Set;
import nl.mpi.tg.eg.experiment.client.listener.TimerListner;
import nl.mpi.tg.eg.frinex.common.listener.TimedStimulusListener;

/**
 * @since Jul 10, 2018 4:21:15 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public class TimerService {

    final HashMap<String, TimerListner> timerListeners = new HashMap<>();

    public void clearAllTimers() {
        for (TimerListner listner : timerListeners.values()) {
            listner.clearTimer();
        }
    }

    public void startTimer(final long initialTimerStartMs, final int msToNext, final String listenerId, final TimedStimulusListener timeoutListener) {
        // this must first check for a saved timer start value and reload it, for cases where a user has refreshed the browser
        final TimerListner timerListner = new TimerListner() {
            @Override
            public void timerTriggered() {
                timeoutListener.postLoadTimerFired();
            }

            @Override
            public long getInitialTimerStartMs() {
                return initialTimerStartMs;
            }

        };
        timerListeners.put(listenerId, timerListner);
        timerListner.startTimer(msToNext);
    }

    public int getTimerValue(final String listenerId) {
        final TimerListner timerEntry = timerListeners.get(listenerId);
        if (timerEntry == null) {
            return -1;
        } else {
            return (int) timerEntry.getTimerValue();
        }
    }

    public void clearTimer(final String listenerId) {
        timerListeners.get(listenerId).clearTimer();
    }

    public Set<String> getTimerIds() {
        return timerListeners.keySet();
    }
}
