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
package io.github.fasset.fasset.accounts;

import io.github.fasset.fasset.accounts.nomenclature.AccountIdResolver;
import io.github.fasset.fasset.book.keeper.Account;
import io.github.fasset.fasset.book.keeper.util.UnEnteredDetailsException;
import io.github.fasset.fasset.model.FixedAsset;
import org.javamoney.moneta.Money;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.time.LocalDate;

import static io.github.fasset.fasset.book.keeper.AccountAttribute.ACCOUNT_SCHEME;
import static io.github.fasset.fasset.book.keeper.AccountAttribute.ACCOUNT_SUB_TYPE;
import static io.github.fasset.fasset.book.keeper.AccountAttribute.ACCOUNT_TYPE;
import static io.github.fasset.fasset.book.keeper.AccountAttribute.CATEGORY;
import static io.github.fasset.fasset.book.keeper.AccountAttribute.SERVICE_OUTLET;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.when;

public class AcquisitionAccountResolverTest {

    private final static FixedAsset radio = new FixedAsset("Radio", Money.of(200, "KES"), "ELECTRONICS", "001", LocalDate.of(2018, 2, 5), "abc01", Money.of(9.5, "KES"));
    private final static FixedAsset lenovo = new FixedAsset("Lenovo IDP110", Money.of(5600, "KES"), "COMPUTERS", "987", LocalDate.of(2018, 2, 13), "abc02", Money.of(13.42, "KES"));
    private final static FixedAsset chair = new FixedAsset("Chair", Money.of(156, "KES"), "FURNITURE", "010", LocalDate.of(2018, 1, 13), "abc03", Money.of(19.24, "KES"));
    private AcquisitionAccountResolver acquisitionAccountResolver;

    @Before
    public void setUp() throws Exception {

        AccountIdResolver accountIdResolver = Mockito.mock(AccountIdResolver.class);

        AccountIdResolver creditAccountIDResolver = Mockito.mock(AccountIdResolver.class);

        when(creditAccountIDResolver.accountName(radio)).thenReturn("Sundry Creditors Account");
        when(creditAccountIDResolver.accountName(lenovo)).thenReturn("Sundry Creditors Account");
        when(creditAccountIDResolver.accountName(chair)).thenReturn("Sundry Creditors Account");
        when(creditAccountIDResolver.accountNumber(radio)).thenReturn("0230010051011");
        when(creditAccountIDResolver.accountNumber(lenovo)).thenReturn("0140010051011");
        when(creditAccountIDResolver.accountNumber(chair)).thenReturn("0180010051011");
        when(creditAccountIDResolver.resolveCategoryId(radio)).thenReturn("ELECTRONICS");
        when(creditAccountIDResolver.resolveCategoryId(lenovo)).thenReturn("COMPUTERS");
        when(creditAccountIDResolver.resolveCategoryId(chair)).thenReturn("FURNITURE");
        when(creditAccountIDResolver.generalLedgerName(chair)).thenReturn("FURNITURE");
        when(creditAccountIDResolver.generalLedgerName(lenovo)).thenReturn("COMPUTERS");
        when(creditAccountIDResolver.generalLedgerName(radio)).thenReturn("ELECTRONICS");

        acquisitionAccountResolver = new AcquisitionAccountResolver(accountIdResolver, creditAccountIDResolver);

        when(accountIdResolver.resolveCategoryId(lenovo)).thenReturn("COMPUTERS");
        when(accountIdResolver.resolveContraAccountId(lenovo)).thenReturn("Accumulated Depreciation on Computers");
        when(accountIdResolver.generalLedgerName(lenovo)).thenReturn("1025648");

        when(accountIdResolver.resolveCategoryId(radio)).thenReturn("Electronics");
        when(accountIdResolver.resolveContraAccountId(radio)).thenReturn("Accumulated Depreciation on Electronics");
        when(accountIdResolver.generalLedgerName(radio)).thenReturn("1025649");

        when(accountIdResolver.resolveCategoryId(chair)).thenReturn("Furniture");
        when(accountIdResolver.resolveContraAccountId(chair)).thenReturn("Accumulated Depreciation on Furniture");
        when(accountIdResolver.generalLedgerName(chair)).thenReturn("1025650");


    }

    @Test
    public void getAcquisitionDebitAccount() throws UnEnteredDetailsException {

        Account computers = acquisitionAccountResolver.resolveDebitAccount(lenovo);
        Account sundryCreditors = acquisitionAccountResolver.resolveCreditAccount(lenovo);

        Account electronics = acquisitionAccountResolver.resolveDebitAccount(radio);
        Account sundryCreditors1 = acquisitionAccountResolver.resolveCreditAccount(radio);

        Account furniture = acquisitionAccountResolver.resolveDebitAccount(chair);
        Account sundryCreditors2 = acquisitionAccountResolver.resolveCreditAccount(chair);


        assertNotEquals(computers, sundryCreditors);

        /*This sameness test is going to interfere with the undefined a/c hieracrchy problem*/
        assertNotEquals(sundryCreditors, sundryCreditors1);
        assertNotEquals(sundryCreditors, sundryCreditors2);
        assertNotEquals(sundryCreditors1, sundryCreditors2);

        assertEquals("Asset", computers.getAttribute(ACCOUNT_TYPE));
        assertEquals("Fixed Assets", computers.getAttribute(ACCOUNT_SCHEME));
        assertEquals(lenovo.getCategory(), computers.getAttribute(CATEGORY));
        assertEquals("Non Current Asset", computers.getAttribute(ACCOUNT_SUB_TYPE));
        assertEquals(lenovo.getSolId(), computers.getAttribute(SERVICE_OUTLET));
    }

    @Test
    public void getAcquisitionCreditAccount() throws UnEnteredDetailsException {

        Account computers = acquisitionAccountResolver.resolveDebitAccount(lenovo);
        Account sundryCreditors = acquisitionAccountResolver.resolveCreditAccount(lenovo);

        assertNotEquals(sundryCreditors, computers);
        assertEquals("Liability", sundryCreditors.getAttribute(ACCOUNT_TYPE));
        assertEquals("Sundry Creditors", sundryCreditors.getAttribute(ACCOUNT_SCHEME));
        assertEquals(lenovo.getCategory(), sundryCreditors.getAttribute(CATEGORY));
        assertEquals("Current Liability", sundryCreditors.getAttribute(ACCOUNT_SUB_TYPE));
        assertEquals(lenovo.getSolId(), sundryCreditors.getAttribute(SERVICE_OUTLET));
    }
}