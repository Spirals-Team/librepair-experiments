package fileReader;

import org.junit.Before;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.assertTrue;


public class PcapFileReaderTest {
    PcapFileReader pcapFileReader;

    @Before
    public void setup(){
        OutputStream outputStream = new ByteArrayOutputStream();
        File file = new File("test-files" + File.separator + "test-capture.pcap");
        pcapFileReader = new PcapFileReader(file);
    }
    @Test
    public void readPcapFile(){
        String toCompare = "44000000900000000000000000000000"+
                        "00000000000000004400010000000400"+
                        "00000000440002000000040000000000"+
                        "44000300000004000100000044000400"+
                        "000004002a0000000000000000000000"+
                        "0000000044000600000004002a000000"+
                        "00000000000000000000000044000800"+
                        "00000400020000004400090000000400"+
                        "0000000044000a00000004005c000000"+
                        "80000000ffffffffffff00239c3bd020"+
                        "00239c3bd020a0e64cb3455709000000"+
                        "6400210400124a756e697065725f5353"+
                        "47355f6261736963010882848b960c18"+
                        "30480301010504000100000706555320"+
                        "010b1b2a010032041224606c";

        assertTrue(toCompare.equals(pcapFileReader.read()));
    }


}
