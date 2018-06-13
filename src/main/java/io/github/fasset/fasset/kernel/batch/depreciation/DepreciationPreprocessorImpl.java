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
package io.github.fasset.fasset.kernel.batch.depreciation;

import io.github.fasset.fasset.config.MoneyProperties;
import io.github.fasset.fasset.kernel.util.DepreciationExecutionException;
import io.github.fasset.fasset.kernel.util.convert.LocalDateToYearMonthConverter;
import io.github.fasset.fasset.model.FixedAsset;
import org.javamoney.moneta.Money;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.time.YearMonth;
import java.util.Objects;

/**
 * This component acts as middleware between calculated depreciation and actual application of calculated
 * depreciation which is supposed to check certain business rules are maintained, for instance the netBookValue
 * is never to go below zero, and also that no asset is to be depreciated prior to its purchase date. In the
 * later the depreciation is simply set to zero while in the former the depreciation is set to be equivalent
 * to the fixedAsset's netBookValue as at the period of depreciation
 *
 * @author edwin.njeru
 */
@Component("depreciationPreprocessor")
public class DepreciationPreprocessorImpl implements DepreciationPreprocessor {

    private static final Logger log = LoggerFactory.getLogger(DepreciationPreprocessorImpl.class);
    private final LocalDateToYearMonthConverter localDateToYearMonthConverter;
    private final MoneyProperties moneyProperties;
    private YearMonth month;
    private FixedAsset asset;
    private Money depreciationAmount;


    @Autowired
    public DepreciationPreprocessorImpl(@Qualifier("localDateToYearMonthConverter") LocalDateToYearMonthConverter localDateToYearMonthConverter, MoneyProperties moneyProperties) {
        this.localDateToYearMonthConverter = localDateToYearMonthConverter;
        this.moneyProperties = moneyProperties;
    }


    /**
     * @return Depreciation period as month
     */
    @Override
    public YearMonth getMonth() {
        log.debug("Returning month : {}", month);
        return month;
    }

    /**
     * @return FixedAsset item being depreciated
     */
    @Override
    public FixedAsset getAsset() {
        log.debug("Returning fixedAsset : {}", asset);
        return asset;
    }

    /**
     * @return amount of depreciation
     */
    @Override
    public Money getDepreciationAmount() {
        log.debug("Returning depreciation amount : {}", depreciationAmount);
        return depreciationAmount;
    }

    /**
     * Sets the asset to be reviewed for depreciation
     *
     * @param asset which is being depreciated
     */
    @Override
    public DepreciationPreprocessor setAsset(FixedAsset asset) {
        log.debug("Setting asset as : {}", asset);
        this.asset = asset;
        return this;
    }

    /**
     * Sets depreciation period in months
     *
     * @param month in which depreciation occurs
     */
    @Override
    public DepreciationPreprocessor setMonth(YearMonth month) {
        log.debug("Setting month as : {}", month);
        this.month = month;
        return this;
    }

    /**
     * Sets the amount of depreciation for review
     *
     * @param depreciationAmount This is the amount by which we are to depreciate the asset
     */
    @Override
    public DepreciationPreprocessor setDepreciationAmount(Money depreciationAmount) {
        log.debug("Setting depreciation amount as : {}", depreciationAmount);
        this.depreciationAmount = depreciationAmount;
        return this;
    }

    /**
     * This method ensures all properties are set and evaluated
     */
    @Override
    public DepreciationPreprocessor setProperties() {
        log.debug("Setting depreciation preprocessor properties...");

        if (asset == null || month == null) {
            String message = String.format("Exception encountered : Either the FixedAsset " + "instance %s or the month %s instance is null", asset, month);
            throw new DepreciationExecutionException(message, new NullPointerException());
        } else {
            depreciationAmountRealignment(asset, month);
        }

        return this;
    }

    private void depreciationAmountRealignment(FixedAsset asset, YearMonth month) {

        log.debug("Calling the depreciation alignment algorithm....");

        depreciationTimingCheck(asset, month);

        depreciationRevaluation(asset);
    }

    private void depreciationRevaluation(FixedAsset asset) {

        log.debug("Performing re-evaluation of the depreciation amount...");

        if (asset.getPurchaseCost().isGreaterThan(Money.of(0.00, moneyProperties.getDefaultCurrency()))) {

            if (asset.getNetBookValue().isLessThan(depreciationAmount)) {

                log.debug("The netBookValue of asset : {} is less that the depreciation amount", asset);

                if (asset.getNetBookValue().isEqualTo(Money.of(0.00, moneyProperties.getDefaultCurrency()))) {

                    log.debug("The NetBookValue is zero, setting depreciation to zero....");
                    // No further processing required
                    this.depreciationAmount = Money.of(0.00, moneyProperties.getDefaultCurrency());

                    log.debug("The depreciation amount is today zero : {}", depreciationAmount);

                } else {

                    log.debug("Resetting depreciation amount to the remaining value of the netBookValue");
                    this.depreciationAmount = asset.getNetBookValue();

                    log.debug("Depreciation has been set to : {}", this.depreciationAmount);
                }
            }

        } else {

            log.warn(
                "The asset has a negative purchase cost, meaning the " + " asset is actually an adjustment, ideally we are to leave it alone...but a " + "quick review of your book wouldn't hurt...");
            //If the purchase cost is less than zero we do nothing
        }
    }

    @SuppressWarnings("all")
    private void depreciationTimingCheck(FixedAsset asset, YearMonth month) {
        log.debug("Reviewing the depreciation timing for asset : {}, relative to the " + "month: {}", asset, month);
        if (localDateToYearMonthConverter.convert(Objects.requireNonNull(asset.getPurchaseDate())).isAfter(month)) {
            log.debug(
                "The month of purchase of asset: {} comes later than the depreciation period : {}" + "therefore we are resetting the depreciation formally calculated as : {} " + "amount to zero",
                asset, month, depreciationAmount);
            this.depreciationAmount = Money.of(0.00, moneyProperties.getDefaultCurrency());
            log.debug("Depreciation amount has been reset to zero : {}", depreciationAmount);
        }
        log.debug("The depreciation has passed the timing test and will be retained at : {}", depreciationAmount);
    }
}
