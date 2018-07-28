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

import de.avanux.smartapplianceenabler.control.ControlMonitor;

public interface Meter extends ControlMonitor {

    /**
     * Average power consumption during measurement interval in watt.
     * @return
     */
    int getAveragePower();

    /**
     * Minimum power consumption during measurement interval in watt.
     * @return
     */
    int getMinPower();

    /**
     * Maximum power consumption during measurement interval in watt.
     * @return
     */
    int getMaxPower();

    /**
     * Interval for which average power will be calculated. 
     * @return
     */
    Integer getMeasurementInterval();

    /**
     * Returns the energy metered since energy counter was started.
     * @return energy in kWh
     */
    float getEnergy();

    /**
     * Start counting energy.
     */
    void startEnergyMeter();

    /**
     * Stop counting energy. The energy counter is not being reset so that count may continue
     * if {@link #startEnergyMeter()} is called.
     */
    void stopEnergyMeter();

    /**
     * Reset energy counter to 0.
     */
    void resetEnergyMeter();
}
