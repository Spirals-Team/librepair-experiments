package validator;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class FileExtensionValidatorTest {

    FileExtensionValidator validator;

    @Before
    public void setUp(){
        validator = new FileExtensionValidator(".pcap");
    }

    @Test
    public void validatePcapExtension(){
        assertTrue(validator.validateExtension("/home/test/.pcapwireless.pcap"));

    }

    @Test
    public void validateWrongPcapExtension(){
        assertFalse(validator.validateExtension("/home/test/.pcapwireless.pcap."));
    }

}