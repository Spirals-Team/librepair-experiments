package model;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import model.device.Device;
import model.device.MacAddress;
import model.device.roles.AccessPoint;

public class CapturaServiceTest {
	
	ArrayList<Captura> capturas;
	
	MacAddress ma1;
	MacAddress ma2;
	MacAddress ma3;
	
	@Before
    public void setUp(){
    	
    	ArrayList<Captura> capturas;
    	
    	byte addra[] = {  0x4a, 0x08, 0x7f, 0x41, 0x12, 0x47 };
    	byte addrc[] = {  0x46, 0xa, 0x5b, 0x68, 0x15, 0x4c };
    	byte addre[] = {  0x4d, 0xa, 0x57, 0x4c, 0x4c, 0x09 };
    	byte addrd[] = {  0x4d, 0xa, 0x57, 0x4c, 0x4c, 0x09 };

    	
    	byte d1[] = {  0x4d, 0xa, 0x57, 0x4c, 0x4c, 0x09 };     	 //access point a
    	MacAddress ma1 = new MacAddress(d1);
    	Device dev1 = new Device(ma1);
    	AccessPoint a = new AccessPoint(dev1, "");
    	
    	byte d2[] = {  0x4d, 0xa, 0x57, 0x4c, 0x4c, 0x09 };			//access point b
    	MacAddress ma2 = new MacAddress(d2);
    	Device dev2 = new Device(ma2);
    	AccessPoint b = new AccessPoint(dev2, "");
    	
    	byte d3[] = {  0x4d, 0xa, 0x57, 0x4c, 0x4c, 0x09 };			// access point d
    	MacAddress ma3 = new MacAddress(d3);
    	Device dev3 = new Device(ma3);
    	AccessPoint d = new AccessPoint(dev3, "");
    }

	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
