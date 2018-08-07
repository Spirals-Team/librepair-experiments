package com.hedvig.productPricing.service.sagas;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.DeleteObjectTaggingRequest;
import com.amazonaws.services.s3.model.GetObjectTaggingRequest;
import com.amazonaws.services.s3.model.ObjectTagging;
import com.amazonaws.services.s3.model.SetObjectTaggingRequest;
import com.hedvig.productPricing.service.events.CertificateUploadFailedEvent;
import com.hedvig.productPricing.service.events.CertificateUploadedEvent;
import lombok.val;
import org.axonframework.eventhandling.saga.EndSaga;
import org.axonframework.eventhandling.saga.SagaEventHandler;
import org.axonframework.eventhandling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@Saga
public class CertificateUploadSaga {

    @Autowired
    AmazonS3Client s3Client;

    private static Logger log = LoggerFactory.getLogger(CertificateUploadSaga.class);

    @StartSaga
    @EndSaga
    @SagaEventHandler(associationProperty = "memberId")
    public void on(CertificateUploadFailedEvent e) {
        log.info("Deleting certificate '{}' from s3 bucket: {}  due to {}", e.getCertificateKey(), e.getBucketName(), e.getMessage());
        s3Client.deleteObject(e.getBucketName(), e.getCertificateKey());
    }

}
