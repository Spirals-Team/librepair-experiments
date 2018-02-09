package io.ebeaninternal.server.profile;

import io.ebeaninternal.metric.TimedMetricStats;

/**
 * Snapshot of the current statistics for a Counter or TimeCounter.
 */
class DTimeMetricStats implements TimedMetricStats {

  private final String name;

  private String location;

  private final long startTime;

  private final long count;

  private final long total;

  private final long max;

  DTimeMetricStats(String name, long collectionStart, long count, long total, long max) {
    this.name = name;
    this.startTime = collectionStart;
    this.count = count;
    this.total = total;
    // collection is racy so sanitize the max value if it has not been set
    // this most likely would happen when count = 1 so max = mean
    this.max = max != Long.MIN_VALUE ? max : (count < 1 ? 0 : Math.round(total / count));
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();
    if (location != null) {
      sb.append("loc:").append(location).append(" ");
    }
    if (name != null) {
      sb.append("name:").append(name).append(" ");
    }
    sb.append("count:").append(count)
      .append(" total:").append(total)
      .append(" max:").append(max);
    return sb.toString();
  }

  public void setLocation(String location) {
    this.location = location;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public String getLocation() {
    return location;
  }

  /**
   * Return the time the counter started statistics collection.
   */
  @Override
  public long getStartTime() {
    return startTime;
  }

  /**
   * Return the count of values collected.
   */
  @Override
  public long getCount() {
    return count;
  }

  /**
   * Return the total of all the values.
   */
  @Override
  public long getTotal() {
    return total;
  }

  /**
   * Return the Max value collected.
   */
  @Override
  public long getMax() {
    return max;
  }

  /**
   * Return the mean value rounded up.
   */
  @Override
  public long getMean() {
    return (count < 1) ? 0L : Math.round((double)(total / count));
  }

}
