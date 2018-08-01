package net.posesor.app.events;

import lombok.Value;

@Value
public class NotifySettlementsChangedCommand {
    private String principalName;
}

