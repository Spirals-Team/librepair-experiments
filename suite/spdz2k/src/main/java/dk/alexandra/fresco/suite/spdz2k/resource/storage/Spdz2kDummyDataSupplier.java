package dk.alexandra.fresco.suite.spdz2k.resource.storage;

import dk.alexandra.fresco.framework.util.ArithmeticDummyDataSupplier;
import dk.alexandra.fresco.framework.util.MultiplicationTripleShares;
import dk.alexandra.fresco.framework.util.Pair;
import dk.alexandra.fresco.suite.spdz2k.datatypes.CompUInt;
import dk.alexandra.fresco.suite.spdz2k.datatypes.CompUIntFactory;
import dk.alexandra.fresco.suite.spdz2k.datatypes.Spdz2kInputMask;
import dk.alexandra.fresco.suite.spdz2k.datatypes.Spdz2kSIntArithmetic;
import dk.alexandra.fresco.suite.spdz2k.datatypes.Spdz2kSIntBoolean;
import dk.alexandra.fresco.suite.spdz2k.datatypes.Spdz2kTriple;
import java.math.BigInteger;

/**
 * Insecure implementation of {@link Spdz2kDataSupplier}. <p>This class deterministically generates
 * pre-processing material for each party and can therefore not be used in production.</p>
 */
public class Spdz2kDummyDataSupplier<
    PlainT extends CompUInt<?, ?, PlainT>> implements
    Spdz2kDataSupplier<PlainT> {

  private final int myId;
  private final ArithmeticDummyDataSupplier supplier;
  private final PlainT secretSharedKey;
  private final CompUIntFactory<PlainT> factory;

  public Spdz2kDummyDataSupplier(int myId, int noOfParties, PlainT secretSharedKey,
      CompUIntFactory<PlainT> factory) {
    this.myId = myId;
    this.secretSharedKey = secretSharedKey;
    this.factory = factory;
    this.supplier = new ArithmeticDummyDataSupplier(myId, noOfParties,
        BigInteger.ONE.shiftLeft(factory.getCompositeBitLength()));
  }

  @Override
  public Spdz2kTriple<PlainT, Spdz2kSIntArithmetic<PlainT>> getNextTripleSharesFull() {
    MultiplicationTripleShares rawTriple = supplier.getMultiplicationTripleShares();
    return new Spdz2kTriple<>(
        toSpdz2kSInt(rawTriple.getLeft()),
        toSpdz2kSInt(rawTriple.getRight()),
        toSpdz2kSInt(rawTriple.getProduct()));
  }

  @Override
  public Spdz2kTriple<PlainT, Spdz2kSIntBoolean<PlainT>> getNextBitTripleShares() {
    MultiplicationTripleShares rawTriple = supplier.getMultiplicationBitTripleShares();
    return new Spdz2kTriple<>(
        toSpdz2kSIntBool(rawTriple.getLeft()),
        toSpdz2kSIntBool(rawTriple.getRight()),
        toSpdz2kSIntBool(rawTriple.getProduct()));
  }

  @Override
  public Spdz2kInputMask<PlainT> getNextInputMask(int towardPlayerId) {
    Pair<BigInteger, BigInteger> raw = supplier.getRandomElementShare();
    if (myId == towardPlayerId) {
      return new Spdz2kInputMask<>(toSpdz2kSInt(raw),
          factory.createFromBigInteger(raw.getFirst()));
    } else {
      return new Spdz2kInputMask<>(toSpdz2kSInt(raw));
    }
  }

  @Override
  public Spdz2kSIntArithmetic<PlainT> getNextBitShare() {
    return toSpdz2kSInt(supplier.getRandomBitShare());
  }

  @Override
  public PlainT getSecretSharedKey() {
    return secretSharedKey;
  }

  @Override
  public Spdz2kSIntArithmetic<PlainT> getNextRandomElementShare() {
    return toSpdz2kSInt(supplier.getRandomElementShare());
  }

  private Spdz2kSIntArithmetic<PlainT> toSpdz2kSInt(Pair<BigInteger, BigInteger> raw) {
    PlainT openValue = factory.createFromBigInteger(raw.getFirst());
    PlainT share = factory.createFromBigInteger(raw.getSecond());
    PlainT macShare = openValue.multiply(secretSharedKey);
    return new Spdz2kSIntArithmetic<>(share, macShare);
  }

  private Spdz2kSIntBoolean<PlainT> toSpdz2kSIntBool(Pair<BigInteger, BigInteger> raw) {
    PlainT openValue = factory.createFromBigInteger(raw.getFirst()).toBitRep();
    PlainT share = factory.createFromBigInteger(raw.getSecond()).toBitRep();
    PlainT macShare = openValue.toArithmeticRep().multiply(secretSharedKey);
    return new Spdz2kSIntBoolean<>(share, macShare);
  }

}
