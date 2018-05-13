
package io.github.oliviercailloux.y2018.teach_spreadsheets.csv;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;

import io.github.oliviercailloux.y2018.teach_spreadsheets.courses.Course;

public class CsvFileReaderTest {

	@SuppressWarnings("unused")
	@Test
	public void testReadCourseCSVfile() {

		List<Course> expected = new ArrayList<>();

		Course course = new Course();
		course.setName("Statistiques");
		course.setapogeeCode("14");
		course.setYearOfStud("2008");
		course.setCM_Hour(8);
		course.setTD_Hour(4);
		course.setCMTD_Hour(8);
		course.setTP_Hour(8);
		course.setGrpNbr("8");

		expected.add(course);

		String filename = "src/test/resources/oneCourseTest.csv";

		List<Course> actual = new ArrayList<>();

		try {
			CsvFileReader.readCourseCSVfile(filename, actual);
		} catch (NumberFormatException e) {
			Assert.assertTrue(false);
		} catch (FileNotFoundException e) {
			Assert.assertTrue(false);
		} catch (IllegalArgumentException e) {
			Assert.assertTrue(false);
		} catch (IOException e) {
			Assert.assertTrue(false);
		}
		Assertions.assertThat(actual.get(0)).isEqualToComparingFieldByField(expected.get(0));
	}

}
