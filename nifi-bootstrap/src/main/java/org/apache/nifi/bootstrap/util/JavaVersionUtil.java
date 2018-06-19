package org.apache.nifi.bootstrap.util;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public class JavaVersionUtil {
    public static int getMajorVersion(Class c) throws IOException {
        String pathToClass = "/" + c.getPackage().getName().replaceAll("\\.", "/") + "/" + c.getSimpleName() + ".class";
        InputStream classInputStream = c.getResourceAsStream(pathToClass);
        final DataInputStream input = new DataInputStream(classInputStream);
        input.skipBytes(4); // Skip "magic number" at the beginning of all classes
        input.skipBytes(2); // Skip minor version number
        return input.readUnsignedShort(); // return major version number
    }
}
