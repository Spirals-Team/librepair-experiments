package com.hedvig.productPricing.service.events;

import lombok.Value;

import java.util.UUID;

@Value
public class CertificateUploadedEvent {
    String memberId;
    UUID productId;
    String bucketName;
    String certificateKey;
}
