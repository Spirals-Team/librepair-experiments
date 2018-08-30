/* LCM type definition class file
 * This file was automatically generated by lcm-gen
 * DO NOT MODIFY BY HAND!!!!
 */
package idsc;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;

public final class BinaryBlob implements lcm.lcm.LCMEncodable {
  public int data_length;
  public byte data[];

  public BinaryBlob() {
  }

  public static final long LCM_FINGERPRINT;
  public static final long LCM_FINGERPRINT_BASE = 0x9c7079c442ed5c7cL;
  static {
    LCM_FINGERPRINT = _hashRecursive(new ArrayList<Class<?>>());
  }

  public static long _hashRecursive(ArrayList<Class<?>> classes) {
    if (classes.contains(idsc.BinaryBlob.class))
      return 0L;
    classes.add(idsc.BinaryBlob.class);
    long hash = LCM_FINGERPRINT_BASE;
    classes.remove(classes.size() - 1);
    return (hash << 1) + ((hash >> 63) & 1);
  }

  @Override
  public void encode(DataOutput outs) throws IOException {
    outs.writeLong(LCM_FINGERPRINT);
    _encodeRecursive(outs);
  }

  @Override
  public void _encodeRecursive(DataOutput outs) throws IOException {
    outs.writeInt(this.data_length);
    if (this.data_length > 0)
      outs.write(this.data, 0, data_length);
  }

  public BinaryBlob(byte[] data) throws IOException {
    this(new lcm.lcm.LCMDataInputStream(data));
  }

  public BinaryBlob(DataInput ins) throws IOException {
    if (ins.readLong() != LCM_FINGERPRINT)
      throw new IOException("LCM Decode error: bad fingerprint");
    _decodeRecursive(ins);
  }

  public static idsc.BinaryBlob _decodeRecursiveFactory(DataInput ins) throws IOException {
    idsc.BinaryBlob o = new idsc.BinaryBlob();
    o._decodeRecursive(ins);
    return o;
  }

  @Override
  public void _decodeRecursive(DataInput ins) throws IOException {
    this.data_length = ins.readInt();
    this.data = new byte[data_length];
    ins.readFully(this.data, 0, data_length);
  }

  public idsc.BinaryBlob copy() {
    idsc.BinaryBlob outobj = new idsc.BinaryBlob();
    outobj.data_length = this.data_length;
    outobj.data = new byte[data_length];
    if (this.data_length > 0)
      System.arraycopy(this.data, 0, outobj.data, 0, this.data_length);
    return outobj;
  }
}
