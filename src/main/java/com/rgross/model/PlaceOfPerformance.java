package com.rgross.model;

import javax.persistence.*;

@Entity
@Table(name = "placeofperformance")
public class PlaceOfPerformance {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name= "ID", nullable=false, unique=true)
    private long id;


    @OneToOne
    @JoinColumn(name="LOCATION", referencedColumnName = "ID")
    private Location placeOfPerformanceLocation;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Location getPlaceOfPerformanceLocation() {
        return placeOfPerformanceLocation;
    }

    public void setPlaceOfPerformanceLocation(Location placeOfPerformanceLocation) {
        this.placeOfPerformanceLocation = placeOfPerformanceLocation;
    }

}
