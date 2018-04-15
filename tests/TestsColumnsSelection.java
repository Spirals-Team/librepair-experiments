
import devopsproject.DataFrame;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestsColumnsSelection {

    private DataFrame df;

    public TestsColumnsSelection() {
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
    public void locV1() {
        System.out.println("LOCV1 :");
        System.out.println("-----------------------------");
        df.loc("Nom").show();
        System.out.println("-----------------------------");
    }

    @Test
    public void locV2() {
        System.out.println("LOCV2 :");
        System.out.println("-----------------------------");
        df.loc(new ArrayList<>(Arrays.asList("Age", "Nom"))).show();
        System.out.println("-----------------------------");
    }

    @Test
    public void locV3() {
        System.out.println("LOCV3 :");
        System.out.println("-----------------------------");
        df.loc("Age", "Nom", "Lola").show();
        System.out.println("-----------------------------");
    }

    @Test
    public void locV41() {
        System.out.println("LOCV41 :");
        System.out.println("-----------------------------");
        df.loc("Nom", "Lola").show();
        System.out.println("-----------------------------");
    }

    @Test
    public void locV42() {
        System.out.println("LOCV42 :");
        System.out.println("-----------------------------");
        df.loc("Lola", "Nom").show();
        System.out.println("-----------------------------");
    }

    @Test(expected = IllegalArgumentException.class)
    public void locNoSuchLabelExceptionV1() {
        df.loc("NoSuchLabel");
    }

    @Test(expected = IllegalArgumentException.class)
    public void locNoSuchLabelExceptionV2() {
        df.loc(new ArrayList<>(Arrays.asList("Age", "Nom", "Prénom")));
    }

    @Test(expected = IllegalArgumentException.class)
    public void locNoSuchLabelExceptionV3() {
        df.loc("Age", "Nom", "Prénom");
    }

    @Test(expected = IllegalArgumentException.class)
    public void locNoSuchLabelExceptionV41() {
        df.loc("Age", "Prénom");
    }

    @Test(expected = IllegalArgumentException.class)
    public void locNoSuchLabelExceptionV42() {
        df.loc("Prénom", "Nom");
    }
}
