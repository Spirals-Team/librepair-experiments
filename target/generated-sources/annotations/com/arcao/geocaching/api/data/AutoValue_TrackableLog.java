
package com.arcao.geocaching.api.data;

import com.arcao.geocaching.api.data.coordinates.Coordinates;
import com.arcao.geocaching.api.data.type.TrackableLogType;
import java.util.Date;
import java.util.List;
import javax.annotation.Generated;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_TrackableLog extends TrackableLog {

  private final int cacheId;
  private final String code;
  private final int id;
  private final List<ImageData> images;
  private final boolean archived;
  private final String guid;
  private final String text;
  private final TrackableLogType type;
  private final User loggedBy;
  private final Date created;
  private final Coordinates updatedCoordinates;
  private final String url;
  private final Date visited;

  private AutoValue_TrackableLog(
      int cacheId,
      String code,
      int id,
      List<ImageData> images,
      boolean archived,
      String guid,
      String text,
      TrackableLogType type,
      User loggedBy,
      Date created,
      Coordinates updatedCoordinates,
      String url,
      Date visited) {
    this.cacheId = cacheId;
    this.code = code;
    this.id = id;
    this.images = images;
    this.archived = archived;
    this.guid = guid;
    this.text = text;
    this.type = type;
    this.loggedBy = loggedBy;
    this.created = created;
    this.updatedCoordinates = updatedCoordinates;
    this.url = url;
    this.visited = visited;
  }

  @Override
  public int cacheId() {
    return cacheId;
  }

  @Override
  public String code() {
    return code;
  }

  @Override
  public int id() {
    return id;
  }

  @Override
  public List<ImageData> images() {
    return images;
  }

  @Override
  public boolean archived() {
    return archived;
  }

  @Override
  public String guid() {
    return guid;
  }

  @Override
  public String text() {
    return text;
  }

  @Override
  public TrackableLogType type() {
    return type;
  }

  @Override
  public User loggedBy() {
    return loggedBy;
  }

  @Override
  public Date created() {
    return created;
  }

  @Override
  public Coordinates updatedCoordinates() {
    return updatedCoordinates;
  }

  @Override
  public String url() {
    return url;
  }

  @Override
  public Date visited() {
    return visited;
  }

  @Override
  public String toString() {
    return "TrackableLog{"
        + "cacheId=" + cacheId + ", "
        + "code=" + code + ", "
        + "id=" + id + ", "
        + "images=" + images + ", "
        + "archived=" + archived + ", "
        + "guid=" + guid + ", "
        + "text=" + text + ", "
        + "type=" + type + ", "
        + "loggedBy=" + loggedBy + ", "
        + "created=" + created + ", "
        + "updatedCoordinates=" + updatedCoordinates + ", "
        + "url=" + url + ", "
        + "visited=" + visited
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof TrackableLog) {
      TrackableLog that = (TrackableLog) o;
      return (this.cacheId == that.cacheId())
           && (this.code.equals(that.code()))
           && (this.id == that.id())
           && (this.images.equals(that.images()))
           && (this.archived == that.archived())
           && (this.guid.equals(that.guid()))
           && (this.text.equals(that.text()))
           && (this.type.equals(that.type()))
           && (this.loggedBy.equals(that.loggedBy()))
           && (this.created.equals(that.created()))
           && (this.updatedCoordinates.equals(that.updatedCoordinates()))
           && (this.url.equals(that.url()))
           && (this.visited.equals(that.visited()));
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= this.cacheId;
    h *= 1000003;
    h ^= this.code.hashCode();
    h *= 1000003;
    h ^= this.id;
    h *= 1000003;
    h ^= this.images.hashCode();
    h *= 1000003;
    h ^= this.archived ? 1231 : 1237;
    h *= 1000003;
    h ^= this.guid.hashCode();
    h *= 1000003;
    h ^= this.text.hashCode();
    h *= 1000003;
    h ^= this.type.hashCode();
    h *= 1000003;
    h ^= this.loggedBy.hashCode();
    h *= 1000003;
    h ^= this.created.hashCode();
    h *= 1000003;
    h ^= this.updatedCoordinates.hashCode();
    h *= 1000003;
    h ^= this.url.hashCode();
    h *= 1000003;
    h ^= this.visited.hashCode();
    return h;
  }

  private static final long serialVersionUID = -8616502691991228922L;

  static final class Builder extends TrackableLog.Builder {
    private Integer cacheId;
    private String code;
    private Integer id;
    private List<ImageData> images;
    private Boolean archived;
    private String guid;
    private String text;
    private TrackableLogType type;
    private User loggedBy;
    private Date created;
    private Coordinates updatedCoordinates;
    private String url;
    private Date visited;
    Builder() {
    }
    @Override
    public TrackableLog.Builder cacheId(int cacheId) {
      this.cacheId = cacheId;
      return this;
    }
    @Override
    public TrackableLog.Builder code(String code) {
      if (code == null) {
        throw new NullPointerException("Null code");
      }
      this.code = code;
      return this;
    }
    @Override
    public TrackableLog.Builder id(int id) {
      this.id = id;
      return this;
    }
    @Override
    public TrackableLog.Builder images(List<ImageData> images) {
      if (images == null) {
        throw new NullPointerException("Null images");
      }
      this.images = images;
      return this;
    }
    @Override
    public TrackableLog.Builder archived(boolean archived) {
      this.archived = archived;
      return this;
    }
    @Override
    public TrackableLog.Builder guid(String guid) {
      if (guid == null) {
        throw new NullPointerException("Null guid");
      }
      this.guid = guid;
      return this;
    }
    @Override
    public TrackableLog.Builder text(String text) {
      if (text == null) {
        throw new NullPointerException("Null text");
      }
      this.text = text;
      return this;
    }
    @Override
    public TrackableLog.Builder type(TrackableLogType type) {
      if (type == null) {
        throw new NullPointerException("Null type");
      }
      this.type = type;
      return this;
    }
    @Override
    public TrackableLog.Builder loggedBy(User loggedBy) {
      if (loggedBy == null) {
        throw new NullPointerException("Null loggedBy");
      }
      this.loggedBy = loggedBy;
      return this;
    }
    @Override
    public TrackableLog.Builder created(Date created) {
      if (created == null) {
        throw new NullPointerException("Null created");
      }
      this.created = created;
      return this;
    }
    @Override
    public TrackableLog.Builder updatedCoordinates(Coordinates updatedCoordinates) {
      if (updatedCoordinates == null) {
        throw new NullPointerException("Null updatedCoordinates");
      }
      this.updatedCoordinates = updatedCoordinates;
      return this;
    }
    @Override
    public TrackableLog.Builder url(String url) {
      if (url == null) {
        throw new NullPointerException("Null url");
      }
      this.url = url;
      return this;
    }
    @Override
    public TrackableLog.Builder visited(Date visited) {
      if (visited == null) {
        throw new NullPointerException("Null visited");
      }
      this.visited = visited;
      return this;
    }
    @Override
    public TrackableLog build() {
      String missing = "";
      if (this.cacheId == null) {
        missing += " cacheId";
      }
      if (this.code == null) {
        missing += " code";
      }
      if (this.id == null) {
        missing += " id";
      }
      if (this.images == null) {
        missing += " images";
      }
      if (this.archived == null) {
        missing += " archived";
      }
      if (this.guid == null) {
        missing += " guid";
      }
      if (this.text == null) {
        missing += " text";
      }
      if (this.type == null) {
        missing += " type";
      }
      if (this.loggedBy == null) {
        missing += " loggedBy";
      }
      if (this.created == null) {
        missing += " created";
      }
      if (this.updatedCoordinates == null) {
        missing += " updatedCoordinates";
      }
      if (this.url == null) {
        missing += " url";
      }
      if (this.visited == null) {
        missing += " visited";
      }
      if (!missing.isEmpty()) {
        throw new IllegalStateException("Missing required properties:" + missing);
      }
      return new AutoValue_TrackableLog(
          this.cacheId,
          this.code,
          this.id,
          this.images,
          this.archived,
          this.guid,
          this.text,
          this.type,
          this.loggedBy,
          this.created,
          this.updatedCoordinates,
          this.url,
          this.visited);
    }
  }

}
