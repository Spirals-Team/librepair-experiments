package parser;

import java.io.InputStream;
import java.io.OutputStream;

public interface Converter <T> {

    InputStream to(T fromObject);
    T from(InputStream toObject);





}
