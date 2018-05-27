
package com.arcao.geocaching.api.data.apilimits;

import javax.annotation.Generated;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_MaxPerPage extends MaxPerPage {

  private final int geocaches;
  private final int geocacheLogs;
  private final int trackables;
  private final int trackableLogs;
  private final int cacheNotes;
  private final int galleryImages;

  private AutoValue_MaxPerPage(
      int geocaches,
      int geocacheLogs,
      int trackables,
      int trackableLogs,
      int cacheNotes,
      int galleryImages) {
    this.geocaches = geocaches;
    this.geocacheLogs = geocacheLogs;
    this.trackables = trackables;
    this.trackableLogs = trackableLogs;
    this.cacheNotes = cacheNotes;
    this.galleryImages = galleryImages;
  }

  @Override
  public int geocaches() {
    return geocaches;
  }

  @Override
  public int geocacheLogs() {
    return geocacheLogs;
  }

  @Override
  public int trackables() {
    return trackables;
  }

  @Override
  public int trackableLogs() {
    return trackableLogs;
  }

  @Override
  public int cacheNotes() {
    return cacheNotes;
  }

  @Override
  public int galleryImages() {
    return galleryImages;
  }

  @Override
  public String toString() {
    return "MaxPerPage{"
        + "geocaches=" + geocaches + ", "
        + "geocacheLogs=" + geocacheLogs + ", "
        + "trackables=" + trackables + ", "
        + "trackableLogs=" + trackableLogs + ", "
        + "cacheNotes=" + cacheNotes + ", "
        + "galleryImages=" + galleryImages
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof MaxPerPage) {
      MaxPerPage that = (MaxPerPage) o;
      return (this.geocaches == that.geocaches())
           && (this.geocacheLogs == that.geocacheLogs())
           && (this.trackables == that.trackables())
           && (this.trackableLogs == that.trackableLogs())
           && (this.cacheNotes == that.cacheNotes())
           && (this.galleryImages == that.galleryImages());
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= this.geocaches;
    h *= 1000003;
    h ^= this.geocacheLogs;
    h *= 1000003;
    h ^= this.trackables;
    h *= 1000003;
    h ^= this.trackableLogs;
    h *= 1000003;
    h ^= this.cacheNotes;
    h *= 1000003;
    h ^= this.galleryImages;
    return h;
  }

  private static final long serialVersionUID = -5594435197387844111L;

  static final class Builder extends MaxPerPage.Builder {
    private Integer geocaches;
    private Integer geocacheLogs;
    private Integer trackables;
    private Integer trackableLogs;
    private Integer cacheNotes;
    private Integer galleryImages;
    Builder() {
    }
    @Override
    public MaxPerPage.Builder geocaches(int geocaches) {
      this.geocaches = geocaches;
      return this;
    }
    @Override
    public MaxPerPage.Builder geocacheLogs(int geocacheLogs) {
      this.geocacheLogs = geocacheLogs;
      return this;
    }
    @Override
    public MaxPerPage.Builder trackables(int trackables) {
      this.trackables = trackables;
      return this;
    }
    @Override
    public MaxPerPage.Builder trackableLogs(int trackableLogs) {
      this.trackableLogs = trackableLogs;
      return this;
    }
    @Override
    public MaxPerPage.Builder cacheNotes(int cacheNotes) {
      this.cacheNotes = cacheNotes;
      return this;
    }
    @Override
    public MaxPerPage.Builder galleryImages(int galleryImages) {
      this.galleryImages = galleryImages;
      return this;
    }
    @Override
    public MaxPerPage build() {
      String missing = "";
      if (this.geocaches == null) {
        missing += " geocaches";
      }
      if (this.geocacheLogs == null) {
        missing += " geocacheLogs";
      }
      if (this.trackables == null) {
        missing += " trackables";
      }
      if (this.trackableLogs == null) {
        missing += " trackableLogs";
      }
      if (this.cacheNotes == null) {
        missing += " cacheNotes";
      }
      if (this.galleryImages == null) {
        missing += " galleryImages";
      }
      if (!missing.isEmpty()) {
        throw new IllegalStateException("Missing required properties:" + missing);
      }
      return new AutoValue_MaxPerPage(
          this.geocaches,
          this.geocacheLogs,
          this.trackables,
          this.trackableLogs,
          this.cacheNotes,
          this.galleryImages);
    }
  }

}
