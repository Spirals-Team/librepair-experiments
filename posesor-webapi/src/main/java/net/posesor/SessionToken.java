package net.posesor;

import lombok.Value;

/**
 * Example of POJO token used to simplify logic and dependency injection.
 */
@Value
public class SessionToken {
    private String userName;
}
