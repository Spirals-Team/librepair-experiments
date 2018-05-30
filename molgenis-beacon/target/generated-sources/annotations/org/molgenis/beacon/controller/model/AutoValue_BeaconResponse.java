
package org.molgenis.beacon.controller.model;

import java.util.List;
import javax.annotation.Generated;
import javax.annotation.Nullable;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_BeaconResponse extends BeaconResponse {

  private final String id;
  private final String name;
  private final String apiVersion;
  private final BeaconOrganizationResponse organization;
  private final String description;
  private final String version;
  private final String welcomeUrl;
  private final List<BeaconDatasetResponse> datasets;

  AutoValue_BeaconResponse(
      String id,
      String name,
      String apiVersion,
      @Nullable BeaconOrganizationResponse organization,
      @Nullable String description,
      @Nullable String version,
      @Nullable String welcomeUrl,
      List<BeaconDatasetResponse> datasets) {
    if (id == null) {
      throw new NullPointerException("Null id");
    }
    this.id = id;
    if (name == null) {
      throw new NullPointerException("Null name");
    }
    this.name = name;
    if (apiVersion == null) {
      throw new NullPointerException("Null apiVersion");
    }
    this.apiVersion = apiVersion;
    this.organization = organization;
    this.description = description;
    this.version = version;
    this.welcomeUrl = welcomeUrl;
    if (datasets == null) {
      throw new NullPointerException("Null datasets");
    }
    this.datasets = datasets;
  }

  @Override
  public String getId() {
    return id;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public String getApiVersion() {
    return apiVersion;
  }

  @Nullable
  @Override
  public BeaconOrganizationResponse getOrganization() {
    return organization;
  }

  @Nullable
  @Override
  public String getDescription() {
    return description;
  }

  @Nullable
  @Override
  public String getVersion() {
    return version;
  }

  @Nullable
  @Override
  public String getWelcomeUrl() {
    return welcomeUrl;
  }

  @Override
  public List<BeaconDatasetResponse> getDatasets() {
    return datasets;
  }

  @Override
  public String toString() {
    return "BeaconResponse{"
        + "id=" + id + ", "
        + "name=" + name + ", "
        + "apiVersion=" + apiVersion + ", "
        + "organization=" + organization + ", "
        + "description=" + description + ", "
        + "version=" + version + ", "
        + "welcomeUrl=" + welcomeUrl + ", "
        + "datasets=" + datasets
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof BeaconResponse) {
      BeaconResponse that = (BeaconResponse) o;
      return (this.id.equals(that.getId()))
           && (this.name.equals(that.getName()))
           && (this.apiVersion.equals(that.getApiVersion()))
           && ((this.organization == null) ? (that.getOrganization() == null) : this.organization.equals(that.getOrganization()))
           && ((this.description == null) ? (that.getDescription() == null) : this.description.equals(that.getDescription()))
           && ((this.version == null) ? (that.getVersion() == null) : this.version.equals(that.getVersion()))
           && ((this.welcomeUrl == null) ? (that.getWelcomeUrl() == null) : this.welcomeUrl.equals(that.getWelcomeUrl()))
           && (this.datasets.equals(that.getDatasets()));
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
    h ^= this.apiVersion.hashCode();
    h *= 1000003;
    h ^= (organization == null) ? 0 : this.organization.hashCode();
    h *= 1000003;
    h ^= (description == null) ? 0 : this.description.hashCode();
    h *= 1000003;
    h ^= (version == null) ? 0 : this.version.hashCode();
    h *= 1000003;
    h ^= (welcomeUrl == null) ? 0 : this.welcomeUrl.hashCode();
    h *= 1000003;
    h ^= this.datasets.hashCode();
    return h;
  }

}
