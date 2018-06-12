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
package io.github.fasset.fasset.kernel.util.convert;

import io.github.fasset.fasset.config.MoneyProperties;
import org.javamoney.moneta.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * Converts Double object to Money object
 */
@Component("doubleToMoneyConverter")
public class DoubleToMoneyConverter implements Converter<Double, Money> {


    private final MoneyProperties moneyProperties;

    @Autowired
    public DoubleToMoneyConverter(MoneyProperties moneyProperties) {
        this.moneyProperties = moneyProperties;
    }

    @Nullable
    @Override
    public Money convert(Double aDouble) {

        return Money.of(aDouble, moneyProperties.getDefaultCurrency());
    }
}
