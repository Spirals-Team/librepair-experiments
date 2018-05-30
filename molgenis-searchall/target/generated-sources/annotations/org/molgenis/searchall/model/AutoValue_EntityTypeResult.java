
package org.molgenis.searchall.model;

import com.google.common.collect.ImmutableList;
import java.util.List;
import javax.annotation.Generated;
import javax.annotation.Nullable;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_EntityTypeResult extends EntityTypeResult {

  private final String label;
  private final String description;
  private final String id;
  private final String packageId;
  private final boolean labelMatch;
  private final boolean descriptionMatch;
  private final ImmutableList<AttributeResult> attributes;
  private final long nrOfMatchingEntities;

  private AutoValue_EntityTypeResult(
      String label,
      @Nullable String description,
      String id,
      @Nullable String packageId,
      boolean labelMatch,
      boolean descriptionMatch,
      ImmutableList<AttributeResult> attributes,
      long nrOfMatchingEntities) {
    this.label = label;
    this.description = description;
    this.id = id;
    this.packageId = packageId;
    this.labelMatch = labelMatch;
    this.descriptionMatch = descriptionMatch;
    this.attributes = attributes;
    this.nrOfMatchingEntities = nrOfMatchingEntities;
  }

  @Override
  public String getLabel() {
    return label;
  }

  @Nullable
  @Override
  public String getDescription() {
    return description;
  }

  @Override
  public String getId() {
    return id;
  }

  @Nullable
  @Override
  public String getPackageId() {
    return packageId;
  }

  @Override
  public boolean isLabelMatch() {
    return labelMatch;
  }

  @Override
  public boolean isDescriptionMatch() {
    return descriptionMatch;
  }

  @Override
  public ImmutableList<AttributeResult> getAttributes() {
    return attributes;
  }

  @Override
  public long getNrOfMatchingEntities() {
    return nrOfMatchingEntities;
  }

  @Override
  public String toString() {
    return "EntityTypeResult{"
        + "label=" + label + ", "
        + "description=" + description + ", "
        + "id=" + id + ", "
        + "packageId=" + packageId + ", "
        + "labelMatch=" + labelMatch + ", "
        + "descriptionMatch=" + descriptionMatch + ", "
        + "attributes=" + attributes + ", "
        + "nrOfMatchingEntities=" + nrOfMatchingEntities
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof EntityTypeResult) {
      EntityTypeResult that = (EntityTypeResult) o;
      return (this.label.equals(that.getLabel()))
           && ((this.description == null) ? (that.getDescription() == null) : this.description.equals(that.getDescription()))
           && (this.id.equals(that.getId()))
           && ((this.packageId == null) ? (that.getPackageId() == null) : this.packageId.equals(that.getPackageId()))
           && (this.labelMatch == that.isLabelMatch())
           && (this.descriptionMatch == that.isDescriptionMatch())
           && (this.attributes.equals(that.getAttributes()))
           && (this.nrOfMatchingEntities == that.getNrOfMatchingEntities());
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= this.label.hashCode();
    h *= 1000003;
    h ^= (description == null) ? 0 : this.description.hashCode();
    h *= 1000003;
    h ^= this.id.hashCode();
    h *= 1000003;
    h ^= (packageId == null) ? 0 : this.packageId.hashCode();
    h *= 1000003;
    h ^= this.labelMatch ? 1231 : 1237;
    h *= 1000003;
    h ^= this.descriptionMatch ? 1231 : 1237;
    h *= 1000003;
    h ^= this.attributes.hashCode();
    h *= 1000003;
    h ^= (this.nrOfMatchingEntities >>> 32) ^ this.nrOfMatchingEntities;
    return h;
  }

  static final class Builder extends EntityTypeResult.Builder {
    private String label;
    private String description;
    private String id;
    private String packageId;
    private Boolean labelMatch;
    private Boolean descriptionMatch;
    private ImmutableList<AttributeResult> attributes;
    private Long nrOfMatchingEntities;
    Builder() {
    }
    @Override
    public EntityTypeResult.Builder setLabel(String label) {
      if (label == null) {
        throw new NullPointerException("Null label");
      }
      this.label = label;
      return this;
    }
    @Override
    public EntityTypeResult.Builder setDescription(@Nullable String description) {
      this.description = description;
      return this;
    }
    @Override
    public EntityTypeResult.Builder setId(String id) {
      if (id == null) {
        throw new NullPointerException("Null id");
      }
      this.id = id;
      return this;
    }
    @Override
    public EntityTypeResult.Builder setPackageId(@Nullable String packageId) {
      this.packageId = packageId;
      return this;
    }
    @Override
    public EntityTypeResult.Builder setLabelMatch(boolean labelMatch) {
      this.labelMatch = labelMatch;
      return this;
    }
    @Override
    public EntityTypeResult.Builder setDescriptionMatch(boolean descriptionMatch) {
      this.descriptionMatch = descriptionMatch;
      return this;
    }
    @Override
    public EntityTypeResult.Builder setAttributes(List<AttributeResult> attributes) {
      if (attributes == null) {
        throw new NullPointerException("Null attributes");
      }
      this.attributes = ImmutableList.copyOf(attributes);
      return this;
    }
    @Override
    public EntityTypeResult.Builder setNrOfMatchingEntities(long nrOfMatchingEntities) {
      this.nrOfMatchingEntities = nrOfMatchingEntities;
      return this;
    }
    @Override
    public EntityTypeResult build() {
      String missing = "";
      if (this.label == null) {
        missing += " label";
      }
      if (this.id == null) {
        missing += " id";
      }
      if (this.labelMatch == null) {
        missing += " labelMatch";
      }
      if (this.descriptionMatch == null) {
        missing += " descriptionMatch";
      }
      if (this.attributes == null) {
        missing += " attributes";
      }
      if (this.nrOfMatchingEntities == null) {
        missing += " nrOfMatchingEntities";
      }
      if (!missing.isEmpty()) {
        throw new IllegalStateException("Missing required properties:" + missing);
      }
      return new AutoValue_EntityTypeResult(
          this.label,
          this.description,
          this.id,
          this.packageId,
          this.labelMatch,
          this.descriptionMatch,
          this.attributes,
          this.nrOfMatchingEntities);
    }
  }

}
