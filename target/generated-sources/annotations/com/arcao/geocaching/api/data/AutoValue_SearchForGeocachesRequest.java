

package com.arcao.geocaching.api.data;

import com.arcao.geocaching.api.GeocachingApi;
import com.arcao.geocaching.api.data.coordinates.Coordinates;
import com.arcao.geocaching.api.data.sort.SortBy;
import com.arcao.geocaching.api.filter.Filter;
import java.util.Collection;
import javax.annotation.Generated;
import org.jetbrains.annotations.Nullable;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_SearchForGeocachesRequest extends SearchForGeocachesRequest {

  private final GeocachingApi.ResultQuality resultQuality;
  private final int maxPerPage;
  private final int geocacheLogCount;
  private final int trackableLogCount;
  private final Collection<Filter> filters;
  private final Collection<SortBy> sortKeys;
  private final Coordinates sortPoint;

  private AutoValue_SearchForGeocachesRequest(
      GeocachingApi.ResultQuality resultQuality,
      int maxPerPage,
      int geocacheLogCount,
      int trackableLogCount,
      Collection<Filter> filters,
      Collection<SortBy> sortKeys,
      @Nullable Coordinates sortPoint) {
    this.resultQuality = resultQuality;
    this.maxPerPage = maxPerPage;
    this.geocacheLogCount = geocacheLogCount;
    this.trackableLogCount = trackableLogCount;
    this.filters = filters;
    this.sortKeys = sortKeys;
    this.sortPoint = sortPoint;
  }

  @Override
  public GeocachingApi.ResultQuality resultQuality() {
    return resultQuality;
  }

  @Override
  public int maxPerPage() {
    return maxPerPage;
  }

  @Override
  public int geocacheLogCount() {
    return geocacheLogCount;
  }

  @Override
  public int trackableLogCount() {
    return trackableLogCount;
  }

  @Override
  public Collection<Filter> filters() {
    return filters;
  }

  @Override
  public Collection<SortBy> sortKeys() {
    return sortKeys;
  }

  @Nullable
  @Override
  public Coordinates sortPoint() {
    return sortPoint;
  }

  @Override
  public String toString() {
    return "SearchForGeocachesRequest{"
         + "resultQuality=" + resultQuality + ", "
         + "maxPerPage=" + maxPerPage + ", "
         + "geocacheLogCount=" + geocacheLogCount + ", "
         + "trackableLogCount=" + trackableLogCount + ", "
         + "filters=" + filters + ", "
         + "sortKeys=" + sortKeys + ", "
         + "sortPoint=" + sortPoint
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof SearchForGeocachesRequest) {
      SearchForGeocachesRequest that = (SearchForGeocachesRequest) o;
      return (this.resultQuality.equals(that.resultQuality()))
           && (this.maxPerPage == that.maxPerPage())
           && (this.geocacheLogCount == that.geocacheLogCount())
           && (this.trackableLogCount == that.trackableLogCount())
           && (this.filters.equals(that.filters()))
           && (this.sortKeys.equals(that.sortKeys()))
           && ((this.sortPoint == null) ? (that.sortPoint() == null) : this.sortPoint.equals(that.sortPoint()));
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h$ = 1;
    h$ *= 1000003;
    h$ ^= resultQuality.hashCode();
    h$ *= 1000003;
    h$ ^= maxPerPage;
    h$ *= 1000003;
    h$ ^= geocacheLogCount;
    h$ *= 1000003;
    h$ ^= trackableLogCount;
    h$ *= 1000003;
    h$ ^= filters.hashCode();
    h$ *= 1000003;
    h$ ^= sortKeys.hashCode();
    h$ *= 1000003;
    h$ ^= (sortPoint == null) ? 0 : sortPoint.hashCode();
    return h$;
  }

  private static final long serialVersionUID = 8209857729196134528L;

  static final class Builder extends SearchForGeocachesRequest.Builder {
    private GeocachingApi.ResultQuality resultQuality;
    private Integer maxPerPage;
    private Integer geocacheLogCount;
    private Integer trackableLogCount;
    private Collection<Filter> filters;
    private Collection<SortBy> sortKeys;
    private Coordinates sortPoint;
    Builder() {
    }
    @Override
    public SearchForGeocachesRequest.Builder resultQuality(GeocachingApi.ResultQuality resultQuality) {
      if (resultQuality == null) {
        throw new NullPointerException("Null resultQuality");
      }
      this.resultQuality = resultQuality;
      return this;
    }
    @Override
    public SearchForGeocachesRequest.Builder maxPerPage(int maxPerPage) {
      this.maxPerPage = maxPerPage;
      return this;
    }
    @Override
    public SearchForGeocachesRequest.Builder geocacheLogCount(int geocacheLogCount) {
      this.geocacheLogCount = geocacheLogCount;
      return this;
    }
    @Override
    public SearchForGeocachesRequest.Builder trackableLogCount(int trackableLogCount) {
      this.trackableLogCount = trackableLogCount;
      return this;
    }
    @Override
    protected SearchForGeocachesRequest.Builder filters(Collection<Filter> filters) {
      if (filters == null) {
        throw new NullPointerException("Null filters");
      }
      this.filters = filters;
      return this;
    }
    @Override
    protected SearchForGeocachesRequest.Builder sortKeys(Collection<SortBy> sortKeys) {
      if (sortKeys == null) {
        throw new NullPointerException("Null sortKeys");
      }
      this.sortKeys = sortKeys;
      return this;
    }
    @Override
    public SearchForGeocachesRequest.Builder sortPoint(@Nullable Coordinates sortPoint) {
      this.sortPoint = sortPoint;
      return this;
    }
    @Override
    protected SearchForGeocachesRequest realBuild() {
      String missing = "";
      if (this.resultQuality == null) {
        missing += " resultQuality";
      }
      if (this.maxPerPage == null) {
        missing += " maxPerPage";
      }
      if (this.geocacheLogCount == null) {
        missing += " geocacheLogCount";
      }
      if (this.trackableLogCount == null) {
        missing += " trackableLogCount";
      }
      if (this.filters == null) {
        missing += " filters";
      }
      if (this.sortKeys == null) {
        missing += " sortKeys";
      }
      if (!missing.isEmpty()) {
        throw new IllegalStateException("Missing required properties:" + missing);
      }
      return new AutoValue_SearchForGeocachesRequest(
          this.resultQuality,
          this.maxPerPage,
          this.geocacheLogCount,
          this.trackableLogCount,
          this.filters,
          this.sortKeys,
          this.sortPoint);
    }
  }

}
