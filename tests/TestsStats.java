
import devopsproject.DataFrame;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

@SuppressWarnings("rawtypes")
public class TestsStats {

    private DataFrame df;

    public TestsStats() {
        df = new DataFrame("tests/resources/test.csv", ",");
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void meanColumn() {
        System.out.println("MEAN COLUMN");
        System.out.println("-----------------------------");
        System.out.println(df.meanColumn("Age"));
        System.out.println("-----------------------------");
    }

    @Test
    public void meanColumnFloat() {
        System.out.println("MEAN COLUMN FLOAT");
        df = new DataFrame("tests/resources/test1.csv", ",");
        System.out.println("-----------------------------");
        System.out.println(df.meanColumn("Float"));
        System.out.println("-----------------------------");
    }

    @Test(expected = IllegalArgumentException.class)
    public void meanColumnNoSuchLabelException() {
        df.meanColumn("NoSuchLabel");
    }

    @Test(expected = IllegalArgumentException.class)
    public void meanColumnNotNumericException() {
        df.meanColumn("Nom");
    }

    @Test
    public void minColumn() {
        System.out.println("MIN COLUMN");
        System.out.println("-----------------------------");
        System.out.println(df.minColumn("Age"));
        System.out.println("-----------------------------");
    }

    @Test(expected = IllegalArgumentException.class)
    public void minColumnNoSuchLabelException() {
        df.minColumn("NoSuchLabel");
    }

    @Test(expected = IllegalArgumentException.class)
    public void minColumnNotComparableException() {
        Object o1 = new Object();
        Object o2 = new Object();
        Object o3 = new Object();
        List<Object> column = Arrays.asList(o1, o2, o3);
        List<List> elements = new ArrayList<>();
        elements.add(column);
        String[] labels = {"column"};
        df = new DataFrame(labels, elements);
        df.minColumn("column");
    }

    @Test(expected = IllegalArgumentException.class)
    public void minColumnNotComparableExceptionV2() {
        int i1 = 3;
        int i2 = 2;
        float f3 = 1.0f;
        List<Object> column = Arrays.asList(i1, i2, f3);
        List<List> elements = new ArrayList<>();
        elements.add(column);
        String[] labels = {"column"};
        df = new DataFrame(labels, elements);
        df.minColumn("column");
    }

    @Test
    public void maxColumn() {
        System.out.println("MAX COLUMN");
        System.out.println("-----------------------------");
        System.out.println(df.maxColumn("Age"));
        System.out.println("-----------------------------");
    }

    @Test(expected = IllegalArgumentException.class)
    public void maxColumnNoSuchLabelException() {
        df.maxColumn("NoSuchLabel");
    }

    @Test(expected = IllegalArgumentException.class)
    public void maxColumnNotComparableException() {
        Object o1 = new Object();
        Object o2 = new Object();
        Object o3 = new Object();
        List<Object> column = Arrays.asList(o1, o2, o3);
        List<List> elements = new ArrayList<>();
        elements.add(column);
        String[] labels = {"column"};
        df = new DataFrame(labels, elements);
        df.maxColumn("column");
    }

    @Test(expected = IllegalArgumentException.class)
    public void maxColumnNotComparableExceptionV2() {
        int i1 = 3;
        int i2 = 2;
        float f3 = 1.0f;
        List<Object> column = Arrays.asList(i1, i2, f3);
        List<List> elements = new ArrayList<>();
        elements.add(column);
        String[] labels = {"column"};
        df = new DataFrame(labels, elements);
        df.maxColumn("column");
    }
}
