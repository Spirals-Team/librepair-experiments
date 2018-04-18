package evaluator;

import model.RawPackage;
import org.junit.Test;

import static org.junit.Assert.*;

public class AndExpressionTest {

    private Expression<RawPackage> andExpression;
    private RawPackage expression = new RawPackage(new byte[]{0x40, (byte)0xa3, 0x15, (byte)0x9c, (byte)0x80});

    @Test
    public void interpret() {
        Expression<RawPackage> expression1 = new ByteInOffsetExpression((byte)0x00, (byte)0x40);
        Expression<RawPackage> expression2 = new ByteInOffsetExpression((byte)0x04, (byte)0x80);
        andExpression = new AndExpression(expression1, expression2);

        assertTrue(andExpression.interpret(expression));

    }

    @Test
    public void interpret2() {
        Expression<RawPackage> expression1 = new ByteInOffsetExpression((byte)0x0,(byte) 0x4);
        Expression<RawPackage> expression2 = new ByteInOffsetExpression((byte)0x4, (byte)0x80);
        andExpression = new AndExpression(expression1, expression2);

        assertFalse(andExpression.interpret(expression));

    }

    @Test
    public void interpret3() {
        Expression<RawPackage> expression1 = new ByteInOffsetExpression((byte)0x0, (byte)0x4);
        Expression<RawPackage> expression2 = new ByteInOffsetExpression((byte)0x4, (byte)0x10);
        andExpression = new AndExpression(expression1, expression2);

        assertFalse(andExpression.interpret(expression));

    }

}