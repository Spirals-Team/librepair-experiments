package model.device.roles;

import model.device.Device;

public abstract class Role {
    protected Device device;

   public Device getDevice(){
        return device;
    }

}
