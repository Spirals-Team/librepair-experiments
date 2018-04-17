package model.device;

import org.pcap4j.util.ByteArrays;
import org.pcap4j.util.LinkLayerAddress;


public class MacAddress extends LinkLayerAddress {
    public MacAddress(byte[] address){
        super(address);
    }
}
