/*
 * fassets - Project for light-weight tracking of fixed assets
 * Copyright © 2018 Edwin Njeru (mailnjeru@gmail.com)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package io.github.fasset.fasset.kernel.batch.depreciation.agent;

import io.github.fasset.fasset.kernel.batch.depreciation.DepreciationProceeds;
import io.github.fasset.fasset.kernel.util.DepreciationExecutionException;
import io.github.fasset.fasset.model.FixedAsset;
import io.github.fasset.fasset.model.NetBookValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.YearMonth;

/**
 * On invocation calculates the NetBookValue for any given Asset, at a specified Month and records the
 * same into the DepreciationProceeds item provided
 */
@Component("netBookValueAgent")
public class NetBookValueAgentImpl implements NetBookValueAgent {

    private static final Logger log = LoggerFactory.getLogger(NetBookValueAgentImpl.class);

    /**
     * Upon invocation the implementation will return the netBoookValue item for the relevant month
     * in which depreciation has occured
     *
     * @param asset FixedAsset item whose Net Book Value we are tracking
     * @param month YearMonth in which depreciation has occured
     * @return The relevant NetBookValue item
     */
    @Override
    public NetBookValue invoke(FixedAsset asset, YearMonth month, DepreciationProceeds proceeds) {

        log.debug("Processing NetBookValue item for the asset : {} in the period : {}", asset, month);

        NetBookValue netBookValue = createNetBookValue(asset, month);

        log.debug("Sending netBookValueItem created : {}", netBookValue);

        //send(()->netBookValue);

        proceeds.setNetBookValue(netBookValue);

        return netBookValue;
    }

    /**
     * Creates a NetBookValue object using the parameters given
     *
     * @param asset the FixedAsset item whose netBookValue is to be revalued
     * @param month of the netBookValue valuation
     * @return NetBookValue as at the month
     */
    private NetBookValue createNetBookValue(FixedAsset asset, YearMonth month) {
        NetBookValue netBookValue = new NetBookValue();

        log.trace("Creating netBookValue instance relative to the asset : {} for the month : {}", asset, month);

        try {
            netBookValue.setCategory(asset.getCategory()).setFixedAssetId(asset.getId()).setMonth(month).setSolId(asset.getSolId()).setNetBookValue(asset.getNetBookValue());
        } catch (Throwable e) {
            String message = String.format("Exception encountered while creating netBookValue instance relative" + "to the asset : %s for the month : %s", asset, month);
            throw new DepreciationExecutionException(message, e);
        }

        log.trace("NetBookValue item created : {}", netBookValue);

        return netBookValue;
    }

}
