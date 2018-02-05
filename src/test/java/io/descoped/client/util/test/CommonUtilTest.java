package io.descoped.client.util.test;

import io.descoped.client.util.CommonUtil;
import org.junit.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.spy;

/**
 * @author Ove Ranheim (oranheim@gmail.com)
 * @since 22/11/2017
 */
public class CommonUtilTest {

    private static final Logger log = LoggerFactory.getLogger(CommonUtilTest.class);

    @Test
    public void testClassloader() throws Exception {
        assertThat(CommonUtil.tccl()).isEqualTo(Thread.currentThread().getContextClassLoader());
    }

    @Test
    public void testStreams() throws Exception {
        OutputStream out = CommonUtil.getConsoleOutputStream();
        assertThat(out).toString().isEmpty();
        CommonUtil.closeConsoleOutputStream();
        assertThat(CommonUtil.getConsoleOutputStream()).toString().isEmpty();

        out = CommonUtil.newOutputStream();
        assertThat(out).toString().isEmpty();

        out.write("foo".getBytes());
        assertThat(out.toString()).isEqualTo("foo");

        InputStream in = new ByteArrayInputStream("bar".getBytes());
        out = CommonUtil.writeInputToOutputStream(in);
        assertThat(out.toString()).isEqualTo("bar");

        in = new ByteArrayInputStream("foobar".getBytes());
        CommonUtil.writeInputToOutputStream(in, out);
        assertThat(out.toString()).isEqualTo("barfoobar");

        CommonUtil.closeAndCreateNewOutputStream(out);
    }

    @Test
    public void testMockLog() throws Exception {
        assertThat(CommonUtil.fileSeparator).isNotBlank();

        CommonUtil mock = Mockito.mock(CommonUtil.class);
        spy(mock).info("foo");
        spy(mock).debug("foo");
        spy(mock).trace("foo");
        spy(mock).warn("foo");
        spy(mock).error("foo");

        assertThat(mock.getCurrentPath()).exists();
        assertThat(mock.currentPath()).isEqualTo(mock.getCurrentPath().toString());

        List untypedStrings = new ArrayList();
        untypedStrings.add("foo");
        untypedStrings.add("bar");
        untypedStrings.add("foobar");

        List<String> typedStrings = new ArrayList();
        typedStrings = CommonUtil.typedList(untypedStrings, String.class, typedStrings.getClass());
        assertThat(untypedStrings).isEqualTo(typedStrings);
    }

}
