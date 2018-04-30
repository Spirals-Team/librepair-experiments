/*
 * Copyright 2016 Daniel Nilsson
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this
 * file except in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under
 * the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package com.github.dannil.scbjavaclient.constants;

import java.util.Locale;

/**
 * <p>Constants which hold values for using the API.</p>
 *
 * @since 0.0.4
 */
public final class APIConstants {

    /**
     * <p>The default locale of the API (sv, SE).</p>
     **/
    public static final Locale FALLBACK_LOCALE = new Locale("sv", "SE");

    /**
     * <p>The default root URL of the API. Is generated by taking the URL to the API and
     * replacing the language segment with {@link #FALLBACK_LOCALE} language</p>
     */
    public static final String ROOT_URL = "https://api.scb.se/OV0104/v1/doris/" + FALLBACK_LOCALE.getLanguage()
            + "/ssd/";

    /**
     * <p>Constant for age code.</p>
     */
    public static final String AGE_CODE = "Alder";

    /**
     * <p>Constant for air pollutant code.</p>
     */
    public static final String AIRPOLLUTANT_CODE = "Luftfororening";

    /**
     * <p>Constant for contents code.</p>
     */
    public static final String CONTENTSCODE_CODE = "ContentsCode";

    /**
     * <p>Constant for economic indicator code.</p>
     */
    public static final String ECONOMICINDICATOR_CODE = "EkoIndikator";

    /**
     * <p>Constant for greenhouse gas code.</p>
     */
    public static final String GREENHOUSEGAS_CODE = "Vaxthusgaser";

    /**
     * <p>Constant for item code.</p>
     */
    public static final String ITEM_CODE = "Kontopost";

    /**
     * <p>Constant for market code.</p>
     */
    public static final String MARKET_CODE = "Marknad";

    /**
     * <p>Constant for region code.</p>
     */
    public static final String REGION_CODE = "Region";

    /**
     * <p>Constant for sector code.</p>
     */
    public static final String SECTOR_CODE = "Sektor";

    /**
     * <p>Constant for SNI 2002 code.</p>
     */
    public static final String SNI2002_CODE = "SNI2002";

    /**
     * <p>Constant for SNI 2007 code.</p>
     */
    public static final String SNI2007_CODE = "SNI2007";

    /**
     * <p>Constant for SNI 92 code.</p>
     */
    public static final String SNI92_CODE = "SNI92";

    /**
     * <p>Constant for SPIN 2002 code.</p>
     */
    public static final String SPIN_2002 = "SPIN2002";

    /**
     * <p>Constant for SPIN 2007 code.</p>
     */
    public static final String SPIN_2007 = "SPIN2007";

    /**
     * <p>Constant for SPIN 2015 code.</p>
     */
    public static final String SPIN_2015 = "SPIN2015";

    /**
     * <p>Constant for sub-sector code.</p>
     */
    public static final String SUBSECTOR_CODE = "Delsektor";

    /**
     * <p>Constant for sex code.</p>
     */
    public static final String SEX_CODE = "Kon";

    /**
     * <p>Constant for time code.</p>
     */
    public static final String TIME_CODE = "Tid";

    /**
     * <p>Constant for type of building code.</p>
     */
    public static final String TYPEOFBUILDING_CODE = "Hustyp";

    /**
     * <p>Constant for type of expenditure code.</p>
     */
    public static final String TYPEOFEXPENDITURE_CODE = "Kostnadsslag";

    /**
     * <p>Private constructor to prevent instantiation.</p>
     */
    private APIConstants() {

    }

}
