package io.github.oliviercailloux.y2018.teach_spreadsheets.odf;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.odftoolkit.simple.SpreadsheetDocument;
import org.odftoolkit.simple.table.Cell;
import org.odftoolkit.simple.table.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.oliviercailloux.y2018.teach_spreadsheets.courses.Course;

/**
 * This class allow you to read a file of courses for Dauphine University
 * teachers from an instance of {@link ODSReader}
 * 
 * @author Victor CHEN (Kantoki), Louis FONTAINE (fontlo15)
 * @version Version 1.0 Last Update : 13/05/2018.
 * 
 * 
 * 
 */
public class ReadCourses {

	private final static String COURSTD = "CMTD";
	private final static String COURSTP = "CMTP";
	private final static String TD = "TD";
	private final static String TP = "TP";
	private final static int NBATTRIBUTE = 8;

	private final static Logger LOGGER = LoggerFactory.getLogger(ReadCourses.class);

	/**
	 * Assuming that the tables of courses starts at cells startCell1 and startCell2
	 * for each sheet
	 */
	private final static String startCell1 = "B4";
	/**
	 * Assuming that the tables of courses starts at cells startCell1 and startCell2
	 * for each sheet
	 */
	private final static String startCell2 = "P4";

	private ODSReader reader;

	public ReadCourses(ODSReader reader) {
		this.reader = Objects.requireNonNull(reader);
	}

	/**
	 * This method returns a List of {@link Course} from the ODS file read by the
	 * {@link ODSReader} in the attribute and the cell Position in argument.
	 * 
	 * @param cellPosition:
	 *            the position of the first Cell of the Table where are the
	 *            courses(ex: B2)
	 * 
	 */
	public List<Course> readCoursesFromCell(String cellPosition) {
		List<Course> courses = new ArrayList<>();

		Table tableCourante = reader.getSheet();

		String yearOfStudy = tableCourante.getTableName();

		Cell startCell = tableCourante.getCellByPosition(cellPosition);

		int startCellColumnIndex = startCell.getColumnIndex();
		int startCellRowIndex = startCell.getRowIndex();

		Cell actualCell = startCell;

		for (int i = startCellRowIndex; i < tableCourante.getRowCount(); i++) {
			Course course = new Course();
			course.setYearOfStud(yearOfStudy);
			int j = -1;
			for (j = startCellColumnIndex; j < startCellColumnIndex + NBATTRIBUTE; j++) {
				actualCell = tableCourante.getCellByPosition(j, i);

				String cellText = actualCell.getDisplayText().replaceAll(",", ".");

				if ("".equals(cellText) && j == startCellColumnIndex) {
					break;
				}

				int columnRelativeNumber = j - startCellColumnIndex;
				switch (columnRelativeNumber) {
				case 0:
					course.setName(cellText);
					break;
				case 1:
					course.setapogeeCode(cellText);
					break;
				case 2:
					course.setSupervisor(cellText);
					break;
				case 3:
					course.setTeachers(cellText);
					break;
				case 4:
					if (this.reader.isDiagonalBorder(actualCell) || "".equals(cellText)) {
						course.setCM_Hour(0);
					} else {
						String hourStr = cellText;
						String[] hourTab = hourStr.split("h");
						course.setCM_Hour(Double.parseDouble(hourTab[0]));
					}
					break;

				case 5:
					if (this.reader.isDiagonalBorder(actualCell) || "".equals(cellText)) {
						course.setTD_Hour(0);
						course.setCMTD_Hour(0);
					} else {
						String hourStr = cellText;
						String[] hourTab = hourStr.split("h");
						if (hourStr.contains(COURSTD)) {
							course.setCMTD_Hour(Double.parseDouble(hourTab[0]));
						} else if (hourStr.contains(TD)) {
							course.setTD_Hour(Double.parseDouble(hourTab[0]));
						}
					}
					break;
				case 6:
					if (this.reader.isDiagonalBorder(actualCell) || "".equals(cellText)) {
						course.setTP_Hour(0);
						course.setCMTP_Hour(0);
					} else {
						String hourStr = cellText;
						String[] hourTab = hourStr.split("h");
						if (hourStr.contains(COURSTP)) {
							course.setCMTP_Hour(Double.parseDouble(hourTab[0]));
						} else if (hourStr.contains(TP)) {
							course.setTP_Hour(Double.parseDouble(hourTab[0]));
						}

					}
					break;
				case 7:
					if (this.reader.isDiagonalBorder(actualCell) || "".equals(cellText)) {
						course.setGrpNbr("");
					} else {
						course.setGrpNbr(cellText);
					}
					break;
				default:
					break;
				}
			}
			if (j == startCellColumnIndex) {
				break;
			}
			courses.add(course);
		}

		return courses;
	}

	/**
	 * This method returns a List of {@link Course} from the ODS file read by the
	 * {@link ODSReader} in the attribute and a sheet of the file.
	 * 
	 * This method only works with specific sheet of the spreadsheet document, with
	 * a specific format.
	 * 
	 * @see the template in resources
	 * 
	 * @param sheet:
	 *            the sheet of the spreadsheet document where you want to read the
	 *            courses.
	 * 
	 */
	public List<Course> readCoursesFromSheet(Table sheet) {
		List<Course> courses = new ArrayList<>();
		reader.setSheet(sheet);

		Table actualSheet = reader.getSheet();

		// if the table format is correct
		if (actualSheet.getCellByPosition("B3").getDisplayText().equals("Matière")) {
			courses.addAll(this.readCoursesFromCell(startCell1));
			courses.addAll(this.readCoursesFromCell(startCell2));
			LOGGER.info("Table " + actualSheet.getTableName() + " courses have been added successfully\n");
		} else {
			LOGGER.info("Table " + actualSheet.getTableName() + " doesn't have courses or the table format is wrong\n");
		}

		return courses;
	}

	@SuppressWarnings("resource")
	public List<Course> readCourses() {
		List<Course> courses = new ArrayList<>();
		SpreadsheetDocument document = reader.getDocument();

		List<Table> tables = document.getTableList();

		for (Table table : tables) {
			courses.addAll(this.readCoursesFromSheet(table));
		}

		return courses;
	}

	public static void main(String[] args) throws Exception {
		try (InputStream is = ReadCourses.class.getClassLoader().getResourceAsStream("Saisie_voeux_dauphine.ods")) {
			try (SpreadsheetDocument sd = SpreadsheetDocument.loadDocument(is)) {
				String yearOfStudy = "L3_Informatique";
				ODSReader odsR = new ODSReader(sd, yearOfStudy);

				List<Course> courses = new ArrayList<>();

				ReadCourses reader = new ReadCourses(odsR);
				courses = reader.readCourses();

				for (Course course : courses) {
					System.out.println(course + "\n");
				}
			}

		}
	}
}
