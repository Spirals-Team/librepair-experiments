/*
 * Copyright (C) 2015 Max Planck Institute for Psycholinguistics, Nijmegen
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
package nl.mpi.tg.eg.frinex;

import android.content.Context;
import java.io.File;
import java.io.IOException;

/**
 * @since Dec 10, 2015 3:24:50 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public interface AudioRecorder {

    public String startRecording(final File outputDirectory, Context context) throws IOException;

    public void stopRecording() throws IOException;

    public long getTime();

    public boolean isRecording();

    public void pauseRecorder();

    public void resumeRecorder();

    public void terminateRecorder();
}
