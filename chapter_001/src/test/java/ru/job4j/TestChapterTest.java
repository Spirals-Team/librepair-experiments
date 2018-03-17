package ru.job4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author Hincu Andrei (andreih1981@gmail.com)on 01.09.2017.
 * @version $Id$.
 * @since 0.1.
 */
public class TestChapterTest {
    /**
     * Test.
     */
    @Test
    public void whenWordConteinsSubwordThenTrue() throws InterruptedException {
        TestChapter testChapter = new TestChapter();
        boolean rezultTest = testChapter.contains("Привет", "иве");
        boolean ex = true;
        assertThat(rezultTest, is(ex));
        Long a = 111L;
        --a;
        System.out.println(a);

        Thread t = new Thread(()-> System.out.println("Thread end"));
        synchronized (t) {
            t.start();
            t.wait();
        }
        System.out.println("Main end");
        List list = new ArrayList<Long>();
        list.add(1);
        list.add("1");
        System.out.println(list.size());

    }
}
