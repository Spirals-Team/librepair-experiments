package model;

public class RawPackage {

    private byte[] data;

    public RawPackage(byte [] data){

        this.data = data;

    }

    public byte getByteAt(int offset){

        if(offset > data.length)
            throw new IndexOutOfBoundsException("Offset exceed packet lengh. Packet lengh = " +
            data.length + ", offset = " + offset);

        return data[offset];

    }

}
