package io.github.oliviercailloux.y2018.teach_spreadsheets.csv;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.oliviercailloux.y2018.teach_spreadsheets.courses.Course;

/**
 * @author Victor CHEN (Kantoki), Davaux Tuan Nam (tuannamdavaux) File 1 -
 * @version Version 1.0 Last Update : 28/03/2018. This class allows you to write
 *          a list of courses in CSV format.
 * @see librarySource https://commons.apache.org/proper/commons-csv/
 */
public class CsvFileWriter {

	private final static Logger LOGGER = LoggerFactory.getLogger(CsvFileWriter.class);

	// Delimiter used in CSV file
	private static final String NEW_LINE_SEPARATOR = "\n";

	// CSV file header
	private static final Object[] FILE_HEADER = { "Name", "Apogee Code", "Year of Study", "CM hours", "TD hours",
			"CMTD hours", "TP hours", "CM groups number", "TD groups number", "CMTD groups number",
			"TP groups number" };

	public static void writeInCSV(List<Course> courses, String fileName) throws IOException {
		String fileLocation = System.getProperty("user.dir");

		// Create the CSVFormat object with "\n" as a record delimiter
		CSVFormat csvFileFormat = CSVFormat.EXCEL.withRecordSeparator(NEW_LINE_SEPARATOR);
		// initialize FileWriter object
		try (FileWriter fileWriter = new FileWriter(fileName)) {
			LOGGER.info("File " + fileName + " is ready to be writen");
			// initialize CSVPrinter object
			try (CSVPrinter csvFilePrinter = new CSVPrinter(fileWriter, csvFileFormat)) {
				LOGGER.info("File " + fileName + " is ready to be writen with CSVPrinter");
				// Create CSV file header
				csvFilePrinter.printRecord(FILE_HEADER);

				// Writing
				for (Course c : courses) {
					List<String> coursesRecord = new ArrayList<>();
					coursesRecord.add(c.getName());
					coursesRecord.add(c.getapogeeCode());
					coursesRecord.add(c.getYearOfStud());
					coursesRecord.add(String.valueOf(c.getCM_Hour()));
					coursesRecord.add(String.valueOf(c.getTD_Hour()));
					coursesRecord.add(String.valueOf(c.getCMTD_Hour()));
					coursesRecord.add(String.valueOf(c.getTP_Hour()));
					coursesRecord.add(c.getGrpNbr());

					csvFilePrinter.printRecord(coursesRecord);
				}
				LOGGER.info("\nYour courses have been successfully exported into a CSV File named " + fileName + " in "
						+ fileLocation + " !");

			}
		}

	}

}
