package com.hedvig.productPricing.service.pdfcreate;

import com.itextpdf.html2pdf.HtmlConverter;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.UUID;

@Component
public class PdfCreatorImpl implements PdfCreator {
 
    public static final ClassPathResource HTML = new ClassPathResource("/test.html");
    public static final String DEST = "fullmakt.pdf";

    private final String insuranceMandateTemplate;

    public PdfCreatorImpl() throws IOException {
        insuranceMandateTemplate = IOUtils.toString(new ClassPathResource("fullmakt.html").getInputStream(), Charset.forName("UTF-8"));
    }

    @Override
    public byte[] createMandate(LocalDate date, String ssn, String fullName, String referenceToken, String signature) throws IOException {

        String filledTemplate = this.insuranceMandateTemplate.
                replace("{DATE}", date.format(DateTimeFormatter.ISO_LOCAL_DATE)).
                replace("{SSN}", ssn).
                replace("{FULL_NAME}", fullName).
                replace("{REFERENCE_ID}", referenceToken).
                replace("{SIGNATURE}", String.join("\n", signature.split("(?<=\\G.{140})")));

        return convertToPdf(filledTemplate);
    }

    /**
     * Main method
     */
    public static void main(String[] args) throws IOException {
        /*
        PdfCreator creator = new PdfCreatorImpl();
        final String pretendReferenceToken = UUID.randomUUID().toString();
        final byte[] mandate = creator.createMandate(LocalDate.now(), "YEAHAA", "Mr Storförsäkraren", pretendReferenceToken, signatureB64);
        //convertToPdf(new ClassPathResource("fullmakt.html").getInputStream(), new FileOutputStream(DEST), LocalDate.now(), "8409188516", "Johan Tjelldén");
        //new PdfCreator().createPdf(fileOutputStream, "2017-10-09", "1900-00-00", "John Doe");
        File f = new File("fullmakt.pdf");
        FileUtils.writeByteArrayToFile(f, mandate);*/

    }

    private static byte[] convertToPdf(String template) throws IOException {

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        HtmlConverter.convertToPdf(template, output);
        return output.toByteArray();
    }
}