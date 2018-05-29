

package com.arcao.geocaching.api.data;

import com.arcao.geocaching.api.data.coordinates.Coordinates;
import com.arcao.geocaching.api.data.type.MemberType;
import javax.annotation.Generated;
import org.jetbrains.annotations.Nullable;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_User extends User {

  private final long id;
  private final String publicGuid;
  private final String userName;
  private final String avatarUrl;
  private final Coordinates homeCoordinates;
  private final boolean admin;
  private final MemberType memberType;
  private final int findCount;
  private final int hideCount;
  private final int galleryImageCount;

  private AutoValue_User(
      long id,
      String publicGuid,
      String userName,
      String avatarUrl,
      @Nullable Coordinates homeCoordinates,
      boolean admin,
      @Nullable MemberType memberType,
      int findCount,
      int hideCount,
      int galleryImageCount) {
    this.id = id;
    this.publicGuid = publicGuid;
    this.userName = userName;
    this.avatarUrl = avatarUrl;
    this.homeCoordinates = homeCoordinates;
    this.admin = admin;
    this.memberType = memberType;
    this.findCount = findCount;
    this.hideCount = hideCount;
    this.galleryImageCount = galleryImageCount;
  }

  @Override
  public long id() {
    return id;
  }

  @Override
  public String publicGuid() {
    return publicGuid;
  }

  @Override
  public String userName() {
    return userName;
  }

  @Override
  public String avatarUrl() {
    return avatarUrl;
  }

  @Nullable
  @Override
  public Coordinates homeCoordinates() {
    return homeCoordinates;
  }

  @Override
  public boolean admin() {
    return admin;
  }

  @Nullable
  @Override
  public MemberType memberType() {
    return memberType;
  }

  @Override
  public int findCount() {
    return findCount;
  }

  @Override
  public int hideCount() {
    return hideCount;
  }

  @Override
  public int galleryImageCount() {
    return galleryImageCount;
  }

  @Override
  public String toString() {
    return "User{"
         + "id=" + id + ", "
         + "publicGuid=" + publicGuid + ", "
         + "userName=" + userName + ", "
         + "avatarUrl=" + avatarUrl + ", "
         + "homeCoordinates=" + homeCoordinates + ", "
         + "admin=" + admin + ", "
         + "memberType=" + memberType + ", "
         + "findCount=" + findCount + ", "
         + "hideCount=" + hideCount + ", "
         + "galleryImageCount=" + galleryImageCount
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof User) {
      User that = (User) o;
      return (this.id == that.id())
           && (this.publicGuid.equals(that.publicGuid()))
           && (this.userName.equals(that.userName()))
           && (this.avatarUrl.equals(that.avatarUrl()))
           && ((this.homeCoordinates == null) ? (that.homeCoordinates() == null) : this.homeCoordinates.equals(that.homeCoordinates()))
           && (this.admin == that.admin())
           && ((this.memberType == null) ? (that.memberType() == null) : this.memberType.equals(that.memberType()))
           && (this.findCount == that.findCount())
           && (this.hideCount == that.hideCount())
           && (this.galleryImageCount == that.galleryImageCount());
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h$ = 1;
    h$ *= 1000003;
    h$ ^= (int) ((id >>> 32) ^ id);
    h$ *= 1000003;
    h$ ^= publicGuid.hashCode();
    h$ *= 1000003;
    h$ ^= userName.hashCode();
    h$ *= 1000003;
    h$ ^= avatarUrl.hashCode();
    h$ *= 1000003;
    h$ ^= (homeCoordinates == null) ? 0 : homeCoordinates.hashCode();
    h$ *= 1000003;
    h$ ^= admin ? 1231 : 1237;
    h$ *= 1000003;
    h$ ^= (memberType == null) ? 0 : memberType.hashCode();
    h$ *= 1000003;
    h$ ^= findCount;
    h$ *= 1000003;
    h$ ^= hideCount;
    h$ *= 1000003;
    h$ ^= galleryImageCount;
    return h$;
  }

  private static final long serialVersionUID = 1808891631464643511L;

  static final class Builder extends User.Builder {
    private Long id;
    private String publicGuid;
    private String userName;
    private String avatarUrl;
    private Coordinates homeCoordinates;
    private Boolean admin;
    private MemberType memberType;
    private Integer findCount;
    private Integer hideCount;
    private Integer galleryImageCount;
    Builder() {
    }
    @Override
    public User.Builder id(long id) {
      this.id = id;
      return this;
    }
    @Override
    public User.Builder publicGuid(String publicGuid) {
      if (publicGuid == null) {
        throw new NullPointerException("Null publicGuid");
      }
      this.publicGuid = publicGuid;
      return this;
    }
    @Override
    public User.Builder userName(String userName) {
      if (userName == null) {
        throw new NullPointerException("Null userName");
      }
      this.userName = userName;
      return this;
    }
    @Override
    public User.Builder avatarUrl(String avatarUrl) {
      if (avatarUrl == null) {
        throw new NullPointerException("Null avatarUrl");
      }
      this.avatarUrl = avatarUrl;
      return this;
    }
    @Override
    public User.Builder homeCoordinates(@Nullable Coordinates homeCoordinates) {
      this.homeCoordinates = homeCoordinates;
      return this;
    }
    @Override
    public User.Builder admin(boolean admin) {
      this.admin = admin;
      return this;
    }
    @Override
    public User.Builder memberType(@Nullable MemberType memberType) {
      this.memberType = memberType;
      return this;
    }
    @Override
    public User.Builder findCount(int findCount) {
      this.findCount = findCount;
      return this;
    }
    @Override
    public User.Builder hideCount(int hideCount) {
      this.hideCount = hideCount;
      return this;
    }
    @Override
    public User.Builder galleryImageCount(int galleryImageCount) {
      this.galleryImageCount = galleryImageCount;
      return this;
    }
    @Override
    public User build() {
      String missing = "";
      if (this.id == null) {
        missing += " id";
      }
      if (this.publicGuid == null) {
        missing += " publicGuid";
      }
      if (this.userName == null) {
        missing += " userName";
      }
      if (this.avatarUrl == null) {
        missing += " avatarUrl";
      }
      if (this.admin == null) {
        missing += " admin";
      }
      if (this.findCount == null) {
        missing += " findCount";
      }
      if (this.hideCount == null) {
        missing += " hideCount";
      }
      if (this.galleryImageCount == null) {
        missing += " galleryImageCount";
      }
      if (!missing.isEmpty()) {
        throw new IllegalStateException("Missing required properties:" + missing);
      }
      return new AutoValue_User(
          this.id,
          this.publicGuid,
          this.userName,
          this.avatarUrl,
          this.homeCoordinates,
          this.admin,
          this.memberType,
          this.findCount,
          this.hideCount,
          this.galleryImageCount);
    }
  }

}
