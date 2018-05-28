package interfaces;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = entity.City.class)
public interface ICity {
    void setName(String name);
    void setLat(double lat);
    void setLon(double lon);
    String getName();
    double getLat();
    double getLon();
    
}
