package model.device.roles;
import model.device.Device;

import java.util.Objects;


public class Sniffer extends Role {
    long rangeInMeters;

    public Sniffer(Device device,long rangeInMeters){
        this.rangeInMeters = Math.abs(rangeInMeters);
        this.device = device;
    }
    public Device getDevice() {
        return device;
    }

    @Override
    public String toString() {
        return "Sniffer{" +
                "rangeInMeters=" + rangeInMeters +
                ", device=" + device +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sniffer sniffer = (Sniffer) o;
        return rangeInMeters == sniffer.rangeInMeters &&
                Objects.equals(device, sniffer.device);
    }

    @Override
    public int hashCode() {

        return Objects.hash(rangeInMeters, device);
    }
}
