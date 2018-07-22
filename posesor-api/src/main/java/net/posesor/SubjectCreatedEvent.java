package net.posesor;

import lombok.Value;

@Value
public class SubjectCreatedEvent {
    private String principalName;
    private String subjectId;
    private String name;
}
