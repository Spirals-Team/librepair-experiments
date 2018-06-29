package com.hedvig.productPricing.service.commands;

import lombok.Value;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

@Value
public class CertificateUploadCommand {
    @TargetAggregateIdentifier
    String memberId;
    String bucketName;
    String key;
}
