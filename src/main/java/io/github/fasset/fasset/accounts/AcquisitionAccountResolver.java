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
import io.github.fasset.fasset.model.FixedAsset;
import io.github.ghacupha.time.point.SimpleDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Currency;

import static io.github.fasset.fasset.book.keeper.AccountAttribute.ACCOUNT_SCHEME;
import static io.github.fasset.fasset.book.keeper.AccountAttribute.ACCOUNT_SUB_TYPE;
import static io.github.fasset.fasset.book.keeper.AccountAttribute.ACCOUNT_TYPE;
import static io.github.fasset.fasset.book.keeper.AccountAttribute.CATEGORY;
import static io.github.fasset.fasset.book.keeper.AccountAttribute.CONTRA_ACCOUNT;
import static io.github.fasset.fasset.book.keeper.AccountAttribute.GENERAL_LEDGER;
import static io.github.fasset.fasset.book.keeper.AccountAttribute.SERVICE_OUTLET;
import static io.github.fasset.fasset.book.keeper.balance.AccountSide.CREDIT;
import static io.github.fasset.fasset.book.keeper.balance.AccountSide.DEBIT;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * This object can dictate appropriate account for tracking a given {@code FixedAsset} item. This is obtained from a manually configured
 * properties file. While the account must be appropriate to the circumstances of the transaction (i.e. whether acquisition, transfer,disposal)
 * the account must match the fixedAsset's currency amount, the service outlet, and the fixedAsset's category. Ideally the account names
 * are pre-stated in a properties file.
 * <br> But that is to be implemented another time, since implementing that today messes up the
 * container configuration which recognizes this object as a singleton called "chartOfAccounts".
 * The Object must lookup whether the account exists before generating a new Account for the fixedAsset. Which bring a new problem,
 * the resolution of the opening date. Will it be pegged to the purchase date of the asset?
 */
@Component("acquisitionAccountResolver")
public class AcquisitionAccountResolver implements AccountResolver {

    private static final org.slf4j.Logger log = getLogger(AcquisitionAccountResolver.class);

    private AccountIdResolver debitAccountIdResolver;
    private AccountIdResolver creditAccountIDResolver;

    @Autowired
    public AcquisitionAccountResolver(@Qualifier("debitAccountIdResolver") AccountIdResolver debitAccountIdResolver, @Qualifier("creditAccountIDResolver") AccountIdResolver creditAccountIDResolver) {
        this.debitAccountIdResolver = debitAccountIdResolver;
        this.creditAccountIDResolver = creditAccountIDResolver;
    }

    /**
     * Generates appropriate Account for the asset passed in the parameter, when we are posting Acquisition
     *
     * @param fixedAsset for which we seek an appropriate Account
     * @return Account appropriate for the recording of transaction for the parameter
     * fixedAsset
     */
    public Account resolveDebitAccount(FixedAsset fixedAsset) {

        log.debug("Getting acquisition debit account for asset : {}", fixedAsset.getAssetDescription());

        Account debitAccount = new Account(debitAccountIdResolver.accountName(fixedAsset), debitAccountIdResolver.accountNumber(fixedAsset), DEBIT,
            Currency.getInstance(fixedAsset.getPurchaseCost().getCurrency().getCurrencyCode()), SimpleDate.ofLocal(fixedAsset.getPurchaseDate()));

        debitAccount.addAttribute(CONTRA_ACCOUNT, debitAccountIdResolver.resolveContraAccountId(fixedAsset));
        debitAccount.addAttribute(GENERAL_LEDGER, debitAccountIdResolver.generalLedgerName(fixedAsset));
        debitAccount.addAttribute(ACCOUNT_TYPE, "Asset");
        debitAccount.addAttribute(ACCOUNT_SUB_TYPE, "Non Current Asset");
        debitAccount.addAttribute(ACCOUNT_SCHEME, "Fixed Assets");
        debitAccount.addAttribute(CATEGORY, debitAccountIdResolver.resolveCategoryId(fixedAsset));
        debitAccount.addAttribute(SERVICE_OUTLET, fixedAsset.getSolId());

        return debitAccount;
    }

    /**
     * Generates appropriate credit Account for the asset passed in the parameter, when we are posting Acquisition
     *
     * @param fixedAsset for which we seek an appropriate Account
     * @return Account appropriate for the recording of transaction for the parameter
     * fixedAsset
     */
    public Account resolveCreditAccount(FixedAsset fixedAsset) {

        log.debug("Getting acquisition credit account for asset : {}", fixedAsset.getAssetDescription());

        Account creditAccount = new Account(creditAccountIDResolver.accountName(fixedAsset), creditAccountIDResolver.accountNumber(fixedAsset), CREDIT,
            Currency.getInstance(fixedAsset.getPurchaseCost().getCurrency().getCurrencyCode()), SimpleDate.ofLocal(fixedAsset.getPurchaseDate()));

        creditAccount.addAttribute(GENERAL_LEDGER, creditAccountIDResolver.generalLedgerName(fixedAsset));
        creditAccount.addAttribute(ACCOUNT_TYPE, "Liability"); //TBD on this
        creditAccount.addAttribute(ACCOUNT_SUB_TYPE, "Current Liability");
        creditAccount.addAttribute(ACCOUNT_SCHEME, "Sundry Creditors");
        creditAccount.addAttribute(CATEGORY, creditAccountIDResolver.resolveCategoryId(fixedAsset));
        creditAccount.addAttribute(SERVICE_OUTLET, fixedAsset.getSolId());

        return creditAccount;
    }
}
