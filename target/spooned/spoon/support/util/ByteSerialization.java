/**
 * Copyright (C) 2006-2017 INRIA and contributors
 * Spoon - http://spoon.gforge.inria.fr/
 *
 * This software is governed by the CeCILL-C License under French law and
 * abiding by the rules of distribution of free software. You can use, modify
 * and/or redistribute the software under the terms of the CeCILL-C license as
 * circulated by CEA, CNRS and INRIA at http://www.cecill.info.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the CeCILL-C License for more details.
 *
 * The fact that you are presently reading this means that you have had
 * knowledge of the CeCILL-C license and that you accept its terms.
 */
package spoon.support.util;


public class ByteSerialization {
    private ByteSerialization() {
    }

    public static byte[] serialize(java.lang.Object obj) throws java.io.IOException {
        byte[] serializedObject = null;
        java.io.ByteArrayOutputStream bo = new java.io.ByteArrayOutputStream();
        java.io.ObjectOutputStream so = new java.io.ObjectOutputStream(bo);
        so.writeObject(obj);
        so.flush();
        serializedObject = bo.toByteArray();
        so.close();
        return serializedObject;
    }

    public static java.lang.Object deserialize(byte[] serializedObject) throws java.lang.Exception {
        java.lang.Object objInput = null;
        java.io.ByteArrayInputStream bi = new java.io.ByteArrayInputStream(serializedObject);
        java.io.ObjectInputStream si = new java.io.ObjectInputStream(bi);
        objInput = si.readObject();
        si.close();
        return objInput;
    }
}

