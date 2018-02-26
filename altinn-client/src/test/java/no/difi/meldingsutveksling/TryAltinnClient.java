package no.difi.meldingsutveksling;

import no.difi.meldingsutveksling.domain.sbdh.EduDocument;
import no.difi.meldingsutveksling.shipping.UploadRequest;
import org.slf4j.Marker;

public class TryAltinnClient {

    private static class MockRequest implements UploadRequest {

        @Override
        public String getSender() {
            return "123456789";
        }

        @Override
        public String getReceiver() {
            return "987654321";
        }

        @Override
        public String getSenderReference() {
            return "my reference";
        }

        @Override
        public EduDocument getPayload() {
            return new EduDocument();
        }

        @Override
        public Marker getMarkers() {
            return null;
        }

    }

    public static void main(String[] args) {
        AltinnClient altinnClient = new AltinnClient();

        System.out.println(".... Uploading test.zip");
        altinnClient.send(new TryAltinnClient.MockRequest());
        System.out.println(".... Downloading test.zip");
        altinnClient.download("test.zip");
    }
}
