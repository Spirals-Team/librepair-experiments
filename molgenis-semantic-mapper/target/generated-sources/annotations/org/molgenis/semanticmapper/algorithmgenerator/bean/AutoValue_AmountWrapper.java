
package org.molgenis.semanticmapper.algorithmgenerator.bean;

import javax.annotation.Generated;
import javax.annotation.Nullable;
import org.jscience.physics.amount.Amount;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_AmountWrapper extends AmountWrapper {

  private final Amount<?> amount;
  private final boolean determined;

  AutoValue_AmountWrapper(
      @Nullable Amount<?> amount,
      boolean determined) {
    this.amount = amount;
    this.determined = determined;
  }

  @Nullable
  @Override
  public Amount<?> getAmount() {
    return amount;
  }

  @Override
  public boolean isDetermined() {
    return determined;
  }

  @Override
  public String toString() {
    return "AmountWrapper{"
        + "amount=" + amount + ", "
        + "determined=" + determined
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof AmountWrapper) {
      AmountWrapper that = (AmountWrapper) o;
      return ((this.amount == null) ? (that.getAmount() == null) : this.amount.equals(that.getAmount()))
           && (this.determined == that.isDetermined());
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= (amount == null) ? 0 : this.amount.hashCode();
    h *= 1000003;
    h ^= this.determined ? 1231 : 1237;
    return h;
  }

}
