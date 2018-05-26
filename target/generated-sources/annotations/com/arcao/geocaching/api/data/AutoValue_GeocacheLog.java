
package com.arcao.geocaching.api.data;

import com.arcao.geocaching.api.data.coordinates.Coordinates;
import com.arcao.geocaching.api.data.type.GeocacheLogType;
import java.util.Date;
import java.util.List;
import javax.annotation.Generated;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_GeocacheLog extends GeocacheLog {

  private final long id;
  private final String cacheCode;
  private final Date created;
  private final Date visited;
  private final GeocacheLogType logType;
  private final User author;
  private final String text;
  private final List<ImageData> images;
  private final Coordinates updatedCoordinates;
  private final boolean approved;
  private final boolean archived;
  private final boolean undeletable;

  private AutoValue_GeocacheLog(
      long id,
      String cacheCode,
      Date created,
      Date visited,
      GeocacheLogType logType,
      User author,
      String text,
      List<ImageData> images,
      Coordinates updatedCoordinates,
      boolean approved,
      boolean archived,
      boolean undeletable) {
    this.id = id;
    this.cacheCode = cacheCode;
    this.created = created;
    this.visited = visited;
    this.logType = logType;
    this.author = author;
    this.text = text;
    this.images = images;
    this.updatedCoordinates = updatedCoordinates;
    this.approved = approved;
    this.archived = archived;
    this.undeletable = undeletable;
  }

  @Override
  public long id() {
    return id;
  }

  @Override
  public String cacheCode() {
    return cacheCode;
  }

  @Override
  public Date created() {
    return created;
  }

  @Override
  public Date visited() {
    return visited;
  }

  @Override
  public GeocacheLogType logType() {
    return logType;
  }

  @Override
  public User author() {
    return author;
  }

  @Override
  public String text() {
    return text;
  }

  @Override
  public List<ImageData> images() {
    return images;
  }

  @Override
  public Coordinates updatedCoordinates() {
    return updatedCoordinates;
  }

  @Override
  public boolean approved() {
    return approved;
  }

  @Override
  public boolean archived() {
    return archived;
  }

  @Override
  public boolean undeletable() {
    return undeletable;
  }

  @Override
  public String toString() {
    return "GeocacheLog{"
        + "id=" + id + ", "
        + "cacheCode=" + cacheCode + ", "
        + "created=" + created + ", "
        + "visited=" + visited + ", "
        + "logType=" + logType + ", "
        + "author=" + author + ", "
        + "text=" + text + ", "
        + "images=" + images + ", "
        + "updatedCoordinates=" + updatedCoordinates + ", "
        + "approved=" + approved + ", "
        + "archived=" + archived + ", "
        + "undeletable=" + undeletable
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof GeocacheLog) {
      GeocacheLog that = (GeocacheLog) o;
      return (this.id == that.id())
           && (this.cacheCode.equals(that.cacheCode()))
           && (this.created.equals(that.created()))
           && (this.visited.equals(that.visited()))
           && (this.logType.equals(that.logType()))
           && (this.author.equals(that.author()))
           && (this.text.equals(that.text()))
           && (this.images.equals(that.images()))
           && (this.updatedCoordinates.equals(that.updatedCoordinates()))
           && (this.approved == that.approved())
           && (this.archived == that.archived())
           && (this.undeletable == that.undeletable());
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= (this.id >>> 32) ^ this.id;
    h *= 1000003;
    h ^= this.cacheCode.hashCode();
    h *= 1000003;
    h ^= this.created.hashCode();
    h *= 1000003;
    h ^= this.visited.hashCode();
    h *= 1000003;
    h ^= this.logType.hashCode();
    h *= 1000003;
    h ^= this.author.hashCode();
    h *= 1000003;
    h ^= this.text.hashCode();
    h *= 1000003;
    h ^= this.images.hashCode();
    h *= 1000003;
    h ^= this.updatedCoordinates.hashCode();
    h *= 1000003;
    h ^= this.approved ? 1231 : 1237;
    h *= 1000003;
    h ^= this.archived ? 1231 : 1237;
    h *= 1000003;
    h ^= this.undeletable ? 1231 : 1237;
    return h;
  }

  private static final long serialVersionUID = 9088433857246687793L;

  static final class Builder extends GeocacheLog.Builder {
    private Long id;
    private String cacheCode;
    private Date created;
    private Date visited;
    private GeocacheLogType logType;
    private User author;
    private String text;
    private List<ImageData> images;
    private Coordinates updatedCoordinates;
    private Boolean approved;
    private Boolean archived;
    private Boolean undeletable;
    Builder() {
    }
    @Override
    public GeocacheLog.Builder id(long id) {
      this.id = id;
      return this;
    }
    @Override
    public GeocacheLog.Builder cacheCode(String cacheCode) {
      if (cacheCode == null) {
        throw new NullPointerException("Null cacheCode");
      }
      this.cacheCode = cacheCode;
      return this;
    }
    @Override
    public GeocacheLog.Builder created(Date created) {
      if (created == null) {
        throw new NullPointerException("Null created");
      }
      this.created = created;
      return this;
    }
    @Override
    public GeocacheLog.Builder visited(Date visited) {
      if (visited == null) {
        throw new NullPointerException("Null visited");
      }
      this.visited = visited;
      return this;
    }
    @Override
    public GeocacheLog.Builder logType(GeocacheLogType logType) {
      if (logType == null) {
        throw new NullPointerException("Null logType");
      }
      this.logType = logType;
      return this;
    }
    @Override
    public GeocacheLog.Builder author(User author) {
      if (author == null) {
        throw new NullPointerException("Null author");
      }
      this.author = author;
      return this;
    }
    @Override
    public GeocacheLog.Builder text(String text) {
      if (text == null) {
        throw new NullPointerException("Null text");
      }
      this.text = text;
      return this;
    }
    @Override
    public GeocacheLog.Builder images(List<ImageData> images) {
      if (images == null) {
        throw new NullPointerException("Null images");
      }
      this.images = images;
      return this;
    }
    @Override
    public GeocacheLog.Builder updatedCoordinates(Coordinates updatedCoordinates) {
      if (updatedCoordinates == null) {
        throw new NullPointerException("Null updatedCoordinates");
      }
      this.updatedCoordinates = updatedCoordinates;
      return this;
    }
    @Override
    public GeocacheLog.Builder approved(boolean approved) {
      this.approved = approved;
      return this;
    }
    @Override
    public GeocacheLog.Builder archived(boolean archived) {
      this.archived = archived;
      return this;
    }
    @Override
    public GeocacheLog.Builder undeletable(boolean undeletable) {
      this.undeletable = undeletable;
      return this;
    }
    @Override
    public GeocacheLog build() {
      String missing = "";
      if (this.id == null) {
        missing += " id";
      }
      if (this.cacheCode == null) {
        missing += " cacheCode";
      }
      if (this.created == null) {
        missing += " created";
      }
      if (this.visited == null) {
        missing += " visited";
      }
      if (this.logType == null) {
        missing += " logType";
      }
      if (this.author == null) {
        missing += " author";
      }
      if (this.text == null) {
        missing += " text";
      }
      if (this.images == null) {
        missing += " images";
      }
      if (this.updatedCoordinates == null) {
        missing += " updatedCoordinates";
      }
      if (this.approved == null) {
        missing += " approved";
      }
      if (this.archived == null) {
        missing += " archived";
      }
      if (this.undeletable == null) {
        missing += " undeletable";
      }
      if (!missing.isEmpty()) {
        throw new IllegalStateException("Missing required properties:" + missing);
      }
      return new AutoValue_GeocacheLog(
          this.id,
          this.cacheCode,
          this.created,
          this.visited,
          this.logType,
          this.author,
          this.text,
          this.images,
          this.updatedCoordinates,
          this.approved,
          this.archived,
          this.undeletable);
    }
  }

}
