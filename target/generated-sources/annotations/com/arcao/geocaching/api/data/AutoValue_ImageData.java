

package com.arcao.geocaching.api.data;

import java.util.Arrays;
import java.util.Date;
import javax.annotation.Generated;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_ImageData extends ImageData {

  private final String name;
  private final String description;
  private final String mobileUrl;
  private final String thumbUrl;
  private final String url;
  private final String fileName;
  private final Date created;
  private final byte[] imageData;

  private AutoValue_ImageData(
      String name,
      @Nullable String description,
      @Nullable String mobileUrl,
      @Nullable String thumbUrl,
      @Nullable String url,
      @Nullable String fileName,
      @Nullable Date created,
      @Nullable byte[] imageData) {
    this.name = name;
    this.description = description;
    this.mobileUrl = mobileUrl;
    this.thumbUrl = thumbUrl;
    this.url = url;
    this.fileName = fileName;
    this.created = created;
    this.imageData = imageData;
  }

  @NotNull
  @Override
  public String name() {
    return name;
  }

  @Nullable
  @Override
  public String description() {
    return description;
  }

  @Nullable
  @Override
  public String mobileUrl() {
    return mobileUrl;
  }

  @Nullable
  @Override
  public String thumbUrl() {
    return thumbUrl;
  }

  @Nullable
  @Override
  public String url() {
    return url;
  }

  @Nullable
  @Override
  public String fileName() {
    return fileName;
  }

  @Nullable
  @Override
  public Date created() {
    return created;
  }

  @Nullable
  @SuppressWarnings(value = {"mutable"})
  @Override
  public byte[] imageData() {
    return imageData;
  }

  @Override
  public String toString() {
    return "ImageData{"
         + "name=" + name + ", "
         + "description=" + description + ", "
         + "mobileUrl=" + mobileUrl + ", "
         + "thumbUrl=" + thumbUrl + ", "
         + "url=" + url + ", "
         + "fileName=" + fileName + ", "
         + "created=" + created + ", "
         + "imageData=" + Arrays.toString(imageData)
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof ImageData) {
      ImageData that = (ImageData) o;
      return (this.name.equals(that.name()))
           && ((this.description == null) ? (that.description() == null) : this.description.equals(that.description()))
           && ((this.mobileUrl == null) ? (that.mobileUrl() == null) : this.mobileUrl.equals(that.mobileUrl()))
           && ((this.thumbUrl == null) ? (that.thumbUrl() == null) : this.thumbUrl.equals(that.thumbUrl()))
           && ((this.url == null) ? (that.url() == null) : this.url.equals(that.url()))
           && ((this.fileName == null) ? (that.fileName() == null) : this.fileName.equals(that.fileName()))
           && ((this.created == null) ? (that.created() == null) : this.created.equals(that.created()))
           && (Arrays.equals(this.imageData, (that instanceof AutoValue_ImageData) ? ((AutoValue_ImageData) that).imageData : that.imageData()));
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h$ = 1;
    h$ *= 1000003;
    h$ ^= name.hashCode();
    h$ *= 1000003;
    h$ ^= (description == null) ? 0 : description.hashCode();
    h$ *= 1000003;
    h$ ^= (mobileUrl == null) ? 0 : mobileUrl.hashCode();
    h$ *= 1000003;
    h$ ^= (thumbUrl == null) ? 0 : thumbUrl.hashCode();
    h$ *= 1000003;
    h$ ^= (url == null) ? 0 : url.hashCode();
    h$ *= 1000003;
    h$ ^= (fileName == null) ? 0 : fileName.hashCode();
    h$ *= 1000003;
    h$ ^= (created == null) ? 0 : created.hashCode();
    h$ *= 1000003;
    h$ ^= Arrays.hashCode(imageData);
    return h$;
  }

  private static final long serialVersionUID = 1116404414881607691L;

  static final class Builder extends ImageData.Builder {
    private String name;
    private String description;
    private String mobileUrl;
    private String thumbUrl;
    private String url;
    private String fileName;
    private Date created;
    private byte[] imageData;
    Builder() {
    }
    @Override
    public ImageData.Builder name(String name) {
      if (name == null) {
        throw new NullPointerException("Null name");
      }
      this.name = name;
      return this;
    }
    @Override
    public ImageData.Builder description(@Nullable String description) {
      this.description = description;
      return this;
    }
    @Override
    public ImageData.Builder mobileUrl(@Nullable String mobileUrl) {
      this.mobileUrl = mobileUrl;
      return this;
    }
    @Override
    public ImageData.Builder thumbUrl(@Nullable String thumbUrl) {
      this.thumbUrl = thumbUrl;
      return this;
    }
    @Override
    public ImageData.Builder url(@Nullable String url) {
      this.url = url;
      return this;
    }
    @Override
    public ImageData.Builder fileName(@Nullable String fileName) {
      this.fileName = fileName;
      return this;
    }
    @Override
    public ImageData.Builder created(@Nullable Date created) {
      this.created = created;
      return this;
    }
    @Override
    public ImageData.Builder imageData(@Nullable byte[] imageData) {
      this.imageData = imageData;
      return this;
    }
    @Override
    public ImageData build() {
      String missing = "";
      if (this.name == null) {
        missing += " name";
      }
      if (!missing.isEmpty()) {
        throw new IllegalStateException("Missing required properties:" + missing);
      }
      return new AutoValue_ImageData(
          this.name,
          this.description,
          this.mobileUrl,
          this.thumbUrl,
          this.url,
          this.fileName,
          this.created,
          this.imageData);
    }
  }

}
