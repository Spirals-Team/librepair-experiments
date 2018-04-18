package model.device;

import model.Coordinates;
import model.device.roles.Role;
import java.time.Instant;
import java.util.HashMap;

public class InstantMap {

    HashMap<Role, Coordinates> locations;
    Instant instantOfMap;

    public InstantMap(Instant instant){
        this.instantOfMap=instant;
        locations = new HashMap<>();

    }

    public void register(Role role, Coordinates coords){
            locations.put(role, coords);
    }
    public boolean unregister(Role role){
        return locations.remove(role)!=null;
    }
    public Coordinates getCoordinates(Role role){
        return locations.get(role);

    }


}
