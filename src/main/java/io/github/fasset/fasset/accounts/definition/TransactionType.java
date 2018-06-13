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
package io.github.fasset.fasset.accounts.definition;

/**
 * These  constants the types of transactions possible with fixed assets and their register
 */
public enum TransactionType {

    /**
     * These be the type of transaction where assets are purchased. It is expected at a minimum that 2 accounts
     * are involved, named the Fixed Asset's account to be debit, and the sundry creditor's account. If the fixed asset
     * account is to be maintained at cost, then a contra account is also opened that will track the accrued depreciation
     * on the same account.
     */
    ACQUISITION {
        /**
         * Returns the name of this enum constant, as contained in the
         * declaration.  This method may be overridden, though it typically
         * isn't necessary or desirable.  An enum type should override this
         * method when a more "programmer-friendly" string form exists.
         *
         * @return the name of this enum constant
         */
        @Override
        public String toString() {
            return "acquisition";
        }
    },

    /**
     * In this transaction we are simply recognizing existing assets, taking their costs to debit the fixed asset account,
     * against the sundry creditor's account. The accumulated depreciation should also be posted with difference the between
     * the asset's cost and the asset's net book value, against the depreciation account
     */
    RECOGNITION,

    /**
     * The transaction involves the posting of credits in the asset cost accounts (if the asset is maintained at the revalued cost),
     * or the accumulated depreciation account if the asset is maintained in the register at cost, and the debits are posted in
     * the depreciation expense account
     */
    DEPRECIATION,

    /**
     * This involves debits in the accumulated depreciation account (if the asset is maintained in the register at cost) with the
     * full amount of depreciation, a credit in the asset's account with the full cost (if the asset is maintained at cost), or with
     * the net book value if the asset is maintained at revalued cost. The amount of proceeds is recognized in the cash account by
     * a debit with the amount received from the sale proceeds, and then the imbalance in the transaction is posted either a debit in
     * the loss on disposal, of a credit in the profit on disposal of the category of the asset
     */
    DISPOSAL,

    /**
     * TBD
     */
    REVALUATION,

    /**
     * The developer hopes that the implementation of the REVALUATION mode will be dynamic  enough that this mode will not be
     * necessary
     */
    DEVALUATION,

    /**
     * This is a moving concept as transfer could mean amendment of some wrongly assigned category into the appropriate category, or
     * transfer from some service outlet to another service outlet. In the later the amounts accumulated in depreciation are simply
     * reassigned to the appropriate service outlet, while in the former the amounts of computed depreciation have to be revalued afresh
     * across the timeline of the assets existence. Any amounts of manual revaluation are reassigned without reassessment since it is
     * assumed that the assessment was correct in the first place. If this condition cannot be guaranteed, the next option might be disposal
     * and re acquisition of the same asset.
     */
    TRANSFER,

}
