

package com.arcao.geocaching.api.data;

import com.arcao.geocaching.api.data.coordinates.Coordinates;
import com.arcao.geocaching.api.data.type.WaypointType;
import java.util.Date;
import javax.annotation.Generated;
import org.jetbrains.annotations.Nullable;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_Waypoint extends Waypoint {

  private final Coordinates coordinates;
  private final Date time;
  private final String waypointCode;
  private final String name;
  private final String note;
  private final WaypointType waypointType;

  private AutoValue_Waypoint(
      Coordinates coordinates,
      Date time,
      String waypointCode,
      String name,
      @Nullable String note,
      WaypointType waypointType) {
    this.coordinates = coordinates;
    this.time = time;
    this.waypointCode = waypointCode;
    this.name = name;
    this.note = note;
    this.waypointType = waypointType;
  }

  @Override
  public Coordinates coordinates() {
    return coordinates;
  }

  @Override
  public Date time() {
    return time;
  }

  @Override
  public String waypointCode() {
    return waypointCode;
  }

  @Override
  public String name() {
    return name;
  }

  @Nullable
  @Override
  public String note() {
    return note;
  }

  @Override
  public WaypointType waypointType() {
    return waypointType;
  }

  @Override
  public String toString() {
    return "Waypoint{"
         + "coordinates=" + coordinates + ", "
         + "time=" + time + ", "
         + "waypointCode=" + waypointCode + ", "
         + "name=" + name + ", "
         + "note=" + note + ", "
         + "waypointType=" + waypointType
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof Waypoint) {
      Waypoint that = (Waypoint) o;
      return (this.coordinates.equals(that.coordinates()))
           && (this.time.equals(that.time()))
           && (this.waypointCode.equals(that.waypointCode()))
           && (this.name.equals(that.name()))
           && ((this.note == null) ? (that.note() == null) : this.note.equals(that.note()))
           && (this.waypointType.equals(that.waypointType()));
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h$ = 1;
    h$ *= 1000003;
    h$ ^= coordinates.hashCode();
    h$ *= 1000003;
    h$ ^= time.hashCode();
    h$ *= 1000003;
    h$ ^= waypointCode.hashCode();
    h$ *= 1000003;
    h$ ^= name.hashCode();
    h$ *= 1000003;
    h$ ^= (note == null) ? 0 : note.hashCode();
    h$ *= 1000003;
    h$ ^= waypointType.hashCode();
    return h$;
  }

  private static final long serialVersionUID = -7183357014183017947L;

  static final class Builder extends Waypoint.Builder {
    private Coordinates coordinates;
    private Date time;
    private String waypointCode;
    private String name;
    private String note;
    private WaypointType waypointType;
    Builder() {
    }
    @Override
    public Waypoint.Builder coordinates(Coordinates coordinates) {
      if (coordinates == null) {
        throw new NullPointerException("Null coordinates");
      }
      this.coordinates = coordinates;
      return this;
    }
    @Override
    public Waypoint.Builder time(Date time) {
      if (time == null) {
        throw new NullPointerException("Null time");
      }
      this.time = time;
      return this;
    }
    @Override
    public Waypoint.Builder waypointCode(String waypointCode) {
      if (waypointCode == null) {
        throw new NullPointerException("Null waypointCode");
      }
      this.waypointCode = waypointCode;
      return this;
    }
    @Override
    public Waypoint.Builder name(String name) {
      if (name == null) {
        throw new NullPointerException("Null name");
      }
      this.name = name;
      return this;
    }
    @Override
    public Waypoint.Builder note(@Nullable String note) {
      this.note = note;
      return this;
    }
    @Override
    public Waypoint.Builder waypointType(WaypointType waypointType) {
      if (waypointType == null) {
        throw new NullPointerException("Null waypointType");
      }
      this.waypointType = waypointType;
      return this;
    }
    @Override
    public Waypoint build() {
      String missing = "";
      if (this.coordinates == null) {
        missing += " coordinates";
      }
      if (this.time == null) {
        missing += " time";
      }
      if (this.waypointCode == null) {
        missing += " waypointCode";
      }
      if (this.name == null) {
        missing += " name";
      }
      if (this.waypointType == null) {
        missing += " waypointType";
      }
      if (!missing.isEmpty()) {
        throw new IllegalStateException("Missing required properties:" + missing);
      }
      return new AutoValue_Waypoint(
          this.coordinates,
          this.time,
          this.waypointCode,
          this.name,
          this.note,
          this.waypointType);
    }
  }

}
