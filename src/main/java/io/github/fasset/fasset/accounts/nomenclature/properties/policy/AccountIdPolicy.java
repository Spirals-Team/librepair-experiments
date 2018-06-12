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

/**
 * This interface acts as building blocks to how assets are identified in front-facing systems using simple structure
 * which is data that has to be extracted from the asset attributes themselves.
 * <br> For instance instead of inquiring the currency code to be given to an account number to track a given asset, instead
 * we are asking what currency code will be given to an account number that identifies an account of an asset whose monetary
 * attributes are denominated in currency whose ISO 4217 code is 'KES' or 'USD'.
 * <br> This means that this interface can be reused for other things and not necessarily assets, since the queries are less abstract
 * <br> Also this means that the accountIdPolicy which should exist in the business domain and currently only exists in my
 * mind as the developer can be changed changed from a single point being an implementation of this policy.
 * <br> The current implementation will be known as {@code AccountIdPolicyVersion1}. Upon emergence of more light this might
 * be scrapped and be implemented as {@code AccountIdPolicyVersion2}. The 'scrapping' is a matter of changing default implementation
 * in the dependency container.
 * <br> One thing is not likely to change though. The fact that most things are assessed, depreciated, analysed, classified from
 * the asset category perspective. Depreciation, reporting, verification, revaluation and maintenance policies are bound to
 * be formulated and implemented with regards to an assets category.
 * <br> Even as a developer well rehearsed with the problem space I do have or think to have attained all the light to clearly
 * perceive the problem space and its attributes, but what I do think is that the business domain can be for the yet unforeseeable
 * future, be bent to appreciate assets from the category perspective. Bigger companies would not lack the service-outlet guys,
 * the currency guy, year of manufacture and year of acquisition guys. But that would be beyond the reach of this application.
 * Such guys should see me on the side and we can talk about how much value we need to give to my time, because such flexibility
 * would require time to implement. Or we can still try and do in this rapid development cycle, without shooting our hypothetical foot
 * and yet this is without guarantees of actually not blowing the whole damn foot in the process. One such scenario would be in
 * a distributed environment ... oh! Here's another one, micro-services architecture. If you would think to implement mutating
 * configurations in these two you are destined to experience pain the heights of which you have not experienced before.
 */
public interface AccountIdPolicy {

    /**
     * Using the currency code used in the fixed assets value at cost, the currency's ISO 4217 code, this method generates
     * the unique code to be used in the account number sequence after the service outlet code
     *
     * @param currencyCode ISO 4217 currency code used to retrieve account number sequence code
     * @return Account number sequence code to follow the service outlet nomenclature
     */
    String currencyCode(String currencyCode);

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
    String generalLedgerCode(TransactionType transactionType, AccountSide accountSide, String category);

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
    String accountPlaceHolder(TransactionType transactionType, AccountSide accountSide, String category);

    /**
     * @param transactionType Type of transaction Enum
     * @param accountSide     Enum shows whether we are posting on the CREDIT side or the DEBIT side
     * @param category        of the asset for which we seek transaction account name
     * @return Name of the account
     */
    String accountName(TransactionType transactionType, AccountSide accountSide, String category);

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
    Appendable accountNamePrefix(TransactionType transaction, AccountSide accountSide, String category);

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
    CharSequence appendant(TransactionType transaction, AccountSide accountSide);
}
