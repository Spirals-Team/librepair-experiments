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
package io.github.fasset.fasset.book.keeper.service;

import io.github.fasset.fasset.book.Transaction;
import io.github.fasset.fasset.book.keeper.Account;
import io.github.fasset.fasset.book.keeper.AccountingTransaction;
import io.github.fasset.fasset.book.keeper.balance.AccountBalance;
import io.github.fasset.fasset.book.keeper.balance.AccountSide;
import io.github.ghacupha.cash.Cash;
import io.github.ghacupha.cash.HardCash;
import io.github.ghacupha.time.point.SimpleDate;
import io.github.ghacupha.time.point.TimePoint;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Currency;

import static io.github.fasset.fasset.book.keeper.balance.AccountSide.CREDIT;
import static io.github.fasset.fasset.book.keeper.balance.AccountSide.DEBIT;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AccountServiceStressTestIT {

    private static final Logger log = LoggerFactory.getLogger(AccountServiceStressTestIT.class);

    private static final Currency KES = Currency.getInstance("KES");

    @Qualifier("accountService")
    @Autowired
    private AccountService persistentAccountService;

    private TimePoint openingDate = SimpleDate.on(2018, 2, 1);

    private Account testAccount;

    private Account computers;
    private Account sundryDebtorsAccount;
    private Account cashAccount;
    private Account taxAccount;

    private int computersId;
    private int sundryDebtorsId;
    private int cashAccountId;
    private int taxAccountId;

    private Transaction buysChairs;

    private static Cash shilling(double amount) {
        return HardCash.shilling(amount);
    }

    private static AccountBalance balance(Cash ammount, AccountSide accountSide) {

        return AccountBalance.newBalance(ammount, accountSide);
    }

    @Before
    public void setUp() throws Exception {

        buysChairs = AccountingTransaction.create("Buy chairs", SimpleDate.of(2018, 4, 20), KES);

        testAccount = new Account("Computers", "001", DEBIT, KES, openingDate);

        computers = persistentAccountService.saveAccount(testAccount);
        sundryDebtorsAccount = persistentAccountService.saveAccount(new Account("Sundry Debtor's Account", "002", DEBIT, KES, openingDate));
        cashAccount = persistentAccountService.saveAccount(new Account("Cash Account", "003", CREDIT, KES, openingDate));
        taxAccount = persistentAccountService.saveAccount(new Account("Vat Account", "211", CREDIT, KES, SimpleDate.of(2018, 4, 20)));

        computersId = computers.getId();
        sundryDebtorsId = sundryDebtorsAccount.getId();
        cashAccountId = cashAccount.getId();
        taxAccountId = taxAccount.getId();

    }

    @Test
    public void entriesRecallAfterAcSideReversal() throws Exception {

        //reQuisitionForChairs();

        log.info("Loading 100000 entries through transaction");
        for (int i = 0; i < 10000; i++) {
            Transaction temp = AccountingTransaction.create("Purchase Computers", SimpleDate.of(2018, 3, 31), KES);
            temp.addEntry(DEBIT, shilling(350.23), computers, "Invoice100");
            temp.addEntry(CREDIT, shilling(150.23), sundryDebtorsAccount, "Accounting for expense Id 100");
            temp.addEntry(CREDIT, shilling(200), cashAccount, "Cash IFO Computer Suppliers");
            temp.post();
        }

        log.info("Now checking account balances posted....");
        assertEquals(balance(shilling(3502300.00), DEBIT), persistentAccountService.fetchAccountById(computersId).balance(SimpleDate.today()));
        assertEquals(balance(shilling(1502300.00), CREDIT), persistentAccountService.fetchAccountById(sundryDebtorsId).balance(SimpleDate.today()));
        assertEquals(balance(shilling(2000000), CREDIT), persistentAccountService.fetchAccountById(cashAccountId).balance(SimpleDate.today()));
    }

    private void reQuisitionForChairs() throws Exception {
        buysChairs.addEntry(DEBIT, shilling(480.23), sundryDebtorsAccount, "Furniture Suppliers invoice 218");
        buysChairs.addEntry(CREDIT, shilling(20.23), taxAccount, "16% Vat for furniture suppliers");
        buysChairs.addEntry(CREDIT, shilling(460.00), cashAccount, "Cash IFO Furniture Suppliers");

        buysChairs.post();
    }
}
