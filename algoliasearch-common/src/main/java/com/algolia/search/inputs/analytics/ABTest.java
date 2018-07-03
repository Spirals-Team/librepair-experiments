package com.algolia.search.inputs.analytics;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

public class ABTest {
  // Properties available at construction time
  public String name;
  public List<Variant> variants;
  public ZonedDateTime endAt;

  // Properties set by the AB Testing API
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  public long abTestID;

  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  public int clickSignificance;

  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  public int conversionSignificance;

  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  public ZonedDateTime createdAt;

  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  public String status;

  public ABTest() {}

  public ABTest(String name, List<Variant> variants, LocalDateTime endAt) {
    this.name = name;
    this.variants = variants;
    this.endAt = endAt.atZone(ZoneId.of("UTC"));
  }
}
