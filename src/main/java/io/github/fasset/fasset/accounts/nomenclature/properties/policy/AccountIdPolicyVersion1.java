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

import io.github.fasset.fasset.accounts.definition.TransactionType;
import io.github.fasset.fasset.book.keeper.balance.AccountSide;
import io.github.fasset.fasset.kernel.util.PropertiesUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

import static io.github.fasset.fasset.accounts.definition.AccountNumberSegment.GENERAL_LEDGER_CODE;
import static io.github.fasset.fasset.accounts.definition.AccountNumberSegment.PLACE_HOLDER;
import static io.github.fasset.fasset.accounts.nomenclature.properties.policy.KeyFormatter.formatKey;

/**
 * Version1 implementation of the {@link AccountIdPolicy}.
 * The most important client for this objects is the {@code FileAccountIdService}, since this class depends on the
 * configurations written down in a properties file knows as the {@code config/account-id.properties}
 */
public class AccountIdPolicyVersion1 implements AccountIdPolicy {

    private static final Logger log = LoggerFactory.getLogger(AccountIdPolicyVersion1.class);

    private final Properties accountConfigProperties;

    public AccountIdPolicyVersion1(String accountIdProperties) {
        String accountProperties = accountIdProperties == null ? "account-id" : accountIdProperties;
        this.accountConfigProperties = PropertiesUtils.fetchConfigProperties(accountProperties);
    }

    /**
     * Using the currency code used in the fixed assets value at cost, the currency's ISO 4217 code, this method generates
     * the unique code to be used in the account number sequence after the service outlet code
     *
     * @param iso4217Code ISO 4217 currency code used to retrieve account number sequence code
     * @return Account number sequence code to follow the service outlet nomenclature
     */
    @Override
    public String currencyCode(String iso4217Code) {

        log.trace("Fetching currency code for ISO4217 currency code provided as : {}", iso4217Code);

        String code = accountConfigProperties.getProperty(iso4217Code);

        log.trace("Code for ISO4217 currency code : {} resolved as {}", iso4217Code, code);

        return code;
    }

    /**
     * Using the category of an asset this method returns the generic nomenclature code for the category, which in
     * accordance to the Account nomenclature and hierarchy policy version 1.0 follows after the currency
     * code in the account number sequence
     *
     * @param transactionType This is the type of fixed asset transaction and could ACQUISITION, DEPRECIATION among others
     * @param accountSide     The direction which we are posting. This could be DEBIT or CREDIT
     * @param category        of the asset for which we need a category code
     * @return The category code to be added to the account number sequence after the currency code
     */
    @Override
    public String generalLedgerCode(TransactionType transactionType, AccountSide accountSide, String category) {

        log.debug("Fetching account ledger code transaction: {}, of the category {}, posting on the {} side", transactionType, category, accountSide);

        String key = formatKey(category, transactionType, accountSide, GENERAL_LEDGER_CODE); // e.g "sundry.acquisition. credit.general-ledger-code"

        log.debug("Fetching generalLedgerCode for an account whose key is encoded as {}", key);

        String glcode = accountConfigProperties.getProperty(key);

        log.debug("GL code for posting {} for the category {} on the {} side, resolved as {}", transactionType, category, accountSide, glcode);

        return glcode;
    }

    /**
     * Using the provided category of an asset this method returns a specific nomenclature code for the
     * category. This is the code segment that typically follows the general ledger code in the
     * account number sequence
     *
     * @param transactionType The type of fixed asset transaction
     * @param accountSide     Enum shows whether we are posting on the CREDIT side or the DEBIT side
     * @param category        of the Asset for which we need a placeholder
     * @return String GL Id to be used for credit transactions
     */
    @Override
    public String accountPlaceHolder(TransactionType transactionType, AccountSide accountSide, String category) {

        log.debug("Resolving account placeHolder for {} transaction, posting on the {} side for {} category", transactionType, accountSide, category);

        String key = KeyFormatter.formatKey(category, transactionType, accountSide, PLACE_HOLDER);

        log.debug("Resolving placeHolder for the key, {}", key);

        return accountConfigProperties.getProperty(key);
    }

    /**
     * @param transactionType Type of transaction Enum
     * @param accountSide     Enum shows whether we are posting on the CREDIT side or the DEBIT side
     * @param category        of the asset for which we seek transaction account name
     * @return Name of the account
     */
    @Override
    public String accountName(TransactionType transactionType, AccountSide accountSide, String category) {

        log.debug("Resolving credit posting account for transaction type {}, for asset : {}", transactionType, category);

        String key = formatKey(category, transactionType, accountSide);

        log.debug("Fetching account label for the key: {}", key);

        String accountLabel = accountConfigProperties.getProperty(key);//e.g computers.acquisition.credit

        log.debug("Credit posting account label for {} of the category {} resolved to be {}, using the key: {}", transactionType, category, accountLabel, key);

        return accountLabel;
    }

    /**
     * This method returns a string of pattern to be prefixed to an account name as  per query. It is
     * assumed for instance when such a query is called on an asset account during an acquisition, that
     * we are hoping to find the configure prefix that would go something like "Accumulated Depreciation
     * on Computers" for instance.
     * <br> Please note that this method was not created to supplant the account number sequences and
     * is not even expected to be used so many times. In fact where the asset manager is maintaining the
     * assets at their amortised value then this method will likely not be used at all.
     *
     * @param transaction Type of asset transaction  we are carrying out.
     * @param accountSide The account side to which we are posting the transaction for this part of the entry
     * @param category    of the fixed asset being transacted
     * @return The appendable prefix name to be added to the account name
     */
    @Override
    public Appendable accountNamePrefix(TransactionType transaction, AccountSide accountSide, String category) {
        return null;
    }

    /**
     * This is a character value that can be used as a separator between the appended prefix or suffix
     * where one might be required at an application wide level. For instance the contra account for
     * computers account might be called "Accumulated Depreciation - Computers", where the hyphen is
     * supplied by this method. Using the configuration you have whatever constructs the user has desired
     *
     * @param transaction Type of asset transaction  we are carrying out.
     * @param accountSide The account side to which we are posting the transaction for this part of the entry
     * @return The character to be used while appending account names to prefix or suffix
     */
    @Override
    public CharSequence appendant(TransactionType transaction, AccountSide accountSide) {
        return null;
    }

    /**
     * This methods combines the account segments combining them into an account number sequence which is used
     * application wide.
     * <br> The existence of this method makes it possible for the application to be configured to create
     * different forms of identifications for an account number for different purposes.
     * <br> We assume the following args are passed in the following order for this policy version
     *         <br>i.  sol id
     *         <br>ii. currency code
     *         <br>iii.general ledger
     *         <br>iv. placeholder
     *
     * @param accountSegments Parameters containing the various segments that form an account number
     * @return account number sequence
     */
    @Override
    public String accountNumberMotif(String... accountSegments) {

        // TODO make this appendages more dynamic
        StringBuilder sb = new StringBuilder()
            .append(accountSegments[0])
            .append(accountSegments[1])
            .append(accountSegments[2])
            .append(accountSegments[3]);

        return sb.toString();
    }
}
