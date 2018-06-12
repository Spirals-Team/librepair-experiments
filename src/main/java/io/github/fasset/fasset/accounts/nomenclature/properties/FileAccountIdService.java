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
package io.github.fasset.fasset.accounts.nomenclature.properties;

import io.github.fasset.fasset.accounts.definition.TransactionType;
import io.github.fasset.fasset.accounts.nomenclature.properties.policy.AccountIdPolicy;
import io.github.fasset.fasset.book.keeper.balance.AccountSide;
import io.github.fasset.fasset.model.FixedAsset;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Implements the {@code AccountIdService} using pre stated values in a properties which are managed in
 * an internal map
 * <br> This implementation provides identities for accounts various types of transactions, for instance:
 * <br> i. acquisition transactions,
 * <br>in this transaction it is expected that the debit account would be the asset account tracking its category,
 * and the credit account would be the sundry creditor's account. This being because tracking who exactly is paid
 * for this asset is beyond the scope of this application.
 * <br> One peculiar aspect of this implementation is that pre-configured properties are read from a properties
 * file. This is peculiar because this implementation will have siblings in future that could be reading configuration
 * properties from a json api over http, from a database which is configured at runtime or even from an excel file.
 * <br> Even though this is the case the underlying policy can be changed at compile time, should another be desired,
 * you just have to initialize this object with anything that implements the {@link AccountIdPolicy} interface in the
 * constructor.
 */
@Component("accountIdConfigurationPropertiesService")
public final class FileAccountIdService extends AbstractAccountIdService implements AccountIdService {

    private static final Logger log = getLogger(FileAccountIdService.class);

    public FileAccountIdService(AccountIdPolicy accountIdPolicy) {

        super(accountIdPolicy);
    }

    /**
     * Using the category of an asset this method returns the generic nomenclature code for the category, which in
     * accordance to the Account nomenclature and hierarchy policy version 1.0 follows after the currency
     * code in the account number sequence
     *
     * @param transactionType This is the type of fixed asset transaction and could ACQUISITION, DEPRECIATION among others
     * @param accountSide     The direction which we are posting. This could be DEBIT or CREDIT
     * @param fixedAsset      From which we inquire the category of the asset for which we need a category code
     * @return The category code to be added to the account number sequence after the currency code
     */
    @Override
    public String generalLedgerCode(TransactionType transactionType, AccountSide accountSide, FixedAsset fixedAsset) {

        log.debug("Fetching account ledger code transaction: {}, of asset {}, posting on the {} side", transactionType, fixedAsset, accountSide);

        return accountIdPolicy.generalLedgerCode(transactionType, accountSide, fixedAsset.getCategory());
    }

    /**
     * @param fixedAsset      for which seek an account placeHolder
     * @param accountSide     Whether we are posting on the debit side or the credit side
     * @param transactionType The type of transaction for the fixed asset
     * @return String GL Id to be used for credit transactions
     */
    @Override
    public String accountPlaceHolder(TransactionType transactionType, AccountSide accountSide, FixedAsset fixedAsset) {

        log.debug("Resolving credit posting account for transaction type {}, for asset : {}", transactionType, fixedAsset);

        return accountIdPolicy.accountPlaceHolder(transactionType, accountSide, fixedAsset.getCategory());
    }

    /**
     * @param accountSide     This enum denotes whether or not we are posting on the CREDIT or on the DEBIT side
     * @param transactionType Type of transaction Enum
     * @param fixedAsset      Asset for which we seek transaction account name
     * @return Name of the account
     */
    @Override
    public String accountName(TransactionType transactionType, AccountSide accountSide, FixedAsset fixedAsset) {

        log.debug("Resolving credit posting account for transaction type {}, for asset : {}", transactionType, fixedAsset);

        return accountIdPolicy.accountName(transactionType, accountSide, fixedAsset.getCategory());
    }

    /**
     * Resolves the name of the account to be used as a contra account in case the actual account  is
     * required to retain the original valuation for reporting purposes
     *
     * @param transaction The type of transaction associated with the initialization of a contra account
     * @param accountSide To which we are posting the transaction during initialization of a contra account
     * @param fixedAsset  Fixed asset which we are tracking by means of the accounting system
     * @return name of the contra account
     */
    @Override
    public String contraAccountName(TransactionType transaction, AccountSide accountSide, FixedAsset fixedAsset) {

        String contraAccount = "";

        try {
            contraAccount = String.valueOf(accountIdPolicy.accountNamePrefix(transaction, accountSide, fixedAsset.getCategory()).append(accountIdPolicy.appendant(transaction, accountSide))
                .append(accountIdPolicy.accountName(transaction, accountSide, fixedAsset.getCategory())));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return contraAccount;
    }
}
