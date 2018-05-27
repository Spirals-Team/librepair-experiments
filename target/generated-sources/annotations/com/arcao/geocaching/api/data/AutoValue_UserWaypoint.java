
package com.arcao.geocaching.api.data;

import com.arcao.geocaching.api.data.coordinates.Coordinates;
import java.util.Date;
import javax.annotation.Generated;
import org.jetbrains.annotations.Nullable;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_UserWaypoint extends UserWaypoint {

  private final String cacheCode;
  private final String description;
  private final long id;
  private final Coordinates coordinates;
  private final Date date;
  private final int userId;
  private final boolean correctedCoordinate;

  private AutoValue_UserWaypoint(
      @Nullable String cacheCode,
      @Nullable String description,
      long id,
      Coordinates coordinates,
      Date date,
      int userId,
      boolean correctedCoordinate) {
    this.cacheCode = cacheCode;
    this.description = description;
    this.id = id;
    this.coordinates = coordinates;
    this.date = date;
    this.userId = userId;
    this.correctedCoordinate = correctedCoordinate;
  }

  @Nullable
  @Override
  public String cacheCode() {
    return cacheCode;
  }

  @Nullable
  @Override
  public String description() {
    return description;
  }

  @Override
  public long id() {
    return id;
  }

  @Override
  public Coordinates coordinates() {
    return coordinates;
  }

  @Override
  public Date date() {
    return date;
  }

  @Override
  public int userId() {
    return userId;
  }

  @Override
  public boolean correctedCoordinate() {
    return correctedCoordinate;
  }

  @Override
  public String toString() {
    return "UserWaypoint{"
        + "cacheCode=" + cacheCode + ", "
        + "description=" + description + ", "
        + "id=" + id + ", "
        + "coordinates=" + coordinates + ", "
        + "date=" + date + ", "
        + "userId=" + userId + ", "
        + "correctedCoordinate=" + correctedCoordinate
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof UserWaypoint) {
      UserWaypoint that = (UserWaypoint) o;
      return ((this.cacheCode == null) ? (that.cacheCode() == null) : this.cacheCode.equals(that.cacheCode()))
           && ((this.description == null) ? (that.description() == null) : this.description.equals(that.description()))
           && (this.id == that.id())
           && (this.coordinates.equals(that.coordinates()))
           && (this.date.equals(that.date()))
           && (this.userId == that.userId())
           && (this.correctedCoordinate == that.correctedCoordinate());
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= (cacheCode == null) ? 0 : this.cacheCode.hashCode();
    h *= 1000003;
    h ^= (description == null) ? 0 : this.description.hashCode();
    h *= 1000003;
    h ^= (this.id >>> 32) ^ this.id;
    h *= 1000003;
    h ^= this.coordinates.hashCode();
    h *= 1000003;
    h ^= this.date.hashCode();
    h *= 1000003;
    h ^= this.userId;
    h *= 1000003;
    h ^= this.correctedCoordinate ? 1231 : 1237;
    return h;
  }

  private static final long serialVersionUID = 2635449057331423781L;

  static final class Builder extends UserWaypoint.Builder {
    private String cacheCode;
    private String description;
    private Long id;
    private Coordinates coordinates;
    private Date date;
    private Integer userId;
    private Boolean correctedCoordinate;
    Builder() {
    }
    @Override
    public UserWaypoint.Builder cacheCode(@Nullable String cacheCode) {
      this.cacheCode = cacheCode;
      return this;
    }
    @Override
    public UserWaypoint.Builder description(@Nullable String description) {
      this.description = description;
      return this;
    }
    @Override
    public UserWaypoint.Builder id(long id) {
      this.id = id;
      return this;
    }
    @Override
    public UserWaypoint.Builder coordinates(Coordinates coordinates) {
      if (coordinates == null) {
        throw new NullPointerException("Null coordinates");
      }
      this.coordinates = coordinates;
      return this;
    }
    @Override
    public UserWaypoint.Builder date(Date date) {
      if (date == null) {
        throw new NullPointerException("Null date");
      }
      this.date = date;
      return this;
    }
    @Override
    public UserWaypoint.Builder userId(int userId) {
      this.userId = userId;
      return this;
    }
    @Override
    public UserWaypoint.Builder correctedCoordinate(boolean correctedCoordinate) {
      this.correctedCoordinate = correctedCoordinate;
      return this;
    }
    @Override
    public UserWaypoint build() {
      String missing = "";
      if (this.id == null) {
        missing += " id";
      }
      if (this.coordinates == null) {
        missing += " coordinates";
      }
      if (this.date == null) {
        missing += " date";
      }
      if (this.userId == null) {
        missing += " userId";
      }
      if (this.correctedCoordinate == null) {
        missing += " correctedCoordinate";
      }
      if (!missing.isEmpty()) {
        throw new IllegalStateException("Missing required properties:" + missing);
      }
      return new AutoValue_UserWaypoint(
          this.cacheCode,
          this.description,
          this.id,
          this.coordinates,
          this.date,
          this.userId,
          this.correctedCoordinate);
    }
  }

}
