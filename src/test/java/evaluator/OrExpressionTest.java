package evaluator;

import model.RawPackage;
import org.junit.Test;

import static org.junit.Assert.*;

public class OrExpressionTest {
    private OrExpression<RawPackage> orExpression;

    private RawPackage expression = new RawPackage(new byte[]{0x40, (byte)0xa3, 0x15, (byte)0x9c, (byte)0x80});


    @Test
    public void interpret() {
        Expression<RawPackage> expression1 = new ByteInOffsetExpression(0, (byte)0x40);
        Expression<RawPackage> expression2 = new ByteInOffsetExpression(4, (byte)0x80);
        orExpression = new OrExpression(expression1, expression2);

        assertTrue(orExpression.interpret(expression));

    }

    @Test
    public void interpret2() {
        Expression<RawPackage>  expression1 = new ByteInOffsetExpression(0,(byte) 0x4);
        Expression<RawPackage>  expression2 = new ByteInOffsetExpression(4, (byte)0x80);
        orExpression = new OrExpression(expression1, expression2);

        assertTrue(orExpression.interpret(expression));

    }

    @Test
    public void interpret3() {
        Expression<RawPackage>  expression1 = new ByteInOffsetExpression(0, (byte) 0x4);
        Expression<RawPackage>  expression2 = new ByteInOffsetExpression(4, (byte) 0x10);
        orExpression = new OrExpression(expression1, expression2);

        assertFalse(orExpression.interpret(expression));
    }
}