
package com.arcao.geocaching.api.data;

import java.util.Date;
import java.util.List;
import javax.annotation.Generated;
import org.jetbrains.annotations.Nullable;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_Trackable extends Trackable {

  private final long id;
  private final String name;
  private final String goal;
  private final String description;
  private final String trackableTypeName;
  private final String trackableTypeImage;
  private final User owner;
  private final String currentCacheCode;
  private final User currentOwner;
  private final String trackingNumber;
  private final Date created;
  private final boolean allowedToBeCollected;
  private final boolean inCollection;
  private final boolean archived;
  private final List<TrackableLog> trackableLogs;
  private final List<ImageData> images;

  private AutoValue_Trackable(
      long id,
      @Nullable String name,
      @Nullable String goal,
      @Nullable String description,
      String trackableTypeName,
      String trackableTypeImage,
      @Nullable User owner,
      @Nullable String currentCacheCode,
      @Nullable User currentOwner,
      @Nullable String trackingNumber,
      Date created,
      boolean allowedToBeCollected,
      boolean inCollection,
      boolean archived,
      @Nullable List<TrackableLog> trackableLogs,
      @Nullable List<ImageData> images) {
    this.id = id;
    this.name = name;
    this.goal = goal;
    this.description = description;
    this.trackableTypeName = trackableTypeName;
    this.trackableTypeImage = trackableTypeImage;
    this.owner = owner;
    this.currentCacheCode = currentCacheCode;
    this.currentOwner = currentOwner;
    this.trackingNumber = trackingNumber;
    this.created = created;
    this.allowedToBeCollected = allowedToBeCollected;
    this.inCollection = inCollection;
    this.archived = archived;
    this.trackableLogs = trackableLogs;
    this.images = images;
  }

  @Override
  public long id() {
    return id;
  }

  @Nullable
  @Override
  public String name() {
    return name;
  }

  @Nullable
  @Override
  public String goal() {
    return goal;
  }

  @Nullable
  @Override
  public String description() {
    return description;
  }

  @Override
  public String trackableTypeName() {
    return trackableTypeName;
  }

  @Override
  public String trackableTypeImage() {
    return trackableTypeImage;
  }

  @Nullable
  @Override
  public User owner() {
    return owner;
  }

  @Nullable
  @Override
  public String currentCacheCode() {
    return currentCacheCode;
  }

  @Nullable
  @Override
  public User currentOwner() {
    return currentOwner;
  }

  @Nullable
  @Override
  public String trackingNumber() {
    return trackingNumber;
  }

  @Override
  public Date created() {
    return created;
  }

  @Override
  public boolean allowedToBeCollected() {
    return allowedToBeCollected;
  }

  @Override
  public boolean inCollection() {
    return inCollection;
  }

  @Override
  public boolean archived() {
    return archived;
  }

  @Nullable
  @Override
  public List<TrackableLog> trackableLogs() {
    return trackableLogs;
  }

  @Nullable
  @Override
  public List<ImageData> images() {
    return images;
  }

  @Override
  public String toString() {
    return "Trackable{"
        + "id=" + id + ", "
        + "name=" + name + ", "
        + "goal=" + goal + ", "
        + "description=" + description + ", "
        + "trackableTypeName=" + trackableTypeName + ", "
        + "trackableTypeImage=" + trackableTypeImage + ", "
        + "owner=" + owner + ", "
        + "currentCacheCode=" + currentCacheCode + ", "
        + "currentOwner=" + currentOwner + ", "
        + "trackingNumber=" + trackingNumber + ", "
        + "created=" + created + ", "
        + "allowedToBeCollected=" + allowedToBeCollected + ", "
        + "inCollection=" + inCollection + ", "
        + "archived=" + archived + ", "
        + "trackableLogs=" + trackableLogs + ", "
        + "images=" + images
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof Trackable) {
      Trackable that = (Trackable) o;
      return (this.id == that.id())
           && ((this.name == null) ? (that.name() == null) : this.name.equals(that.name()))
           && ((this.goal == null) ? (that.goal() == null) : this.goal.equals(that.goal()))
           && ((this.description == null) ? (that.description() == null) : this.description.equals(that.description()))
           && (this.trackableTypeName.equals(that.trackableTypeName()))
           && (this.trackableTypeImage.equals(that.trackableTypeImage()))
           && ((this.owner == null) ? (that.owner() == null) : this.owner.equals(that.owner()))
           && ((this.currentCacheCode == null) ? (that.currentCacheCode() == null) : this.currentCacheCode.equals(that.currentCacheCode()))
           && ((this.currentOwner == null) ? (that.currentOwner() == null) : this.currentOwner.equals(that.currentOwner()))
           && ((this.trackingNumber == null) ? (that.trackingNumber() == null) : this.trackingNumber.equals(that.trackingNumber()))
           && (this.created.equals(that.created()))
           && (this.allowedToBeCollected == that.allowedToBeCollected())
           && (this.inCollection == that.inCollection())
           && (this.archived == that.archived())
           && ((this.trackableLogs == null) ? (that.trackableLogs() == null) : this.trackableLogs.equals(that.trackableLogs()))
           && ((this.images == null) ? (that.images() == null) : this.images.equals(that.images()));
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= (this.id >>> 32) ^ this.id;
    h *= 1000003;
    h ^= (name == null) ? 0 : this.name.hashCode();
    h *= 1000003;
    h ^= (goal == null) ? 0 : this.goal.hashCode();
    h *= 1000003;
    h ^= (description == null) ? 0 : this.description.hashCode();
    h *= 1000003;
    h ^= this.trackableTypeName.hashCode();
    h *= 1000003;
    h ^= this.trackableTypeImage.hashCode();
    h *= 1000003;
    h ^= (owner == null) ? 0 : this.owner.hashCode();
    h *= 1000003;
    h ^= (currentCacheCode == null) ? 0 : this.currentCacheCode.hashCode();
    h *= 1000003;
    h ^= (currentOwner == null) ? 0 : this.currentOwner.hashCode();
    h *= 1000003;
    h ^= (trackingNumber == null) ? 0 : this.trackingNumber.hashCode();
    h *= 1000003;
    h ^= this.created.hashCode();
    h *= 1000003;
    h ^= this.allowedToBeCollected ? 1231 : 1237;
    h *= 1000003;
    h ^= this.inCollection ? 1231 : 1237;
    h *= 1000003;
    h ^= this.archived ? 1231 : 1237;
    h *= 1000003;
    h ^= (trackableLogs == null) ? 0 : this.trackableLogs.hashCode();
    h *= 1000003;
    h ^= (images == null) ? 0 : this.images.hashCode();
    return h;
  }

  private static final long serialVersionUID = 5984147222015866863L;

  static final class Builder extends Trackable.Builder {
    private Long id;
    private String name;
    private String goal;
    private String description;
    private String trackableTypeName;
    private String trackableTypeImage;
    private User owner;
    private String currentCacheCode;
    private User currentOwner;
    private String trackingNumber;
    private Date created;
    private Boolean allowedToBeCollected;
    private Boolean inCollection;
    private Boolean archived;
    private List<TrackableLog> trackableLogs;
    private List<ImageData> images;
    Builder() {
    }
    @Override
    public Trackable.Builder id(long id) {
      this.id = id;
      return this;
    }
    @Override
    public Trackable.Builder name(@Nullable String name) {
      this.name = name;
      return this;
    }
    @Override
    public Trackable.Builder goal(@Nullable String goal) {
      this.goal = goal;
      return this;
    }
    @Override
    public Trackable.Builder description(@Nullable String description) {
      this.description = description;
      return this;
    }
    @Override
    public Trackable.Builder trackableTypeName(String trackableTypeName) {
      if (trackableTypeName == null) {
        throw new NullPointerException("Null trackableTypeName");
      }
      this.trackableTypeName = trackableTypeName;
      return this;
    }
    @Override
    public Trackable.Builder trackableTypeImage(String trackableTypeImage) {
      if (trackableTypeImage == null) {
        throw new NullPointerException("Null trackableTypeImage");
      }
      this.trackableTypeImage = trackableTypeImage;
      return this;
    }
    @Override
    public Trackable.Builder owner(@Nullable User owner) {
      this.owner = owner;
      return this;
    }
    @Override
    public Trackable.Builder currentCacheCode(@Nullable String currentCacheCode) {
      this.currentCacheCode = currentCacheCode;
      return this;
    }
    @Override
    public Trackable.Builder currentOwner(@Nullable User currentOwner) {
      this.currentOwner = currentOwner;
      return this;
    }
    @Override
    public Trackable.Builder trackingNumber(@Nullable String trackingNumber) {
      this.trackingNumber = trackingNumber;
      return this;
    }
    @Override
    public Trackable.Builder created(Date created) {
      if (created == null) {
        throw new NullPointerException("Null created");
      }
      this.created = created;
      return this;
    }
    @Override
    public Trackable.Builder allowedToBeCollected(boolean allowedToBeCollected) {
      this.allowedToBeCollected = allowedToBeCollected;
      return this;
    }
    @Override
    public Trackable.Builder inCollection(boolean inCollection) {
      this.inCollection = inCollection;
      return this;
    }
    @Override
    public Trackable.Builder archived(boolean archived) {
      this.archived = archived;
      return this;
    }
    @Override
    public Trackable.Builder trackableLogs(@Nullable List<TrackableLog> trackableLogs) {
      this.trackableLogs = trackableLogs;
      return this;
    }
    @Override
    public Trackable.Builder images(@Nullable List<ImageData> images) {
      this.images = images;
      return this;
    }
    @Override
    public Trackable build() {
      String missing = "";
      if (this.id == null) {
        missing += " id";
      }
      if (this.trackableTypeName == null) {
        missing += " trackableTypeName";
      }
      if (this.trackableTypeImage == null) {
        missing += " trackableTypeImage";
      }
      if (this.created == null) {
        missing += " created";
      }
      if (this.allowedToBeCollected == null) {
        missing += " allowedToBeCollected";
      }
      if (this.inCollection == null) {
        missing += " inCollection";
      }
      if (this.archived == null) {
        missing += " archived";
      }
      if (!missing.isEmpty()) {
        throw new IllegalStateException("Missing required properties:" + missing);
      }
      return new AutoValue_Trackable(
          this.id,
          this.name,
          this.goal,
          this.description,
          this.trackableTypeName,
          this.trackableTypeImage,
          this.owner,
          this.currentCacheCode,
          this.currentOwner,
          this.trackingNumber,
          this.created,
          this.allowedToBeCollected,
          this.inCollection,
          this.archived,
          this.trackableLogs,
          this.images);
    }
  }

}
