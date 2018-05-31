

package com.arcao.geocaching.api.data.userprofile;

import java.util.Date;
import javax.annotation.Generated;
import org.jetbrains.annotations.Nullable;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_PublicProfile extends PublicProfile {

  private final String forumTitle;
  private final Date lastVisit;
  private final String location;
  private final Date memberSince;
  private final String occupation;
  private final ProfilePhoto profilePhoto;
  private final String profileText;

  private AutoValue_PublicProfile(
      String forumTitle,
      Date lastVisit,
      String location,
      Date memberSince,
      String occupation,
      @Nullable ProfilePhoto profilePhoto,
      String profileText) {
    this.forumTitle = forumTitle;
    this.lastVisit = lastVisit;
    this.location = location;
    this.memberSince = memberSince;
    this.occupation = occupation;
    this.profilePhoto = profilePhoto;
    this.profileText = profileText;
  }

  @Override
  public String forumTitle() {
    return forumTitle;
  }

  @Override
  public Date lastVisit() {
    return lastVisit;
  }

  @Override
  public String location() {
    return location;
  }

  @Override
  public Date memberSince() {
    return memberSince;
  }

  @Override
  public String occupation() {
    return occupation;
  }

  @Nullable
  @Override
  public ProfilePhoto profilePhoto() {
    return profilePhoto;
  }

  @Override
  public String profileText() {
    return profileText;
  }

  @Override
  public String toString() {
    return "PublicProfile{"
         + "forumTitle=" + forumTitle + ", "
         + "lastVisit=" + lastVisit + ", "
         + "location=" + location + ", "
         + "memberSince=" + memberSince + ", "
         + "occupation=" + occupation + ", "
         + "profilePhoto=" + profilePhoto + ", "
         + "profileText=" + profileText
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof PublicProfile) {
      PublicProfile that = (PublicProfile) o;
      return (this.forumTitle.equals(that.forumTitle()))
           && (this.lastVisit.equals(that.lastVisit()))
           && (this.location.equals(that.location()))
           && (this.memberSince.equals(that.memberSince()))
           && (this.occupation.equals(that.occupation()))
           && ((this.profilePhoto == null) ? (that.profilePhoto() == null) : this.profilePhoto.equals(that.profilePhoto()))
           && (this.profileText.equals(that.profileText()));
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h$ = 1;
    h$ *= 1000003;
    h$ ^= forumTitle.hashCode();
    h$ *= 1000003;
    h$ ^= lastVisit.hashCode();
    h$ *= 1000003;
    h$ ^= location.hashCode();
    h$ *= 1000003;
    h$ ^= memberSince.hashCode();
    h$ *= 1000003;
    h$ ^= occupation.hashCode();
    h$ *= 1000003;
    h$ ^= (profilePhoto == null) ? 0 : profilePhoto.hashCode();
    h$ *= 1000003;
    h$ ^= profileText.hashCode();
    return h$;
  }

  private static final long serialVersionUID = 7624712344301138677L;

  static final class Builder extends PublicProfile.Builder {
    private String forumTitle;
    private Date lastVisit;
    private String location;
    private Date memberSince;
    private String occupation;
    private ProfilePhoto profilePhoto;
    private String profileText;
    Builder() {
    }
    @Override
    public PublicProfile.Builder forumTitle(String forumTitle) {
      if (forumTitle == null) {
        throw new NullPointerException("Null forumTitle");
      }
      this.forumTitle = forumTitle;
      return this;
    }
    @Override
    public PublicProfile.Builder lastVisit(Date lastVisit) {
      if (lastVisit == null) {
        throw new NullPointerException("Null lastVisit");
      }
      this.lastVisit = lastVisit;
      return this;
    }
    @Override
    public PublicProfile.Builder location(String location) {
      if (location == null) {
        throw new NullPointerException("Null location");
      }
      this.location = location;
      return this;
    }
    @Override
    public PublicProfile.Builder memberSince(Date memberSince) {
      if (memberSince == null) {
        throw new NullPointerException("Null memberSince");
      }
      this.memberSince = memberSince;
      return this;
    }
    @Override
    public PublicProfile.Builder occupation(String occupation) {
      if (occupation == null) {
        throw new NullPointerException("Null occupation");
      }
      this.occupation = occupation;
      return this;
    }
    @Override
    public PublicProfile.Builder profilePhoto(@Nullable ProfilePhoto profilePhoto) {
      this.profilePhoto = profilePhoto;
      return this;
    }
    @Override
    public PublicProfile.Builder profileText(String profileText) {
      if (profileText == null) {
        throw new NullPointerException("Null profileText");
      }
      this.profileText = profileText;
      return this;
    }
    @Override
    public PublicProfile build() {
      String missing = "";
      if (this.forumTitle == null) {
        missing += " forumTitle";
      }
      if (this.lastVisit == null) {
        missing += " lastVisit";
      }
      if (this.location == null) {
        missing += " location";
      }
      if (this.memberSince == null) {
        missing += " memberSince";
      }
      if (this.occupation == null) {
        missing += " occupation";
      }
      if (this.profileText == null) {
        missing += " profileText";
      }
      if (!missing.isEmpty()) {
        throw new IllegalStateException("Missing required properties:" + missing);
      }
      return new AutoValue_PublicProfile(
          this.forumTitle,
          this.lastVisit,
          this.location,
          this.memberSince,
          this.occupation,
          this.profilePhoto,
          this.profileText);
    }
  }

}
