package com.algolia.search.responses;

import com.algolia.search.inputs.analytics.ABTest;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.ArrayList;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ABTests {
  public ArrayList<ABTest> abtests;
  public int count;
  public int total;
}
