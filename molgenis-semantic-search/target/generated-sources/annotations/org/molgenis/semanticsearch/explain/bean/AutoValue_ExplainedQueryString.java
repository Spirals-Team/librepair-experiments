
package org.molgenis.semanticsearch.explain.bean;

import javax.annotation.Generated;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_ExplainedQueryString extends ExplainedQueryString {

  private final String matchedWords;
  private final String queryString;
  private final String tagName;
  private final double score;

  AutoValue_ExplainedQueryString(
      String matchedWords,
      String queryString,
      String tagName,
      double score) {
    if (matchedWords == null) {
      throw new NullPointerException("Null matchedWords");
    }
    this.matchedWords = matchedWords;
    if (queryString == null) {
      throw new NullPointerException("Null queryString");
    }
    this.queryString = queryString;
    if (tagName == null) {
      throw new NullPointerException("Null tagName");
    }
    this.tagName = tagName;
    this.score = score;
  }

  @Override
  public String getMatchedWords() {
    return matchedWords;
  }

  @Override
  public String getQueryString() {
    return queryString;
  }

  @Override
  public String getTagName() {
    return tagName;
  }

  @Override
  public double getScore() {
    return score;
  }

  @Override
  public String toString() {
    return "ExplainedQueryString{"
        + "matchedWords=" + matchedWords + ", "
        + "queryString=" + queryString + ", "
        + "tagName=" + tagName + ", "
        + "score=" + score
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof ExplainedQueryString) {
      ExplainedQueryString that = (ExplainedQueryString) o;
      return (this.matchedWords.equals(that.getMatchedWords()))
           && (this.queryString.equals(that.getQueryString()))
           && (this.tagName.equals(that.getTagName()))
           && (Double.doubleToLongBits(this.score) == Double.doubleToLongBits(that.getScore()));
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= this.matchedWords.hashCode();
    h *= 1000003;
    h ^= this.queryString.hashCode();
    h *= 1000003;
    h ^= this.tagName.hashCode();
    h *= 1000003;
    h ^= (Double.doubleToLongBits(this.score) >>> 32) ^ Double.doubleToLongBits(this.score);
    return h;
  }

}
