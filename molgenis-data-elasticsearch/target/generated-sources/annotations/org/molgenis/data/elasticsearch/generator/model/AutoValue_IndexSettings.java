
package org.molgenis.data.elasticsearch.generator.model;

import javax.annotation.Generated;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_IndexSettings extends IndexSettings {

  private final int numberOfShards;
  private final int numberOfReplicas;

  private AutoValue_IndexSettings(
      int numberOfShards,
      int numberOfReplicas) {
    this.numberOfShards = numberOfShards;
    this.numberOfReplicas = numberOfReplicas;
  }

  @Override
  public int getNumberOfShards() {
    return numberOfShards;
  }

  @Override
  public int getNumberOfReplicas() {
    return numberOfReplicas;
  }

  @Override
  public String toString() {
    return "IndexSettings{"
        + "numberOfShards=" + numberOfShards + ", "
        + "numberOfReplicas=" + numberOfReplicas
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof IndexSettings) {
      IndexSettings that = (IndexSettings) o;
      return (this.numberOfShards == that.getNumberOfShards())
           && (this.numberOfReplicas == that.getNumberOfReplicas());
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= this.numberOfShards;
    h *= 1000003;
    h ^= this.numberOfReplicas;
    return h;
  }

  static final class Builder extends IndexSettings.Builder {
    private Integer numberOfShards;
    private Integer numberOfReplicas;
    Builder() {
    }
    @Override
    public IndexSettings.Builder setNumberOfShards(int numberOfShards) {
      this.numberOfShards = numberOfShards;
      return this;
    }
    @Override
    public IndexSettings.Builder setNumberOfReplicas(int numberOfReplicas) {
      this.numberOfReplicas = numberOfReplicas;
      return this;
    }
    @Override
    public IndexSettings build() {
      String missing = "";
      if (this.numberOfShards == null) {
        missing += " numberOfShards";
      }
      if (this.numberOfReplicas == null) {
        missing += " numberOfReplicas";
      }
      if (!missing.isEmpty()) {
        throw new IllegalStateException("Missing required properties:" + missing);
      }
      return new AutoValue_IndexSettings(
          this.numberOfShards,
          this.numberOfReplicas);
    }
  }

}
