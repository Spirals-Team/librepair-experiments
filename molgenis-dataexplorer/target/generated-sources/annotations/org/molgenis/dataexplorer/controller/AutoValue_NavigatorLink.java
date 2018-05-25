
package org.molgenis.dataexplorer.controller;

import javax.annotation.Generated;
import javax.annotation.Nullable;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_NavigatorLink extends NavigatorLink {

  private final String href;
  private final String label;

  AutoValue_NavigatorLink(
      String href,
      @Nullable String label) {
    if (href == null) {
      throw new NullPointerException("Null href");
    }
    this.href = href;
    this.label = label;
  }

  @Override
  public String getHref() {
    return href;
  }

  @Nullable
  @Override
  public String getLabel() {
    return label;
  }

  @Override
  public String toString() {
    return "NavigatorLink{"
        + "href=" + href + ", "
        + "label=" + label
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof NavigatorLink) {
      NavigatorLink that = (NavigatorLink) o;
      return (this.href.equals(that.getHref()))
           && ((this.label == null) ? (that.getLabel() == null) : this.label.equals(that.getLabel()));
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= this.href.hashCode();
    h *= 1000003;
    h ^= (label == null) ? 0 : this.label.hashCode();
    return h;
  }

}
