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
package io.github.fasset.fasset.accounts.nomenclature.properties.policy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.github.fasset.fasset.accounts.definition.TransactionType.ACQUISITION;
import static io.github.fasset.fasset.book.keeper.balance.AccountSide.CREDIT;
import static io.github.fasset.fasset.book.keeper.balance.AccountSide.DEBIT;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AccountIdPolicyVersion1Test {

    private AccountIdPolicy accountIdPolicy;

    @BeforeEach
    void setUp() {
        accountIdPolicy = new AccountIdPolicyVersion1("account-id");
    }

    @Test
    void testCurrencyCode() {

        assertEquals("00", accountIdPolicy.currencyCode("KES"));
        assertEquals("01", accountIdPolicy.currencyCode("USD"));
        assertEquals("02", accountIdPolicy.currencyCode("GBP"));
        assertEquals("03", accountIdPolicy.currencyCode("EUR"));
        assertEquals("04", accountIdPolicy.currencyCode("INR"));
        assertEquals("05", accountIdPolicy.currencyCode("CAD"));
        assertEquals("06", accountIdPolicy.currencyCode("CHF"));
        assertEquals("07", accountIdPolicy.currencyCode("ZAR"));

    }

    @Test
    void generalLedgerCode() {

        assertEquals("10051", accountIdPolicy.generalLedgerCode(ACQUISITION, CREDIT, "FURNITURE & FITTINGS"));
        assertEquals("00153", accountIdPolicy.generalLedgerCode(ACQUISITION, DEBIT, "FURNITURE & FITTINGS"));
        assertEquals("10051", accountIdPolicy.generalLedgerCode(ACQUISITION, CREDIT, "COMPUTERS"));
        assertEquals("00152", accountIdPolicy.generalLedgerCode(ACQUISITION, DEBIT, "COMPUTERS"));
        assertEquals("10051", accountIdPolicy.generalLedgerCode(ACQUISITION, CREDIT, "ELECTRONIC EQUIPMENT"));
        assertEquals("00156", accountIdPolicy.generalLedgerCode(ACQUISITION, DEBIT, "ELECTRONIC EQUIPMENT"));
        assertEquals("10051", accountIdPolicy.generalLedgerCode(ACQUISITION, CREDIT, "COMPUTER SOFTWARE"));
        assertEquals("00155", accountIdPolicy.generalLedgerCode(ACQUISITION, DEBIT, "COMPUTER SOFTWARE"));
        assertEquals("10051", accountIdPolicy.generalLedgerCode(ACQUISITION, CREDIT, "MOTOR VEHICLES"));
        assertEquals("00151", accountIdPolicy.generalLedgerCode(ACQUISITION, DEBIT, "MOTOR VEHICLES"));
    }

    @Test
    void accountPlaceHolder() {

        assertEquals("001", accountIdPolicy.accountPlaceHolder(ACQUISITION, CREDIT, "FURNITURE & FITTINGS"));
        assertEquals("001", accountIdPolicy.accountPlaceHolder(ACQUISITION, DEBIT, "FURNITURE & FITTINGS"));
        assertEquals("001", accountIdPolicy.accountPlaceHolder(ACQUISITION, CREDIT, "COMPUTERS"));
        assertEquals("001", accountIdPolicy.accountPlaceHolder(ACQUISITION, DEBIT, "COMPUTERS"));
        assertEquals("001", accountIdPolicy.accountPlaceHolder(ACQUISITION, CREDIT, "ELECTRONIC EQUIPMENT"));
        assertEquals("001", accountIdPolicy.accountPlaceHolder(ACQUISITION, DEBIT, "ELECTRONIC EQUIPMENT"));
        assertEquals("001", accountIdPolicy.accountPlaceHolder(ACQUISITION, CREDIT, "COMPUTER SOFTWARE"));
        assertEquals("001", accountIdPolicy.accountPlaceHolder(ACQUISITION, DEBIT, "COMPUTER SOFTWARE"));
        assertEquals("001", accountIdPolicy.accountPlaceHolder(ACQUISITION, CREDIT, "MOTOR VEHICLES"));
        assertEquals("001", accountIdPolicy.accountPlaceHolder(ACQUISITION, DEBIT, "MOTOR VEHICLES"));
    }

    @Test
    void accountName() {

        assertEquals("SUNDRY CREDITORS", accountIdPolicy.accountName(ACQUISITION, CREDIT, "FURNITURE & FITTINGS"));
        assertEquals("FURNITURE AND FITTINGS", accountIdPolicy.accountName(ACQUISITION, DEBIT, "FURNITURE & FITTINGS"));
        assertEquals("SUNDRY CREDITORS", accountIdPolicy.accountName(ACQUISITION, CREDIT, "COMPUTERS"));
        assertEquals("COMPUTERS", accountIdPolicy.accountName(ACQUISITION, DEBIT, "COMPUTERS"));
        assertEquals("SUNDRY CREDITORS", accountIdPolicy.accountName(ACQUISITION, CREDIT, "ELECTRONIC EQUIPMENT"));
        assertEquals("ELECTRONIC EQUIPMENT", accountIdPolicy.accountName(ACQUISITION, DEBIT, "ELECTRONIC EQUIPMENT"));
        assertEquals("SUNDRY CREDITORS", accountIdPolicy.accountName(ACQUISITION, CREDIT, "COMPUTER SOFTWARE"));
        assertEquals("COMPUTER SOFTWARE", accountIdPolicy.accountName(ACQUISITION, DEBIT, "COMPUTER SOFTWARE"));
        assertEquals("SUNDRY CREDITORS", accountIdPolicy.accountName(ACQUISITION, CREDIT, "MOTOR VEHICLES"));
        assertEquals("MOTOR VEHICLES", accountIdPolicy.accountName(ACQUISITION, DEBIT, "MOTOR VEHICLES"));
    }
}