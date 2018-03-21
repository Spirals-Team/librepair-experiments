package nl.mpi.tg.eg.frinex;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import android.content.Context;
import android.media.MediaScannerConnection;

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
/**
 * @since Jul 31, 2017 11:25:59 AM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public class StimuliJsonWriter {

    private final File outputDirectory;
    private static final String JSON_SUFFIX = ".json";

    public StimuliJsonWriter(final File outputDirectory) {
        this.outputDirectory = outputDirectory;
        System.out.println("JsonWriter: " + outputDirectory.getAbsolutePath());
    }

    public boolean writeJsonFile(Context context, String stimulusId, String stimuliData) throws IOException {
        System.out.println("writeJsonFile: " + stimulusId + JSON_SUFFIX);
        if (!outputDirectory.exists()) {
            outputDirectory.mkdirs();
        }
        final File csvFile = new File(outputDirectory, stimulusId + JSON_SUFFIX);
        final FileWriter jsonFileWriter = new FileWriter(csvFile, false);
        jsonFileWriter.write(stimuliData);
        jsonFileWriter.close();
        MediaScannerConnection.scanFile(context, new String[]{csvFile.getAbsolutePath()}, null, null);
        return true;
    }
}
