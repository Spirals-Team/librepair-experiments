package net.posesor.app.events;

import lombok.Value;

@Value
public class DocumentsChanged {
    private String principalName;
}
