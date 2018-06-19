package dk.alexandra.fresco.suite.spdz2k.datatypes;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigInteger;
import java.util.Random;
import org.junit.Test;

public class TestCompUInt128 {

  private final BigInteger two = BigInteger.valueOf(2);
  private final BigInteger twoTo32 = BigInteger.ONE.shiftLeft(32);
  private final BigInteger twoTo64 = BigInteger.ONE.shiftLeft(64);
  private final BigInteger twoTo128 = BigInteger.ONE.shiftLeft(128);

  @Test
  public void testConstruct() {
    assertEquals(
        BigInteger.ZERO,
        new CompUInt128(BigInteger.ZERO).toBigInteger()
    );
    assertEquals(
        BigInteger.ONE,
        new CompUInt128(BigInteger.ONE).toBigInteger()
    );
    assertEquals(
        new BigInteger("42"),
        new CompUInt128(new BigInteger("42")).toBigInteger()
    );
    assertEquals(
        twoTo32,
        new CompUInt128(twoTo32).toBigInteger()
    );
    assertEquals(
        twoTo32.subtract(BigInteger.ONE),
        new CompUInt128(twoTo32.subtract(BigInteger.ONE)).toBigInteger()
    );
    assertEquals(
        twoTo32.add(BigInteger.ONE),
        new CompUInt128(twoTo32.add(BigInteger.ONE)).toBigInteger()
    );
    assertEquals(
        twoTo64.subtract(BigInteger.ONE),
        new CompUInt128(twoTo64.subtract(BigInteger.ONE)).toBigInteger()
    );
    assertEquals(
        twoTo64,
        new CompUInt128(twoTo64).toBigInteger()
    );
    assertEquals(
        twoTo64.add(BigInteger.ONE),
        new CompUInt128(twoTo64.add(BigInteger.ONE)).toBigInteger()
    );
    assertEquals(
        twoTo128.subtract(BigInteger.ONE),
        new CompUInt128(twoTo128.subtract(BigInteger.ONE)).toBigInteger()
    );
    assertEquals(
        BigInteger.valueOf(-1).mod(twoTo128),
        new CompUInt128(BigInteger.valueOf(-1)).toBigInteger()
    );
    assertEquals(
        BigInteger.valueOf(-2).mod(twoTo128),
        new CompUInt128(BigInteger.valueOf(-2)).toBigInteger()
    );
    assertEquals(
        twoTo128.subtract(BigInteger.ONE).negate().mod(twoTo128),
        new CompUInt128(twoTo128.subtract(BigInteger.ONE).negate()).toBigInteger()
    );
  }

  @Test
  public void testAdd() {
    assertEquals(
        BigInteger.ZERO,
        new CompUInt128(0).add(new CompUInt128(0)).toBigInteger()
    );
    assertEquals(
        two,
        new CompUInt128(1).add(new CompUInt128(1)).toBigInteger()
    );
    assertEquals(
        twoTo32,
        new CompUInt128(twoTo32).add(new CompUInt128(0)).toBigInteger()
    );
    assertEquals(
        twoTo32.add(BigInteger.ONE),
        new CompUInt128(twoTo32).add(new CompUInt128(1)).toBigInteger()
    );
    assertEquals(
        twoTo64,
        new CompUInt128(twoTo64).add(new CompUInt128(0)).toBigInteger()
    );
    assertEquals(
        twoTo64.add(BigInteger.ONE),
        new CompUInt128(twoTo64).add(new CompUInt128(1)).toBigInteger()
    );
    assertEquals(
        twoTo128.subtract(BigInteger.ONE),
        new CompUInt128(twoTo128.subtract(BigInteger.ONE)).add(new CompUInt128(0))
            .toBigInteger()
    );
    assertEquals(
        BigInteger.ZERO,
        new CompUInt128(twoTo128.subtract(BigInteger.ONE))
            .add(new CompUInt128(BigInteger.ONE)).toBigInteger()
    );
    assertEquals(
        twoTo128.subtract(new BigInteger("10000000")).add(twoTo32.add(twoTo64)).mod(twoTo128),
        new CompUInt128(twoTo128.subtract(new BigInteger("10000000")))
            .add(new CompUInt128(twoTo32.add(twoTo64))).toBigInteger()
    );
    assertEquals(
        twoTo32.add(twoTo64).mod(twoTo128),
        new CompUInt128(twoTo32).add(new CompUInt128(twoTo64)).toBigInteger()
    );
  }

  @Test
  public void testMultiply() {
    assertEquals(
        BigInteger.ZERO,
        new CompUInt128(0).multiply(new CompUInt128(0)).toBigInteger()
    );
    assertEquals(
        BigInteger.ZERO,
        new CompUInt128(1).multiply(new CompUInt128(0)).toBigInteger()
    );
    assertEquals(
        BigInteger.ZERO,
        new CompUInt128(0).multiply(new CompUInt128(1)).toBigInteger()
    );
    assertEquals(
        BigInteger.ZERO,
        new CompUInt128(1024).multiply(new CompUInt128(0)).toBigInteger()
    );
    assertEquals(
        BigInteger.ZERO,
        new CompUInt128(twoTo128.subtract(BigInteger.ONE)).multiply(new CompUInt128(0))
            .toBigInteger()
    );
    assertEquals(
        BigInteger.ONE,
        new CompUInt128(1).multiply(new CompUInt128(1)).toBigInteger()
    );
    assertEquals(
        twoTo128.subtract(BigInteger.ONE),
        new CompUInt128(new CompUInt128(1))
            .multiply(new CompUInt128(twoTo128.subtract(BigInteger.ONE)))
            .toBigInteger()
    );
    assertEquals(
        twoTo128.subtract(BigInteger.ONE),
        new CompUInt128(twoTo128.subtract(BigInteger.ONE)).multiply(new CompUInt128(1))
            .toBigInteger()
    );
    // multiply no overflow
    assertEquals(
        new BigInteger("42").multiply(new BigInteger("7")),
        new CompUInt128(new BigInteger("42")).multiply(new CompUInt128(new BigInteger("7")))
            .toBigInteger()
    );
    // multiply with overflow
    assertEquals(
        new BigInteger("42").multiply(twoTo128.subtract(BigInteger.ONE)).mod(twoTo128),
        new CompUInt128(new BigInteger("42"))
            .multiply(new CompUInt128(twoTo128.subtract(BigInteger.ONE)))
            .toBigInteger()
    );
    assertEquals(
        twoTo64.multiply(twoTo64.add(BigInteger.TEN)).mod(twoTo128),
        new CompUInt128(twoTo64)
            .multiply(new CompUInt128(twoTo64.add(BigInteger.TEN)))
            .toBigInteger()
    );
  }

  @Test
  public void testNegate() {
    assertEquals(
        BigInteger.ZERO,
        new CompUInt128(BigInteger.ZERO).negate().toBigInteger()
    );
    assertEquals(
        BigInteger.ONE,
        new CompUInt128(twoTo128.subtract(BigInteger.ONE)).negate().toBigInteger()
    );
    assertEquals(
        two,
        new CompUInt128(twoTo128.subtract(two)).negate().toBigInteger()
    );
    assertEquals(
        twoTo128.subtract(two),
        new CompUInt128(two).negate().toBigInteger()
    );
  }

  @Test
  public void testSubtract() {
    assertEquals(
        BigInteger.ZERO,
        new CompUInt128(BigInteger.ZERO).subtract(new CompUInt128(BigInteger.ZERO))
            .toBigInteger()
    );
    assertEquals(
        BigInteger.ONE,
        new CompUInt128(BigInteger.ONE).subtract(new CompUInt128(BigInteger.ZERO))
            .toBigInteger()
    );
    assertEquals(
        BigInteger.ZERO,
        new CompUInt128(BigInteger.ONE).subtract(new CompUInt128(BigInteger.ONE))
            .toBigInteger()
    );
    assertEquals(
        twoTo128.subtract(BigInteger.ONE),
        new CompUInt128(BigInteger.ONE).subtract(new CompUInt128(two))
            .toBigInteger()
    );
    assertEquals(
        new BigInteger("1121381238012").subtract(new BigInteger("10261555690727498232")).mod(twoTo128),
        new CompUInt128(new BigInteger("1121381238012"))
            .subtract(new CompUInt128(new BigInteger("10261555690727498232")))
            .toBigInteger()
    );
  }

  @Test
  public void testToByteArrayWithPadding() {
    byte[] bytes = new byte[]{0x42};
    UInt<CompUInt128> uint = new CompUInt128(bytes, true);
    byte[] expected = new byte[16];
    expected[expected.length - 1] = 0x42;
    byte[] actual = uint.toByteArray();
    assertArrayEquals(expected, actual);
  }

  @Test
  public void testToByteArray() {
    byte[] bytes = new byte[16];
    new Random(1).nextBytes(bytes);
    UInt<CompUInt128> uint = new CompUInt128(bytes);
    byte[] actual = uint.toByteArray();
    assertArrayEquals(bytes, actual);
  }

  @Test
  public void testToByteArrayMore() {
    byte[] bytes = new byte[]{
        0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, // high
        0x02, 0x02, 0x02, 0x02, // mid
        0x03, 0x03, 0x02, 0x03  // low
    };
    UInt<CompUInt128> uint = new CompUInt128(bytes);
    byte[] actual = uint.toByteArray();
    assertArrayEquals(bytes, actual);
  }

  @Test
  public void testGetBitLength() {
    CompUInt128 uint = new CompUInt128(1);
    assertEquals(128, uint.getBitLength());
    assertEquals(64, uint.getHighBitLength());
    assertEquals(64, uint.getLowBitLength());
  }

  @Test
  public void testToInt() {
    UInt<CompUInt128> uint = new CompUInt128(1);
    assertEquals(1, uint.toInt());
  }

  @Test
  public void testToString() {
    UInt<CompUInt128> uint = new CompUInt128(12135);
    assertEquals("12135", uint.toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPadIllegal() {
    new CompUInt128(new byte[50], true);
  }

  @Test
  public void testIsZero() {
    assertTrue(new CompUInt128(0, 0, 0).isZero());
    assertFalse(new CompUInt128(0, 0, 1).isZero());
    assertFalse(new CompUInt128(0, 1, 0).isZero());
    assertFalse(new CompUInt128(1, 0, 0).isZero());
  }

  @Test
  public void testTestBit() {
    assertTrue(new CompUInt128(0, 0, 1).testBit(0));
    assertFalse(new CompUInt128(0, 0, 0).testBit(0));
    assertTrue(new CompUInt128(0, 1, 0).testBit(32));
    assertFalse(new CompUInt128(0, 0, 1).testBit(32));
    assertTrue(new CompUInt128(1, 0, 0).testBit(64));
    assertFalse(new CompUInt128(0, 1, 1).testBit(64));
    assertTrue(new CompUInt128(0, 0, 1 << 14).testBit(14));
    assertTrue(new CompUInt128(0, 1 << 10, 0).testBit(32 + 10));
    assertTrue(new CompUInt128(1 << 2, 0, 0).testBit(64 + 2));
    assertTrue(new CompUInt128(1L << 62, 0, 0).testBit(64 + 62));
    assertTrue(new CompUInt128(0x8000000000000000L, 0, 0).testBit(64 + 63));
  }

  @Test
  public void testClearAboveBitAt() {
    assertEquals(new CompUInt128(0, 0, 0).toBigInteger(),
        new CompUInt128(0, 0, 0).clearAboveBitAt(63).toBigInteger());
    assertEquals(new CompUInt128(0, 0, 123123).toBigInteger(),
        new CompUInt128(0, 0, 123123).clearAboveBitAt(63).toBigInteger());
    assertEquals(new CompUInt128(0, 0, 123123).toBigInteger(),
        new CompUInt128(1, 0, 123123).clearAboveBitAt(63).toBigInteger());
    assertEquals(new CompUInt128(0, 0x70001001, 123123).toBigInteger(),
        new CompUInt128(1, 0xf0001001, 123123).clearAboveBitAt(63).toBigInteger());
    assertEquals(new CompUInt128(0, 0x00000021, 123123).toBigInteger(),
        new CompUInt128(1, 0xf0001021, 123123).clearAboveBitAt(44).toBigInteger());
    assertEquals(new CompUInt128(0x7000100100000000L, 0x70001001, 123123).toBigInteger(),
        new CompUInt128(0xf000100100000000L, 0x70001001, 123123).clearAboveBitAt(127)
            .toBigInteger());
    assertEquals(new CompUInt128(0, 0, 0x00000001).toBigInteger(),
        new CompUInt128(1, 1, 0xff001021).clearAboveBitAt(5).toBigInteger());
  }

  @Test
  public void testShiftLeftSmall() {
    byte[] bytes = new byte[16];
    new Random(1).nextBytes(bytes);
    CompUInt128 r = new CompUInt128(bytes);
    for (int i = 0; i < 64; i++) {
      assertEquals("Number of shifts " + i, r.toBigInteger().shiftLeft(i).mod(twoTo128).toString(2),
          r.shiftLeftSmall(i).toBigInteger().toString(2));
    }
    assertEquals(new BigInteger("1").shiftLeft(63),
        new CompUInt128(new BigInteger("1")).shiftLeftSmall(63).toBigInteger());
    assertEquals(new BigInteger("12312").shiftLeft(12),
        new CompUInt128(new BigInteger("12312")).shiftLeftSmall(12).toBigInteger());
    assertEquals(new BigInteger("12312"),
        new CompUInt128(new BigInteger("12312")).shiftLeftSmall(0).toBigInteger());
    assertEquals(new BigInteger("12312"),
        new CompUInt128(new BigInteger("12312")).shiftLeftSmall(-1).toBigInteger());
  }

  @Test
  public void testShiftRightSmall() {
    byte[] bytes = new byte[16];
    new Random(2).nextBytes(bytes);
    CompUInt128 r = new CompUInt128(bytes);
    for (int i = 0; i < 64; i++) {
      assertEquals("Number of shifts " + i,
          r.toBigInteger().shiftRight(i).mod(twoTo128).toString(2),
          r.shiftRightSmall(i).toBigInteger().toString(2));
    }
    for (int i = 0; i < 64; i++) {
      CompUInt128 element = new CompUInt128(1L << i);
      assertEquals("Number of shifts " + i, BigInteger.ONE,
          element.shiftRightSmall(i).toBigInteger());
    }
    assertEquals(new BigInteger("12312"),
        new CompUInt128(new BigInteger("12312")).shiftRightSmall(0).toBigInteger());
    assertEquals(new BigInteger("12312"),
        new CompUInt128(new BigInteger("12312")).shiftRightSmall(-1).toBigInteger());
  }

  @Test
  public void testShiftRightLowOnly() {
    byte[] bytes = new byte[16];
    new Random(2).nextBytes(bytes);
    CompUInt128 r = new CompUInt128(bytes);
    // top bit 0
    for (int i = 0; i < 63; i++) {
      CompUInt128 element = new CompUInt128(1L << i);
      assertEquals("Number of shifts " + i, BigInteger.ONE,
          element.shiftRightLowOnly(i).toBigInteger());
    }
    // top bit 1
    CompUInt128 element = new CompUInt128(BigInteger.ONE.shiftLeft(63));
    assertEquals("Number of shifts " + 63, BigInteger.ONE.shiftLeft(63).negate().shiftRight(63).mod(twoTo64),
        element.shiftRightLowOnly(63).toBigInteger());
  }

  @Test
  public void testToBit() {
    assertEquals(BigInteger.ZERO, new CompUInt128(0).toBitRep().toBigInteger());
    assertEquals(twoTo64.shiftRight(1), new CompUInt128(1).toBitRep().toBigInteger());
    assertEquals(twoTo64.shiftLeft(63), new CompUInt128(twoTo64).toBitRep().toBigInteger());
  }

}
