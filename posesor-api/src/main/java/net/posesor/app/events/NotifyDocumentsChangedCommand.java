package net.posesor.app.events;

import lombok.Value;

@Value
public class NotifyDocumentsChangedCommand {
    private String principalName;
}
