
package org.molgenis.metadata.manager.model;

import java.util.List;
import javax.annotation.Generated;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_EditorSort extends EditorSort {

  private final List<EditorOrder> orders;

  AutoValue_EditorSort(
      List<EditorOrder> orders) {
    if (orders == null) {
      throw new NullPointerException("Null orders");
    }
    this.orders = orders;
  }

  @Override
  public List<EditorOrder> getOrders() {
    return orders;
  }

  @Override
  public String toString() {
    return "EditorSort{"
        + "orders=" + orders
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof EditorSort) {
      EditorSort that = (EditorSort) o;
      return (this.orders.equals(that.getOrders()));
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= this.orders.hashCode();
    return h;
  }

}
