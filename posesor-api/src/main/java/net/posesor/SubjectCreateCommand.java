package net.posesor;

import lombok.Value;

@Value
public class SubjectCreateCommand {
    private String principalName;
    private String subjectId;
    private String name;
}
