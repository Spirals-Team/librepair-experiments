// code by jph
package ch.ethz.idsc.retina.dev.steer;

import java.nio.ByteBuffer;

import ch.ethz.idsc.gokart.core.DataEvent;
import ch.ethz.idsc.retina.sys.OfflineUse;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;

/** information received from micro-autobox about steering
 * 
 * the manufacturer of the steering column does <em>not</em>
 * share details about the exact meaning of the values sent by
 * the device, therefore our documentation also lacks clues. */
public class SteerGetEvent extends DataEvent {
  /* package */ static final int LENGTH = 44;
  // ---
  /** variable */
  public final float motAsp_CANInput;
  /** constant 2.0 */
  public final float motAsp_Qual;
  /** variable */
  public final float tsuTrq_CANInput;
  /** constant 2.0 */
  public final float tsuTrq_Qual;
  /** post processing of data has shown that refMotTrq_CANInput
   * allows the interpretation of reference motor torque.
   * refMotTrq_CANInput closely correlates to the demanded torque
   * as commanded by {@link SteerPutEvent}. Due to communication,
   * there is a time delay until the demanded torque is considered
   * as "reference" by the steering actuator. */
  public final float refMotTrq_CANInput;
  /** when the device {@link #isActive()} then the difference between
   * "estMotTrq_CANInput - refMotTrq_CANInput" is typically small.
   * When the torque command is disabled, the value estMotTrq_CANInput
   * takes an arbitrary value that should be ignored. */
  public final float estMotTrq_CANInput;
  /** {@link SteerPutEvent} commands the steer actuator to be passive or active.
   * In passive mode, no torque is applied by the device.
   * 
   * estMotTrq_Qual == 1.0 means the device is passive
   * estMotTrq_Qual == 2.0 means the device is active
   * and then the difference "refMotTrq_CANInput - estMotTrq_CANInput"
   * should be small.
   * 
   * see {@link #isActive()} */
  private final float estMotTrq_Qual;
  // ---
  /** angular position relative to fixed but initially unknown offset */
  private final float gcpRelRckPos;
  /** gcpRelRckQual is constant 2.0 */
  public final float gcpRelRckQual;
  /** gearRat is constant 22.0 */
  public final float gearRat;
  /** halfRckPos is constant 72.0 */
  public final float halfRckPos;

  /** @param byteBuffer from which constructor reads 44 bytes */
  public SteerGetEvent(ByteBuffer byteBuffer) {
    motAsp_CANInput = byteBuffer.getFloat();
    motAsp_Qual = byteBuffer.getFloat();
    tsuTrq_CANInput = byteBuffer.getFloat();
    tsuTrq_Qual = byteBuffer.getFloat();
    refMotTrq_CANInput = byteBuffer.getFloat();
    estMotTrq_CANInput = byteBuffer.getFloat();
    estMotTrq_Qual = byteBuffer.getFloat();
    // ---
    gcpRelRckPos = byteBuffer.getFloat();
    gcpRelRckQual = byteBuffer.getFloat();
    gearRat = byteBuffer.getFloat();
    halfRckPos = byteBuffer.getFloat();
  }

  @Override
  protected void insert(ByteBuffer byteBuffer) {
    byteBuffer.putFloat(motAsp_CANInput);
    byteBuffer.putFloat(motAsp_Qual);
    byteBuffer.putFloat(tsuTrq_CANInput);
    byteBuffer.putFloat(tsuTrq_Qual);
    byteBuffer.putFloat(refMotTrq_CANInput);
    byteBuffer.putFloat(estMotTrq_CANInput);
    byteBuffer.putFloat(estMotTrq_Qual);
    // ---
    byteBuffer.putFloat(gcpRelRckPos);
    byteBuffer.putFloat(gcpRelRckQual);
    byteBuffer.putFloat(gearRat);
    byteBuffer.putFloat(halfRckPos);
  }

  @Override
  protected int length() {
    return LENGTH;
  }

  /** gcpRelRckPos == offset + factor * steering_angle
   * 
   * the offset has to be determined in a calibration procedure
   * 
   * @return relative angular position with respect to positive z-axis, i.e.
   * increasing values correspond to ccw rotation */
  public float getGcpRelRckPos() {
    return gcpRelRckPos;
  }

  /** @return true if the device is active and receives and follows torque commands */
  public boolean isActive() {
    return estMotTrq_Qual == 2.0f;
  }

  /** @return vector of length 11 */
  @OfflineUse
  public Tensor values_raw() {
    return Tensors.vector( //
        motAsp_CANInput, //
        motAsp_Qual, //
        tsuTrq_CANInput, //
        tsuTrq_Qual, //
        refMotTrq_CANInput, //
        estMotTrq_CANInput, //
        estMotTrq_Qual, //
        gcpRelRckPos, //
        gcpRelRckQual, //
        gearRat, //
        halfRckPos);
  }
}
