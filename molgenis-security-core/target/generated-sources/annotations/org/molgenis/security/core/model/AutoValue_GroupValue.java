
package org.molgenis.security.core.model;

import com.google.common.collect.ImmutableList;
import javax.annotation.Generated;
import javax.annotation.Nullable;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_GroupValue extends GroupValue {

  private final String name;
  private final String label;
  private final String description;
  private final boolean public0;
  private final ImmutableList<RoleValue> roles;
  private final PackageValue rootPackage;

  private AutoValue_GroupValue(
      String name,
      String label,
      @Nullable String description,
      boolean public0,
      ImmutableList<RoleValue> roles,
      PackageValue rootPackage) {
    this.name = name;
    this.label = label;
    this.description = description;
    this.public0 = public0;
    this.roles = roles;
    this.rootPackage = rootPackage;
  }

  @Override
  public String getName() {
    return name;
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
  public boolean isPublic() {
    return public0;
  }

  @Override
  public ImmutableList<RoleValue> getRoles() {
    return roles;
  }

  @Override
  public PackageValue getRootPackage() {
    return rootPackage;
  }

  @Override
  public String toString() {
    return "GroupValue{"
        + "name=" + name + ", "
        + "label=" + label + ", "
        + "description=" + description + ", "
        + "public=" + public0 + ", "
        + "roles=" + roles + ", "
        + "rootPackage=" + rootPackage
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof GroupValue) {
      GroupValue that = (GroupValue) o;
      return (this.name.equals(that.getName()))
           && (this.label.equals(that.getLabel()))
           && ((this.description == null) ? (that.getDescription() == null) : this.description.equals(that.getDescription()))
           && (this.public0 == that.isPublic())
           && (this.roles.equals(that.getRoles()))
           && (this.rootPackage.equals(that.getRootPackage()));
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= this.name.hashCode();
    h *= 1000003;
    h ^= this.label.hashCode();
    h *= 1000003;
    h ^= (description == null) ? 0 : this.description.hashCode();
    h *= 1000003;
    h ^= this.public0 ? 1231 : 1237;
    h *= 1000003;
    h ^= this.roles.hashCode();
    h *= 1000003;
    h ^= this.rootPackage.hashCode();
    return h;
  }

  static final class Builder extends GroupValue.Builder {
    private String name;
    private String label;
    private String description;
    private Boolean public0;
    private ImmutableList.Builder<RoleValue> rolesBuilder$;
    private ImmutableList<RoleValue> roles;
    private PackageValue rootPackage;
    Builder() {
    }
    @Override
    public GroupValue.Builder setName(String name) {
      if (name == null) {
        throw new NullPointerException("Null name");
      }
      this.name = name;
      return this;
    }
    @Override
    public GroupValue.Builder setLabel(String label) {
      if (label == null) {
        throw new NullPointerException("Null label");
      }
      this.label = label;
      return this;
    }
    @Override
    public GroupValue.Builder setDescription(@Nullable String description) {
      this.description = description;
      return this;
    }
    @Override
    public GroupValue.Builder setPublic(boolean public0) {
      this.public0 = public0;
      return this;
    }
    @Override
    public ImmutableList.Builder<RoleValue> rolesBuilder() {
      if (rolesBuilder$ == null) {
        rolesBuilder$ = ImmutableList.builder();
      }
      return rolesBuilder$;
    }
    @Override
    public GroupValue.Builder setRootPackage(PackageValue rootPackage) {
      if (rootPackage == null) {
        throw new NullPointerException("Null rootPackage");
      }
      this.rootPackage = rootPackage;
      return this;
    }
    @Override
    public GroupValue build() {
      if (rolesBuilder$ != null) {
        this.roles = rolesBuilder$.build();
      } else if (this.roles == null) {
        this.roles = ImmutableList.of();
      }
      String missing = "";
      if (this.name == null) {
        missing += " name";
      }
      if (this.label == null) {
        missing += " label";
      }
      if (this.public0 == null) {
        missing += " public";
      }
      if (this.rootPackage == null) {
        missing += " rootPackage";
      }
      if (!missing.isEmpty()) {
        throw new IllegalStateException("Missing required properties:" + missing);
      }
      return new AutoValue_GroupValue(
          this.name,
          this.label,
          this.description,
          this.public0,
          this.roles,
          this.rootPackage);
    }
  }

}
