package com.rgross.comparator;

import com.rgross.model.Location;

import java.util.Comparator;

/**
 * Created by ryan_gross on 11/30/17.
 */
public class LocationComparator implements Comparator<Location> {

    @Override
    public int compare(Location locationOne, Location locationTwo) {

        if (locationOne.getCountyPercentage() < locationTwo.getCountyPercentage()) {
            return 0;
        } else {
            return 1;
        }

    }

}
