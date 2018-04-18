package model;


import model.device.ConfigurationMap;
import model.device.Device;
import model.device.MacAddress;
import model.device.roles.Sniffer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import parser.JsonConverter;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class JsonTest {

    Device dev0;
    Device dev1;
    Sniffer sniff0;
    Sniffer sniff1;
    byte[] macdev0 = {(byte) 0XAB, (byte) 0XCD, (byte) 0XEF, (byte) 0XAB, (byte) 0XCD, (byte) 0XEF};
    byte[] macdev1 = {(byte) 0XAA, (byte) 0XBB, (byte) 0XCC, (byte) 0XDD, (byte) 0XEE, (byte) 0XFF};
    Coordinates coords1 = new Coordinates(33.69201, -13.02367);
    Coordinates coords0 = new Coordinates(8.20609, 172.71046);

    ConfigurationMap map1;

    @Before
    public void setUp() {
        dev0 = new Device(new MacAddress(macdev0));
        dev1 = new Device(new MacAddress(macdev1));
        sniff0 = new Sniffer(dev0,100);
        sniff1 = new Sniffer(dev1,100);
        map1 = new ConfigurationMap();
        map1.register(sniff0, coords0);
        map1.register(sniff1, coords1);
    }


    @Test
    public void generateAndParse() throws IOException {
        //Generate Json
        JsonConverter<ConfigurationMap> jsonConverter = new JsonConverter<>();

        InputStream json=jsonConverter.to(map1);

        //Parse Json
        ConfigurationMap parsed = jsonConverter.from(json);

        //Test
        Assert.assertEquals(map1,parsed);
        Assert.assertEquals(coords0,parsed.getCoordinates(sniff0));
        Assert.assertEquals(coords1,parsed.getCoordinates(sniff1));
    }






    }




