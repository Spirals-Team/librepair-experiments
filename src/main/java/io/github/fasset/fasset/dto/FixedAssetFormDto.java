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
package io.github.fasset.fasset.dto;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import io.github.fasset.fasset.kernel.util.convert.DoubleToMoneyConverter;
import io.github.fasset.fasset.model.FixedAsset;

import java.time.LocalDate;

/**
 * This is a DTO used by the Additions workflow to transfer user input from the front end
 */
public class FixedAssetFormDto {

    private DoubleToMoneyConverter doubleToMoneyConverter;

    private String solId;

    private String barcode;

    private String assetDescription;

    private LocalDate purchaseDate;

    private String category;

    private double purchaseCost;

    private double netBookValue;

    public FixedAssetFormDto(DoubleToMoneyConverter doubleToMoneyConverter) {
        this.doubleToMoneyConverter = doubleToMoneyConverter;
    }

    public FixedAsset getFixedAsset() {

        return new FixedAsset().setSolId(solId).setBarcode(barcode).setAssetDescription(assetDescription).setPurchaseDate(purchaseDate).setCategory(category)
            .setPurchaseCost(doubleToMoneyConverter.convert(purchaseCost)).setNetBookValue(doubleToMoneyConverter.convert(netBookValue));

    }

    public String getSolId() {
        return solId;
    }

    public FixedAssetFormDto setSolId(String solId) {
        this.solId = solId;
        return this;
    }

    public String getBarcode() {
        return barcode;
    }

    public FixedAssetFormDto setBarcode(String barcode) {
        this.barcode = barcode;
        return this;
    }

    public String getAssetDescription() {
        return assetDescription;
    }

    public FixedAssetFormDto setAssetDescription(String assetDescription) {
        this.assetDescription = assetDescription;
        return this;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public FixedAssetFormDto setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
        return this;
    }

    public String getCategory() {
        return category;
    }

    public FixedAssetFormDto setCategory(String category) {
        this.category = category;
        return this;
    }

    public double getPurchaseCost() {
        return purchaseCost;
    }

    public FixedAssetFormDto setPurchaseCost(double purchaseCost) {
        this.purchaseCost = purchaseCost;
        return this;
    }

    public double getNetBookValue() {
        return netBookValue;
    }

    public FixedAssetFormDto setNetBookValue(double netBookValue) {
        this.netBookValue = netBookValue;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FixedAssetFormDto that = (FixedAssetFormDto) o;
        return Double.compare(that.purchaseCost, purchaseCost) == 0 && Double.compare(that.netBookValue, netBookValue) == 0 && Objects.equal(doubleToMoneyConverter, that.doubleToMoneyConverter) &&
            Objects.equal(solId, that.solId) && Objects.equal(barcode, that.barcode) && Objects.equal(assetDescription, that.assetDescription) && Objects.equal(purchaseDate, that.purchaseDate) &&
            Objects.equal(category, that.category);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(doubleToMoneyConverter, solId, barcode, assetDescription, purchaseDate, category, purchaseCost, netBookValue);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("solId", solId).add("barcode", barcode).add("assetDescription", assetDescription).add("purchaseDate", purchaseDate).add("category", category)
            .add("purchaseCost", purchaseCost).add("netBookValue", netBookValue).toString();
    }
}
