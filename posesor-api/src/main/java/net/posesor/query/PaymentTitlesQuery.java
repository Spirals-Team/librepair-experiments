package net.posesor.query;

import lombok.Value;

@Value
public class PaymentTitlesQuery {
    private String principalName;
    private String hint;
}
