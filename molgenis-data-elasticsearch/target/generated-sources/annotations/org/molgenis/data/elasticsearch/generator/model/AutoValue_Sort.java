
package org.molgenis.data.elasticsearch.generator.model;

import java.util.List;
import javax.annotation.Generated;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_Sort extends Sort {

  private final List<SortOrder> orders;

  private AutoValue_Sort(
      List<SortOrder> orders) {
    this.orders = orders;
  }

  @Override
  public List<SortOrder> getOrders() {
    return orders;
  }

  @Override
  public String toString() {
    return "Sort{"
        + "orders=" + orders
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof Sort) {
      Sort that = (Sort) o;
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

  static final class Builder extends Sort.Builder {
    private List<SortOrder> orders;
    Builder() {
    }
    @Override
    public Sort.Builder setOrders(List<SortOrder> orders) {
      if (orders == null) {
        throw new NullPointerException("Null orders");
      }
      this.orders = orders;
      return this;
    }
    @Override
    public Sort build() {
      String missing = "";
      if (this.orders == null) {
        missing += " orders";
      }
      if (!missing.isEmpty()) {
        throw new IllegalStateException("Missing required properties:" + missing);
      }
      return new AutoValue_Sort(
          this.orders);
    }
  }

}
