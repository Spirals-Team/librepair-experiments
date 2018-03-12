package com.sismics.docs.core.util;

import com.google.common.collect.Lists;
import com.google.common.io.Resources;
import com.sismics.docs.core.dao.jpa.dto.DocumentDto;
import com.sismics.docs.core.model.jpa.File;
import com.sismics.util.mime.MimeType;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;

/**
 * Test of the file utilities.
 * 
 * @author bgamard
 */
public class TestFileUtil {
    @Test
    public void extractContentOpenDocumentTextTest() throws Exception {
        Path path = Paths.get(ClassLoader.getSystemResource("file/document.odt").toURI());
        File file = new File();
        file.setMimeType(MimeType.OPEN_DOCUMENT_TEXT);
        Path pdfPath = PdfUtil.convertToPdf(file, path);
        String content = FileUtil.extractContent(null, file, path, pdfPath);
        Assert.assertTrue(content.contains("Lorem ipsum dolor sit amen."));
    }
    
    @Test
    public void extractContentOfficeDocumentTest() throws Exception {
        Path path = Paths.get(ClassLoader.getSystemResource("file/document.docx").toURI());
        File file = new File();
        file.setMimeType(MimeType.OFFICE_DOCUMENT);
        Path pdfPath = PdfUtil.convertToPdf(file, path);
        String content = FileUtil.extractContent(null, file, path, pdfPath);
        Assert.assertTrue(content.contains("Lorem ipsum dolor sit amen."));
    }

    @Test
    public void extractContentPdf() throws Exception {
        Path path = Paths.get(ClassLoader.getSystemResource("file/udhr.pdf").toURI());
        File file = new File();
        file.setMimeType(MimeType.APPLICATION_PDF);
        String content = FileUtil.extractContent(null, file, path, path);
        Assert.assertTrue(content.contains("All human beings are born free and equal in dignity and rights."));
    }

    @Test
    public void extractContentScannedPdf() throws Exception {
        Path path = Paths.get(ClassLoader.getSystemResource("file/scanned.pdf").toURI());
        File file = new File();
        file.setMimeType(MimeType.APPLICATION_PDF);
        String content = FileUtil.extractContent("eng", file, path, path);
        System.out.println(content);
        Assert.assertTrue(content.contains("All human beings are born free and equal in dignity and rights."));
    }

    @Test
    public void convertToPdfTest() throws Exception {
        try (InputStream inputStream0 = Resources.getResource("file/apollo_landscape.jpg").openStream();
                InputStream inputStream1 = Resources.getResource("file/apollo_portrait.jpg").openStream();
                InputStream inputStream2 = Resources.getResource("file/udhr_encrypted.pdf").openStream();
                InputStream inputStream3 = Resources.getResource("file/document.docx").openStream();
                InputStream inputStream4 = Resources.getResource("file/document.odt").openStream()) {
            // Document
            DocumentDto documentDto = new DocumentDto();
            documentDto.setTitle("My super document 1");
            documentDto.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit.\r\n Duis id turpis iaculis, commodo est ac, efficitur quam.\t Nam accumsan magna in orci vulputate ultricies. Sed vulputate neque magna, at laoreet leo ultricies vel. Proin eu hendrerit felis. Quisque sit amet arcu efficitur, pulvinar orci sed, imperdiet elit. Nunc posuere ex sed fermentum congue. Aliquam ultrices convallis finibus. Praesent iaculis justo vitae dictum auctor. Praesent suscipit imperdiet erat ac maximus. Aenean pharetra quam sed fermentum commodo. Donec sagittis ipsum nibh, id congue dolor venenatis quis. In tincidunt nisl non ex sollicitudin, a imperdiet neque scelerisque. Nullam lacinia ac orci sed faucibus. Donec tincidunt venenatis justo, nec fermentum justo rutrum a.");
            documentDto.setSubject("A set of random picture");
            documentDto.setIdentifier("ID-2016-08-00001");
            documentDto.setPublisher("My Publisher, Inc.");
            documentDto.setFormat("A4 standard ISO format");
            documentDto.setType("Image");
            documentDto.setCoverage("France");
            documentDto.setRights("Public Domain");
            documentDto.setLanguage("en");
            documentDto.setCreator("user1");
            documentDto.setCreateTimestamp(new Date().getTime());
            
            // First file
            Files.copy(inputStream0, DirectoryUtil.getStorageDirectory().resolve("apollo_landscape"), StandardCopyOption.REPLACE_EXISTING);
            File file0 = new File();
            file0.setId("apollo_landscape");
            file0.setMimeType(MimeType.IMAGE_JPEG);
            
            // Second file
            Files.copy(inputStream1, DirectoryUtil.getStorageDirectory().resolve("apollo_portrait"), StandardCopyOption.REPLACE_EXISTING);
            File file1 = new File();
            file1.setId("apollo_portrait");
            file1.setMimeType(MimeType.IMAGE_JPEG);
            
            // Third file
            Files.copy(inputStream2, DirectoryUtil.getStorageDirectory().resolve("udhr"), StandardCopyOption.REPLACE_EXISTING);
            File file2 = new File();
            file2.setId("udhr");
            file2.setPrivateKey("OnceUponATime");
            file2.setMimeType(MimeType.APPLICATION_PDF);
            
            // Fourth file
            Files.copy(inputStream3, DirectoryUtil.getStorageDirectory().resolve("document_docx"), StandardCopyOption.REPLACE_EXISTING);
            File file3 = new File();
            file3.setId("document_docx");
            file3.setMimeType(MimeType.OFFICE_DOCUMENT);
            
            // Fifth file
            Files.copy(inputStream4, DirectoryUtil.getStorageDirectory().resolve("document_odt"), StandardCopyOption.REPLACE_EXISTING);
            File file4 = new File();
            file4.setId("document_odt");
            file4.setMimeType(MimeType.OPEN_DOCUMENT_TEXT);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            PdfUtil.convertToPdf(documentDto, Lists.newArrayList(file0, file1, file2, file3, file4), true, true, 10, outputStream);
            Assert.assertTrue(outputStream.toByteArray().length > 0);
        }
    }
}
