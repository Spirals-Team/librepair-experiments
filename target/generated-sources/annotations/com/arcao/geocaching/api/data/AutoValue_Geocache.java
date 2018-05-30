

package com.arcao.geocaching.api.data;

import com.arcao.geocaching.api.data.coordinates.Coordinates;
import com.arcao.geocaching.api.data.type.AttributeType;
import com.arcao.geocaching.api.data.type.ContainerType;
import com.arcao.geocaching.api.data.type.GeocacheType;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;
import javax.annotation.Generated;
import org.jetbrains.annotations.Nullable;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_Geocache extends Geocache {

  private final boolean archived;
  private final boolean available;
  private final GeocacheType geocacheType;
  private final boolean favoritable;
  private final String code;
  private final ContainerType containerType;
  private final Date lastUpdateDate;
  private final Date lastVisitDate;
  private final float difficulty;
  private final int favoritePoints;
  private final Date foundDate;
  private final String personalNote;
  private final boolean favoritedByUser;
  private final boolean foundByUser;
  private final long id;
  private final int imageCount;
  private final boolean premium;
  private final boolean recommended;
  private final Coordinates coordinates;
  private final String name;
  private final User owner;
  private final String placedBy;
  private final Date publishDate;
  private final float terrain;
  private final int trackableCount;
  private final Date placeDate;
  private final String url;
  private final String guid;
  private final List<Waypoint> waypoints;
  private final String hint;
  private final String longDescription;
  private final boolean longDescriptionHtml;
  private final String shortDescription;
  private final boolean shortDescriptionHtml;
  private final List<Trackable> trackables;
  private final List<UserWaypoint> userWaypoints;
  private final List<ImageData> images;
  private final EnumSet<AttributeType> attributes;
  private final String countryName;
  private final Date createDate;
  private final List<GeocacheLog> geocacheLogs;
  private final String stateName;

  private AutoValue_Geocache(
      boolean archived,
      boolean available,
      GeocacheType geocacheType,
      boolean favoritable,
      String code,
      ContainerType containerType,
      @Nullable Date lastUpdateDate,
      @Nullable Date lastVisitDate,
      float difficulty,
      int favoritePoints,
      @Nullable Date foundDate,
      @Nullable String personalNote,
      boolean favoritedByUser,
      boolean foundByUser,
      long id,
      int imageCount,
      boolean premium,
      boolean recommended,
      Coordinates coordinates,
      String name,
      User owner,
      String placedBy,
      @Nullable Date publishDate,
      float terrain,
      int trackableCount,
      Date placeDate,
      String url,
      String guid,
      @Nullable List<Waypoint> waypoints,
      @Nullable String hint,
      @Nullable String longDescription,
      boolean longDescriptionHtml,
      @Nullable String shortDescription,
      boolean shortDescriptionHtml,
      @Nullable List<Trackable> trackables,
      @Nullable List<UserWaypoint> userWaypoints,
      @Nullable List<ImageData> images,
      @Nullable EnumSet<AttributeType> attributes,
      @Nullable String countryName,
      @Nullable Date createDate,
      @Nullable List<GeocacheLog> geocacheLogs,
      @Nullable String stateName) {
    this.archived = archived;
    this.available = available;
    this.geocacheType = geocacheType;
    this.favoritable = favoritable;
    this.code = code;
    this.containerType = containerType;
    this.lastUpdateDate = lastUpdateDate;
    this.lastVisitDate = lastVisitDate;
    this.difficulty = difficulty;
    this.favoritePoints = favoritePoints;
    this.foundDate = foundDate;
    this.personalNote = personalNote;
    this.favoritedByUser = favoritedByUser;
    this.foundByUser = foundByUser;
    this.id = id;
    this.imageCount = imageCount;
    this.premium = premium;
    this.recommended = recommended;
    this.coordinates = coordinates;
    this.name = name;
    this.owner = owner;
    this.placedBy = placedBy;
    this.publishDate = publishDate;
    this.terrain = terrain;
    this.trackableCount = trackableCount;
    this.placeDate = placeDate;
    this.url = url;
    this.guid = guid;
    this.waypoints = waypoints;
    this.hint = hint;
    this.longDescription = longDescription;
    this.longDescriptionHtml = longDescriptionHtml;
    this.shortDescription = shortDescription;
    this.shortDescriptionHtml = shortDescriptionHtml;
    this.trackables = trackables;
    this.userWaypoints = userWaypoints;
    this.images = images;
    this.attributes = attributes;
    this.countryName = countryName;
    this.createDate = createDate;
    this.geocacheLogs = geocacheLogs;
    this.stateName = stateName;
  }

  @Override
  public boolean archived() {
    return archived;
  }

  @Override
  public boolean available() {
    return available;
  }

  @Override
  public GeocacheType geocacheType() {
    return geocacheType;
  }

  @Override
  public boolean favoritable() {
    return favoritable;
  }

  @Override
  public String code() {
    return code;
  }

  @Override
  public ContainerType containerType() {
    return containerType;
  }

  @Nullable
  @Override
  public Date lastUpdateDate() {
    return lastUpdateDate;
  }

  @Nullable
  @Override
  public Date lastVisitDate() {
    return lastVisitDate;
  }

  @Override
  public float difficulty() {
    return difficulty;
  }

  @Override
  public int favoritePoints() {
    return favoritePoints;
  }

  @Nullable
  @Override
  public Date foundDate() {
    return foundDate;
  }

  @Nullable
  @Override
  public String personalNote() {
    return personalNote;
  }

  @Override
  public boolean favoritedByUser() {
    return favoritedByUser;
  }

  @Override
  public boolean foundByUser() {
    return foundByUser;
  }

  @Override
  public long id() {
    return id;
  }

  @Override
  public int imageCount() {
    return imageCount;
  }

  @Override
  public boolean premium() {
    return premium;
  }

  @Override
  public boolean recommended() {
    return recommended;
  }

  @Override
  public Coordinates coordinates() {
    return coordinates;
  }

  @Override
  public String name() {
    return name;
  }

  @Override
  public User owner() {
    return owner;
  }

  @Override
  public String placedBy() {
    return placedBy;
  }

  @Nullable
  @Override
  public Date publishDate() {
    return publishDate;
  }

  @Override
  public float terrain() {
    return terrain;
  }

  @Override
  public int trackableCount() {
    return trackableCount;
  }

  @Override
  public Date placeDate() {
    return placeDate;
  }

  @Override
  public String url() {
    return url;
  }

  @Override
  public String guid() {
    return guid;
  }

  @Nullable
  @Override
  public List<Waypoint> waypoints() {
    return waypoints;
  }

  @Nullable
  @Override
  public String hint() {
    return hint;
  }

  @Nullable
  @Override
  public String longDescription() {
    return longDescription;
  }

  @Override
  public boolean longDescriptionHtml() {
    return longDescriptionHtml;
  }

  @Nullable
  @Override
  public String shortDescription() {
    return shortDescription;
  }

  @Override
  public boolean shortDescriptionHtml() {
    return shortDescriptionHtml;
  }

  @Nullable
  @Override
  public List<Trackable> trackables() {
    return trackables;
  }

  @Nullable
  @Override
  public List<UserWaypoint> userWaypoints() {
    return userWaypoints;
  }

  @Nullable
  @Override
  public List<ImageData> images() {
    return images;
  }

  @Nullable
  @Override
  public EnumSet<AttributeType> attributes() {
    return attributes;
  }

  @Nullable
  @Override
  public String countryName() {
    return countryName;
  }

  @Nullable
  @Override
  public Date createDate() {
    return createDate;
  }

  @Nullable
  @Override
  public List<GeocacheLog> geocacheLogs() {
    return geocacheLogs;
  }

  @Nullable
  @Override
  public String stateName() {
    return stateName;
  }

  @Override
  public String toString() {
    return "Geocache{"
         + "archived=" + archived + ", "
         + "available=" + available + ", "
         + "geocacheType=" + geocacheType + ", "
         + "favoritable=" + favoritable + ", "
         + "code=" + code + ", "
         + "containerType=" + containerType + ", "
         + "lastUpdateDate=" + lastUpdateDate + ", "
         + "lastVisitDate=" + lastVisitDate + ", "
         + "difficulty=" + difficulty + ", "
         + "favoritePoints=" + favoritePoints + ", "
         + "foundDate=" + foundDate + ", "
         + "personalNote=" + personalNote + ", "
         + "favoritedByUser=" + favoritedByUser + ", "
         + "foundByUser=" + foundByUser + ", "
         + "id=" + id + ", "
         + "imageCount=" + imageCount + ", "
         + "premium=" + premium + ", "
         + "recommended=" + recommended + ", "
         + "coordinates=" + coordinates + ", "
         + "name=" + name + ", "
         + "owner=" + owner + ", "
         + "placedBy=" + placedBy + ", "
         + "publishDate=" + publishDate + ", "
         + "terrain=" + terrain + ", "
         + "trackableCount=" + trackableCount + ", "
         + "placeDate=" + placeDate + ", "
         + "url=" + url + ", "
         + "guid=" + guid + ", "
         + "waypoints=" + waypoints + ", "
         + "hint=" + hint + ", "
         + "longDescription=" + longDescription + ", "
         + "longDescriptionHtml=" + longDescriptionHtml + ", "
         + "shortDescription=" + shortDescription + ", "
         + "shortDescriptionHtml=" + shortDescriptionHtml + ", "
         + "trackables=" + trackables + ", "
         + "userWaypoints=" + userWaypoints + ", "
         + "images=" + images + ", "
         + "attributes=" + attributes + ", "
         + "countryName=" + countryName + ", "
         + "createDate=" + createDate + ", "
         + "geocacheLogs=" + geocacheLogs + ", "
         + "stateName=" + stateName
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof Geocache) {
      Geocache that = (Geocache) o;
      return (this.archived == that.archived())
           && (this.available == that.available())
           && (this.geocacheType.equals(that.geocacheType()))
           && (this.favoritable == that.favoritable())
           && (this.code.equals(that.code()))
           && (this.containerType.equals(that.containerType()))
           && ((this.lastUpdateDate == null) ? (that.lastUpdateDate() == null) : this.lastUpdateDate.equals(that.lastUpdateDate()))
           && ((this.lastVisitDate == null) ? (that.lastVisitDate() == null) : this.lastVisitDate.equals(that.lastVisitDate()))
           && (Float.floatToIntBits(this.difficulty) == Float.floatToIntBits(that.difficulty()))
           && (this.favoritePoints == that.favoritePoints())
           && ((this.foundDate == null) ? (that.foundDate() == null) : this.foundDate.equals(that.foundDate()))
           && ((this.personalNote == null) ? (that.personalNote() == null) : this.personalNote.equals(that.personalNote()))
           && (this.favoritedByUser == that.favoritedByUser())
           && (this.foundByUser == that.foundByUser())
           && (this.id == that.id())
           && (this.imageCount == that.imageCount())
           && (this.premium == that.premium())
           && (this.recommended == that.recommended())
           && (this.coordinates.equals(that.coordinates()))
           && (this.name.equals(that.name()))
           && (this.owner.equals(that.owner()))
           && (this.placedBy.equals(that.placedBy()))
           && ((this.publishDate == null) ? (that.publishDate() == null) : this.publishDate.equals(that.publishDate()))
           && (Float.floatToIntBits(this.terrain) == Float.floatToIntBits(that.terrain()))
           && (this.trackableCount == that.trackableCount())
           && (this.placeDate.equals(that.placeDate()))
           && (this.url.equals(that.url()))
           && (this.guid.equals(that.guid()))
           && ((this.waypoints == null) ? (that.waypoints() == null) : this.waypoints.equals(that.waypoints()))
           && ((this.hint == null) ? (that.hint() == null) : this.hint.equals(that.hint()))
           && ((this.longDescription == null) ? (that.longDescription() == null) : this.longDescription.equals(that.longDescription()))
           && (this.longDescriptionHtml == that.longDescriptionHtml())
           && ((this.shortDescription == null) ? (that.shortDescription() == null) : this.shortDescription.equals(that.shortDescription()))
           && (this.shortDescriptionHtml == that.shortDescriptionHtml())
           && ((this.trackables == null) ? (that.trackables() == null) : this.trackables.equals(that.trackables()))
           && ((this.userWaypoints == null) ? (that.userWaypoints() == null) : this.userWaypoints.equals(that.userWaypoints()))
           && ((this.images == null) ? (that.images() == null) : this.images.equals(that.images()))
           && ((this.attributes == null) ? (that.attributes() == null) : this.attributes.equals(that.attributes()))
           && ((this.countryName == null) ? (that.countryName() == null) : this.countryName.equals(that.countryName()))
           && ((this.createDate == null) ? (that.createDate() == null) : this.createDate.equals(that.createDate()))
           && ((this.geocacheLogs == null) ? (that.geocacheLogs() == null) : this.geocacheLogs.equals(that.geocacheLogs()))
           && ((this.stateName == null) ? (that.stateName() == null) : this.stateName.equals(that.stateName()));
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h$ = 1;
    h$ *= 1000003;
    h$ ^= archived ? 1231 : 1237;
    h$ *= 1000003;
    h$ ^= available ? 1231 : 1237;
    h$ *= 1000003;
    h$ ^= geocacheType.hashCode();
    h$ *= 1000003;
    h$ ^= favoritable ? 1231 : 1237;
    h$ *= 1000003;
    h$ ^= code.hashCode();
    h$ *= 1000003;
    h$ ^= containerType.hashCode();
    h$ *= 1000003;
    h$ ^= (lastUpdateDate == null) ? 0 : lastUpdateDate.hashCode();
    h$ *= 1000003;
    h$ ^= (lastVisitDate == null) ? 0 : lastVisitDate.hashCode();
    h$ *= 1000003;
    h$ ^= Float.floatToIntBits(difficulty);
    h$ *= 1000003;
    h$ ^= favoritePoints;
    h$ *= 1000003;
    h$ ^= (foundDate == null) ? 0 : foundDate.hashCode();
    h$ *= 1000003;
    h$ ^= (personalNote == null) ? 0 : personalNote.hashCode();
    h$ *= 1000003;
    h$ ^= favoritedByUser ? 1231 : 1237;
    h$ *= 1000003;
    h$ ^= foundByUser ? 1231 : 1237;
    h$ *= 1000003;
    h$ ^= (int) ((id >>> 32) ^ id);
    h$ *= 1000003;
    h$ ^= imageCount;
    h$ *= 1000003;
    h$ ^= premium ? 1231 : 1237;
    h$ *= 1000003;
    h$ ^= recommended ? 1231 : 1237;
    h$ *= 1000003;
    h$ ^= coordinates.hashCode();
    h$ *= 1000003;
    h$ ^= name.hashCode();
    h$ *= 1000003;
    h$ ^= owner.hashCode();
    h$ *= 1000003;
    h$ ^= placedBy.hashCode();
    h$ *= 1000003;
    h$ ^= (publishDate == null) ? 0 : publishDate.hashCode();
    h$ *= 1000003;
    h$ ^= Float.floatToIntBits(terrain);
    h$ *= 1000003;
    h$ ^= trackableCount;
    h$ *= 1000003;
    h$ ^= placeDate.hashCode();
    h$ *= 1000003;
    h$ ^= url.hashCode();
    h$ *= 1000003;
    h$ ^= guid.hashCode();
    h$ *= 1000003;
    h$ ^= (waypoints == null) ? 0 : waypoints.hashCode();
    h$ *= 1000003;
    h$ ^= (hint == null) ? 0 : hint.hashCode();
    h$ *= 1000003;
    h$ ^= (longDescription == null) ? 0 : longDescription.hashCode();
    h$ *= 1000003;
    h$ ^= longDescriptionHtml ? 1231 : 1237;
    h$ *= 1000003;
    h$ ^= (shortDescription == null) ? 0 : shortDescription.hashCode();
    h$ *= 1000003;
    h$ ^= shortDescriptionHtml ? 1231 : 1237;
    h$ *= 1000003;
    h$ ^= (trackables == null) ? 0 : trackables.hashCode();
    h$ *= 1000003;
    h$ ^= (userWaypoints == null) ? 0 : userWaypoints.hashCode();
    h$ *= 1000003;
    h$ ^= (images == null) ? 0 : images.hashCode();
    h$ *= 1000003;
    h$ ^= (attributes == null) ? 0 : attributes.hashCode();
    h$ *= 1000003;
    h$ ^= (countryName == null) ? 0 : countryName.hashCode();
    h$ *= 1000003;
    h$ ^= (createDate == null) ? 0 : createDate.hashCode();
    h$ *= 1000003;
    h$ ^= (geocacheLogs == null) ? 0 : geocacheLogs.hashCode();
    h$ *= 1000003;
    h$ ^= (stateName == null) ? 0 : stateName.hashCode();
    return h$;
  }

  private static final long serialVersionUID = 7938069911500506011L;

  static final class Builder extends Geocache.Builder {
    private Boolean archived;
    private Boolean available;
    private GeocacheType geocacheType;
    private Boolean favoritable;
    private String code;
    private ContainerType containerType;
    private Date lastUpdateDate;
    private Date lastVisitDate;
    private Float difficulty;
    private Integer favoritePoints;
    private Date foundDate;
    private String personalNote;
    private Boolean favoritedByUser;
    private Boolean foundByUser;
    private Long id;
    private Integer imageCount;
    private Boolean premium;
    private Boolean recommended;
    private Coordinates coordinates;
    private String name;
    private User owner;
    private String placedBy;
    private Date publishDate;
    private Float terrain;
    private Integer trackableCount;
    private Date placeDate;
    private String url;
    private String guid;
    private List<Waypoint> waypoints;
    private String hint;
    private String longDescription;
    private Boolean longDescriptionHtml;
    private String shortDescription;
    private Boolean shortDescriptionHtml;
    private List<Trackable> trackables;
    private List<UserWaypoint> userWaypoints;
    private List<ImageData> images;
    private EnumSet<AttributeType> attributes;
    private String countryName;
    private Date createDate;
    private List<GeocacheLog> geocacheLogs;
    private String stateName;
    Builder() {
    }
    @Override
    public Geocache.Builder archived(boolean archived) {
      this.archived = archived;
      return this;
    }
    @Override
    public Geocache.Builder available(boolean available) {
      this.available = available;
      return this;
    }
    @Override
    public Geocache.Builder geocacheType(GeocacheType geocacheType) {
      if (geocacheType == null) {
        throw new NullPointerException("Null geocacheType");
      }
      this.geocacheType = geocacheType;
      return this;
    }
    @Override
    public Geocache.Builder favoritable(boolean favoritable) {
      this.favoritable = favoritable;
      return this;
    }
    @Override
    public Geocache.Builder code(String code) {
      if (code == null) {
        throw new NullPointerException("Null code");
      }
      this.code = code;
      return this;
    }
    @Override
    public Geocache.Builder containerType(ContainerType containerType) {
      if (containerType == null) {
        throw new NullPointerException("Null containerType");
      }
      this.containerType = containerType;
      return this;
    }
    @Override
    public Geocache.Builder lastUpdateDate(@Nullable Date lastUpdateDate) {
      this.lastUpdateDate = lastUpdateDate;
      return this;
    }
    @Override
    public Geocache.Builder lastVisitDate(@Nullable Date lastVisitDate) {
      this.lastVisitDate = lastVisitDate;
      return this;
    }
    @Override
    public Geocache.Builder difficulty(float difficulty) {
      this.difficulty = difficulty;
      return this;
    }
    @Override
    public Geocache.Builder favoritePoints(int favoritePoints) {
      this.favoritePoints = favoritePoints;
      return this;
    }
    @Override
    public Geocache.Builder foundDate(@Nullable Date foundDate) {
      this.foundDate = foundDate;
      return this;
    }
    @Override
    public Geocache.Builder personalNote(@Nullable String personalNote) {
      this.personalNote = personalNote;
      return this;
    }
    @Override
    public Geocache.Builder favoritedByUser(boolean favoritedByUser) {
      this.favoritedByUser = favoritedByUser;
      return this;
    }
    @Override
    public Geocache.Builder foundByUser(boolean foundByUser) {
      this.foundByUser = foundByUser;
      return this;
    }
    @Override
    public Geocache.Builder id(long id) {
      this.id = id;
      return this;
    }
    @Override
    public Geocache.Builder imageCount(int imageCount) {
      this.imageCount = imageCount;
      return this;
    }
    @Override
    public Geocache.Builder premium(boolean premium) {
      this.premium = premium;
      return this;
    }
    @Override
    public Geocache.Builder recommended(boolean recommended) {
      this.recommended = recommended;
      return this;
    }
    @Override
    public Geocache.Builder coordinates(Coordinates coordinates) {
      if (coordinates == null) {
        throw new NullPointerException("Null coordinates");
      }
      this.coordinates = coordinates;
      return this;
    }
    @Override
    public Geocache.Builder name(String name) {
      if (name == null) {
        throw new NullPointerException("Null name");
      }
      this.name = name;
      return this;
    }
    @Override
    public Geocache.Builder owner(User owner) {
      if (owner == null) {
        throw new NullPointerException("Null owner");
      }
      this.owner = owner;
      return this;
    }
    @Override
    public Geocache.Builder placedBy(String placedBy) {
      if (placedBy == null) {
        throw new NullPointerException("Null placedBy");
      }
      this.placedBy = placedBy;
      return this;
    }
    @Override
    public Geocache.Builder publishDate(@Nullable Date publishDate) {
      this.publishDate = publishDate;
      return this;
    }
    @Override
    public Geocache.Builder terrain(float terrain) {
      this.terrain = terrain;
      return this;
    }
    @Override
    public Geocache.Builder trackableCount(int trackableCount) {
      this.trackableCount = trackableCount;
      return this;
    }
    @Override
    public Geocache.Builder placeDate(Date placeDate) {
      if (placeDate == null) {
        throw new NullPointerException("Null placeDate");
      }
      this.placeDate = placeDate;
      return this;
    }
    @Override
    public Geocache.Builder url(String url) {
      if (url == null) {
        throw new NullPointerException("Null url");
      }
      this.url = url;
      return this;
    }
    @Override
    public Geocache.Builder guid(String guid) {
      if (guid == null) {
        throw new NullPointerException("Null guid");
      }
      this.guid = guid;
      return this;
    }
    @Override
    public Geocache.Builder waypoints(@Nullable List<Waypoint> waypoints) {
      this.waypoints = waypoints;
      return this;
    }
    @Override
    public Geocache.Builder hint(@Nullable String hint) {
      this.hint = hint;
      return this;
    }
    @Override
    public Geocache.Builder longDescription(@Nullable String longDescription) {
      this.longDescription = longDescription;
      return this;
    }
    @Override
    public Geocache.Builder longDescriptionHtml(boolean longDescriptionHtml) {
      this.longDescriptionHtml = longDescriptionHtml;
      return this;
    }
    @Override
    public Geocache.Builder shortDescription(@Nullable String shortDescription) {
      this.shortDescription = shortDescription;
      return this;
    }
    @Override
    public Geocache.Builder shortDescriptionHtml(boolean shortDescriptionHtml) {
      this.shortDescriptionHtml = shortDescriptionHtml;
      return this;
    }
    @Override
    public Geocache.Builder trackables(@Nullable List<Trackable> trackables) {
      this.trackables = trackables;
      return this;
    }
    @Override
    public Geocache.Builder userWaypoints(@Nullable List<UserWaypoint> userWaypoints) {
      this.userWaypoints = userWaypoints;
      return this;
    }
    @Override
    public Geocache.Builder images(@Nullable List<ImageData> images) {
      this.images = images;
      return this;
    }
    @Override
    public Geocache.Builder attributes(@Nullable EnumSet<AttributeType> attributes) {
      this.attributes = attributes;
      return this;
    }
    @Override
    public Geocache.Builder countryName(@Nullable String countryName) {
      this.countryName = countryName;
      return this;
    }
    @Override
    public Geocache.Builder createDate(@Nullable Date createDate) {
      this.createDate = createDate;
      return this;
    }
    @Override
    public Geocache.Builder geocacheLogs(@Nullable List<GeocacheLog> geocacheLogs) {
      this.geocacheLogs = geocacheLogs;
      return this;
    }
    @Override
    public Geocache.Builder stateName(@Nullable String stateName) {
      this.stateName = stateName;
      return this;
    }
    @Override
    public Geocache build() {
      String missing = "";
      if (this.archived == null) {
        missing += " archived";
      }
      if (this.available == null) {
        missing += " available";
      }
      if (this.geocacheType == null) {
        missing += " geocacheType";
      }
      if (this.favoritable == null) {
        missing += " favoritable";
      }
      if (this.code == null) {
        missing += " code";
      }
      if (this.containerType == null) {
        missing += " containerType";
      }
      if (this.difficulty == null) {
        missing += " difficulty";
      }
      if (this.favoritePoints == null) {
        missing += " favoritePoints";
      }
      if (this.favoritedByUser == null) {
        missing += " favoritedByUser";
      }
      if (this.foundByUser == null) {
        missing += " foundByUser";
      }
      if (this.id == null) {
        missing += " id";
      }
      if (this.imageCount == null) {
        missing += " imageCount";
      }
      if (this.premium == null) {
        missing += " premium";
      }
      if (this.recommended == null) {
        missing += " recommended";
      }
      if (this.coordinates == null) {
        missing += " coordinates";
      }
      if (this.name == null) {
        missing += " name";
      }
      if (this.owner == null) {
        missing += " owner";
      }
      if (this.placedBy == null) {
        missing += " placedBy";
      }
      if (this.terrain == null) {
        missing += " terrain";
      }
      if (this.trackableCount == null) {
        missing += " trackableCount";
      }
      if (this.placeDate == null) {
        missing += " placeDate";
      }
      if (this.url == null) {
        missing += " url";
      }
      if (this.guid == null) {
        missing += " guid";
      }
      if (this.longDescriptionHtml == null) {
        missing += " longDescriptionHtml";
      }
      if (this.shortDescriptionHtml == null) {
        missing += " shortDescriptionHtml";
      }
      if (!missing.isEmpty()) {
        throw new IllegalStateException("Missing required properties:" + missing);
      }
      return new AutoValue_Geocache(
          this.archived,
          this.available,
          this.geocacheType,
          this.favoritable,
          this.code,
          this.containerType,
          this.lastUpdateDate,
          this.lastVisitDate,
          this.difficulty,
          this.favoritePoints,
          this.foundDate,
          this.personalNote,
          this.favoritedByUser,
          this.foundByUser,
          this.id,
          this.imageCount,
          this.premium,
          this.recommended,
          this.coordinates,
          this.name,
          this.owner,
          this.placedBy,
          this.publishDate,
          this.terrain,
          this.trackableCount,
          this.placeDate,
          this.url,
          this.guid,
          this.waypoints,
          this.hint,
          this.longDescription,
          this.longDescriptionHtml,
          this.shortDescription,
          this.shortDescriptionHtml,
          this.trackables,
          this.userWaypoints,
          this.images,
          this.attributes,
          this.countryName,
          this.createDate,
          this.geocacheLogs,
          this.stateName);
    }
  }

}
