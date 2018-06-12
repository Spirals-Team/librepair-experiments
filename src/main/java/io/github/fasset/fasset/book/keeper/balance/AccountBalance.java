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
package io.github.fasset.fasset.book.keeper.balance;

import io.github.fasset.fasset.book.keeper.util.MismatchedCurrencyException;
import io.github.ghacupha.cash.Cash;
import io.github.ghacupha.cash.HardCash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Currency;

/**
 * Represents the amount and sign of the amount you could find in an account. Using this
 * you could say an account has 20 dollars on Credit side.
 *
 * @author edwin.njeru
 */
public class AccountBalance {

    private static final Logger log = LoggerFactory.getLogger(AccountBalance.class);

    private final Cash amount;
    private final AccountSide accountSide;

    public AccountBalance(Cash amount, AccountSide accountSide) {
        this.amount = amount;
        this.accountSide = accountSide;
    }

    public static AccountBalance nil(Currency currency, AccountSide accountSide) {

        return newBalance(HardCash.of(0, currency), accountSide);
    }

    public static AccountBalance newBalance(Cash amount, AccountSide accountSide) {

        return new AccountBalance(amount, accountSide);
    }

    public AccountBalance add(Collection<AccountBalance> args) {

        final AccountBalance[] thisBalance = {newBalance(this.amount, this.accountSide)};

        args.forEach(i -> {
            try {
                thisBalance[0] = thisBalance[0].add(i);
            } catch (MismatchedCurrencyException e) {
                e.printStackTrace();
            }
        });

        return thisBalance[0];
    }

    public AccountBalance add(AccountBalance arg) throws MismatchedCurrencyException {

        checkCurrency(arg);

        log.debug("Adding {} to {}", arg, this);

        synchronized (this) {

            if (arg.getAccountSide() == accountSide) {

                return newBalance(this.amount.plus(arg.getAmount()), this.accountSide);

            } else {

                if (arg.getAmount().isLessThan(this.amount)) {

                    return newBalance(this.amount.minus(arg.getAmount()), this.accountSide);
                }

                if (arg.getAmount().isMoreThan(this.amount)) {

                    return newBalance(arg.getAmount().minus(this.amount), arg.getAccountSide());
                }
            }
        }

        return newBalance(this.amount, this.accountSide);
    }

    private void checkCurrency(AccountBalance arg) throws MismatchedCurrencyException {

        if (!arg.getAmount().getCurrency().equals(this.amount.getCurrency())) {
            String message = String.format("Cannot add balance containing mismatched currency. Expected : %s but found : %s", this.amount.getCurrency().getCurrencyCode(),
                arg.getAmount().getCurrency().getCurrencyCode());

            throw new MismatchedCurrencyException(message);
        }
    }

    public Cash getAmount() {
        return amount;
    }

    public AccountSide getAccountSide() {
        return accountSide;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AccountBalance that = (AccountBalance) o;

        if (!amount.equals(that.amount)) {
            return false;
        }
        return accountSide == that.accountSide;
    }

    @Override
    public int hashCode() {
        int result = amount.hashCode();
        result = 31 * result + accountSide.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return amount.getNumber().doubleValue() + " " + accountSide;
    }
}
