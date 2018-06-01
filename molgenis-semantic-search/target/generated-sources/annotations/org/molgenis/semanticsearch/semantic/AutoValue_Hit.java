
package org.molgenis.semanticsearch.semantic;

import javax.annotation.Generated;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_Hit<T> extends Hit<T> {

  private final T result;
  private final int scoreInt;

  AutoValue_Hit(
      T result,
      int scoreInt) {
    if (result == null) {
      throw new NullPointerException("Null result");
    }
    this.result = result;
    this.scoreInt = scoreInt;
  }

  @Override
  public T getResult() {
    return result;
  }

  @Override
  public int getScoreInt() {
    return scoreInt;
  }

  @Override
  public String toString() {
    return "Hit{"
        + "result=" + result + ", "
        + "scoreInt=" + scoreInt
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof Hit) {
      Hit<?> that = (Hit<?>) o;
      return (this.result.equals(that.getResult()))
           && (this.scoreInt == that.getScoreInt());
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= this.result.hashCode();
    h *= 1000003;
    h ^= this.scoreInt;
    return h;
  }

}
