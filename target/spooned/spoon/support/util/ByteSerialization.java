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

