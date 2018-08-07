package com.hedvig.productPricing.service.pdfcreate;

import java.io.IOException;
import java.time.LocalDate;

public interface PdfCreator {
    byte[] createMandate(LocalDate date, String ssn, String fullName, String referenceToken, String signature) throws IOException;
}
