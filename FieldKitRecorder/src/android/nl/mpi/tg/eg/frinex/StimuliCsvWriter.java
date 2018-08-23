package nl.mpi.tg.eg.frinex;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import android.content.Context;
import android.media.MediaScannerConnection;

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
/**
 * @since June 5, 2018 17:40 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public class StimuliCsvWriter {

    private final File outputDirectory;
    private static final String CSV_SUFFIX = ".csv";

    public StimuliCsvWriter(final File outputDirectory) {
        this.outputDirectory = outputDirectory;
        System.out.println("CsvWriter: " + outputDirectory.getAbsolutePath());
    }

    public boolean writeCsvFile(Context context, final String userId, final String screenName, final int dataChannel, String eventTag, String tagValue1, String tagValue2, int eventMs) throws IOException {
        System.out.println("writeCsvFile: " + userId + CSV_SUFFIX);
        if (!outputDirectory.exists()) {
            outputDirectory.mkdirs();
        }
        final File csvFile = new File(outputDirectory, userId + CSV_SUFFIX);
        final FileWriter csvFileWriter = new FileWriter(csvFile, true);
        csvFileWriter.write(userId);
        csvFileWriter.write(",");
        csvFileWriter.write(screenName);
        csvFileWriter.write(",");
        csvFileWriter.write(dataChannel);
        csvFileWriter.write(",");
        csvFileWriter.write(eventTag);
        csvFileWriter.write(",");
        csvFileWriter.write(tagValue1);
        csvFileWriter.write(",");
        csvFileWriter.write(tagValue2);
        csvFileWriter.write(",");
        csvFileWriter.write(eventMs);
        csvFileWriter.write("\n");
        csvFileWriter.close();
        MediaScannerConnection.scanFile(context, new String[]{csvFile.getAbsolutePath()}, null, null);
        return true;
    }
}
