
package org.molgenis.metadata.manager.model;

import java.util.List;
import java.util.Map;
import javax.annotation.Generated;
import javax.annotation.Nullable;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_EditorAttribute extends EditorAttribute {

  private final String id;
  private final String name;
  private final String type;
  private final EditorAttributeIdentifier parent;
  private final EditorEntityTypeIdentifier refEntityType;
  private final EditorAttributeIdentifier mappedByAttribute;
  private final EditorSort orderBy;
  private final String expression;
  private final boolean nullable;
  private final boolean auto;
  private final boolean visible;
  private final String label;
  private final Map<String, String> labelI18n;
  private final String description;
  private final Map<String, String> descriptionI18n;
  private final boolean aggregatable;
  private final List<String> enumOptions;
  private final Long rangeMin;
  private final Long rangeMax;
  private final boolean readonly;
  private final boolean unique;
  private final List<EditorTagIdentifier> tags;
  private final String nullableExpression;
  private final String visibleExpression;
  private final String validationExpression;
  private final String defaultValue;
  private final Integer sequenceNumber;

  AutoValue_EditorAttribute(
      String id,
      @Nullable String name,
      @Nullable String type,
      @Nullable EditorAttributeIdentifier parent,
      @Nullable EditorEntityTypeIdentifier refEntityType,
      @Nullable EditorAttributeIdentifier mappedByAttribute,
      @Nullable EditorSort orderBy,
      @Nullable String expression,
      boolean nullable,
      boolean auto,
      boolean visible,
      @Nullable String label,
      Map<String, String> labelI18n,
      @Nullable String description,
      Map<String, String> descriptionI18n,
      boolean aggregatable,
      @Nullable List<String> enumOptions,
      @Nullable Long rangeMin,
      @Nullable Long rangeMax,
      boolean readonly,
      boolean unique,
      List<EditorTagIdentifier> tags,
      @Nullable String nullableExpression,
      @Nullable String visibleExpression,
      @Nullable String validationExpression,
      @Nullable String defaultValue,
      Integer sequenceNumber) {
    if (id == null) {
      throw new NullPointerException("Null id");
    }
    this.id = id;
    this.name = name;
    this.type = type;
    this.parent = parent;
    this.refEntityType = refEntityType;
    this.mappedByAttribute = mappedByAttribute;
    this.orderBy = orderBy;
    this.expression = expression;
    this.nullable = nullable;
    this.auto = auto;
    this.visible = visible;
    this.label = label;
    if (labelI18n == null) {
      throw new NullPointerException("Null labelI18n");
    }
    this.labelI18n = labelI18n;
    this.description = description;
    if (descriptionI18n == null) {
      throw new NullPointerException("Null descriptionI18n");
    }
    this.descriptionI18n = descriptionI18n;
    this.aggregatable = aggregatable;
    this.enumOptions = enumOptions;
    this.rangeMin = rangeMin;
    this.rangeMax = rangeMax;
    this.readonly = readonly;
    this.unique = unique;
    if (tags == null) {
      throw new NullPointerException("Null tags");
    }
    this.tags = tags;
    this.nullableExpression = nullableExpression;
    this.visibleExpression = visibleExpression;
    this.validationExpression = validationExpression;
    this.defaultValue = defaultValue;
    if (sequenceNumber == null) {
      throw new NullPointerException("Null sequenceNumber");
    }
    this.sequenceNumber = sequenceNumber;
  }

  @Override
  public String getId() {
    return id;
  }

  @Nullable
  @Override
  public String getName() {
    return name;
  }

  @Nullable
  @Override
  public String getType() {
    return type;
  }

  @Nullable
  @Override
  public EditorAttributeIdentifier getParent() {
    return parent;
  }

  @Nullable
  @Override
  public EditorEntityTypeIdentifier getRefEntityType() {
    return refEntityType;
  }

  @Nullable
  @Override
  public EditorAttributeIdentifier getMappedByAttribute() {
    return mappedByAttribute;
  }

  @Nullable
  @Override
  public EditorSort getOrderBy() {
    return orderBy;
  }

  @Nullable
  @Override
  public String getExpression() {
    return expression;
  }

  @Override
  public boolean isNullable() {
    return nullable;
  }

  @Override
  public boolean isAuto() {
    return auto;
  }

  @Override
  public boolean isVisible() {
    return visible;
  }

  @Nullable
  @Override
  public String getLabel() {
    return label;
  }

  @Override
  public Map<String, String> getLabelI18n() {
    return labelI18n;
  }

  @Nullable
  @Override
  public String getDescription() {
    return description;
  }

  @Override
  public Map<String, String> getDescriptionI18n() {
    return descriptionI18n;
  }

  @Override
  public boolean isAggregatable() {
    return aggregatable;
  }

  @Nullable
  @Override
  public List<String> getEnumOptions() {
    return enumOptions;
  }

  @Nullable
  @Override
  public Long getRangeMin() {
    return rangeMin;
  }

  @Nullable
  @Override
  public Long getRangeMax() {
    return rangeMax;
  }

  @Override
  public boolean isReadonly() {
    return readonly;
  }

  @Override
  public boolean isUnique() {
    return unique;
  }

  @Override
  public List<EditorTagIdentifier> getTags() {
    return tags;
  }

  @Nullable
  @Override
  public String getNullableExpression() {
    return nullableExpression;
  }

  @Nullable
  @Override
  public String getVisibleExpression() {
    return visibleExpression;
  }

  @Nullable
  @Override
  public String getValidationExpression() {
    return validationExpression;
  }

  @Nullable
  @Override
  public String getDefaultValue() {
    return defaultValue;
  }

  @Override
  public Integer getSequenceNumber() {
    return sequenceNumber;
  }

  @Override
  public String toString() {
    return "EditorAttribute{"
        + "id=" + id + ", "
        + "name=" + name + ", "
        + "type=" + type + ", "
        + "parent=" + parent + ", "
        + "refEntityType=" + refEntityType + ", "
        + "mappedByAttribute=" + mappedByAttribute + ", "
        + "orderBy=" + orderBy + ", "
        + "expression=" + expression + ", "
        + "nullable=" + nullable + ", "
        + "auto=" + auto + ", "
        + "visible=" + visible + ", "
        + "label=" + label + ", "
        + "labelI18n=" + labelI18n + ", "
        + "description=" + description + ", "
        + "descriptionI18n=" + descriptionI18n + ", "
        + "aggregatable=" + aggregatable + ", "
        + "enumOptions=" + enumOptions + ", "
        + "rangeMin=" + rangeMin + ", "
        + "rangeMax=" + rangeMax + ", "
        + "readonly=" + readonly + ", "
        + "unique=" + unique + ", "
        + "tags=" + tags + ", "
        + "nullableExpression=" + nullableExpression + ", "
        + "visibleExpression=" + visibleExpression + ", "
        + "validationExpression=" + validationExpression + ", "
        + "defaultValue=" + defaultValue + ", "
        + "sequenceNumber=" + sequenceNumber
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof EditorAttribute) {
      EditorAttribute that = (EditorAttribute) o;
      return (this.id.equals(that.getId()))
           && ((this.name == null) ? (that.getName() == null) : this.name.equals(that.getName()))
           && ((this.type == null) ? (that.getType() == null) : this.type.equals(that.getType()))
           && ((this.parent == null) ? (that.getParent() == null) : this.parent.equals(that.getParent()))
           && ((this.refEntityType == null) ? (that.getRefEntityType() == null) : this.refEntityType.equals(that.getRefEntityType()))
           && ((this.mappedByAttribute == null) ? (that.getMappedByAttribute() == null) : this.mappedByAttribute.equals(that.getMappedByAttribute()))
           && ((this.orderBy == null) ? (that.getOrderBy() == null) : this.orderBy.equals(that.getOrderBy()))
           && ((this.expression == null) ? (that.getExpression() == null) : this.expression.equals(that.getExpression()))
           && (this.nullable == that.isNullable())
           && (this.auto == that.isAuto())
           && (this.visible == that.isVisible())
           && ((this.label == null) ? (that.getLabel() == null) : this.label.equals(that.getLabel()))
           && (this.labelI18n.equals(that.getLabelI18n()))
           && ((this.description == null) ? (that.getDescription() == null) : this.description.equals(that.getDescription()))
           && (this.descriptionI18n.equals(that.getDescriptionI18n()))
           && (this.aggregatable == that.isAggregatable())
           && ((this.enumOptions == null) ? (that.getEnumOptions() == null) : this.enumOptions.equals(that.getEnumOptions()))
           && ((this.rangeMin == null) ? (that.getRangeMin() == null) : this.rangeMin.equals(that.getRangeMin()))
           && ((this.rangeMax == null) ? (that.getRangeMax() == null) : this.rangeMax.equals(that.getRangeMax()))
           && (this.readonly == that.isReadonly())
           && (this.unique == that.isUnique())
           && (this.tags.equals(that.getTags()))
           && ((this.nullableExpression == null) ? (that.getNullableExpression() == null) : this.nullableExpression.equals(that.getNullableExpression()))
           && ((this.visibleExpression == null) ? (that.getVisibleExpression() == null) : this.visibleExpression.equals(that.getVisibleExpression()))
           && ((this.validationExpression == null) ? (that.getValidationExpression() == null) : this.validationExpression.equals(that.getValidationExpression()))
           && ((this.defaultValue == null) ? (that.getDefaultValue() == null) : this.defaultValue.equals(that.getDefaultValue()))
           && (this.sequenceNumber.equals(that.getSequenceNumber()));
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= this.id.hashCode();
    h *= 1000003;
    h ^= (name == null) ? 0 : this.name.hashCode();
    h *= 1000003;
    h ^= (type == null) ? 0 : this.type.hashCode();
    h *= 1000003;
    h ^= (parent == null) ? 0 : this.parent.hashCode();
    h *= 1000003;
    h ^= (refEntityType == null) ? 0 : this.refEntityType.hashCode();
    h *= 1000003;
    h ^= (mappedByAttribute == null) ? 0 : this.mappedByAttribute.hashCode();
    h *= 1000003;
    h ^= (orderBy == null) ? 0 : this.orderBy.hashCode();
    h *= 1000003;
    h ^= (expression == null) ? 0 : this.expression.hashCode();
    h *= 1000003;
    h ^= this.nullable ? 1231 : 1237;
    h *= 1000003;
    h ^= this.auto ? 1231 : 1237;
    h *= 1000003;
    h ^= this.visible ? 1231 : 1237;
    h *= 1000003;
    h ^= (label == null) ? 0 : this.label.hashCode();
    h *= 1000003;
    h ^= this.labelI18n.hashCode();
    h *= 1000003;
    h ^= (description == null) ? 0 : this.description.hashCode();
    h *= 1000003;
    h ^= this.descriptionI18n.hashCode();
    h *= 1000003;
    h ^= this.aggregatable ? 1231 : 1237;
    h *= 1000003;
    h ^= (enumOptions == null) ? 0 : this.enumOptions.hashCode();
    h *= 1000003;
    h ^= (rangeMin == null) ? 0 : this.rangeMin.hashCode();
    h *= 1000003;
    h ^= (rangeMax == null) ? 0 : this.rangeMax.hashCode();
    h *= 1000003;
    h ^= this.readonly ? 1231 : 1237;
    h *= 1000003;
    h ^= this.unique ? 1231 : 1237;
    h *= 1000003;
    h ^= this.tags.hashCode();
    h *= 1000003;
    h ^= (nullableExpression == null) ? 0 : this.nullableExpression.hashCode();
    h *= 1000003;
    h ^= (visibleExpression == null) ? 0 : this.visibleExpression.hashCode();
    h *= 1000003;
    h ^= (validationExpression == null) ? 0 : this.validationExpression.hashCode();
    h *= 1000003;
    h ^= (defaultValue == null) ? 0 : this.defaultValue.hashCode();
    h *= 1000003;
    h ^= this.sequenceNumber.hashCode();
    return h;
  }

}
