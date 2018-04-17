package model.device.roles;


import model.device.Device;

public class AccessPoint extends Role {

    private String SSID;



    public AccessPoint(Device dev, String SSID){
        this.device = dev;
        this.SSID = SSID;
    }

    public String getSSID(){return SSID;}



    public Device getDevice() {
        return device;
    }
}
