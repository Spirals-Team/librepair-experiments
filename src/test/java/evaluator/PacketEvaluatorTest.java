package evaluator;

import fileReader.HexStringToByteArray;
import model.RawPackage;
import org.junit.Before;
import org.junit.Test;

import java.beans.Expression;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class PacketEvaluatorTest {
    ByteInOffsetExpression probeRequestExpression;
    ByteInOffsetExpression beaconExpression;
    OrExpression<RawPackage> orExpression;

    private Expression expression;
    private Evaluator evaluator;
    private HexStringToByteArray hexStringToByteArray;

    @Before
    public void setUp(){
        probeRequestExpression = new ByteInOffsetExpression(144, (byte) 0x40);
        beaconExpression = new ByteInOffsetExpression(144, (byte) 0x80);
        orExpression = new OrExpression<RawPackage>(beaconExpression, probeRequestExpression);

        hexStringToByteArray = new HexStringToByteArray();

    }

    @Test
    public void evaluateBeacon() {

        evaluator = new PacketEvaluator(beaconExpression);

        byte[] beaconData = hexStringToByteArray.convert("44000000900000000000000000000000" +
                "00000000000000004400010000000400" +
                "00000000440002000000040000000000" +
                "44000300000004000100000044000400" +
                "000004002a0000000000000000000000" +
                "0000000044000600000004002a000000"+
                "00000000000000000000000044000800"+
                "00000400020000004400090000000400"+
                "0000000044000a00000004005c000000"+
                "80000000ffffffffffff00239c3bd020");

        RawPackage beaconFame = new RawPackage(beaconData);
        assertTrue(evaluator.evaluate(beaconFame));

    }

    @Test
    public void evaluateProbeRequest(){
        evaluator = new PacketEvaluator(orExpression);

        byte[] probeRequestData = hexStringToByteArray.convert(
                "44000000900000000000000000000000"+
                        "00000000000000004400010000000400"+
                        "00000000440002000000040000000000"+
                        "44000300000004000100000044000400"+
                        "00000400080000000000000000000000"+
                        "00000000440006000000040008000000"+
                        "00000000000000000000000044000800"+
                        "00000400020000004400090000000400"+
                        "0000000044000a00000004002a000000"+
                        "40000000ffffffffffff9cb70d7e6c12"+
                        "ffffffffffff80f40000010882848b96"+
                        "0c12182432043048606c");

        RawPackage probeRequestFrame = new RawPackage(probeRequestData);
        assertTrue(evaluator.evaluate(probeRequestFrame));
    }

    @Test
    public void evaluateHTTP(){
        evaluator = new PacketEvaluator(probeRequestExpression);

        byte[] httpData = hexStringToByteArray.convert(
            "f4e3fb6d4c2bf83441badaf108004500"+
            "008b61c9400040061e5dc0a8012768c6"+
            "8fb1b2a500507c84cab0f103108b8018"+
            "00e5b9dc00000101080ab7fff7761503"+
            "7fda474554202f20485454502f312e31"+
            "0d0a486f73743a20636f6e6e65637469"+
            "766974792d636865636b2e7562756e74"+
            "752e636f6d0d0a4163636570743a202a"+
            "2f2a0d0a436f6e6e656374696f6e3a20"+
            "636c6f73650d0a0d0a");

        RawPackage httpPacket = new RawPackage(httpData);
        assertFalse(evaluator.evaluate(httpPacket));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void evaluateIndexOutOfBound(){

        evaluator = new PacketEvaluator(probeRequestExpression);

        byte[] httpData = hexStringToByteArray.convert(
                "f4e3fb6d4c2bf83441badaf108004500"+
                        "008b61c9400040061e5dc0a8012768c6" +
                        "00e5b9dc00000101080ab7fff7761503" +
                        "2f2a0d0a436f6e6e656374696f6e3a20" +
                        "636c6f73650d0a0d0a");

        RawPackage httpPacket = new RawPackage(httpData);
        assertFalse(evaluator.evaluate(httpPacket));
    }

}