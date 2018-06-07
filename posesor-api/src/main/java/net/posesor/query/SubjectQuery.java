package net.posesor.query;

import lombok.Value;

import java.util.List;

@Value
public class SubjectQuery {
    private String principalName;
    private List<String> subjectIds;
}
