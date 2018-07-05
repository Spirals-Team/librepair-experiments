package com.ljd.hackajob.phonebook.model;

import java.util.List;

public class Page<T> {
    private final List<T> results;
    private final long totalCount;
    
    public Page(List<T> results, long totalCount) {
        this.results = results;
        this.totalCount = totalCount;
    }
    
    public List<T> getResults() {
        return results;
    }
    
    public long getTotalCount() {
        return totalCount;
    }
}
