package model.device.roles;

import model.device.Device;

public class Client extends Role {


    public Client(Device device){
        this.device = device;
    }

    @Override
    public String toString() {
        return "Client{" +
                "device=" + device +
                '}';
    }

}
