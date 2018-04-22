package ar.edu.undav.semillero.domain.entity;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;

public class InterviewTest {

	@Test
	public void testCreation() {
		Student student = new Student();
		Company company = new Company();
		String comments = "ok";
        Interview interview = new Interview(student, company, comments);
		Assert.assertEquals(student, interview.getStudent());
		Assert.assertEquals(company, interview.getCompany());
        Assert.assertEquals(LocalDate.now(), interview.getDate());
		Assert.assertEquals(comments, interview.getComments());
	}
}
