
package org.molgenis.data.rest.client.bean;

import java.util.List;
import java.util.Map;
import javax.annotation.Generated;
import javax.annotation.Nullable;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_QueryResponse extends QueryResponse {

  private final String href;
  private final int start;
  private final int num;
  private final int total;
  private final String prevHref;
  private final String nextHref;
  private final List<Map<String, Object>> items;

  AutoValue_QueryResponse(
      String href,
      int start,
      int num,
      int total,
      @Nullable String prevHref,
      @Nullable String nextHref,
      List<Map<String, Object>> items) {
    if (href == null) {
      throw new NullPointerException("Null href");
    }
    this.href = href;
    this.start = start;
    this.num = num;
    this.total = total;
    this.prevHref = prevHref;
    this.nextHref = nextHref;
    if (items == null) {
      throw new NullPointerException("Null items");
    }
    this.items = items;
  }

  @Override
  public String getHref() {
    return href;
  }

  @Override
  public int getStart() {
    return start;
  }

  @Override
  public int getNum() {
    return num;
  }

  @Override
  public int getTotal() {
    return total;
  }

  @Nullable
  @Override
  public String getPrevHref() {
    return prevHref;
  }

  @Nullable
  @Override
  public String getNextHref() {
    return nextHref;
  }

  @Override
  public List<Map<String, Object>> getItems() {
    return items;
  }

  @Override
  public String toString() {
    return "QueryResponse{"
        + "href=" + href + ", "
        + "start=" + start + ", "
        + "num=" + num + ", "
        + "total=" + total + ", "
        + "prevHref=" + prevHref + ", "
        + "nextHref=" + nextHref + ", "
        + "items=" + items
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof QueryResponse) {
      QueryResponse that = (QueryResponse) o;
      return (this.href.equals(that.getHref()))
           && (this.start == that.getStart())
           && (this.num == that.getNum())
           && (this.total == that.getTotal())
           && ((this.prevHref == null) ? (that.getPrevHref() == null) : this.prevHref.equals(that.getPrevHref()))
           && ((this.nextHref == null) ? (that.getNextHref() == null) : this.nextHref.equals(that.getNextHref()))
           && (this.items.equals(that.getItems()));
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= this.href.hashCode();
    h *= 1000003;
    h ^= this.start;
    h *= 1000003;
    h ^= this.num;
    h *= 1000003;
    h ^= this.total;
    h *= 1000003;
    h ^= (prevHref == null) ? 0 : this.prevHref.hashCode();
    h *= 1000003;
    h ^= (nextHref == null) ? 0 : this.nextHref.hashCode();
    h *= 1000003;
    h ^= this.items.hashCode();
    return h;
  }

}
