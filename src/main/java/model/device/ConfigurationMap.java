package model.device;

import model.Coordinates;
import model.device.roles.Sniffer;


import java.util.HashMap;
import java.util.Objects;

public class ConfigurationMap {
        HashMap<Sniffer, Coordinates> locations;



    public ConfigurationMap(){
            locations = new HashMap<>();

        }

        public void register(Sniffer Sniffer, Coordinates coords){
            locations.put(Sniffer, coords);
        }
        public boolean unregister(Sniffer Sniffer){
            return locations.remove(Sniffer)!=null;
        }
        public Coordinates getCoordinates(Sniffer Sniffer){
            return locations.get(Sniffer);

        }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConfigurationMap that = (ConfigurationMap) o;
        return Objects.equals(locations, that.locations);
    }

    @Override
    public int hashCode() {

        return Objects.hash(locations);
    }
}
