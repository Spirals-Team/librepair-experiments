package com.algolia.search.inputs.analytics;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.annotation.Nonnull;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Variant {
  // Properties available at construction time
  public String index;
  public int trafficPercentage;
  public String description;

  // Properties set by the AB Testing API
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  public int averageClickPosition;

  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  public int clickCount;

  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  public float clickThroughRate;

  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  public int conversionCount;

  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  public float conversionRate;

  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  public int noResultCount;

  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  public int searchCount;

  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  public int userCount;

  public Variant() {}

  public Variant(@Nonnull String index, @Nonnull int trafficPercentage, String description) {
    this.index = index;
    this.trafficPercentage = trafficPercentage;
    this.description = (description != null) ? description : "";
  }
}
