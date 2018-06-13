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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import static io.github.fasset.fasset.accounts.definition.TransactionType.ACQUISITION;
import static io.github.fasset.fasset.book.keeper.balance.AccountSide.CREDIT;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * Implements the {@code CreditAccountIdResolver} interface for the transaction that involves acquisition of assets. In summary
 * one can expect that all acquisition transaction would be posted to the Sundry Creditor's account abstracting this application
 * from the knowledge of to which stakeholders settlements as this varies to a large degree depending on the domain of a given
 * tax authority, the same amount being split between vendor and tax authority in varying ratios. This is complicated situation to
 * master that requires more than one account, the effect of which could hardly be justified by the effort.
 *
 * @author edwin.njeru
 */
@Component("creditAccountIDResolver")
public class AcquisitionCreditAccountIdResolver extends AbstractAccountIdResolver implements AccountIdResolver {

    private static final Logger log = getLogger(AcquisitionCreditAccountIdResolver.class);

    @Autowired
    public AcquisitionCreditAccountIdResolver(@Qualifier("accountIdConfigurationPropertiesService") AccountIdService accountIdService) {
        super(accountIdService);
    }

    @Override
    public String accountName(FixedAsset fixedAsset) {

        log.debug("Resolving the account name for the account to be credited in the acquisition of asset : {}", fixedAsset.getAssetDescription());

        return accountIdService.accountName(ACQUISITION, CREDIT, fixedAsset);
    }

    @Override
    public String accountNumber(FixedAsset fixedAsset) {

        log.debug("Resolving the account number for the account to be credited in the acquisition of asset : {}", fixedAsset.getAssetDescription());

        //TODO Abstract account number motif
        return accountIdService.accountNumber(fixedAsset.getSolId(), currencyCode(fixedAsset), glCode(fixedAsset), placeHolder(fixedAsset));
    }

    private String currencyCode(FixedAsset fixedAsset) {

        return accountIdService.currencyCode(fixedAsset.getPurchaseCost().getCurrency().getCurrencyCode());
    }

    private String glCode(FixedAsset fixedAsset) {

        log.debug("Resolving the account GL Code for the account to be credited in the acquisition of asset ");

        return accountIdService.generalLedgerCode(ACQUISITION, CREDIT, fixedAsset);
    }

    private String placeHolder(FixedAsset fixedAsset) {

        log.debug("Resolving the account GL Code for the account to be credited in the acquisition of asset ");

        return accountIdService.accountPlaceHolder(ACQUISITION, CREDIT, fixedAsset);
    }

    /**
     * Resolves the name of the appropriate general ledger that out to be used in this case for the
     * fixed assets. The general ledger is taken to be one hierarchy higher than an account. This is
     * being done by the accountIDResolver as the general-ledger nomenclature has something to do with the nomenclature
     * of the account itself
     *
     * @param fixedAsset For which we need a general ledger
     * @return The ID of the general ledger
     */
    @Override
    public String generalLedgerName(FixedAsset fixedAsset) {

        //TODO return "SUNDRY CREDITORS from accountIdService
        return accountIdService.accountName(ACQUISITION, CREDIT, fixedAsset);
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

        log.debug("Resolving category id for asset: {}", fixedAsset);

        String categoryId = fixedAsset.getCategory().toUpperCase();

        log.debug("Category id for fixed asset : {} resolved as {}", fixedAsset, categoryId);

        // TODO expose this to the accountIdService
        return categoryId;
    }
}
