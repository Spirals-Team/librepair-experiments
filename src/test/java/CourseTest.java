import app.AcademicYear;
import app.Course;
import app.LearningOutcome;
import app.Program;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CourseTest {

    Course course1;
    Course course2;


    @Before
    public void setUp() throws Exception {
        course1 = new Course("NameTest","DescTest", AcademicYear.YEAR_1);
        course2 = new Course("NameTest");
    }
    @Test
    public void testEmptyDescriptionIfNotProvided() throws Exception {
        assertEquals(null, course2.getDescription());
        course2.setDescription("DescTest2");
    }
    @Test
    public void testSetYear() throws Exception {
        course1.setYear(AcademicYear.YEAR_2);
        assertEquals(AcademicYear.YEAR_2, course1.getYear());
    }

    @Test
    public void testAddProgram() throws Exception{
        course1.addProgram(new Program("ProgTest", "ProgTest"));
        assertEquals("ProgTest", course1.getPrograms().get(0).getName());
    }

    @Test
    public void testAddLearningOutcome() throws Exception{
        course1.addLearningOutcome(new LearningOutcome("LoTest", "LoTest"));
        assertEquals("LoTest", course1.getLearningOutcomes().get(0).getName());
    }

}