package evaluator;

import model.RawPackage;
import org.junit.Test;

import static org.junit.Assert.*;

public class ByteInOffsetExpressionTest {

    private Expression<RawPackage> byteInOffsetExpression;


    @Test
    public void interpret() {
        byteInOffsetExpression = new ByteInOffsetExpression(3, (byte)0x3f);
        RawPackage frame = new RawPackage(new byte[]{0x44, 0x00, 0x12, 0x3f, (byte) 0xac});//"4400123fac";

        assertTrue(byteInOffsetExpression.interpret(frame));
    }

    @Test
    public void interpretFalse() {
        byteInOffsetExpression = new ByteInOffsetExpression(4,(byte)0xaf);
        RawPackage frame = new RawPackage(new byte[]{0x44, 0x00, 0x12, 0x3f, (byte) 0xac});

        assertFalse(byteInOffsetExpression.interpret(frame));
    }

    @Test(expected = RuntimeException.class)
    public void interpretOffsetOutOfBound() {
        byteInOffsetExpression = new ByteInOffsetExpression(15, (byte)0xaf);
        RawPackage frame = new RawPackage(new byte[]{0x44, 0x00, 0x12, 0x3f, (byte) 0xac});

        assertTrue(byteInOffsetExpression.interpret(frame));
    }

}