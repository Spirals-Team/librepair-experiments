package net.posesor.api.commands;

import lombok.Value;

@Value
public class NotifySubjectCreatedCommand {
    private String principalName;
}
