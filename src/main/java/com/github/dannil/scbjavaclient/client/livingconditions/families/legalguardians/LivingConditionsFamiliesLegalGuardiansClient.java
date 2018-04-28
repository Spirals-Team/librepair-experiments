/*
 * Copyright 2017 Daniel Nilsson
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

package com.github.dannil.scbjavaclient.client.livingconditions.families.legalguardians;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.github.dannil.scbjavaclient.client.AbstractClient;
import com.github.dannil.scbjavaclient.constants.APIConstants;
import com.github.dannil.scbjavaclient.http.URLEndpoint;
import com.github.dannil.scbjavaclient.model.ResponseModel;

/**
 * <p>Client which handles living conditions families legal guardians data fetching.</p>
 *
 * @since 0.2.0
 */
public class LivingConditionsFamiliesLegalGuardiansClient extends AbstractClient {

    /**
     * <p>Default constructor.</p>
     */
    public LivingConditionsFamiliesLegalGuardiansClient() {
        super();
    }

    /**
     * <p>Overloaded constructor.</p>
     *
     * @param locale
     *            the <code>Locale</code> for this client
     */
    public LivingConditionsFamiliesLegalGuardiansClient(Locale locale) {
        super(locale);
    }

    /**
     * <p>Fetch all legal guardians data.</p>
     *
     * @return the data wrapped in a list of
     *         {@link com.github.dannil.scbjavaclient.model.ResponseModel ResponseModel}
     *         objects
     *
     * @see #getLegalGuardians(Collection, Collection, Collection, Collection)
     */
    public List<ResponseModel> getLegalGuardians() {
        return getLegalGuardians(null, null, null, null);
    }

    /**
     * <p>Fetch all legal guardians data which match the input constraints.</p>
     *
     * @param sexes
     *            the sexes
     * @param caregivers
     *            the caregivers
     * @param familyTypes
     *            the family types
     * @param years
     *            the years
     * @return the data wrapped in a list of
     *         {@link com.github.dannil.scbjavaclient.model.ResponseModel ResponseModel}
     *         objects
     */
    public List<ResponseModel> getLegalGuardians(Collection<String> sexes, Collection<String> caregivers,
            Collection<String> familyTypes, Collection<Integer> years) {
        Map<String, Collection<?>> mappings = new HashMap<>();
        mappings.put(APIConstants.SEX_CODE, sexes);
        mappings.put("Vardnadshavare", caregivers);
        mappings.put("Familjetyp", familyTypes);
        mappings.put(APIConstants.TIME_CODE, years);

        return getResponseModels("LE0102T28", mappings);
    }

    @Override
    public URLEndpoint getUrl() {
        return getRootUrl().append("LE/LE0102/LE0102O/");
    }

}
