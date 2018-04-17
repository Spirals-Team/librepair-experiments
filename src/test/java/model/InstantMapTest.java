package model;


import model.device.Device;
import model.device.InstantMap;
import model.device.MacAddress;
import model.device.roles.AccessPoint;
import model.device.roles.Role;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.Instant;

public class InstantMapTest {
    Coordinates coords0 ;
    Coordinates coords1 ;
    Device dev0;
    Device dev1;
    Role ap0;
    Role ap1;
    InstantMap instantMap;


    @Before
    public void setUp(){
        byte [] macdev0= {(byte)0XAB,(byte)0XCD,(byte)0XEF,(byte)0XAB,(byte)0XCD,(byte)0XEF};
        byte [] macdev1= {(byte)0XAA,(byte)0XBB,(byte)0XCC,(byte)0XDD,(byte)0XEE,(byte)0XFF};
        coords1 = new Coordinates(33.69201,-13.02367);
        coords0 = new Coordinates(8.20609,172.71046);
        dev0 = new Device(new MacAddress(macdev0));
        dev1 = new Device(new MacAddress(macdev1));
        instantMap = new InstantMap(Instant.now());
        ap0 = new AccessPoint(dev0, "Wifi Ungs");
        ap1 = new AccessPoint(dev1, "Campus");


    }


    @Test
    public void addDevice(){
        instantMap.register(ap0,coords0);
        Assert.assertEquals(instantMap.getCoordinates(ap0), coords0);
    }

    @Test
    public void removeDevice(){
        instantMap.register(ap1,coords1);
        instantMap.unregister(ap1);
        Assert.assertNull(instantMap.getCoordinates(ap1));


    }

}
