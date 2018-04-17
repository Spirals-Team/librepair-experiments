package evaluator;

import fileReader.HexStringToByteArray;
import model.RawPackage;

public class ByteInOffsetExpression extends Expression<RawPackage> {

    private int offset;
    private byte byteToCompare;
    private HexStringToByteArray converter;

    public ByteInOffsetExpression(int offset, byte byteToCompare) {
        this.offset = offset;
        this.byteToCompare = byteToCompare;
        this.converter = new HexStringToByteArray();
    }

    @Override
    public boolean interpret(RawPackage context) {

        return context.getByteAt(this.offset) == this.byteToCompare;

    }


}
