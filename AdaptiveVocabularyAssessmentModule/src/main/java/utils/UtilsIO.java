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
package utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.service.AdVocAsStimuliProvider;

/**
 *
 * @author olhshk
 */
public class UtilsIO {

   

    public static void writeCsvFileFastTrack(AdVocAsStimuliProvider provider, int stopBand, String outputDir, int averageNonWordPosition, int nonWordsPerBlock) throws IOException {
        long millis = System.currentTimeMillis();
        int blockSize = nonWordsPerBlock * averageNonWordPosition;
        String fileName = "Fast_track_test_stopped_at_band_" + stopBand + "_" + blockSize + "_" + millis + ".csv";
        System.out.println("writeCsvFile: " + outputDir + fileName);
        final File csvFile = new File(outputDir, fileName);
        String inhoud = provider.getStringFastTrack("", "\n", "", ";");
        BufferedWriter output = new BufferedWriter(new FileWriter(csvFile));
        output.write(inhoud);
        output.close();
    }

    public static void writeHtmlFileFastTrack(AdVocAsStimuliProvider provider, int stopBand, String outputDir, int averageNonWordPosition, int nonWordsPerBlock) throws IOException {
        long millis = System.currentTimeMillis();
        int blockSize = nonWordsPerBlock * averageNonWordPosition;
        String fileName = "Fast_track_test_stopped_at_band_" + stopBand + "_" + blockSize + "_" + millis + ".html";
        System.out.println("writeCsvFile: " + outputDir + fileName);
        final File htmlFile = new File(outputDir, fileName);
        String inhoud = provider.getStringFastTrack("<tr>", "</tr>", "<td>", "</td>");
        String htmlString = "<!DOCTYPE html><html><body><table border=1>" + inhoud + "</table></body></html>";
        BufferedWriter output = new BufferedWriter(new FileWriter(htmlFile));
        output.write(htmlString);
        output.close();
    }

    public static void writeCsvFileFineTuningHistory(AdVocAsStimuliProvider provider, String outputDir, String fileName) throws IOException {
        System.out.println("writeCsvFile: " + outputDir + fileName);
        final File csvFile = new File(outputDir, fileName);
        String inhoud = provider.getStringFineTuningHistory("", "\n", "", ";", "csv");
        BufferedWriter output = new BufferedWriter(new FileWriter(csvFile));
        output.write(inhoud);
        output.close();
    }

    public static void writeHtmlFileFineTuningHistory(AdVocAsStimuliProvider provider, String outputDir, String fileName) throws IOException {
        System.out.println("writeHtmlFile: " + outputDir + fileName);
        final File htmlFile = new File(outputDir, fileName);
        String inhoud = provider.getStringFineTuningHistory("<tr>", "<tr>", "<td>", "<td>", "html");
        BufferedWriter output = new BufferedWriter(new FileWriter(htmlFile));
        String htmlString = "<!DOCTYPE html><html><body><table border=1>" + inhoud + "</table></body></html>";
        output.write(htmlString);
        output.close();
    }

    public static void writeHtmlFullUserResults(AdVocAsStimuliProvider provider, String outputDir, String fileName) throws IOException {
        System.out.println("write full history htm file: " + outputDir + fileName);
        final File htmlFile = new File(outputDir, fileName);
        StringBuilder htmlStringBuilder = new StringBuilder();
        String report = provider.getHtmlStimuliReport();
        htmlStringBuilder.append("<!DOCTYPE html><html><body>");
        htmlStringBuilder.append(report);
        htmlStringBuilder.append("</body></html>");
        BufferedWriter output = new BufferedWriter(new FileWriter(htmlFile));
        output.write(htmlStringBuilder.toString());
        output.close();
    }

    public static void writeCsvMapAsOneCsv(AdVocAsStimuliProvider provider, String outputDir, String fileName) throws IOException {
        System.out.println("write full history htm file: " + outputDir + fileName);
        final File txtFile = new File(outputDir, fileName);

        StringBuilder txtStringBuilder = new StringBuilder();
        Map<String, String> table = provider.getStimuliReport("user_summary");
        for (String rowName : table.keySet()) {
            txtStringBuilder.append(rowName).append(";").append(table.get(rowName)).append("\n");
        }
        txtStringBuilder.append("\n\n");
        table = provider.getStimuliReport("fast_track");
        for (String rowName : table.keySet()) {
            txtStringBuilder.append(rowName).append(";").append(table.get(rowName)).append("\n");
        }
        txtStringBuilder.append("\n\n");
        table = provider.getStimuliReport("fine_tuning");
        for (String rowName : table.keySet()) {
            txtStringBuilder.append(rowName).append(";").append(table.get(rowName)).append("\n");
        }

        BufferedWriter output = new BufferedWriter(new FileWriter(txtFile));
        output.write(txtStringBuilder.toString());
        output.close();
    }

}
