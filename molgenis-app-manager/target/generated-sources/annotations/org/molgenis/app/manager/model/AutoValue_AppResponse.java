
package org.molgenis.app.manager.model;

import javax.annotation.Generated;
import javax.annotation.Nullable;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_AppResponse extends AppResponse {

  private final String id;
  private final String uri;
  private final String label;
  private final String description;
  private final boolean isActive;
  private final boolean includeMenuAndFooter;
  private final String templateContent;
  private final String version;
  private final String resourceFolder;
  private final String appConfig;

  AutoValue_AppResponse(
      String id,
      String uri,
      String label,
      String description,
      boolean isActive,
      boolean includeMenuAndFooter,
      String templateContent,
      String version,
      String resourceFolder,
      @Nullable String appConfig) {
    if (id == null) {
      throw new NullPointerException("Null id");
    }
    this.id = id;
    if (uri == null) {
      throw new NullPointerException("Null uri");
    }
    this.uri = uri;
    if (label == null) {
      throw new NullPointerException("Null label");
    }
    this.label = label;
    if (description == null) {
      throw new NullPointerException("Null description");
    }
    this.description = description;
    this.isActive = isActive;
    this.includeMenuAndFooter = includeMenuAndFooter;
    if (templateContent == null) {
      throw new NullPointerException("Null templateContent");
    }
    this.templateContent = templateContent;
    if (version == null) {
      throw new NullPointerException("Null version");
    }
    this.version = version;
    if (resourceFolder == null) {
      throw new NullPointerException("Null resourceFolder");
    }
    this.resourceFolder = resourceFolder;
    this.appConfig = appConfig;
  }

  @Override
  public String getId() {
    return id;
  }

  @Override
  public String getUri() {
    return uri;
  }

  @Override
  public String getLabel() {
    return label;
  }

  @Override
  public String getDescription() {
    return description;
  }

  @Override
  public boolean getIsActive() {
    return isActive;
  }

  @Override
  public boolean getIncludeMenuAndFooter() {
    return includeMenuAndFooter;
  }

  @Override
  public String getTemplateContent() {
    return templateContent;
  }

  @Override
  public String getVersion() {
    return version;
  }

  @Override
  public String getResourceFolder() {
    return resourceFolder;
  }

  @Nullable
  @Override
  public String getAppConfig() {
    return appConfig;
  }

  @Override
  public String toString() {
    return "AppResponse{"
        + "id=" + id + ", "
        + "uri=" + uri + ", "
        + "label=" + label + ", "
        + "description=" + description + ", "
        + "isActive=" + isActive + ", "
        + "includeMenuAndFooter=" + includeMenuAndFooter + ", "
        + "templateContent=" + templateContent + ", "
        + "version=" + version + ", "
        + "resourceFolder=" + resourceFolder + ", "
        + "appConfig=" + appConfig
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof AppResponse) {
      AppResponse that = (AppResponse) o;
      return (this.id.equals(that.getId()))
           && (this.uri.equals(that.getUri()))
           && (this.label.equals(that.getLabel()))
           && (this.description.equals(that.getDescription()))
           && (this.isActive == that.getIsActive())
           && (this.includeMenuAndFooter == that.getIncludeMenuAndFooter())
           && (this.templateContent.equals(that.getTemplateContent()))
           && (this.version.equals(that.getVersion()))
           && (this.resourceFolder.equals(that.getResourceFolder()))
           && ((this.appConfig == null) ? (that.getAppConfig() == null) : this.appConfig.equals(that.getAppConfig()));
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= this.id.hashCode();
    h *= 1000003;
    h ^= this.uri.hashCode();
    h *= 1000003;
    h ^= this.label.hashCode();
    h *= 1000003;
    h ^= this.description.hashCode();
    h *= 1000003;
    h ^= this.isActive ? 1231 : 1237;
    h *= 1000003;
    h ^= this.includeMenuAndFooter ? 1231 : 1237;
    h *= 1000003;
    h ^= this.templateContent.hashCode();
    h *= 1000003;
    h ^= this.version.hashCode();
    h *= 1000003;
    h ^= this.resourceFolder.hashCode();
    h *= 1000003;
    h ^= (appConfig == null) ? 0 : this.appConfig.hashCode();
    return h;
  }

}
