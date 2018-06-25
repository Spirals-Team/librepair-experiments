/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
