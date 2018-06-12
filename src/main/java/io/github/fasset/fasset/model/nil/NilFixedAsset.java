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
package io.github.fasset.fasset.model.nil;

import io.github.fasset.fasset.model.FixedAsset;
import org.javamoney.moneta.Money;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * This object contains fields for an improperly setup fixedAsset items with their default
 * values, and is used whenever a NullPointerSituation is likely to occur
 */
public class NilFixedAsset extends FixedAsset {

    public NilFixedAsset() {
        super();
    }

    @Override
    public String getSolId() {
        return "998";
    }

    @Override
    public String getBarcode() {
        return "ABC000####";
    }

    @Override
    public String getAssetDescription() {
        return "Nil Asset Item. Please add details";
    }

    @Override
    public LocalDate getPurchaseDate() {
        return LocalDate.now();
    }

    @Override
    public String getCategory() {
        return "";
    }

    @Override
    public Money getPurchaseCost() {
        return Money.of(0.00, "KES");
    }

    @Override
    public Money getNetBookValue() {
        return Money.of(0.00, "KES");
    }

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public int getVersion() {
        return 0;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return LocalDateTime.now();
    }

    @Override
    public LocalDateTime getModifiedAt() {
        return LocalDateTime.now();
    }

    @Override
    public String getCreatedBy() {
        return "developer";
    }

    @Override
    public String getLastModifiedBy() {
        return "developer";
    }
}
