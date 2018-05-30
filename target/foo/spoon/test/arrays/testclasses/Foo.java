package spoon.test.arrays.testclasses;


public class Foo {
    public byte[] readByteArray(long byteCount) throws java.io.EOFException {
        byte[] result = new byte[((int) (byteCount))];
        return result;
    }

    java.lang.String[] s = new java.lang.String[]{ "--help" };

    protected static final int[][] grad4 = new int[][]{ new int[]{ 0, 1, 1, 1 }, new int[]{ 0, 1, 1, -1 }, new int[]{ 0, 1, -1, 1 }, new int[]{ 0, 1, -1, -1 }, new int[]{ 0, -1, 1, 1 }, new int[]{ 0, -1, 1, -1 }, new int[]{ 0, -1, -1, 1 }, new int[]{ 0, -1, -1, -1 }, new int[]{ 1, 0, 1, 1 }, new int[]{ 1, 0, 1, -1 }, new int[]{ 1, 0, -1, 1 }, new int[]{ 1, 0, -1, -1 }, new int[]{ -1, 0, 1, 1 }, new int[]{ -1, 0, 1, -1 }, new int[]{ -1, 0, -1, 1 }, new int[]{ -1, 0, -1, -1 }, new int[]{ 1, 1, 0, 1 }, new int[]{ 1, 1, 0, -1 }, new int[]{ 1, -1, 0, 1 }, new int[]{ 1, -1, 0, -1 }, new int[]{ -1, 1, 0, 1 }, new int[]{ -1, 1, 0, -1 }, new int[]{ -1, -1, 0, 1 }, new int[]{ -1, -1, 0, -1 }, new int[]{ 1, 1, 1, 0 }, new int[]{ 1, 1, -1, 0 }, new int[]{ 1, -1, 1, 0 }, new int[]{ 1, -1, -1, 0 }, new int[]{ -1, 1, 1, 0 }, new int[]{ -1, 1, -1, 0 }, new int[]{ -1, -1, 1, 0 }, new int[]{ -1, -1, -1, 0 } };
}

