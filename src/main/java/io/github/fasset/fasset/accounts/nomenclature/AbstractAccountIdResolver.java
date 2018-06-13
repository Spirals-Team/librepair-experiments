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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <br>Account naming policy is dirt simple. The account is simply the name of the category. There are
 * however embedded hierarchies which are partitioned by variables like, service outlet, currency
 * account type and so on and so forth.
 * <br> This class provides common understanding for instance on implementation and meaning of currency
 * code inclusion into the account number id sequence. That does not need to go into so many types of
 * implementation.
 * <br> It also provides a way to block methods not needed in the subclass, for instance we do not
 * need a contra-account for credit account, so we simply implement it here and throw an unsupported operation
 * exception
 */
public abstract class AbstractAccountIdResolver implements AccountIdResolver {

    private static final Logger log = LoggerFactory.getLogger(AbstractAccountIdResolver.class);

    final AccountIdService accountIdService;

    AbstractAccountIdResolver(AccountIdService accountIdService) {
        this.accountIdService = accountIdService;
    }


    /**
     * The category is of a lower hierarchy than an account yet for the account to be representative
     * of fixed assets comprehensively this out to be mandated as part of the account nomenclature or at least
     * as a field in an account that can be tracked
     *
     * @param fixedAsset For which we need category nomenclature
     * @return The nomenclature of the category
     */
    @Override
    public String resolveCategoryId(FixedAsset fixedAsset) {
        return accountName(fixedAsset);
    }

    public abstract String accountName(FixedAsset fixedAsset);

    /**
     * Resolve the name of a Contra account for a main account used for tracking the asset.
     *
     * @param fixedAsset The asset for which we seek an account to track financially
     * @return The name of the contra account
     * @Deprecated relevance
     */
    @Override
    public String resolveContraAccountId(FixedAsset fixedAsset) {

        throw new UnsupportedOperationException("This entry does not need a contra account");

    }
}
