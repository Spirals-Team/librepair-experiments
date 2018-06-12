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
import io.github.fasset.fasset.accounts.nomenclature.properties.policy.AccountIdPolicyVersion1;
import io.github.fasset.fasset.model.FixedAsset;
import org.javamoney.moneta.Money;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.time.LocalDate;

import static io.github.fasset.fasset.accounts.definition.TransactionType.ACQUISITION;
import static io.github.fasset.fasset.book.keeper.balance.AccountSide.CREDIT;
import static io.github.fasset.fasset.book.keeper.balance.AccountSide.DEBIT;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class AcquisitionCreditAccountIDResolverTest {

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
        
        when(accountIdService.accountName(ACQUISITION, CREDIT, radio)).thenReturn("SUNDRY CREDITORS");
        when(accountIdService.accountName(ACQUISITION, CREDIT, lenovo)).thenReturn("SUNDRY CREDITORS");
        when(accountIdService.accountName(ACQUISITION, CREDIT, chair)).thenReturn("SUNDRY CREDITORS");
        when(accountIdService.accountName(ACQUISITION, CREDIT, zemana)).thenReturn("SUNDRY CREDITORS");
        when(accountIdService.accountName(ACQUISITION, CREDIT, kca)).thenReturn("SUNDRY CREDITORS");
        when(accountIdService.accountName(ACQUISITION, CREDIT, officePartitioning)).thenReturn("SUNDRY CREDITORS");

        when(accountIdService.accountPlaceHolder(ACQUISITION, CREDIT, radio)).thenReturn("001");
        when(accountIdService.accountPlaceHolder(ACQUISITION, CREDIT, lenovo)).thenReturn("001");
        when(accountIdService.accountPlaceHolder(ACQUISITION, CREDIT, chair)).thenReturn("001");
        when(accountIdService.accountPlaceHolder(ACQUISITION, CREDIT, zemana)).thenReturn("001");
        when(accountIdService.accountPlaceHolder(ACQUISITION, CREDIT, kca)).thenReturn("001");
        when(accountIdService.accountPlaceHolder(ACQUISITION, CREDIT, officePartitioning)).thenReturn("001");

        when(accountIdService.generalLedgerCode(ACQUISITION, CREDIT, radio)).thenReturn("10051");
        when(accountIdService.generalLedgerCode(ACQUISITION, CREDIT, lenovo)).thenReturn("10051");
        when(accountIdService.generalLedgerCode(ACQUISITION, CREDIT, chair)).thenReturn("10051");
        when(accountIdService.generalLedgerCode(ACQUISITION, CREDIT, zemana)).thenReturn("10051");
        when(accountIdService.generalLedgerCode(ACQUISITION, CREDIT, kca)).thenReturn("10051");
        when(accountIdService.generalLedgerCode(ACQUISITION, CREDIT, officePartitioning)).thenReturn("10051");

        when(accountIdService.currencyCode("KES")).thenReturn("00");

        accountIdResolver = new AcquisitionCreditAccountIdResolver(accountIdService);
    }

    @Test
    public void resolveName() {

        assertEquals("SUNDRY CREDITORS", accountIdResolver.accountName(radio));
        assertEquals("SUNDRY CREDITORS", accountIdResolver.accountName(lenovo));
        assertEquals("SUNDRY CREDITORS", accountIdResolver.accountName(chair));
        assertEquals("SUNDRY CREDITORS", accountIdResolver.accountName(zemana));
        assertEquals("SUNDRY CREDITORS", accountIdResolver.accountName(kca));
        assertEquals("SUNDRY CREDITORS", accountIdResolver.accountName(officePartitioning));
    }

    @Test
    public void resolveNumber() {

        assertEquals("0010010051001", accountIdResolver.accountNumber(radio));
        assertEquals("9870010051001", accountIdResolver.accountNumber(lenovo));
        assertEquals("0100010051001", accountIdResolver.accountNumber(chair));
        assertEquals("9860010051001", accountIdResolver.accountNumber(zemana));
        assertEquals("9960010051001", accountIdResolver.accountNumber(kca));
        assertEquals("9780010051001", accountIdResolver.accountNumber(officePartitioning));
    }

    @Test
    public void resolveContraAccountId() {

        assertEquals("SUNDRY CREDITORS", accountIdResolver.resolveContraAccountId(radio));
        assertEquals("SUNDRY CREDITORS", accountIdResolver.resolveContraAccountId(chair));
        assertEquals("SUNDRY CREDITORS", accountIdResolver.resolveContraAccountId(lenovo));
    }

    @Test
    public void resolveGeneralLedgerName() {

        assertEquals("SUNDRY CREDITORS", accountIdResolver.generalLedgerName(radio));
        assertEquals("SUNDRY CREDITORS", accountIdResolver.generalLedgerName(chair));
        assertEquals("SUNDRY CREDITORS", accountIdResolver.generalLedgerName(lenovo));
    }

    @Test
    public void resolveCategoryId() {

        assertEquals("ELECTRONICS", accountIdResolver.resolveCategoryId(radio));
        assertEquals("FURNITURE & FITTINGS", accountIdResolver.resolveCategoryId(chair));
        assertEquals("COMPUTERS", accountIdResolver.resolveCategoryId(lenovo));
    }
}