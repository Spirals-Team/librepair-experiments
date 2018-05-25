
package org.molgenis.apps;

import java.net.URI;
import javax.annotation.Generated;
import javax.annotation.Nullable;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_AppInfoDto extends AppInfoDto {

  private final String id;
  private final String name;
  private final String description;
  private final boolean active;
  private final URI iconHref;

  private AutoValue_AppInfoDto(
      String id,
      String name,
      @Nullable String description,
      boolean active,
      @Nullable URI iconHref) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.active = active;
    this.iconHref = iconHref;
  }

  @Override
  public String getId() {
    return id;
  }

  @Override
  public String getName() {
    return name;
  }

  @Nullable
  @Override
  public String getDescription() {
    return description;
  }

  @Override
  public boolean isActive() {
    return active;
  }

  @Nullable
  @Override
  public URI getIconHref() {
    return iconHref;
  }

  @Override
  public String toString() {
    return "AppInfoDto{"
        + "id=" + id + ", "
        + "name=" + name + ", "
        + "description=" + description + ", "
        + "active=" + active + ", "
        + "iconHref=" + iconHref
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof AppInfoDto) {
      AppInfoDto that = (AppInfoDto) o;
      return (this.id.equals(that.getId()))
           && (this.name.equals(that.getName()))
           && ((this.description == null) ? (that.getDescription() == null) : this.description.equals(that.getDescription()))
           && (this.active == that.isActive())
           && ((this.iconHref == null) ? (that.getIconHref() == null) : this.iconHref.equals(that.getIconHref()));
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= this.id.hashCode();
    h *= 1000003;
    h ^= this.name.hashCode();
    h *= 1000003;
    h ^= (description == null) ? 0 : this.description.hashCode();
    h *= 1000003;
    h ^= this.active ? 1231 : 1237;
    h *= 1000003;
    h ^= (iconHref == null) ? 0 : this.iconHref.hashCode();
    return h;
  }

  static final class Builder extends AppInfoDto.Builder {
    private String id;
    private String name;
    private String description;
    private Boolean active;
    private URI iconHref;
    Builder() {
    }
    @Override
    public AppInfoDto.Builder setId(String id) {
      if (id == null) {
        throw new NullPointerException("Null id");
      }
      this.id = id;
      return this;
    }
    @Override
    public AppInfoDto.Builder setName(String name) {
      if (name == null) {
        throw new NullPointerException("Null name");
      }
      this.name = name;
      return this;
    }
    @Override
    public AppInfoDto.Builder setDescription(@Nullable String description) {
      this.description = description;
      return this;
    }
    @Override
    public AppInfoDto.Builder setActive(boolean active) {
      this.active = active;
      return this;
    }
    @Override
    public AppInfoDto.Builder setIconHref(@Nullable URI iconHref) {
      this.iconHref = iconHref;
      return this;
    }
    @Override
    public AppInfoDto build() {
      String missing = "";
      if (this.id == null) {
        missing += " id";
      }
      if (this.name == null) {
        missing += " name";
      }
      if (this.active == null) {
        missing += " active";
      }
      if (!missing.isEmpty()) {
        throw new IllegalStateException("Missing required properties:" + missing);
      }
      return new AutoValue_AppInfoDto(
          this.id,
          this.name,
          this.description,
          this.active,
          this.iconHref);
    }
  }

}
