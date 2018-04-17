package model;


import model.device.Device;
import model.device.ConfigurationMap;
import model.device.MacAddress;
import model.device.roles.AccessPoint;
import model.device.roles.Role;
import model.device.roles.Sniffer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class ConfigurationMapTest {
    Coordinates coords0 ;
    Coordinates coords1 ;
    Device dev0;
    Device dev1;
    Sniffer ap0;
    Sniffer ap1;
    ConfigurationMap ConfigurationMap;


    @Before
    public void setUp(){
        byte [] macdev0= {(byte)0XAB,(byte)0XCD,(byte)0XEF,(byte)0XAB,(byte)0XCD,(byte)0XEF};
        byte [] macdev1= {(byte)0XAA,(byte)0XBB,(byte)0XCC,(byte)0XDD,(byte)0XEE,(byte)0XFF};
        coords1 = new Coordinates(33.69201,-13.02367);
        coords0 = new Coordinates(8.20609,172.71046);
        dev0 = new Device(new MacAddress(macdev0));
        dev1 = new Device(new MacAddress(macdev1));
        ConfigurationMap = new ConfigurationMap();
        ap0 = new Sniffer(dev0,100);
        ap1 = new Sniffer(dev1,150);


    }


    @Test
    public void addDevice(){
        ConfigurationMap.register(ap0,coords0);
        Assert.assertEquals(ConfigurationMap.getCoordinates(ap0), coords0);
    }

    @Test
    public void removeDevice(){
        ConfigurationMap.register(ap1,coords1);
        ConfigurationMap.unregister(ap1);
        Assert.assertNull(ConfigurationMap.getCoordinates(ap1));


    }

}
