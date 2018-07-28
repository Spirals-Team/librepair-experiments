/*
 * Copyright (C) 2017 Axel Müller <axel.mueller@avanux.de>
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */
package de.avanux.smartapplianceenabler.meter;

import de.avanux.smartapplianceenabler.appliance.ApplianceIdConsumer;
import de.avanux.smartapplianceenabler.control.Control;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A PulsePowerMeter calculates power consumption by pulses received.
 */
class PulsePowerMeter implements ApplianceIdConsumer {
    private Logger logger = LoggerFactory.getLogger(PulsePowerMeter.class);
    private String applianceId;
    private static final int MAX_AGE = 3600; // seconds
    private List<Long> impulseTimestamps = Collections.synchronizedList(new ArrayList<Long>());
    private Integer impulsesPerKwh;
    private Integer measurementInterval; // seconds
    private double powerChangeFactor = 2.0;
    private Integer powerBeforeIncrease;
    private boolean powerDecreaseDetected;
    private Control control;

    void setImpulsesPerKwh(Integer impulsesPerKwh) {
        this.impulsesPerKwh = impulsesPerKwh;
    }

    void setMeasurementInterval(Integer measurementInterval) {
        this.measurementInterval = measurementInterval;
    }

    public Integer getMeasurementInterval() {
        return measurementInterval;
    }

    public void setControl(Control control) {
        this.control = control;
    }

    @Override
    public void setApplianceId(String applianceId) {
        this.applianceId = applianceId;
    }

    /**
     * Returns true if the appliance has a control and the control is switched on.
     * If the appliance has no control the appliance state is derived from its power consumption.
     * The device considered to be switched on if the age of the most recent impulse
     * is less than double the interval between the most recent and the second most recent impulse.
     * @return true, if the device is considered to be switched on.
     */
    public boolean isOn() {
        return isOn(System.currentTimeMillis());
    }

    boolean isOn(long referenceTimestamp) {
        if(control != null) {
            return control.isOn();
        }
        return impulseTimestamps.size() > 1 && ! isIntervalIncreaseAboveFactor(powerChangeFactor, referenceTimestamp);
    }

    /**
     * Returns the average power consumption during measurement interval.
     * If there are no timestamps in measurement interval it is calculated by the two most recent timestamps ignoring
     * the measurement interval. If there are less than two timestamps power consumption is 0.
     *
     * @return the average power consumption in W
     */
    public int getAveragePower() {
        return getAveragePower(System.currentTimeMillis());
    }

    int getAveragePower(long referenceTimestamp) {
        checkMandatoryAttributes();
        if(isOn(referenceTimestamp) && isIntervalIncreaseAboveFactor(powerChangeFactor, referenceTimestamp) && powerBeforeIncrease != null) {
            long maxTimestampAge = Double.valueOf(getInterval(powerBeforeIncrease) * powerChangeFactor).longValue();
            if(referenceTimestamp - getSecondMostRecentTimestamp() < maxTimestampAge) {
                /**
                 * Make sure that after a period of high power normal power is reported even before the first impulse
                 * of the low power period is received. Otherwise high power would be reported for a long time even though
                 * low power was consumed.
                 */
                logger.debug("{}: average power (as before increase) = {}W", applianceId, powerBeforeIncrease);
                return powerBeforeIncrease;
            }
            logger.debug("{}: average power (timestamps before increase too old) = 0W", applianceId);
            powerDecreaseDetected = true;
            return 0;
        }
        int timestampsInMeasurementInterval = getImpulsesInMeasurementInterval(referenceTimestamp).size();
        if(timestampsInMeasurementInterval > 1) {
            logger.debug("{}: impulses={} impulsesPerKwh={} measurementInterval={}", applianceId, timestampsInMeasurementInterval, impulsesPerKwh, measurementInterval);
            int averagePower = (timestampsInMeasurementInterval * 1000/impulsesPerKwh) * (3600/measurementInterval);
            logger.debug("{}: averagePower={}W", applianceId, averagePower);
            return averagePower;
        }
        else if (isOn(referenceTimestamp)) {
            // less than 2 timestamps in measurement interval
            return getCurrentPower(referenceTimestamp);
        }
        else {
            logger.debug("{}: Not switched on.", applianceId);
        }
        return 0;
    }

    /**
     * Returns the minimum power consumption during measurement interval.
     * If there are no timestamps in measurement interval it is calculated by the two most recent timestamps ignoring
     * the measurement interval. If there are less than two timestamps power consumption is 0.
     *
     * @return the minimum power consumption in W
     */
    public int getMinPower() {
        return getMinPower(System.currentTimeMillis());
    }

    int getMinPower(long referenceTimestamp) {
        int minPower = 0;
        // min power = longest interval between two timestamps
        List<Long> timestamps = getImpulsesInMeasurementInterval(referenceTimestamp);
        if(timestamps.size() > 1) {
            long longestInterval = 0;
            for(int i=1;i<timestamps.size();i++) {
                long interval = timestamps.get(i) - timestamps.get(i-1);
                if(interval > longestInterval) {
                    longestInterval = interval;
                }
            }
            minPower = getPower(longestInterval);
            logger.debug("{}: minPower={}W longestInterval={}", applianceId, minPower, longestInterval);
        }
        else if (isOn(referenceTimestamp)) {
            // less than 2 timestamps in measurement interval
            minPower = getCurrentPower(referenceTimestamp);
        }
        int averagePower = getAveragePower(referenceTimestamp);
        if(averagePower < minPower) {
            minPower = averagePower;
        }
        return minPower;
    }

    /**
     * Returns the maximum power consumption during measurement interval.
     * If there are no timestamps in measurement interval it is calculated by the two most recent timestamps ignoring
     * the measurement interval. If there are less than two timestamps power consumption is 0.
     *
     * @return the minimum power consumption in W
     */
    public int getMaxPower() {
        return getMaxPower(System.currentTimeMillis());
    }

    int getMaxPower(long referenceTimestamp) {
        int maxPower = 0;
        // max power = shortest interval between two timestamps
        List<Long> timestamps = getImpulsesInMeasurementInterval(referenceTimestamp);
        if(timestamps.size() > 1) {
            long shortestInterval = Long.MAX_VALUE;
            for(int i=1;i<timestamps.size();i++) {
                long interval = timestamps.get(i) - timestamps.get(i-1);
                if(interval < shortestInterval) {
                    shortestInterval = interval;
                }
            }
            maxPower = getPower(shortestInterval);
            logger.debug("{}: maxPower={}W shortestInterval={}", applianceId, maxPower, shortestInterval);
        }
        else if (isOn(referenceTimestamp)) {
            // less than 2 timestamps in measurement interval
            maxPower = getCurrentPower(referenceTimestamp);
        }
        int averagePower = getAveragePower(referenceTimestamp);
        if(averagePower > maxPower) {
            maxPower = averagePower;
        }
        return maxPower;
    }

    private int getCurrentPower(long referenceTimestamp) {
        int currentPower = 0;
        Long intervalBetweenTwoMostRecentImpulseTimestamps = getIntervalBetweenTwoMostRecentImpulses();
        if(intervalBetweenTwoMostRecentImpulseTimestamps != null) {
            if(! isIntervalIncreaseAboveFactor(powerChangeFactor, referenceTimestamp)) {
                // calculate power from 2 most recent timestamps
                currentPower = getPower(intervalBetweenTwoMostRecentImpulseTimestamps);
            }
            else {
                logger.warn("{}: More than 2 impulses cached but not recent enough.", applianceId);
            }
        }
        else {
            logger.warn("{}: No or less than 2 impulses cached.", applianceId);
        }
        logger.debug("{}: currentPower={}W", applianceId, currentPower);
        return currentPower;
    }

    List<Long> getImpulsesInMeasurementInterval(long referenceTimestamp) {
        List<Long> timestamps = new ArrayList<Long>();
        if(measurementInterval != null) {
            for(Long timestamp : new ArrayList<>(impulseTimestamps)) {
                if(referenceTimestamp - timestamp < measurementInterval * 1000) {
                    timestamps.add(timestamp);
                }
            }
        }
        logger.debug("{}: {} timestamps in measurement interval", applianceId, timestamps.size());
        return timestamps;
    }

    /**
     * Returns true, if the interval between the two most recent timestamps multiplied by
     * the factor is less than the interval between the most recent timestamp and the reference timestamp.
     * This method can be used to detect a decrease in power consumption.
     * @param factor
     * @param referenceTimestamp
     * @return
     */
    private boolean isIntervalIncreaseAboveFactor(double factor, long referenceTimestamp) {
        Long intervalBetweenTwoMostRecentImpulses = getIntervalBetweenTwoMostRecentImpulses();
        if(intervalBetweenTwoMostRecentImpulses != null) {
            // at this point we can be sure that there are at least two timestamps cached
            long mostRecentTimestamp = getMostRecentTimestamp();
            long intervalSinceMostRecentTimestamp = referenceTimestamp - mostRecentTimestamp;
            if(intervalBetweenTwoMostRecentImpulses * factor < intervalSinceMostRecentTimestamp) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true, if the interval between the two most recent timestamps divided by
     * the factor is greater than the interval between the most recent timestamp and the reference timestamp.
     * This method can be used to detect an increase in power consumption.
     * @param factor
     * @param referenceTimestamp
     * @return
     */
    private boolean isIntervalDecreaseBelowFactor(double factor, long referenceTimestamp) {
        Long intervalBetweenTwoMostRecentImpulses = getIntervalBetweenTwoMostRecentImpulses();
        if(intervalBetweenTwoMostRecentImpulses != null) {
            // at this point we can be sure that there are at least two timestamps cached
            long mostRecentTimestamp = impulseTimestamps.get(impulseTimestamps.size() - 1);
            long intervalSinceMostRecentTimestamp = referenceTimestamp - mostRecentTimestamp;
            if(intervalBetweenTwoMostRecentImpulses / factor > intervalSinceMostRecentTimestamp) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the interval between the most recent and the second most recent impulse.
     * @return the interval in milliseconds or null; if there are less than 2 impulse timestamps cached
     */
    private Long getIntervalBetweenTwoMostRecentImpulses() {
        Long mostRecentTimestamp = getMostRecentTimestamp();
        Long secondMostRecentTimestamp = getSecondMostRecentTimestamp();
        if(mostRecentTimestamp != null && secondMostRecentTimestamp != null) {
            return mostRecentTimestamp - secondMostRecentTimestamp;
        }
        return null;
    }

    private Long getMostRecentTimestamp() {
        if(impulseTimestamps.size() > 0) {
            return impulseTimestamps.get(impulseTimestamps.size() - 1);
        }
        return null;
    }

    private Long getSecondMostRecentTimestamp() {
        if(impulseTimestamps.size() > 1) {
            return impulseTimestamps.get(impulseTimestamps.size() - 2);
        }
        return null;
    }

    /**
     * Returns the interval between two timestamps for the given power.
     * @param power the power in watts
     * @return the interval in milliseconds
     */
    private long getInterval(int power) {
        if(power == 0 || impulsesPerKwh == null) {
            return 0;
        }
        return Double.valueOf(3600000 / (power * 1000/impulsesPerKwh)).intValue();
    }

    /**
     * Returns the power cased on the time interval between two impulses.
     * @param intervalBetweenTwoImpulses
     * @return
     */
    private int getPower(long intervalBetweenTwoImpulses) {
        if(intervalBetweenTwoImpulses == 0 || impulsesPerKwh == null) {
            return 0;
        }
        return Double.valueOf(3600000 / (intervalBetweenTwoImpulses * 1000/impulsesPerKwh)).intValue();
    }

    void addTimestampAndMaintain(long timestampMillis) {
        // check for power increase
        if(powerBeforeIncrease == null && isIntervalDecreaseBelowFactor(powerChangeFactor, timestampMillis)) {
            // powerBeforeIncrease == null : avoid detecting intermediate values during ramp up
            powerBeforeIncrease = getCurrentPower(timestampMillis);
            logger.debug("{}: Power increase detected. Power before: {}W", applianceId, powerBeforeIncrease);
        }
        // check for power decrease
        if(powerDecreaseDetected) {
            logger.debug("{}: Reset 'power before increase' since power decrease has been detected", applianceId);
            powerDecreaseDetected = false;
            powerBeforeIncrease = null;
        }
        // add new timestamp
        impulseTimestamps.add(timestampMillis);
        // remove timestamps older than MAX_AGE
        List<Long> impulseTimestampsForRemoval = new ArrayList<Long>();
        for(Long impulseTimestamp : new ArrayList<>(impulseTimestamps)) {
            if(timestampMillis - impulseTimestamp > MAX_AGE * 1000) {
                impulseTimestampsForRemoval.add(impulseTimestamp);
            }
            else {
                break;
            }
        }
        if(impulseTimestampsForRemoval.size() > 0) {
            impulseTimestamps.removeAll(impulseTimestampsForRemoval);
        }
        logger.debug("{}: timestamps added/removed/total: 1/{}/{}", applianceId, impulseTimestampsForRemoval.size(), impulseTimestamps.size());
    }

    private void checkMandatoryAttributes() {
        if(impulsesPerKwh == null) {
            logger.warn("{}: Configuration attributes impulsesPerKwh not set!", applianceId);
        }
        if(measurementInterval == null) {
            logger.warn("{}: Configuration attributes measurementInterval not set!", applianceId);
        }
    }
}
