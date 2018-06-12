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
package io.github.fasset.fasset.model;

import io.github.fasset.fasset.DomainModel;
import org.hibernate.annotations.Type;
import org.hibernate.envers.Audited;
import org.javamoney.moneta.Money;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Objects;

/**
 * Main representation of a fixed asset in the server
 *
 * @author edwin.njeru
 */
@Entity(name = "FixedAsset")
@Audited
public class FixedAsset extends DomainModel<String> implements Serializable, Comparable<FixedAsset> {

    private static final Logger log = LoggerFactory.getLogger(FixedAsset.class);

    @Column(name = "sol_id")
    private String solId;

    @Column(name = "barcode")
    private String barcode;

    @Column(name = "asset_description")
    private String assetDescription;

    @Column(name = "purchase_date")
    private LocalDate purchaseDate;

    @Column(name = "category")
    private String category;

    @Column
    @Type(type = "org.jadira.usertype.moneyandcurrency.moneta.PersistentMoneyAmount", parameters = {@org.hibernate.annotations.Parameter(name = "currencyCode", value = "KES")})
    private Money purchaseCost;

    @Column
    @Type(type = "org.jadira.usertype.moneyandcurrency.moneta.PersistentMoneyAmount", parameters = {@org.hibernate.annotations.Parameter(name = "currencyCode", value = "KES")})
    private Money netBookValue;

    public FixedAsset() {
    }

    public FixedAsset(String assetDescription, Money purchaseCost, String category, String solId, LocalDate purchaseDate, String barcode, Money netBookValue) {
        this(assetDescription, purchaseCost, category, solId, purchaseDate, barcode);
        this.netBookValue = netBookValue;
    }

    public FixedAsset(String assetDescription, Money purchaseCost, String category, String solId, LocalDate purchaseDate, String barcode) {
        this(assetDescription, purchaseCost, category, solId, purchaseDate);
        this.barcode = barcode;
    }

    public FixedAsset(String assetDescription, Money purchaseCost, String category, String solId, LocalDate purchaseDate) {
        this(assetDescription, purchaseCost, category, solId);
        this.purchaseDate = purchaseDate;
    }

    public FixedAsset(String assetDescription, Money purchaseCost, String category, String solId) {
        this(assetDescription, purchaseCost, category);
        this.solId = solId;
    }

    public FixedAsset(String assetDescription, Money purchaseCost, String category) {
        this(assetDescription, purchaseCost);
        this.category = category;
    }

    public FixedAsset(String assetDescription, Money purchaseCost) {
        this.assetDescription = assetDescription;
        this.purchaseCost = purchaseCost;
    }

    public String getSolId() {
        return solId;
    }

    public FixedAsset setSolId(String solId) {
        this.solId = solId;
        return this;
    }

    public String getBarcode() {
        return barcode;
    }

    public FixedAsset setBarcode(String barcode) {
        this.barcode = barcode;
        return this;
    }

    public String getAssetDescription() {
        return assetDescription;
    }

    public FixedAsset setAssetDescription(String assetDescription) {
        this.assetDescription = assetDescription;
        return this;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public FixedAsset setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
        return this;
    }

    public String getCategory() {
        return category;
    }

    public FixedAsset setCategory(String category) {
        this.category = category;
        return this;
    }

    public Money getPurchaseCost() {
        return purchaseCost;
    }

    public FixedAsset setPurchaseCost(Money purchaseCost) {

        log.debug("Setting the purchaseCost for fixedAssetId : {} ,as = {}", getId(), purchaseCost);
        this.purchaseCost = purchaseCost;
        return this;
    }

    public Money getNetBookValue() {
        return netBookValue;
    }

    public FixedAsset setNetBookValue(Money netBookValue) {

        log.debug("Setting NBV for assetId : {}, as = {}", getId(), netBookValue);
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
        if (!super.equals(o)) {
            return false;
        }
        FixedAsset that = (FixedAsset) o;
        return Objects.equals(solId, that.solId) && Objects.equals(barcode, that.barcode) && Objects.equals(assetDescription, that.assetDescription) &&
            Objects.equals(purchaseDate, that.purchaseDate) && Objects.equals(category, that.category) && Objects.equals(purchaseCost, that.purchaseCost) &&
            Objects.equals(netBookValue, that.netBookValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), solId, barcode, assetDescription, purchaseDate, category, purchaseCost, netBookValue);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FixedAsset{");
        sb.append("solId='").append(solId).append('\'');
        sb.append(", barcode='").append(barcode).append('\'');
        sb.append(", assetDescription='").append(assetDescription).append('\'');
        sb.append(", purchaseDate=").append(purchaseDate);
        sb.append(", category='").append(category).append('\'');
        sb.append(", purchaseCost=").append(purchaseCost);
        sb.append(", netBookValue=").append(netBookValue);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public int compareTo(FixedAsset o) {

        return Comparator.comparing(FixedAsset::getSolId).thenComparing(FixedAsset::getCategory).thenComparing(FixedAsset::getPurchaseDate).thenComparing(FixedAsset::getPurchaseCost).compare(this, o);
    }

}
