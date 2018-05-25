
package org.molgenis.searchall.model;

import com.google.common.collect.ImmutableList;
import java.util.List;
import javax.annotation.Generated;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_Result extends Result {

  private final ImmutableList<EntityTypeResult> entityTypes;
  private final ImmutableList<PackageResult> packages;

  private AutoValue_Result(
      ImmutableList<EntityTypeResult> entityTypes,
      ImmutableList<PackageResult> packages) {
    this.entityTypes = entityTypes;
    this.packages = packages;
  }

  @Override
  public ImmutableList<EntityTypeResult> getEntityTypes() {
    return entityTypes;
  }

  @Override
  public ImmutableList<PackageResult> getPackages() {
    return packages;
  }

  @Override
  public String toString() {
    return "Result{"
        + "entityTypes=" + entityTypes + ", "
        + "packages=" + packages
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof Result) {
      Result that = (Result) o;
      return (this.entityTypes.equals(that.getEntityTypes()))
           && (this.packages.equals(that.getPackages()));
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= this.entityTypes.hashCode();
    h *= 1000003;
    h ^= this.packages.hashCode();
    return h;
  }

  static final class Builder extends Result.Builder {
    private ImmutableList<EntityTypeResult> entityTypes;
    private ImmutableList<PackageResult> packages;
    Builder() {
    }
    @Override
    public Result.Builder setEntityTypes(List<EntityTypeResult> entityTypes) {
      if (entityTypes == null) {
        throw new NullPointerException("Null entityTypes");
      }
      this.entityTypes = ImmutableList.copyOf(entityTypes);
      return this;
    }
    @Override
    public Result.Builder setPackages(List<PackageResult> packages) {
      if (packages == null) {
        throw new NullPointerException("Null packages");
      }
      this.packages = ImmutableList.copyOf(packages);
      return this;
    }
    @Override
    public Result build() {
      String missing = "";
      if (this.entityTypes == null) {
        missing += " entityTypes";
      }
      if (this.packages == null) {
        missing += " packages";
      }
      if (!missing.isEmpty()) {
        throw new IllegalStateException("Missing required properties:" + missing);
      }
      return new AutoValue_Result(
          this.entityTypes,
          this.packages);
    }
  }

}
