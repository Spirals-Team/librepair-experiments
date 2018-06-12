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
package io.github.fasset.fasset.book.keeper;

import io.github.fasset.fasset.book.Transaction;
import io.github.fasset.fasset.book.keeper.balance.AccountBalance;
import io.github.fasset.fasset.book.keeper.util.ImmutableEntryException;
import io.github.fasset.fasset.book.keeper.util.MismatchedCurrencyException;
import io.github.fasset.fasset.book.keeper.util.UnableToPostException;
import io.github.ghacupha.time.point.SimpleDate;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Currency;

import static io.github.fasset.fasset.book.keeper.balance.AccountSide.CREDIT;
import static io.github.fasset.fasset.book.keeper.balance.AccountSide.DEBIT;
import static io.github.ghacupha.cash.HardCash.shilling;
import static org.junit.Assert.assertEquals;

public class AccountTransactionTest {

    private static final Logger log = LoggerFactory.getLogger(AccountTransactionTest.class);

    // Required for the reversal tests
    private Account advertisement = new Account("Advertisements", "5280", DEBIT, Currency.getInstance("KES"), SimpleDate.on(2017, 3, 31));
    private Account vat = new Account("VAT", "5281", CREDIT, Currency.getInstance("KES"), SimpleDate.on(2017, 3, 31));
    private Account chequeAccount = new Account("Cheque", "5282", CREDIT, Currency.getInstance("KES"), SimpleDate.on(2017, 3, 31));
    private Account edwinsAccount = new Account("Edwin Njeru", "40001", CREDIT, Currency.getInstance("KES"), SimpleDate.on(2017, 10, 5));


    @Test
    public void directedTransactionWorks() throws Exception, UnableToPostException, MismatchedCurrencyException, ImmutableEntryException {

        log.info("\n Testing if the transaction will work. First we create the pay for bills Transaction, using period 2017-11-2, and currency KES");
        Transaction payForBillBoards = new AccountingTransaction("BillboardsPayment", new SimpleDate(2017, 11, 2), Currency.getInstance("KES"));

        log.info("\n Done. We DEBIT the Advertisement account, and credit the VAT and Banker's Cheque accounts....");
        payForBillBoards.addEntry(DEBIT, shilling(200), advertisement, "Billboards ltd inv 10");
        payForBillBoards.addEntry(CREDIT, shilling(32), vat, "VAT for billBoards");
        payForBillBoards.addEntry(CREDIT, shilling(168), chequeAccount, "CHQ IFO Billboards Ltd");

        // non-posted
        log.info("\n Ok so we have not yet posted the transaction but we want to check if the balances have been effected into the 3 accounts");
        assertEquals(AccountBalance.newBalance(shilling(0), DEBIT), advertisement.balance(2018, 1, 3));
        assertEquals(AccountBalance.newBalance(shilling(0), CREDIT), vat.balance(2018, 1, 3));
        assertEquals(AccountBalance.newBalance(shilling(0), CREDIT), chequeAccount.balance(2018, 1, 3));

        // after posting
        log.info("\n Nothing? Good. So today lets post the transaction...");
        payForBillBoards.post();

        log.info("\n Posted. Done. Now lets check if the balances in the account reflect our intentions");
        assertEquals(AccountBalance.newBalance(shilling(200), DEBIT), advertisement.balance(2017, 11, 30));
        assertEquals(AccountBalance.newBalance(shilling(32), CREDIT), vat.balance(2017, 11, 30));
        assertEquals(AccountBalance.newBalance(shilling(168), CREDIT), chequeAccount.balance(2017, 11, 30));

        // Reimbursement Transaction
        log.info(
            "\n Alright today we gotta reimburse Edwin for the meeting expenses, when he met with the Billboard guys. We create the" + " reimbursement transaction, as of 2017-12-20 in currency KES");
        Transaction reimbursement = new AccountingTransaction("Edwin\'s reimbursement", new SimpleDate(2017, 12, 20), Currency.getInstance("KES"));

        log.info("\n Alright, all we gotta do is debit the advertisement account and credit Edwin's account...");
        reimbursement.addEntry(DEBIT, shilling(150), advertisement, "Reimburse Edwin For Meeting expenses with Billboard guys");
        reimbursement.addEntry(CREDIT, shilling(150), edwinsAccount, "Reimbursement for meeting expenses with billboard guys");

        // before posting
        log.info("\n Again, we are going to check if the system has inappropriately added money into Edwin's account before our explicit posting...");
        assertEquals(AccountBalance.newBalance(shilling(0), CREDIT), edwinsAccount.balance(2017, 12, 31));
        assertEquals(AccountBalance.newBalance(shilling(200), DEBIT), advertisement.balance(2017, 12, 31));
        assertEquals(AccountBalance.newBalance(shilling(32), CREDIT), vat.balance(2017, 12, 31));
        assertEquals(AccountBalance.newBalance(shilling(168), CREDIT), chequeAccount.balance(2017, 12, 31));

        // after posting the reimbursement
        log.info("Nothing there? Good. Let us post the reimbursement transaction...");
        reimbursement.post();
        log.info("Done. Now lets check if the account have the balances we expect. Edwin should have 150 joys, advertisements should be at 350...");
        assertEquals(AccountBalance.newBalance(shilling(150), CREDIT), edwinsAccount.balance(2018, 1, 31));
        assertEquals(AccountBalance.newBalance(shilling(350), DEBIT), advertisement.balance(2018, 1, 31));
        assertEquals(AccountBalance.newBalance(shilling(32), CREDIT), vat.balance(2018, 1, 31));
        assertEquals(AccountBalance.newBalance(shilling(168), CREDIT), chequeAccount.balance(2018, 1, 31));

        // what if the manager wants a previous position as at 5th November 2017
        log.info("\n Ok, this new strategy guy, for some obviously unholy reason wants to know our position as at 2017-11-05, time for some replay...");
        assertEquals(AccountBalance.newBalance(shilling(0), CREDIT), edwinsAccount.balance(2017, 11, 5));
        assertEquals(AccountBalance.newBalance(shilling(200), DEBIT), advertisement.balance(2017, 11, 5));
        assertEquals(AccountBalance.newBalance(shilling(32), CREDIT), vat.balance(2017, 11, 5));
        assertEquals(AccountBalance.newBalance(shilling(168), CREDIT), chequeAccount.balance(2017, 11, 5));

        // Someone screwed up the taxes, we have to reverse
        log.info("\n The internal audit reveals that someone had screwed up our taxes. Our taxes should be on the asset side by at least 13 joys. " +
            "Time to create some tax reversal transaction as at 2018-04-20, in KES as always");
        Transaction taxReversal = new AccountingTransaction("Tax reversal", SimpleDate.on(2018, 4, 20), Currency.getInstance("KES"));

        log.info("\n Adding entries to the tax reversal. We need to debit VAT by 45 joys and CREDIT advertisement expense by the same amount");
        taxReversal.addEntry(DEBIT, shilling(45), vat, "Reversal of Excess VAT");
        taxReversal.addEntry(CREDIT, shilling(45), advertisement, "Reversal of Excess VAT");

        log.info("\n We today post the tax reversal transaction");
        taxReversal.post();

        log.info("\n As per internal audit the VAT should be at 13 joys, asset side. Meaning the advertisement should be an expense of just 305");
        // balance after reversal transaction is posted...
        assertEquals(AccountBalance.newBalance(shilling(150), CREDIT), edwinsAccount.balance(2018, 4, 25));
        assertEquals(AccountBalance.newBalance(shilling(305), DEBIT), advertisement.balance(2018, 4, 25));
        assertEquals(AccountBalance.newBalance(shilling(13), DEBIT), vat.balance(2018, 4, 25));
        assertEquals(AccountBalance.newBalance(shilling(168), CREDIT), chequeAccount.balance(2018, 4, 25));
    }


}