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

import com.google.gwt.user.client.Timer;
import java.util.Date;

/**
 * @since Jul 10, 2018 12:10:57 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public abstract class TimerListner {

    private Timer timer = null;

    public void clearTimer() {
        if (timer != null) {
            timer.cancel();
        }
    }

    public void startTimer(int msToEvent) {
        if (timer != null) {
            timer.cancel();
        }
        timer = new Timer() {
            @Override
            public void run() {
                cancel();
                timerTriggered();
            }
        };
        final long remainingMs = msToEvent - getTimerValue();
        timer.schedule((remainingMs > 0) ? (int) remainingMs : 1); // make sure the timer fires after this call but never gets a negative value
    }

    public long getTimerValue() {
        return new Date().getTime() - getInitialTimerStartMs();
    }

    abstract public void timerTriggered();

    abstract public long getInitialTimerStartMs();
}
