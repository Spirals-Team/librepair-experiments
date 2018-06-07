package net.posesor;

import lombok.Value;

@Value
public class NotifyDocumentsChangedCommand {
    private String principalName;
}
