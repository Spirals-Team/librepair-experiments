
package org.molgenis.beacon.controller.model;

import javax.annotation.Generated;
import javax.annotation.Nullable;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_BeaconOrganizationResponse extends BeaconOrganizationResponse {

  private final String id;
  private final String name;
  private final String description;
  private final String address;
  private final String welcomeUrl;
  private final String contactUrl;
  private final String logoUrl;

  AutoValue_BeaconOrganizationResponse(
      String id,
      String name,
      @Nullable String description,
      @Nullable String address,
      @Nullable String welcomeUrl,
      @Nullable String contactUrl,
      @Nullable String logoUrl) {
    if (id == null) {
      throw new NullPointerException("Null id");
    }
    this.id = id;
    if (name == null) {
      throw new NullPointerException("Null name");
    }
    this.name = name;
    this.description = description;
    this.address = address;
    this.welcomeUrl = welcomeUrl;
    this.contactUrl = contactUrl;
    this.logoUrl = logoUrl;
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

  @Nullable
  @Override
  public String getAddress() {
    return address;
  }

  @Nullable
  @Override
  public String getWelcomeUrl() {
    return welcomeUrl;
  }

  @Nullable
  @Override
  public String getContactUrl() {
    return contactUrl;
  }

  @Nullable
  @Override
  public String getLogoUrl() {
    return logoUrl;
  }

  @Override
  public String toString() {
    return "BeaconOrganizationResponse{"
        + "id=" + id + ", "
        + "name=" + name + ", "
        + "description=" + description + ", "
        + "address=" + address + ", "
        + "welcomeUrl=" + welcomeUrl + ", "
        + "contactUrl=" + contactUrl + ", "
        + "logoUrl=" + logoUrl
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof BeaconOrganizationResponse) {
      BeaconOrganizationResponse that = (BeaconOrganizationResponse) o;
      return (this.id.equals(that.getId()))
           && (this.name.equals(that.getName()))
           && ((this.description == null) ? (that.getDescription() == null) : this.description.equals(that.getDescription()))
           && ((this.address == null) ? (that.getAddress() == null) : this.address.equals(that.getAddress()))
           && ((this.welcomeUrl == null) ? (that.getWelcomeUrl() == null) : this.welcomeUrl.equals(that.getWelcomeUrl()))
           && ((this.contactUrl == null) ? (that.getContactUrl() == null) : this.contactUrl.equals(that.getContactUrl()))
           && ((this.logoUrl == null) ? (that.getLogoUrl() == null) : this.logoUrl.equals(that.getLogoUrl()));
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
    h ^= (address == null) ? 0 : this.address.hashCode();
    h *= 1000003;
    h ^= (welcomeUrl == null) ? 0 : this.welcomeUrl.hashCode();
    h *= 1000003;
    h ^= (contactUrl == null) ? 0 : this.contactUrl.hashCode();
    h *= 1000003;
    h ^= (logoUrl == null) ? 0 : this.logoUrl.hashCode();
    return h;
  }

}
