package com.hashmap.haf.metadata.config.actors.message.query;

import java.util.Set;

public class ScheduleQueryMsg {

    private Set<String> queries;

    public ScheduleQueryMsg(Set<String> queries) {
        this.queries = queries;
    }
}
