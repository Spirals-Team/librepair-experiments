
package com.arcao.geocaching.api.data;

import com.arcao.geocaching.api.data.userprofile.FavoritePointStats;
import com.arcao.geocaching.api.data.userprofile.GeocacheStats;
import com.arcao.geocaching.api.data.userprofile.GlobalStats;
import com.arcao.geocaching.api.data.userprofile.PublicProfile;
import com.arcao.geocaching.api.data.userprofile.TrackableStats;
import java.util.List;
import javax.annotation.Generated;
import org.jetbrains.annotations.Nullable;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_UserProfile extends UserProfile {

  private final FavoritePointStats favoritePointsStats;
  private final GeocacheStats geocacheStats;
  private final PublicProfile publicProfile;
  private final List<Souvenir> souvenirs;
  private final GlobalStats globalStats;
  private final TrackableStats trackableStats;
  private final User user;

  private AutoValue_UserProfile(
      @Nullable FavoritePointStats favoritePointsStats,
      @Nullable GeocacheStats geocacheStats,
      @Nullable PublicProfile publicProfile,
      @Nullable List<Souvenir> souvenirs,
      GlobalStats globalStats,
      @Nullable TrackableStats trackableStats,
      User user) {
    this.favoritePointsStats = favoritePointsStats;
    this.geocacheStats = geocacheStats;
    this.publicProfile = publicProfile;
    this.souvenirs = souvenirs;
    this.globalStats = globalStats;
    this.trackableStats = trackableStats;
    this.user = user;
  }

  @Nullable
  @Override
  public FavoritePointStats favoritePointsStats() {
    return favoritePointsStats;
  }

  @Nullable
  @Override
  public GeocacheStats geocacheStats() {
    return geocacheStats;
  }

  @Nullable
  @Override
  public PublicProfile publicProfile() {
    return publicProfile;
  }

  @Nullable
  @Override
  public List<Souvenir> souvenirs() {
    return souvenirs;
  }

  @Override
  public GlobalStats globalStats() {
    return globalStats;
  }

  @Nullable
  @Override
  public TrackableStats trackableStats() {
    return trackableStats;
  }

  @Override
  public User user() {
    return user;
  }

  @Override
  public String toString() {
    return "UserProfile{"
        + "favoritePointsStats=" + favoritePointsStats + ", "
        + "geocacheStats=" + geocacheStats + ", "
        + "publicProfile=" + publicProfile + ", "
        + "souvenirs=" + souvenirs + ", "
        + "globalStats=" + globalStats + ", "
        + "trackableStats=" + trackableStats + ", "
        + "user=" + user
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof UserProfile) {
      UserProfile that = (UserProfile) o;
      return ((this.favoritePointsStats == null) ? (that.favoritePointsStats() == null) : this.favoritePointsStats.equals(that.favoritePointsStats()))
           && ((this.geocacheStats == null) ? (that.geocacheStats() == null) : this.geocacheStats.equals(that.geocacheStats()))
           && ((this.publicProfile == null) ? (that.publicProfile() == null) : this.publicProfile.equals(that.publicProfile()))
           && ((this.souvenirs == null) ? (that.souvenirs() == null) : this.souvenirs.equals(that.souvenirs()))
           && (this.globalStats.equals(that.globalStats()))
           && ((this.trackableStats == null) ? (that.trackableStats() == null) : this.trackableStats.equals(that.trackableStats()))
           && (this.user.equals(that.user()));
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= (favoritePointsStats == null) ? 0 : this.favoritePointsStats.hashCode();
    h *= 1000003;
    h ^= (geocacheStats == null) ? 0 : this.geocacheStats.hashCode();
    h *= 1000003;
    h ^= (publicProfile == null) ? 0 : this.publicProfile.hashCode();
    h *= 1000003;
    h ^= (souvenirs == null) ? 0 : this.souvenirs.hashCode();
    h *= 1000003;
    h ^= this.globalStats.hashCode();
    h *= 1000003;
    h ^= (trackableStats == null) ? 0 : this.trackableStats.hashCode();
    h *= 1000003;
    h ^= this.user.hashCode();
    return h;
  }

  private static final long serialVersionUID = 872593420072537813L;

  static final class Builder extends UserProfile.Builder {
    private FavoritePointStats favoritePointsStats;
    private GeocacheStats geocacheStats;
    private PublicProfile publicProfile;
    private List<Souvenir> souvenirs;
    private GlobalStats globalStats;
    private TrackableStats trackableStats;
    private User user;
    Builder() {
    }
    @Override
    public UserProfile.Builder favoritePointsStats(@Nullable FavoritePointStats favoritePointsStats) {
      this.favoritePointsStats = favoritePointsStats;
      return this;
    }
    @Override
    public UserProfile.Builder geocacheStats(@Nullable GeocacheStats geocacheStats) {
      this.geocacheStats = geocacheStats;
      return this;
    }
    @Override
    public UserProfile.Builder publicProfile(@Nullable PublicProfile publicProfile) {
      this.publicProfile = publicProfile;
      return this;
    }
    @Override
    public UserProfile.Builder souvenirs(@Nullable List<Souvenir> souvenirs) {
      this.souvenirs = souvenirs;
      return this;
    }
    @Override
    public UserProfile.Builder globalStats(GlobalStats globalStats) {
      if (globalStats == null) {
        throw new NullPointerException("Null globalStats");
      }
      this.globalStats = globalStats;
      return this;
    }
    @Override
    public UserProfile.Builder trackableStats(@Nullable TrackableStats trackableStats) {
      this.trackableStats = trackableStats;
      return this;
    }
    @Override
    public UserProfile.Builder user(User user) {
      if (user == null) {
        throw new NullPointerException("Null user");
      }
      this.user = user;
      return this;
    }
    @Override
    public UserProfile build() {
      String missing = "";
      if (this.globalStats == null) {
        missing += " globalStats";
      }
      if (this.user == null) {
        missing += " user";
      }
      if (!missing.isEmpty()) {
        throw new IllegalStateException("Missing required properties:" + missing);
      }
      return new AutoValue_UserProfile(
          this.favoritePointsStats,
          this.geocacheStats,
          this.publicProfile,
          this.souvenirs,
          this.globalStats,
          this.trackableStats,
          this.user);
    }
  }

}
