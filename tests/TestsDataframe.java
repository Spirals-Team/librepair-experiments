
import devopsproject.DataFrame;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

@SuppressWarnings("rawtypes")
public class TestsDataframe {

    private DataFrame df;

    public TestsDataframe() {

    }

    @Before
    public void setUp() {

    }

    @After
    public void tearDown() {

    }

    @Test
    public void dataframeCreationEmptyConstructor() {
        df = new DataFrame();
    }

    @Test
    public void dataframeCreationLists() {
        String[] labels = new String[3];
        labels[0] = String.valueOf('a');
        labels[1] = String.valueOf('b');
        labels[2] = String.valueOf('c');

        List<String> element1 = new ArrayList<>();
        element1.add("s1");
        element1.add("s2");
        element1.add("s3");

        List<Integer> element2 = new ArrayList<>();
        element2.add(1);
        element2.add(2);
        element2.add(3);

        List<Float> element3 = new ArrayList<>();
        element3.add((float) 1.4);
        element3.add((float) 2.4);
        element3.add((float) 3.5);

        List<List> elements = new ArrayList<>();
        elements.add(element1);
        elements.add(element2);
        elements.add(element3);

        df = new DataFrame(labels, elements);
        System.out.println("");
        df.head(2);
        System.out.println("");
        df.show();
        System.out.println("");
        df.tail(2);
        System.out.println("");
        df.head("b", 2);
        System.out.println("");
        df.tail("b", 2);
        System.out.println("");
    }

    @Test
    public void dataframeCreationFile() throws IOException {
        df = new DataFrame("tests/resources/test1.csv", ",");
        System.out.println("");
        df.head(5);
        System.out.println("");
        df.show();
        System.out.println("");
        df.tail(5);
        System.out.println("");
        df.head("Age", 5);
        System.out.println("");
        df.tail("Age", 5);
        System.out.println("");
        System.out.println(df.meanColumn("Lola"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void dataframeNotCSVFileException() throws IOException {
        df = new DataFrame("tests/src/TestDataFrame.java", ",");
    }

    @Test(expected = IllegalArgumentException.class)
    public void dataframeUncorrectFormatDataV1() throws IOException {
        df = new DataFrame("tests/resources/uncorrectFormat.csv", ",");
    }

    @Test(expected = IllegalArgumentException.class)
    public void dataframeUncorrectFormatDataV2() throws IOException {
        df = new DataFrame("tests/resources/uncorrectFormat2.csv", ",");
    }

    @Test(expected = IllegalArgumentException.class)
    public void dataframeUncorrectFormatDataV3() throws IOException {
        df = new DataFrame("tests/resources/uncorrectFormat3.csv", ",");
    }

    @Test
    public void dataframeSize() {
        Assert.assertEquals(10, new DataFrame("tests/resources/test.csv", ",").size());
        Assert.assertEquals(22, new DataFrame("tests/resources/test1.csv", ",").size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void headLinesNumberExceptionV1() {
        new DataFrame("tests/resources/test1.csv", ",").head(10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void headLinesNumberExceptionV2() {
        new DataFrame("tests/resources/test1.csv", ",").head(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void tailLinesNumberExceptionV1() {
        new DataFrame("tests/resources/test1.csv", ",").tail(10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void tailLinesNumberExceptionV2() {
        new DataFrame("tests/resources/test1.csv", ",").tail(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void headColumnLinesNumberExceptionV1() {
        new DataFrame("tests/resources/test1.csv", ",").head("Nom", 10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void headColumnLinesNumberExceptionV2() {
        new DataFrame("tests/resources/test1.csv", ",").head("Nom", -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void tailColumnLinesNumberExceptionV1() {
        new DataFrame("tests/resources/test1.csv", ",").tail("Nom", 10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void tailColumnLinesNumberExceptionV2() {
        new DataFrame("tests/resources/test1.csv", ",").tail("Nom", -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void headColumnNoSuchLabelException() {
        new DataFrame("tests/resources/test1.csv", ",").head("NoSuchLabel", 5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void tailColumnNoSuchLabelException() {
        new DataFrame("tests/resources/test1.csv", ",").tail("NoSuchLabel", 5);
    }

}
