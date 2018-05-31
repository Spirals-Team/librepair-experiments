

package com.arcao.geocaching.api.data.sort;

import javax.annotation.Generated;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_SortBy extends SortBy {

  private final SortKey key;
  private final SortOrder order;

  AutoValue_SortBy(
      SortKey key,
      SortOrder order) {
    if (key == null) {
      throw new NullPointerException("Null key");
    }
    this.key = key;
    if (order == null) {
      throw new NullPointerException("Null order");
    }
    this.order = order;
  }

  @Override
  public SortKey key() {
    return key;
  }

  @Override
  public SortOrder order() {
    return order;
  }

  @Override
  public String toString() {
    return "SortBy{"
         + "key=" + key + ", "
         + "order=" + order
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof SortBy) {
      SortBy that = (SortBy) o;
      return (this.key.equals(that.key()))
           && (this.order.equals(that.order()));
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h$ = 1;
    h$ *= 1000003;
    h$ ^= key.hashCode();
    h$ *= 1000003;
    h$ ^= order.hashCode();
    return h$;
  }

  private static final long serialVersionUID = -8572570659481989738L;

}
