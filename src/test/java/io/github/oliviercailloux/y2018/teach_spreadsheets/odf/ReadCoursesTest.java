package io.github.oliviercailloux.y2018.teach_spreadsheets.odf;

import java.io.InputStream;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.odftoolkit.simple.SpreadsheetDocument;

import io.github.oliviercailloux.y2018.teach_spreadsheets.courses.Course;

public class ReadCoursesTest {

	/**
	 * Test to check that the method iterates on all courses of the sheet
	 */
	@Test
	public void testReadCourses() throws Exception {
		try (InputStream is = ODSReaderTest.class.getClassLoader().getResourceAsStream("Saisie_voeux_dauphine.ods")) {
			try (SpreadsheetDocument sd = SpreadsheetDocument.loadDocument(is)) {
				String yearOfStudy = "L3_Informatique";
				ODSReader odsR = new ODSReader(sd, yearOfStudy);

				ReadCourses rd = new ReadCourses(odsR);
				List<Course> courses;
				courses = rd.readCoursesFromCell("B4");
				int nbCourses = courses.size();

				Assert.assertEquals(11, nbCourses);
			}
		}
	}

	/**
	 * Test to check that the method retrives the right courses
	 */
	@Test
	public void testSameCourseSheetAndReadCourses() throws Exception {
		try (InputStream is = ODSReaderTest.class.getClassLoader().getResourceAsStream("Saisie_voeux_dauphine.ods")) {
			try (SpreadsheetDocument sd = SpreadsheetDocument.loadDocument(is)) {
				String yearOfStudy = "L3_Informatique";
				ODSReader odsR = new ODSReader(sd, yearOfStudy);

				ReadCourses rd = new ReadCourses(odsR);
				List<Course> courses;
				courses = rd.readCoursesFromCell("B4");

				Course c1 = new Course();
				for (int i = 1; i < courses.size(); i++) {
					c1 = courses.get(0);
				}

				Course c2 = new Course();
				c2.setName("Pré-rentrée : Mise à niveau Probabilités");
				c2.setYearOfStud("L3_Informatique");
				c2.setapogeeCode("A3MANPRO");
				c2.setSupervisor("Mayag");
				c2.setTeachers("Mayag");
				c2.setCM_Hour(0);
				c2.setTD_Hour(0);
				c2.setCMTD_Hour(15);
				c2.setTP_Hour(0);
				c2.setCMTP_Hour(0);
				c2.setGrpNbr("0");

				Assertions.assertThat(c1).isEqualToComparingFieldByFieldRecursively(c2);
			}
		}
	}

}
