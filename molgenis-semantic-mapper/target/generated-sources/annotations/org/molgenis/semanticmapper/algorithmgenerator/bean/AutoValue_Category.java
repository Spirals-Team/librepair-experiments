
package org.molgenis.semanticmapper.algorithmgenerator.bean;

import javax.annotation.Generated;
import javax.annotation.Nullable;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_Category extends Category {

  private final String code;
  private final String label;
  private final AmountWrapper amountWrapper;

  AutoValue_Category(
      String code,
      String label,
      @Nullable AmountWrapper amountWrapper) {
    if (code == null) {
      throw new NullPointerException("Null code");
    }
    this.code = code;
    if (label == null) {
      throw new NullPointerException("Null label");
    }
    this.label = label;
    this.amountWrapper = amountWrapper;
  }

  @Override
  public String getCode() {
    return code;
  }

  @Override
  public String getLabel() {
    return label;
  }

  @Nullable
  @Override
  public AmountWrapper getAmountWrapper() {
    return amountWrapper;
  }

  @Override
  public String toString() {
    return "Category{"
        + "code=" + code + ", "
        + "label=" + label + ", "
        + "amountWrapper=" + amountWrapper
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof Category) {
      Category that = (Category) o;
      return (this.code.equals(that.getCode()))
           && (this.label.equals(that.getLabel()))
           && ((this.amountWrapper == null) ? (that.getAmountWrapper() == null) : this.amountWrapper.equals(that.getAmountWrapper()));
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= this.code.hashCode();
    h *= 1000003;
    h ^= this.label.hashCode();
    h *= 1000003;
    h ^= (amountWrapper == null) ? 0 : this.amountWrapper.hashCode();
    return h;
  }

}
