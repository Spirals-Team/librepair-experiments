

package com.arcao.geocaching.api.data.userprofile;

import javax.annotation.Generated;
import org.jetbrains.annotations.Nullable;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_ProfilePhoto extends ProfilePhoto {

  private final String photoDescription;
  private final String photoFilename;
  private final String photoName;
  private final String photoUrl;

  private AutoValue_ProfilePhoto(
      @Nullable String photoDescription,
      @Nullable String photoFilename,
      @Nullable String photoName,
      @Nullable String photoUrl) {
    this.photoDescription = photoDescription;
    this.photoFilename = photoFilename;
    this.photoName = photoName;
    this.photoUrl = photoUrl;
  }

  @Nullable
  @Override
  public String photoDescription() {
    return photoDescription;
  }

  @Nullable
  @Override
  public String photoFilename() {
    return photoFilename;
  }

  @Nullable
  @Override
  public String photoName() {
    return photoName;
  }

  @Nullable
  @Override
  public String photoUrl() {
    return photoUrl;
  }

  @Override
  public String toString() {
    return "ProfilePhoto{"
         + "photoDescription=" + photoDescription + ", "
         + "photoFilename=" + photoFilename + ", "
         + "photoName=" + photoName + ", "
         + "photoUrl=" + photoUrl
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof ProfilePhoto) {
      ProfilePhoto that = (ProfilePhoto) o;
      return ((this.photoDescription == null) ? (that.photoDescription() == null) : this.photoDescription.equals(that.photoDescription()))
           && ((this.photoFilename == null) ? (that.photoFilename() == null) : this.photoFilename.equals(that.photoFilename()))
           && ((this.photoName == null) ? (that.photoName() == null) : this.photoName.equals(that.photoName()))
           && ((this.photoUrl == null) ? (that.photoUrl() == null) : this.photoUrl.equals(that.photoUrl()));
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h$ = 1;
    h$ *= 1000003;
    h$ ^= (photoDescription == null) ? 0 : photoDescription.hashCode();
    h$ *= 1000003;
    h$ ^= (photoFilename == null) ? 0 : photoFilename.hashCode();
    h$ *= 1000003;
    h$ ^= (photoName == null) ? 0 : photoName.hashCode();
    h$ *= 1000003;
    h$ ^= (photoUrl == null) ? 0 : photoUrl.hashCode();
    return h$;
  }

  private static final long serialVersionUID = 5557754921065357998L;

  static final class Builder extends ProfilePhoto.Builder {
    private String photoDescription;
    private String photoFilename;
    private String photoName;
    private String photoUrl;
    Builder() {
    }
    @Override
    public ProfilePhoto.Builder photoDescription(@Nullable String photoDescription) {
      this.photoDescription = photoDescription;
      return this;
    }
    @Override
    public ProfilePhoto.Builder photoFilename(@Nullable String photoFilename) {
      this.photoFilename = photoFilename;
      return this;
    }
    @Override
    public ProfilePhoto.Builder photoName(@Nullable String photoName) {
      this.photoName = photoName;
      return this;
    }
    @Override
    public ProfilePhoto.Builder photoUrl(@Nullable String photoUrl) {
      this.photoUrl = photoUrl;
      return this;
    }
    @Override
    public ProfilePhoto build() {
      return new AutoValue_ProfilePhoto(
          this.photoDescription,
          this.photoFilename,
          this.photoName,
          this.photoUrl);
    }
  }

}
