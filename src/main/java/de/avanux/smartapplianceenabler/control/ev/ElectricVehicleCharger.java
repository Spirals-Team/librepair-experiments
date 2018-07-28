/*
 * Copyright (C) 2018 Axel Müller <axel.mueller@avanux.de>
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

package de.avanux.smartapplianceenabler.control.ev;

import de.avanux.smartapplianceenabler.appliance.Appliance;
import de.avanux.smartapplianceenabler.appliance.ApplianceIdConsumer;
import de.avanux.smartapplianceenabler.control.Control;
import de.avanux.smartapplianceenabler.control.ControlStateChangedListener;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

@XmlAccessorType(XmlAccessType.FIELD)
public class ElectricVehicleCharger implements Control, ApplianceIdConsumer {

    private transient Logger logger = LoggerFactory.getLogger(ElectricVehicleCharger.class);
    @XmlAttribute
    private Integer voltage = 230;
    @XmlAttribute
    private Integer phases = 1;
    @XmlElements({
            @XmlElement(name = "EVModbusControl", type = EVModbusControl.class),
    })
    private EVControl evControl;
    private transient Appliance appliance;
    private transient String applianceId;
    private transient State state = State.VEHICLE_NOT_CONNECTED;
    private transient boolean useOptionalEnergy = true;
    private transient List<ControlStateChangedListener> controlStateChangedListeners = new ArrayList<>();

    protected enum State {
        VEHICLE_NOT_CONNECTED,
        VEHICLE_CONNECTED,
        CHARGING,
        CHARGING_COMPLETED
    }

    public void setAppliance(Appliance appliance) {
        this.appliance = appliance;
    }

    @Override
    public void setApplianceId(String applianceId) {
        this.applianceId = applianceId;
        evControl.setApplianceId(applianceId);
    }

    public EVControl getEvControl() {
        return evControl;
    }

    protected void setEvControl(EVControl evControl) {
        this.evControl = evControl;
    }

    public void init() {
        logger.debug("{}: voltage={} phases={}", this.applianceId, this.voltage, this.phases);
        evControl.validate();
    }

    public void start(Timer timer) {
        stopCharging();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
               State previousState = state;
               state = getNewState(state);
                if(state != previousState) {
                    onStateChanged(previousState, state);
                }
                else {
                    logger.debug("{}: Vehicle state={}", applianceId, state);
                }
            }
        }, 0, evControl.getVehicleStatusPollInterval() * 1000);
    }

    protected State getNewState(State currenState) {
        State newState = currenState;
        if(currenState == State.CHARGING_COMPLETED) {
            if (evControl.isVehicleConnected()) {
                newState = State.VEHICLE_CONNECTED;
            }
            else {
                newState = State.VEHICLE_NOT_CONNECTED;
            }
        }
        else if(currenState == State.CHARGING) {
            if(evControl.isChargingCompleted()) {
                newState = State.CHARGING_COMPLETED;
            }
            else if(!evControl.isCharging()) {
                if(evControl.isVehicleConnected()) {
                    newState = State.VEHICLE_CONNECTED;
                }
                else {
                    newState = State.VEHICLE_NOT_CONNECTED;
                }
            }
        }
        else if(currenState == State.VEHICLE_CONNECTED) {
            if(evControl.isCharging()) {
                newState = State.CHARGING;
            }
            else if(! evControl.isVehicleConnected()) {
                newState = State.VEHICLE_NOT_CONNECTED;
            }
        }
        else if(evControl.isVehicleConnected()) {
            newState = State.VEHICLE_CONNECTED;
        }
        return newState;
    }

    @Override
    public boolean on(LocalDateTime now, boolean switchOn) {
        if(switchOn) {
            logger.info("{}: Switching on", applianceId);
            startCharging();
        }
        else {
            logger.info("{}: Switching off", applianceId);
            stopCharging();
        }
        for(ControlStateChangedListener listener : controlStateChangedListeners) {
            listener.controlStateChanged(now, switchOn);
        }
        return true;
    }

    @Override
    public boolean isOn() {
        return isCharging();
    }

    private void onStateChanged(State previousState, State newState) {
        logger.debug("{}: Vehicle state changed: previousState={} newState={}", applianceId, previousState, newState);
        if(newState == State.VEHICLE_CONNECTED) {
            this.appliance.activateSchedules();
        }
        if(newState == State.VEHICLE_NOT_CONNECTED) {
            this.appliance.deactivateSchedules();
            stopCharging();
        }
    }

    @Override
    public void addControlStateChangedListener(ControlStateChangedListener listener) {
        this.controlStateChangedListeners.add(listener);
    }

    public boolean isVehicleConnected() {
        return state == State.VEHICLE_CONNECTED;
    }

    public boolean isCharging() {
        return state == State.CHARGING;
    }

    public boolean isChargingCompleted() {
        return state == State.CHARGING_COMPLETED;
    }

    public boolean isUseOptionalEnergy() {
        return useOptionalEnergy;
    }

    public void setChargePower(int power) {
        int current = Float.valueOf(power / (this.voltage * this.phases)).intValue();
        logger.debug("{}: Set charge power: {}W corresponds to {}A", applianceId, power, current);
        evControl.setChargeCurrent(current);
    }

    public void startCharging() {
        logger.debug("{}: Start charging process", applianceId);
        evControl.startCharging();
    }

    public void stopCharging() {
        logger.debug("{}: Stop charging process", applianceId);
        evControl.stopCharging();
    }

}
