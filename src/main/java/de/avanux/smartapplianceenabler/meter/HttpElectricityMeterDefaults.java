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

public class HttpElectricityMeterDefaults {
    // static members won't be serialized but we need those valus on the client
    private Float factorToWatt = 1.0f;
    private Integer measurementInterval = 60; // seconds
    private Integer pollInterval = 10; // seconds

    private static HttpElectricityMeterDefaults instance = new HttpElectricityMeterDefaults();

    public static Float getFactorToWatt() {
        return instance.factorToWatt;
    }

    public static Integer getMeasurementInterval() {
        return instance.measurementInterval;
    }

    public static Integer getPollInterval() {
        return instance.pollInterval;
    }

}
