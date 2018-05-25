
package org.molgenis.semanticmapper.controller;

import javax.annotation.Generated;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_FirstAttributeMappingInfo extends FirstAttributeMappingInfo {

  private final String mappingProjectId;
  private final String target;
  private final String source;
  private final String targetAttribute;

  AutoValue_FirstAttributeMappingInfo(
      String mappingProjectId,
      String target,
      String source,
      String targetAttribute) {
    if (mappingProjectId == null) {
      throw new NullPointerException("Null mappingProjectId");
    }
    this.mappingProjectId = mappingProjectId;
    if (target == null) {
      throw new NullPointerException("Null target");
    }
    this.target = target;
    if (source == null) {
      throw new NullPointerException("Null source");
    }
    this.source = source;
    if (targetAttribute == null) {
      throw new NullPointerException("Null targetAttribute");
    }
    this.targetAttribute = targetAttribute;
  }

  @Override
  public String getMappingProjectId() {
    return mappingProjectId;
  }

  @Override
  public String getTarget() {
    return target;
  }

  @Override
  public String getSource() {
    return source;
  }

  @Override
  public String getTargetAttribute() {
    return targetAttribute;
  }

  @Override
  public String toString() {
    return "FirstAttributeMappingInfo{"
        + "mappingProjectId=" + mappingProjectId + ", "
        + "target=" + target + ", "
        + "source=" + source + ", "
        + "targetAttribute=" + targetAttribute
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof FirstAttributeMappingInfo) {
      FirstAttributeMappingInfo that = (FirstAttributeMappingInfo) o;
      return (this.mappingProjectId.equals(that.getMappingProjectId()))
           && (this.target.equals(that.getTarget()))
           && (this.source.equals(that.getSource()))
           && (this.targetAttribute.equals(that.getTargetAttribute()));
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= this.mappingProjectId.hashCode();
    h *= 1000003;
    h ^= this.target.hashCode();
    h *= 1000003;
    h ^= this.source.hashCode();
    h *= 1000003;
    h ^= this.targetAttribute.hashCode();
    return h;
  }

}
