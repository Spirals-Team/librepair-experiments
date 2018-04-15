
import devopsproject.DataFrame;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestsLinesSelection {

    private final DataFrame df;

    public TestsLinesSelection() {
        df = new DataFrame("tests/resources/test1.csv", ",");
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

    // Test Columns Selection
    @Test
    public void ilocV1() {
        System.out.println("ILOCV1 :");
        System.out.println("-----------------------------");
        df.iloc(5).show();
        System.out.println("-----------------------------");
    }

    @Test
    public void ilocV2() {
        System.out.println("ILOCV2 :");
        System.out.println("-----------------------------");
        df.iloc(new ArrayList<>(Arrays.asList(5, 2, 3))).show();
        System.out.println("-----------------------------");
    }

    @Test
    public void ilocV3() {
        System.out.println("ILOCV3 :");
        System.out.println("-----------------------------");
        df.iloc(5, 2, 3).show();
        System.out.println("-----------------------------");
    }

    @Test
    public void ilocV41() {
        System.out.println("ILOCV41 :");
        System.out.println("-----------------------------");
        df.iloc(2, 5).show();
        System.out.println("-----------------------------");
    }

    @Test
    public void ilocV42() {
        System.out.println("ILOCV42 :");
        System.out.println("-----------------------------");
        df.iloc(6, 0).show();
        System.out.println("-----------------------------");
    }

    @Test(expected = IllegalArgumentException.class)
    public void ilocIndexOutOfBoundExceptionV11() {
        df.iloc(10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ilocIndexOutOfBoundExceptionV12() {
        df.iloc(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ilocIndexOutOfBoundExceptionV21() {
        df.iloc(new ArrayList<>(Arrays.asList(5, 2, 10)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void ilocIndexOutOfBoundExceptionV22() {
        df.iloc(new ArrayList<>(Arrays.asList(5, 2, -1)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void ilocIndexOutOfBoundExceptionV31() {
        df.iloc(5, 2, -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ilocIndexOutOfBoundExceptionV32() {
        df.iloc(5, 2, -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ilocIndexOutOfBoundExceptionV41() {
        df.iloc(0, 10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ilocIndexOutOfBoundExceptionV411() {
        df.iloc(0, -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ilocIndexOutOfBoundExceptionV42() {
        df.iloc(10, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ilocIndexOutOfBoundExceptionV421() {
        df.iloc(-1, 0);
    }
}
