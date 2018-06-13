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
import io.github.fasset.fasset.model.FixedAsset;
import org.javamoney.moneta.Money;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.time.LocalDate;

import static io.github.fasset.fasset.accounts.definition.TransactionType.ACQUISITION;
import static io.github.fasset.fasset.book.keeper.balance.AccountSide.DEBIT;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class AcquisitionDebitAccountIdResolverTest {

    private final static FixedAsset radio = new FixedAsset("Radio", Money.of(200, "KES"), "ELECTRONIC EQUIPMENT", "001", LocalDate.of(2018, 2, 5), "abc01", Money.of(9.5, "KES"));
    private final static FixedAsset lenovo = new FixedAsset("Lenovo IDP110", Money.of(5600, "KES"), "COMPUTERS", "987", LocalDate.of(2018, 2, 13), "abc02", Money.of(13.42, "KES"));
    private final static FixedAsset chair = new FixedAsset("Chair", Money.of(156, "KES"), "FURNITURE & FITTINGS", "010", LocalDate.of(2018, 1, 13), "abc03", Money.of(19.24, "KES"));
    private final static FixedAsset zemana = new FixedAsset("Zemana Antilogger", Money.of(180, "KES"), "COMPUTER SOFTWARE", "986", LocalDate.of(2017, 12, 15), "abc48", Money.of(50.32, "KES"));
    private final static FixedAsset kca = new FixedAsset("KCA 265G", Money.of(950, "KES"), "MOTOR VEHICLES", "996", LocalDate.of(2018, 1, 5), "abc23", Money.of(500.12, "KES"));
    private final static FixedAsset officePartitioning =
        new FixedAsset("Office Partitioning Works", Money.of(1500, "KES"), "OFFICE RENOVATION", "978", LocalDate.of(2018, 1, 12), "abc56", Money.of(50.13, "KES"));
    private AccountIdResolver accountIdResolver;

    @Before
    public void setUp() throws Exception {

        AccountIdService accountIdService = Mockito.mock(AccountIdService.class);

        when(accountIdService.accountName(ACQUISITION, DEBIT, radio)).thenReturn("ELECTRONICS");
        when(accountIdService.accountName(ACQUISITION, DEBIT, lenovo)).thenReturn("COMPUTERS");
        when(accountIdService.accountName(ACQUISITION, DEBIT, chair)).thenReturn("FURNITURE");
        when(accountIdService.accountName(ACQUISITION, DEBIT, zemana)).thenReturn("COMPUTER SOFTWARE");
        when(accountIdService.accountName(ACQUISITION, DEBIT, kca)).thenReturn("MOTOR VEHICLES");
        when(accountIdService.accountName(ACQUISITION, DEBIT, officePartitioning)).thenReturn("OFFICE RENOVATION");

        when(accountIdService.accountPlaceHolder(ACQUISITION, DEBIT, radio)).thenReturn("010");
        when(accountIdService.accountPlaceHolder(ACQUISITION, DEBIT, lenovo)).thenReturn("011");
        when(accountIdService.accountPlaceHolder(ACQUISITION, DEBIT, chair)).thenReturn("012");
        when(accountIdService.accountPlaceHolder(ACQUISITION, DEBIT, zemana)).thenReturn("013");
        when(accountIdService.accountPlaceHolder(ACQUISITION, DEBIT, kca)).thenReturn("014");
        when(accountIdService.accountPlaceHolder(ACQUISITION, DEBIT, officePartitioning)).thenReturn("015");

        when(accountIdService.generalLedgerCode(ACQUISITION, DEBIT, radio)).thenReturn("00156");
        when(accountIdService.generalLedgerCode(ACQUISITION, DEBIT, lenovo)).thenReturn("00152");
        when(accountIdService.generalLedgerCode(ACQUISITION, DEBIT, chair)).thenReturn("00153");
        when(accountIdService.generalLedgerCode(ACQUISITION, DEBIT, zemana)).thenReturn("00155");
        when(accountIdService.generalLedgerCode(ACQUISITION, DEBIT, kca)).thenReturn("00151");
        when(accountIdService.generalLedgerCode(ACQUISITION, DEBIT, officePartitioning)).thenReturn("00154");

        when(accountIdService.contraAccountName(ACQUISITION, DEBIT, radio)).thenReturn("ACCUMULATED DEPRECIATION - ELECTRONIC EQUIPMENT");
        when(accountIdService.contraAccountName(ACQUISITION, DEBIT, lenovo)).thenReturn("ACCUMULATED DEPRECIATION - COMPUTERS");
        when(accountIdService.contraAccountName(ACQUISITION, DEBIT, chair)).thenReturn("ACCUMULATED DEPRECIATION - FURNITURE AND FITTINGS");
        when(accountIdService.contraAccountName(ACQUISITION, DEBIT, zemana)).thenReturn("ACCUMULATED DEPRECIATION - COMPUTER SOFTWARE");
        when(accountIdService.contraAccountName(ACQUISITION, DEBIT, kca)).thenReturn("ACCUMULATED DEPRECIATION - MOTOR VEHICLES");
        when(accountIdService.contraAccountName(ACQUISITION, DEBIT, officePartitioning)).thenReturn("ACCUMULATED DEPRECIATION - OFFICE RENOVATION");

        when(accountIdService.accountNumber("001","00","00156","010")).thenReturn("0010000156010");
        when(accountIdService.accountNumber("987","00","00152","011")).thenReturn("9870000152011");
        when(accountIdService.accountNumber("010","00","00153","012")).thenReturn("0100000153012");
        when(accountIdService.accountNumber("986","00","00155","013")).thenReturn("9860000155013");
        when(accountIdService.accountNumber("996","00","00151","014")).thenReturn("9960000151014");
        when(accountIdService.accountNumber("978","00","00154","015")).thenReturn("9780000154015");

        when(accountIdService.currencyCode("KES")).thenReturn("00");

        accountIdResolver = new AcquisitionDebitAccountIdResolver(accountIdService);
    }

    @Test
    public void resolveName() {

        assertEquals("ELECTRONICS", accountIdResolver.accountName(radio));
        assertEquals("COMPUTERS", accountIdResolver.accountName(lenovo));
        assertEquals("FURNITURE", accountIdResolver.accountName(chair));
        assertEquals("COMPUTER SOFTWARE", accountIdResolver.accountName(zemana));
        assertEquals("MOTOR VEHICLES", accountIdResolver.accountName(kca));
        assertEquals("OFFICE RENOVATION", accountIdResolver.accountName(officePartitioning));

    }

    @Test
    public void resolveContraAccountId() {

        assertEquals("ACCUMULATED DEPRECIATION - ELECTRONIC EQUIPMENT", accountIdResolver.resolveContraAccountId(radio));
        assertEquals("ACCUMULATED DEPRECIATION - COMPUTERS", accountIdResolver.resolveContraAccountId(lenovo));
        assertEquals("ACCUMULATED DEPRECIATION - FURNITURE AND FITTINGS", accountIdResolver.resolveContraAccountId(chair));
        assertEquals("ACCUMULATED DEPRECIATION - COMPUTER SOFTWARE", accountIdResolver.resolveContraAccountId(zemana));
        assertEquals("ACCUMULATED DEPRECIATION - MOTOR VEHICLES", accountIdResolver.resolveContraAccountId(kca));
        assertEquals("ACCUMULATED DEPRECIATION - OFFICE RENOVATION", accountIdResolver.resolveContraAccountId(officePartitioning));

    }

    @Test
    public void resolveCategoryId() {
        assertEquals("ELECTRONICS", accountIdResolver.resolveCategoryId(radio));
        assertEquals("COMPUTERS", accountIdResolver.resolveCategoryId(lenovo));
        assertEquals("FURNITURE", accountIdResolver.resolveCategoryId(chair));
        assertEquals("COMPUTER SOFTWARE", accountIdResolver.resolveCategoryId(zemana));
        assertEquals("MOTOR VEHICLES", accountIdResolver.resolveCategoryId(kca));
        assertEquals("OFFICE RENOVATION", accountIdResolver.resolveCategoryId(officePartitioning));
    }

    @Test
    public void resolveNumber() {

        assertEquals("0010000156010", accountIdResolver.accountNumber(radio));
        assertEquals("9870000152011", accountIdResolver.accountNumber(lenovo));
        assertEquals("0100000153012", accountIdResolver.accountNumber(chair));
        assertEquals("9860000155013", accountIdResolver.accountNumber(zemana));
        assertEquals("9960000151014", accountIdResolver.accountNumber(kca));
        assertEquals("9780000154015", accountIdResolver.accountNumber(officePartitioning));
    }

    @Test
    public void resolveGeneralLedgerName() {

        assertEquals("ELECTRONICS", accountIdResolver.generalLedgerName(radio));
        assertEquals("COMPUTERS", accountIdResolver.generalLedgerName(lenovo));
        assertEquals("FURNITURE", accountIdResolver.generalLedgerName(chair));
        assertEquals("COMPUTER SOFTWARE", accountIdResolver.generalLedgerName(zemana));
        assertEquals("MOTOR VEHICLES", accountIdResolver.generalLedgerName(kca));
        assertEquals("OFFICE RENOVATION", accountIdResolver.generalLedgerName(officePartitioning));

    }
}