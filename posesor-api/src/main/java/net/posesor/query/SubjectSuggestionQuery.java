package net.posesor.query;

import lombok.Value;

@Value
public class SubjectSuggestionQuery {
    private String principalName;
    private String hint;
}
