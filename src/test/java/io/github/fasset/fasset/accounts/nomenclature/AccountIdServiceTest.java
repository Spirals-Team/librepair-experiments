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
package io.github.fasset.fasset.accounts.nomenclature;

import io.github.fasset.fasset.accounts.nomenclature.properties.AccountIdService;
import io.github.fasset.fasset.accounts.nomenclature.properties.FileAccountIdService;
import io.github.fasset.fasset.accounts.nomenclature.properties.policy.AccountIdPolicy;
import io.github.fasset.fasset.model.FixedAsset;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;

import static io.github.fasset.fasset.accounts.definition.TransactionType.ACQUISITION;
import static io.github.fasset.fasset.book.keeper.balance.AccountSide.CREDIT;
import static io.github.fasset.fasset.book.keeper.balance.AccountSide.DEBIT;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class AccountIdServiceTest {

    private final static FixedAsset radio = new FixedAsset("Radio", Money.of(200, "KES"), "ELECTRONIC EQUIPMENT", "001", LocalDate.of(2018, 2, 5), "abc01", Money.of(9.5, "KES"));
    private final static FixedAsset lenovo = new FixedAsset("Lenovo IDP110", Money.of(5600, "KES"), "COMPUTERS", "987", LocalDate.of(2018, 2, 13), "abc02", Money.of(13.42, "KES"));
    private final static FixedAsset chair = new FixedAsset("Chair", Money.of(156, "KES"), "FURNITURE & FITTINGS", "010", LocalDate.of(2018, 1, 13), "abc03", Money.of(19.24, "KES"));
    private final static FixedAsset zemana = new FixedAsset("Zemana Antilogger", Money.of(180, "KES"), "COMPUTER SOFTWARE", "986", LocalDate.of(2017, 12, 15), "abc48", Money.of(50.32, "KES"));
    private final static FixedAsset kca = new FixedAsset("KCA 265G", Money.of(950, "KES"), "MOTOR VEHICLES", "996", LocalDate.of(2018, 1, 5), "abc23", Money.of(500.12, "KES"));
    private final static FixedAsset officePartitioning =
        new FixedAsset("Office Partitioning Works", Money.of(1500, "KES"), "OFFICE RENOVATION", "978", LocalDate.of(2018, 1, 12), "abc56", Money.of(50.13, "KES"));

    private AccountIdService accountIdService;

    @BeforeEach
    void setUp() {

        AccountIdPolicy accountIdPolicy = Mockito.mock(AccountIdPolicy.class);

        // Account name
        when(accountIdPolicy.accountName(ACQUISITION, DEBIT, "ELECTRONIC EQUIPMENT")).thenReturn("ELECTRONIC EQUIPMENT");
        when(accountIdPolicy.accountName(ACQUISITION, CREDIT, "ELECTRONIC EQUIPMENT")).thenReturn("SUNDRY CREDITORS");
        when(accountIdPolicy.accountName(ACQUISITION, CREDIT, "COMPUTERS")).thenReturn("SUNDRY CREDITORS");
        when(accountIdPolicy.accountName(ACQUISITION, DEBIT, "COMPUTERS")).thenReturn("COMPUTERS");
        when(accountIdPolicy.accountName(ACQUISITION, DEBIT, "FURNITURE & FITTINGS")).thenReturn("FURNITURE AND FITTINGS");
        when(accountIdPolicy.accountName(ACQUISITION, CREDIT, "FURNITURE & FITTINGS")).thenReturn("SUNDRY CREDITORS");
        when(accountIdPolicy.accountName(ACQUISITION, CREDIT, "COMPUTER SOFTWARE")).thenReturn("SUNDRY CREDITORS");
        when(accountIdPolicy.accountName(ACQUISITION, DEBIT, "COMPUTER SOFTWARE")).thenReturn("COMPUTER SOFTWARE");
        when(accountIdPolicy.accountName(ACQUISITION, DEBIT, "MOTOR VEHICLES")).thenReturn("MOTOR VEHICLES");
        when(accountIdPolicy.accountName(ACQUISITION, CREDIT, "MOTOR VEHICLES")).thenReturn("SUNDRY CREDITORS");
        when(accountIdPolicy.accountName(ACQUISITION, DEBIT, "OFFICE RENOVATION")).thenReturn("OFFICE RENOVATION");
        when(accountIdPolicy.accountName(ACQUISITION, CREDIT, "OFFICE RENOVATION")).thenReturn("SUNDRY CREDITORS");
        
        // General ledger code
        when(accountIdPolicy.generalLedgerCode(ACQUISITION, DEBIT, "ELECTRONIC EQUIPMENT")).thenReturn("00156");
        when(accountIdPolicy.generalLedgerCode(ACQUISITION, DEBIT, "COMPUTERS")).thenReturn("00152");
        when(accountIdPolicy.generalLedgerCode(ACQUISITION, DEBIT, "FURNITURE & FITTINGS")).thenReturn("00153");
        when(accountIdPolicy.generalLedgerCode(ACQUISITION, DEBIT, "COMPUTER SOFTWARE")).thenReturn("00155");
        when(accountIdPolicy.generalLedgerCode(ACQUISITION, DEBIT, "MOTOR VEHICLES")).thenReturn("00151");
        when(accountIdPolicy.generalLedgerCode(ACQUISITION, DEBIT, "OFFICE RENOVATION")).thenReturn("00154");
        when(accountIdPolicy.generalLedgerCode(ACQUISITION, CREDIT, "ELECTRONIC EQUIPMENT")).thenReturn("10051");
        when(accountIdPolicy.generalLedgerCode(ACQUISITION, CREDIT, "COMPUTERS")).thenReturn("10051");
        when(accountIdPolicy.generalLedgerCode(ACQUISITION, CREDIT, "FURNITURE & FITTINGS")).thenReturn("10051");
        when(accountIdPolicy.generalLedgerCode(ACQUISITION, CREDIT, "COMPUTER SOFTWARE")).thenReturn("10051");
        when(accountIdPolicy.generalLedgerCode(ACQUISITION, CREDIT, "MOTOR VEHICLES")).thenReturn("10051");
        when(accountIdPolicy.generalLedgerCode(ACQUISITION, CREDIT, "OFFICE RENOVATION")).thenReturn("10051");

        // placeHolders
        when(accountIdPolicy.accountPlaceHolder(ACQUISITION, DEBIT, "ELECTRONIC EQUIPMENT")).thenReturn("001");
        when(accountIdPolicy.accountPlaceHolder(ACQUISITION, DEBIT, "COMPUTERS")).thenReturn("001");
        when(accountIdPolicy.accountPlaceHolder(ACQUISITION, DEBIT, "FURNITURE & FITTINGS")).thenReturn("001");
        when(accountIdPolicy.accountPlaceHolder(ACQUISITION, DEBIT, "COMPUTER SOFTWARE")).thenReturn("001");
        when(accountIdPolicy.accountPlaceHolder(ACQUISITION, DEBIT, "MOTOR VEHICLES")).thenReturn("001");
        when(accountIdPolicy.accountPlaceHolder(ACQUISITION, DEBIT, "OFFICE RENOVATION")).thenReturn("001");
        when(accountIdPolicy.accountPlaceHolder(ACQUISITION, CREDIT, "ELECTRONIC EQUIPMENT")).thenReturn("001");
        when(accountIdPolicy.accountPlaceHolder(ACQUISITION, CREDIT, "COMPUTERS")).thenReturn("001");
        when(accountIdPolicy.accountPlaceHolder(ACQUISITION, CREDIT, "FURNITURE & FITTINGS")).thenReturn("001");
        when(accountIdPolicy.accountPlaceHolder(ACQUISITION, CREDIT, "COMPUTER SOFTWARE")).thenReturn("001");
        when(accountIdPolicy.accountPlaceHolder(ACQUISITION, CREDIT, "MOTOR VEHICLES")).thenReturn("001");
        when(accountIdPolicy.accountPlaceHolder(ACQUISITION, CREDIT, "OFFICE RENOVATION")).thenReturn("001");

        // account number
        when(accountIdPolicy.accountNumberMotif("001","00","10051","001")).thenReturn("0010010051001");
        when(accountIdPolicy.accountNumberMotif("978","00","00153","010")).thenReturn("9780000153010");
        when(accountIdPolicy.accountNumberMotif("978","01","00153","010")).thenReturn("9780100153010");

        when(accountIdPolicy.currencyCode("KES")).thenReturn("00");
        when(accountIdPolicy.currencyCode("USD")).thenReturn("01");
        when(accountIdPolicy.currencyCode("GBP")).thenReturn("02");
        when(accountIdPolicy.currencyCode("EUR")).thenReturn("03");
        when(accountIdPolicy.currencyCode("INR")).thenReturn("04");
        when(accountIdPolicy.currencyCode("CAD")).thenReturn("05");
        when(accountIdPolicy.currencyCode("CHF")).thenReturn("06");
        when(accountIdPolicy.currencyCode("ZAR")).thenReturn("07");

        accountIdService = new FileAccountIdService(accountIdPolicy);
    }

    @Test
    public void accountName() {

        assertEquals("SUNDRY CREDITORS", accountIdService.accountName(ACQUISITION, CREDIT, radio));
        assertEquals("ELECTRONIC EQUIPMENT", accountIdService.accountName(ACQUISITION, DEBIT, radio));
        assertEquals("SUNDRY CREDITORS", accountIdService.accountName(ACQUISITION, CREDIT, lenovo));
        assertEquals("COMPUTERS", accountIdService.accountName(ACQUISITION, DEBIT, lenovo));
        assertEquals("SUNDRY CREDITORS", accountIdService.accountName(ACQUISITION, CREDIT, chair));
        assertEquals("FURNITURE AND FITTINGS", accountIdService.accountName(ACQUISITION, DEBIT, chair));
        assertEquals("SUNDRY CREDITORS", accountIdService.accountName(ACQUISITION, CREDIT, zemana));
        assertEquals("COMPUTER SOFTWARE", accountIdService.accountName(ACQUISITION, DEBIT, zemana));
        assertEquals("SUNDRY CREDITORS", accountIdService.accountName(ACQUISITION, CREDIT, kca));
        assertEquals("MOTOR VEHICLES", accountIdService.accountName(ACQUISITION, DEBIT, kca));
        assertEquals("OFFICE RENOVATION", accountIdService.accountName(ACQUISITION, DEBIT, officePartitioning));
        assertEquals("SUNDRY CREDITORS", accountIdService.accountName(ACQUISITION, CREDIT, officePartitioning));
    }

    @Test
    public void generalLegerCode() {

        assertEquals("00153", accountIdService.generalLedgerCode(ACQUISITION, DEBIT, chair));
        assertEquals("00152", accountIdService.generalLedgerCode(ACQUISITION, DEBIT, lenovo));
        assertEquals("00156", accountIdService.generalLedgerCode(ACQUISITION, DEBIT, radio));
        assertEquals("00155", accountIdService.generalLedgerCode(ACQUISITION, DEBIT, zemana));
        assertEquals("00151", accountIdService.generalLedgerCode(ACQUISITION, DEBIT, kca));
        assertEquals("00154", accountIdService.generalLedgerCode(ACQUISITION, DEBIT, officePartitioning));
        assertEquals("10051", accountIdService.generalLedgerCode(ACQUISITION, CREDIT, chair));
        assertEquals("10051", accountIdService.generalLedgerCode(ACQUISITION, CREDIT, lenovo));
        assertEquals("10051", accountIdService.generalLedgerCode(ACQUISITION, CREDIT, radio));
        assertEquals("10051", accountIdService.generalLedgerCode(ACQUISITION, CREDIT, zemana));
        assertEquals("10051", accountIdService.generalLedgerCode(ACQUISITION, CREDIT, kca));
        assertEquals("10051", accountIdService.generalLedgerCode(ACQUISITION, CREDIT, officePartitioning));
    }

    @Test
    public void accountPlaceHolder() throws Exception {

        assertEquals("001", accountIdService.accountPlaceHolder(ACQUISITION, CREDIT, chair));
        assertEquals("001", accountIdService.accountPlaceHolder(ACQUISITION, DEBIT, chair));
        assertEquals("001", accountIdService.accountPlaceHolder(ACQUISITION, CREDIT, lenovo));
        assertEquals("001", accountIdService.accountPlaceHolder(ACQUISITION, DEBIT, lenovo));
        assertEquals("001", accountIdService.accountPlaceHolder(ACQUISITION, CREDIT, radio));
        assertEquals("001", accountIdService.accountPlaceHolder(ACQUISITION, DEBIT, radio));
        assertEquals("001", accountIdService.accountPlaceHolder(ACQUISITION, CREDIT, zemana));
        assertEquals("001", accountIdService.accountPlaceHolder(ACQUISITION, DEBIT, zemana));
        assertEquals("001", accountIdService.accountPlaceHolder(ACQUISITION, CREDIT, kca));
        assertEquals("001", accountIdService.accountPlaceHolder(ACQUISITION, DEBIT, kca));
        assertEquals("001", accountIdService.accountPlaceHolder(ACQUISITION, CREDIT, officePartitioning));
        assertEquals("001", accountIdService.accountPlaceHolder(ACQUISITION, DEBIT, officePartitioning));
    }

    @Test
    public void accountNumber() {

        assertEquals("0010010051001", accountIdService.accountNumber("001","00","10051","001"));
        assertEquals("9780000153010", accountIdService.accountNumber("978","00","00153","010"));
        assertEquals("9780100153010", accountIdService.accountNumber("978","01","00153","010"));
    }

    @Test
    public void getCurrencyCode() {

        assertEquals("00", accountIdService.currencyCode("KES"));
        assertEquals("01", accountIdService.currencyCode("USD"));
        assertEquals("02", accountIdService.currencyCode("GBP"));
        assertEquals("03", accountIdService.currencyCode("EUR"));
        assertEquals("04", accountIdService.currencyCode("INR"));
        assertEquals("05", accountIdService.currencyCode("CAD"));
        assertEquals("06", accountIdService.currencyCode("CHF"));
        assertEquals("07", accountIdService.currencyCode("ZAR"));
    }
}
