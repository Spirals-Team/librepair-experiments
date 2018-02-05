package io.descoped.client.external.enhreg;

import com.github.kevinsawicki.http.HttpRequest;
import io.descoped.client.exception.APIClientException;
import io.descoped.client.util.ConsoleProgress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.zip.GZIPInputStream;

/**
 * @author Ove Ranheim (oranheim@gmail.com)
 * @since 21/11/2017
 */
public class BusinessEntityRegisterClient {

    private static final List<String> FIELD_DEFS = getDatatypeList();
    private static final Logger log = LoggerFactory.getLogger(BusinessEntityRegisterClient.class);
    private static String BRREG_SELSKAPSINFO_URL = "http://data.brreg.no/enhetsregisteret/download/enheter";
    private static String SAFE_SPLIT_QUOTED_REGEXP = ";(?=([^\"]*\"[^\"]*\")*[^\"]*$)";
    private String json;
    private int line;
    private String body;
    private File tempGZipFile;
    private File tempFile;

    public BusinessEntityRegisterClient() {
    }

    private static List<String> getDatatypeList() {
        List<String> list = new ArrayList<>();
        list.add("organisasjonsnummer");
        list.add("navn");
        list.add("stiftelsesdato");
        list.add("registreringsdatoEnhetsregisteret");
        list.add("organisasjonsform");
        list.add("hjemmeside");
        list.add("registrertIFrivillighetsregisteret");
        list.add("registrertIMvaregisteret");
        list.add("registrertIForetaksregisteret");
        list.add("registrertIStiftelsesregisteret");
        list.add("frivilligRegistrertIMvaregisteret");
        list.add("antallAnsatte");
        list.add("institusjonellSektorkode.kode");
        list.add("institusjonellSektorkode.beskrivelse");
        list.add("naeringskode1.kode");
        list.add("naeringskode1.beskrivelse");
        list.add("naeringskode2.kode");
        list.add("naeringskode2.beskrivelse");
        list.add("naeringskode3.kode");
        list.add("naeringskode3.beskrivelse");
        list.add("postadresse.adresse");
        list.add("postadresse.postnummer");
        list.add("postadresse.poststed");
        list.add("postadresse.kommunenummer");
        list.add("postadresse.kommune");
        list.add("postadresse.landkode");
        list.add("postadresse.land");
        list.add("forretningsadresse.adresse");
        list.add("forretningsadresse.postnummer");
        list.add("forretningsadresse.poststed");
        list.add("forretningsadresse.kommunenummer");
        list.add("forretningsadresse.kommune");
        list.add("forretningsadresse.landkode");
        list.add("forretningsadresse.land");
        list.add("sisteInnsendteAarsregnskap");
        list.add("konkurs");
        list.add("underAvvikling");
        list.add("underTvangsavviklingEllerTvangsopplosning");
        list.add("overordnetEnhet");
        list.add("målform");
        list.add("orgform.kode");
        list.add("orgform.beskrivelse");
        return list;
    }

    private String[] splitLine(String row) {
        return row.split(SAFE_SPLIT_QUOTED_REGEXP);
    }

    private String convertToJson(String[] splitted) {
        StringBuffer buf = new StringBuffer();
        buf.append("{\n");
        List<String> fieldDefs = FIELD_DEFS;
        for (int n = 0; n < splitted.length; n++) {
            String value = splitted[n];
            String field = fieldDefs.get(n);

            buf.append("   \"").append(field).append("\": ");

            if (value.length() == 0)
                buf.append("null");
            else
                buf.append(value).append("");

            if (n < splitted.length - 1) buf.append(",");

            buf.append("\n");
        }
        buf.append("}");
        return buf.toString();
    }

    public void fetch() throws IOException {
        log.trace("Fetch: {}", BRREG_SELSKAPSINFO_URL);
        HttpRequest req = HttpRequest.get(BRREG_SELSKAPSINFO_URL);
        if (!req.ok()) throw new APIClientException("Error fetching brreg selskapsdata");

        tempGZipFile = File.createTempFile("brreg-selskaper", ".tmp.gz");
        long contentLength = Long.valueOf(req.header("Content-Length"));
        log.trace("Temp-file: {} -> Content-Length: {} bytes", tempGZipFile.getAbsolutePath(), contentLength);
        Thread progressThread = ConsoleProgress.consoleProgressThread(tempGZipFile, contentLength);
        try {
            req.receive(tempGZipFile);
        } finally {
            ConsoleProgress.interruptProgress(progressThread);
        }
    }

    public void unpack() throws IOException {
        if (!tempGZipFile.exists()) throw new APIClientException("You must call download before you can unpack");
        tempFile = File.createTempFile("brreg-selskaper", ".csv");
        log.trace("Unpack to: {}", tempFile.getAbsolutePath());

        byte[] buffer = new byte[1024];
        try {
            GZIPInputStream gzis;
            try (FileInputStream fis = new FileInputStream(tempGZipFile)) {
                gzis = new GZIPInputStream(fis);
                try (FileOutputStream out = new FileOutputStream(tempFile)) {
                    int len;
                    while ((len = gzis.read(buffer)) > 0) {
                        out.write(buffer, 0, len);
                    }

                    gzis.close();
                    out.close();
                }
            }
            System.out.println("Done unpacking!");

        } catch (IOException ex) {
            throw new APIClientException(ex);
        }
    }

    public void build(Consumer<?> callback) throws IOException {
        json = null;
        line = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(tempFile.toString()))) {
            String rowLine;
            while ((rowLine = br.readLine()) != null) {
                if (line == 0) {
                    line++;
                    continue;
                }
                String[] splitted = splitLine(rowLine);
                json = convertToJson(splitted);
    //                log.trace("{}", json);

                if (line % 100 == 0) log.trace("line: {}", line);

                if (line > 10000) break;

                line++;
            }
        }


    }

}
