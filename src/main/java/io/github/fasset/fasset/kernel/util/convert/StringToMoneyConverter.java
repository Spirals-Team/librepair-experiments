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
import io.github.fasset.fasset.kernel.util.ConverterException;
import org.javamoney.moneta.Money;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Converts string argument into money
 */
@Component("stringToMoneyConverter")
public class StringToMoneyConverter implements Converter<String, Money> {

    private static final Logger log = LoggerFactory.getLogger(StringToMoneyConverter.class);

    private final MoneyProperties monetaryProperties;

    private final StringToDoubleConverter stringToDoubleConverter;


    @Autowired
    public StringToMoneyConverter(MoneyProperties monetaryProperties, @Qualifier("stringToDoubleConverter") StringToDoubleConverter stringToDoubleConverter) {
        this.monetaryProperties = monetaryProperties;
        this.stringToDoubleConverter = stringToDoubleConverter;
    }

    @Nullable
    @Override
    public Money convert(@Nullable final String stringMoney) {

        log.debug("Converting the amount : {} from string to {} money amount", stringMoney, monetaryProperties.getDefaultCurrency());

        Money converted;

        try {
            double moneyAmount = 0;
            if (stringMoney != null) {
                moneyAmount = Objects.requireNonNull(stringToDoubleConverter.convert(stringMoney));
            } else {
                log.error("The string money amount passed is null");
            }
            converted = Money.of(moneyAmount, monetaryProperties.getDefaultCurrency());
        } catch (Throwable e) {
            String message = String.format("Exception encountered while converting amount %stringMoney to Money amount", stringMoney);
            throw new ConverterException(message, e);
        }

        return converted;

    }
}
