package com.example.playce;

import lombok.Builder;

// goes from shortest to longest distance
@Builder
public class MultipleResults {

    private final Result[] results;

    public Result[] getResults() {
       return results;
    }
}
