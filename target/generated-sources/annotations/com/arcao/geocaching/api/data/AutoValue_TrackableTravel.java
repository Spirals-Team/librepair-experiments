
package com.arcao.geocaching.api.data;

import com.arcao.geocaching.api.data.coordinates.Coordinates;
import java.util.Date;
import javax.annotation.Generated;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_TrackableTravel extends TrackableTravel {

  private final long cacheId;
  private final Date dateLogged;
  private final Coordinates coordinates;

  private AutoValue_TrackableTravel(
      long cacheId,
      Date dateLogged,
      Coordinates coordinates) {
    this.cacheId = cacheId;
    this.dateLogged = dateLogged;
    this.coordinates = coordinates;
  }

  @Override
  public long cacheId() {
    return cacheId;
  }

  @Override
  public Date dateLogged() {
    return dateLogged;
  }

  @Override
  public Coordinates coordinates() {
    return coordinates;
  }

  @Override
  public String toString() {
    return "TrackableTravel{"
        + "cacheId=" + cacheId + ", "
        + "dateLogged=" + dateLogged + ", "
        + "coordinates=" + coordinates
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof TrackableTravel) {
      TrackableTravel that = (TrackableTravel) o;
      return (this.cacheId == that.cacheId())
           && (this.dateLogged.equals(that.dateLogged()))
           && (this.coordinates.equals(that.coordinates()));
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= (this.cacheId >>> 32) ^ this.cacheId;
    h *= 1000003;
    h ^= this.dateLogged.hashCode();
    h *= 1000003;
    h ^= this.coordinates.hashCode();
    return h;
  }

  private static final long serialVersionUID = 61007459728740881L;

  static final class Builder extends TrackableTravel.Builder {
    private Long cacheId;
    private Date dateLogged;
    private Coordinates coordinates;
    Builder() {
    }
    @Override
    public TrackableTravel.Builder cacheId(long cacheId) {
      this.cacheId = cacheId;
      return this;
    }
    @Override
    public TrackableTravel.Builder dateLogged(Date dateLogged) {
      if (dateLogged == null) {
        throw new NullPointerException("Null dateLogged");
      }
      this.dateLogged = dateLogged;
      return this;
    }
    @Override
    public TrackableTravel.Builder coordinates(Coordinates coordinates) {
      if (coordinates == null) {
        throw new NullPointerException("Null coordinates");
      }
      this.coordinates = coordinates;
      return this;
    }
    @Override
    public TrackableTravel build() {
      String missing = "";
      if (this.cacheId == null) {
        missing += " cacheId";
      }
      if (this.dateLogged == null) {
        missing += " dateLogged";
      }
      if (this.coordinates == null) {
        missing += " coordinates";
      }
      if (!missing.isEmpty()) {
        throw new IllegalStateException("Missing required properties:" + missing);
      }
      return new AutoValue_TrackableTravel(
          this.cacheId,
          this.dateLogged,
          this.coordinates);
    }
  }

}
