package de.sebastianmonzel.textextractor;

import java.io.InputStream;

public class AbstractTextExtractorTest {
    protected InputStream getInputStreamFromResource(String name) {
        return getClass().getClassLoader().getResourceAsStream(name);
    }
}
