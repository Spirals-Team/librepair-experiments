package com.hedvig.productPricing.service.events;

import lombok.Value;

@Value
public class CertificateUploadFailedEvent {
    String memberId;
    String bucketName;
    String certificateKey;
    String message;
}
