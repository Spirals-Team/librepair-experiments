package spoon.testing;


public class FileAssertTest {
    public static final java.lang.String PATH = "./src/test/java/spoon/testing/testclasses/";

    @org.junit.Test
    public void testEqualsBetweenTwoSameFile() throws java.lang.Exception {
        final java.lang.String actual = (spoon.testing.FileAssertTest.PATH) + "Foo.java";
        spoon.testing.Assert.assertThat(actual).isEqualTo(actual);
    }

    @org.junit.Test(expected = java.lang.AssertionError.class)
    public void testEqualsBetweenTwoDifferentFile() throws java.lang.Exception {
        spoon.testing.Assert.assertThat(((spoon.testing.FileAssertTest.PATH) + "Foo.java")).isEqualTo(((spoon.testing.FileAssertTest.PATH) + "Bar.java"));
    }
}

